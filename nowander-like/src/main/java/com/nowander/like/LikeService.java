package com.nowander.like;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.basesystem.user.SysUser;
import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.enums.RedisKeyPrefix;
import com.nowander.infrastructure.exception.service.ExistException;
import com.nowander.like.likecount.LikeCount;
import com.nowander.like.likerecord.LikeRecord;
import com.nowander.like.likecount.LikeCountCache;
import com.nowander.like.likerecord.LikeRecordCache;
import com.nowander.like.likecount.LikeCountMapper;
import com.nowander.like.likerecord.LikeRecordCommand;
import com.nowander.like.likerecord.LikeRecordMapper;
import com.nowander.like.pool.SaveLikeThreadPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@Service
@AllArgsConstructor
public class LikeService extends ServiceImpl<LikeRecordMapper, LikeRecord> {

    private final LikeRecordCache likeRecordCache;
    private final LikeCountCache likeCountCache;
    private final LikeRecordMapper likeRecordMapper;
    private final LikeCountMapper likeCountMapper;
    private final SaveLikeThreadPool threadPool;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 点赞 需要注意竞态条件
     */
    public void likeOrUnlike(LikeRecordCommand likeRecordCommand, SysUser user) {
        LikeRecord likeRecord = likeRecordCommand.toEntity();
        likeRecord.setUserId(user.getId());
        Boolean isLike = likeRecordCommand.getIsLike();
        String key = likeRecord.getLikeRecordKey();
        String lock = RedisKeyPrefix.LOCK_LIKE + key;
        int retry = 10;
        while (retry > 0) {
            // redis锁
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lock, "1", 1000L, TimeUnit.MILLISECONDS);
            try {
                if (Boolean.TRUE.equals(locked)) {
                    // 点赞
                    Boolean hasLiked = this.checkHasLiked(likeRecord);
                    if (hasLiked.equals(isLike)) {
                        // 重复点赞
                        throw new ExistException(LikeRecord.class);
                    }
                    likeRecordCache.setRecentLike(likeRecord, isLike);
                    break;
                }
            } finally {
                // 只要将点赞记录放到likeRecordCache中，就不怕重复点赞了。所以就可以解锁了
                redisTemplate.opsForValue().getAndDelete(lock);
            }
            // 获取不到就重试
            retry--;
        }
        // 前面已经检查了重复点赞，可以避免刷赞
        likeCountCache.increRecentLike(likeRecord, isLike);
    }

    public Boolean checkHasLiked(LikeRecord likeRecord) {
        /*
        分三步：
        1. 查“最近点赞记录缓存”，检查用户最近是否有点赞或取消点赞的操作并且该操作记录尚未持久化到数据库
        2. 查“点赞记录缓存”
        3. 查数据库并更新“点赞记录缓存”
         */
        Boolean recentLike = likeRecordCache.getRecentLike(likeRecord);
        if (recentLike != null) {
            // 不为空，说明用户最近有点赞或取消点赞，但该数据尚未持久化到数据库
            return recentLike;
        }

        Boolean like = likeRecordCache.getLike(likeRecord);
        if (like != null) {
            return like;
        }
        // 从数据库查数据
        like = likeRecordMapper.countLikeRecord(likeRecord) == 1;
        // 更新缓存
        likeRecordCache.setLike(likeRecord, like);
        return like;
    }

    public LikeCount getTotalLikeCount(LikeCount likeCount) {
        // TODO 布隆过滤器
        // 获取已有点赞数，如果缓存未命中则更新缓存
        Integer count = likeCountCache.getLikeCount(likeCount);
        if (count == null) {
            count = likeCountMapper.getLikeCount(likeCount);
            likeCount.setCount(count == null ? 0 : count);
            likeCountCache.setLikeCount(likeCount);
        }

        // 获取最近新增点赞数
        Integer incre = likeCountCache.getRecentLikeCount(likeCount);
        if (incre != null) {
            likeCount.addCount(incre);
        }
        return likeCount;
    }

    public void saveRecentLikeRecord() {
        List<LikeRecord> allLikeRecord = likeRecordCache.getAndDelAllRecentLikeRecord();
        // 分为已点赞和未点赞两类
        Map<Boolean, List<LikeRecord>> group =
                allLikeRecord.stream().collect(Collectors.groupingBy(LikeRecord::getState));

        List<LikeRecord> toSave = group.get(Boolean.TRUE);
        if (toSave != null && toSave.size() > 0) {
            threadPool.execute(() -> this.saveBatch(toSave));
        }
        List<LikeRecord> toDel = group.get(Boolean.FALSE);
        if (toDel != null && toDel.size() > 0) {
            threadPool.execute(() ->
                    toDel.forEach(likeRecordMapper::delete)
            );
        }
    }

    /**
     * 持久化点赞数
     * 先获取所有key，通过key从缓存中获取key-value，
     * 然后再forEach执行持久化操作，先获取已有点赞数（for update），再更新点赞数
     */
    public void saveRecentLikeCount() {
        Set<String> keys = likeCountCache.getAllKeys();
        keys.stream()
                .map(likeCountCache::getAndDelRecentLikeCount)
                .filter(Objects::nonNull)
                .forEach(likeCount -> {
                    Integer count = likeCountMapper.getLikeCountForUpdate(likeCount);
                    likeCount.addCount(count);
                    likeCountMapper.updateLikeCount(likeCount);
                });
    }

}
