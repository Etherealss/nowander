package com.nowander.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.comment.pojo.po.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据id和authorId删除
     * @param commentId
     * @param authorId
     * @return
     */
    int deleteByAuthor(@Param("commentId") Integer commentId, @Param("authorId") Integer authorId);

    IPage<Comment> getCommentByTime(IPage<Comment> page, @Param("parentId") int parentId,
                                    @Param("parentType") int parentType);

    IPage<Comment> getCommentByLike(IPage<Comment> page, @Param("parentId") int parentId,
                                    @Param("parentType") int parentType);

    IPage<Comment> getReplyByTime(IPage<Comment> page, @Param("parentId") int parentId,
                                    @Param("parentType") int parentType);

    IPage<Comment> getReplyByLike(IPage<Comment> page, @Param("parentId") int parentId,
                                    @Param("parentType") int parentType);
}
