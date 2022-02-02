package com.nowander.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@AllArgsConstructor
public class LikeServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord> implements LikeService {

    private LikeRecordCache likeRecordCache;
    private LikeCountCache likeCountCache;
    private LikeRecordMapper likeRecordMapper;
    private LikeCountMapper likeCountMapper;
    private SaveLikeThreadPool threadPool;

    @Override
    public Msg<Object> likeOrUnlike(LikeRecord likeRecord, Boolean isLike) {
        Msg<Boolean> hasLiked = checkHasLiked(likeRecord);
        // TODO 检查是否已点赞 存在竞态条件
        if (hasLiked.getData().equals(isLike)) {
            return new Msg<>(ApiInfo.LIKE_DUPLICATE);
        }
        likeRecordCache.setRecentLike(likeRecord, isLike);
        likeCountCache.likeOrUnlike(likeRecord, isLike);
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
    public void saveRecentLikeRecord() {
        List<LikeRecord> allLikeRecord = likeRecordCache.getAndDelAllRecentLikeRecord();
        // 分为已点赞和未点赞两类
        Map<Boolean, List<LikeRecord>> group =
                allLikeRecord.stream().collect(Collectors.groupingBy(LikeRecord::getState));

        // TODO 这里用线程池将点赞和取消点赞的操作并发执行，不知道好不好
        List<LikeRecord> toSave = group.get(Boolean.TRUE);
        if (toSave.size() > 0) {
            threadPool.execute(() -> this.saveBatch(toSave));
        }
        List<LikeRecord> toDel = group.get(Boolean.FALSE);
        if (toDel.size() > 0) {
            threadPool.execute(() ->
                    toDel.forEach((likeRecord) -> likeRecordMapper.delete(likeRecord))
            );
        }
    }

    @Override
    public void saveLikeCount() {
        // TODO 也可以先获取所有key，然后一边通过key获取value并持久化，一边删除缓存。
        List<LikeCount> allLikeCount = likeCountCache.getAndDelAllLikeCount();
        for (LikeCount likeCount : allLikeCount) {
            likeCountMapper.update(likeCount, new QueryWrapper<LikeCount>()
                    .eq("targetType", likeCount.getTargetType())
                    .eq("targetId", likeCount.getTargetId())
            );
        }
    }

}
