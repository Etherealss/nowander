package com.nowander.like.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nowander.common.pojo.po.LikeCount;
import com.nowander.common.pojo.po.LikeRecord;
import com.nowander.common.pojo.vo.Msg;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
public interface LikeService extends IService<LikeRecord> {
    /**
     * 点赞或者取消点赞
     * @param likeRecord
     * @param isLike
     * @return
     */
    Msg<Object> likeOrUnlike(LikeRecord likeRecord, Boolean isLike);

    /**
     * 是否已点赞
     * @param likeRecord
     * @return
     */
    Msg<Boolean> checkHasLiked(LikeRecord likeRecord);

    /**
     * 点赞数
     * @param likeCount
     * @return
     */
    Msg<LikeCount> getTotalLikeCount(LikeCount likeCount);

    /**
     * 持久化点赞记录
     */
    void saveRecentLikeRecord();

    /**
     * 持久化点赞数
     */
    void saveRecentLikeCount();
}
