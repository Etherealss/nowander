package com.nowander.like.likecount;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-28
 */
@Mapper
@Repository
public interface LikeCountMapper extends BaseMapper<LikeCount> {
    /**
     * 获取点赞数，用于更新
     * @param likeCount
     */
    Integer getLikeCount(@Param("likeCount") LikeCount likeCount);
    /**
     * 获取点赞数，用于更新
     * @param likeCount
     */
    Integer getLikeCountForUpdate(@Param("likeCount") LikeCount likeCount);

    /**
     * 更新点赞数
     * @param likeCount
     */
    void updateLikeCount(@Param("likeCount") LikeCount likeCount);
}
