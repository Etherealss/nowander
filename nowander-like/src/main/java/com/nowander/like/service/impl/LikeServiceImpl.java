package com.nowander.like.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.pojo.po.LikeCount;
import com.nowander.common.pojo.po.LikeRecord;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.like.cache.LikeCountCache;
import com.nowander.like.cache.LikeRecordCache;
import com.nowander.like.mapper.LikeCountMapper;
import com.nowander.like.mapper.LikeRecordMapper;
import com.nowander.like.pool.SaveLikeThreadPool;
import com.nowander.like.service.LikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@Service
public class LikeServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord> implements LikeService {

    private LikeRecordCache likeRecordCache;
    private LikeCountCache likeCountCache;
    private LikeRecordMapper likeRecordMapper;
    private LikeCountMapper likeCountMapper;
    private SaveLikeThreadPool threadPool;

    @Autowired
    public LikeServiceImpl(LikeRecordCache likeRecordCache, LikeCountCache likeCountCache, LikeRecordMapper likeRecordMapper, LikeCountMapper likeCountMapper, SaveLikeThreadPool threadPool) {
        this.likeRecordCache = likeRecordCache;
        this.likeCountCache = likeCountCache;
        this.likeRecordMapper = likeRecordMapper;
        this.likeCountMapper = likeCountMapper;
        this.threadPool = threadPool;
    }

    /**
     * 用于解决点赞时存在的竞态条件（先检查后执行）
     */
    private final Map<String, ReentrantLock> likeLocks = new ConcurrentHashMap<>();

    /**
     * 点赞 需要注意竞态条件
     * @param likeRecord
     * @param isLike
     * @return
     */
    @Override
    public Msg<Object> likeOrUnlike(LikeRecord likeRecord, Boolean isLike) {
        String key = likeRecord.getLikeRecordKey();
        ReentrantLock lock = null;
        if (likeLocks.containsKey(key)) {
            lock = likeLocks.get(key);
        }
        // 双重检查
        if (lock == null) {
            synchronized (likeLocks) {
                lock = likeLocks.get(key);
                if (lock == null) {
                    lock = new ReentrantLock();
                    likeLocks.put(key, lock);
                }
            }
        }
        // 点赞
        try {
            lock.wait();
            Msg<Boolean> hasLiked = this.checkHasLiked(likeRecord);
            if (hasLiked.getData().equals(isLike)) {
                return new Msg<>(ApiInfo.LIKE_DUPLICATE);
            }
            likeRecordCache.setRecentLike(likeRecord, isLike);
            // 只要将点赞记录放到likeRecordCache中，就不怕重复点赞了。所以就可以解锁了
        } catch (InterruptedException e) {
            log.warn("点赞锁中断！", e);
        } finally {
            lock.unlock();
        }
        likeCountCache.increRecentLike(likeRecord, isLike);
        return Msg.ok();
    }

    @Override
    public Msg<Boolean> checkHasLiked(LikeRecord likeRecord) {
        /*
        分三步：
        1. 查“最近点赞记录缓存”，检查用户最近是否有点赞或取消点赞的操作并且该操作记录尚未持久化到数据库
        2. 查“点赞记录缓存”
        3. 查数据库并更新“点赞记录缓存”
         */
        Boolean recentLike = likeRecordCache.getRecentLike(likeRecord);
        if (recentLike != null) {
            // 不为空，说明用户最近有点赞或取消点赞，但该数据尚未持久化到数据库
            return Msg.ok(recentLike);
        }

        Boolean like = likeRecordCache.getLike(likeRecord);
        if (like != null) {
            return Msg.ok(like);
        }
        // 从数据库查数据
        like = likeRecordMapper.countLikeRecord(likeRecord) == 1;
        // 更新缓存
        likeRecordCache.setLike(likeRecord, like);
        return Msg.ok(like);
    }

    @Override
    public Msg<LikeCount> getTotalLikeCount(LikeCount likeCount) {
        // 获取已有点赞数，如果缓存未命中则更新缓存
        Integer count = likeCountCache.getLikeCount(likeCount);
        if (count == null) {
            count = likeCountMapper.getLikeCount(likeCount);
            likeCount.setCount(count);
            likeCountCache.setLikeCount(likeCount);
        }

        // 获取最近新增点赞数
        Integer incre = likeCountCache.getRecentLikeCount(likeCount);
        if (incre != null) {
            likeCount.addCount(incre);
        }
        return Msg.ok(likeCount);
    }

    @Override
    public void saveRecentLikeRecord() {
        List<LikeRecord> allLikeRecord = likeRecordCache.getAndDelAllRecentLikeRecord();
        // 分为已点赞和未点赞两类
        Map<Boolean, List<LikeRecord>> group =
                allLikeRecord.stream().collect(Collectors.groupingBy(LikeRecord::getState));

        // TODO 这里用线程池将点赞和取消点赞的操作并发执行，不知道好不好
        // TODO 如果某个sql执行失败了，Service一回滚岂不是全部完蛋？redis的已经获取并删除了……
        List<LikeRecord> toSave = group.get(Boolean.TRUE);
        if (toSave != null && toSave.size() > 0) {
            threadPool.execute(() -> this.saveBatch(toSave));
        }
        List<LikeRecord> toDel = group.get(Boolean.FALSE);
        if (toDel != null && toDel.size() > 0) {
            threadPool.execute(() ->
                    toDel.forEach((likeRecord) -> likeRecordMapper.delete(likeRecord))
            );
        }
    }

    /**
     * 持久化点赞数
     * 先获取所有key，通过key从缓存中获取key-value，
     * 然后再forEach执行持久化操作，先获取已有点赞数（for update），再更新点赞数
     */
    @Override
    public void saveRecentLikeCount() {
        Set<String> keys = likeCountCache.getAllKeys();
        keys.stream().map(key -> likeCountCache.getAndDelRecentLikeCount(key)).forEach(likeCount -> {
            Integer count = likeCountMapper.getLikeCountForUpdate(likeCount);
            likeCount.addCount(count);
            likeCountMapper.updateLikeCount(likeCount);
        });
    }

}
