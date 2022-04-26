package com.nowander.forum.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<CommentEntity> {

    /**
     * 根据id和authorId删除
     * @param commentId
     * @param authorId
     * @return
     */
    int deleteByAuthor(@Param("commentId") Integer commentId,
                       @Param("authorId") Integer authorId);

    IPage<CommentEntity> getCommentByTime(IPage<CommentEntity> page,
                                          @Param("parentId") int parentId,
                                          @Param("parentType") int parentType);

    IPage<CommentEntity> getCommentByLike(IPage<CommentEntity> page,
                                          @Param("parentId") int parentId,
                                          @Param("parentType") int parentType);

    IPage<CommentEntity> getReplyByTime(IPage<CommentEntity> page,
                                        @Param("parentId") int parentId,
                                        @Param("parentType") int parentType);

    IPage<CommentEntity> getReplyByLike(IPage<CommentEntity> page,
                                        @Param("parentId") int parentId,
                                        @Param("parentType") int parentType);
}
