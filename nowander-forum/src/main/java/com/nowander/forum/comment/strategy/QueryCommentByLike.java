package com.nowander.forum.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.basesystem.user.SysUser;
import com.nowander.forum.comment.CommentEntity;
import com.nowander.infrastructure.enums.CommentConstants;
import com.nowander.infrastructure.enums.CommentParentType;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryCommentByLike extends QueryCommentStrategy {

    public QueryCommentByLike(int commentSize, int replySize, int curPage) {
        super(commentSize, replySize, curPage, false);
    }

    @Override
    protected IPage<CommentEntity> getComments(int parentId, CommentParentType parentIdType) {
        return commentMapper.getCommentByLike(
                new Page<>(curPage, commentSize), parentId, parentIdType.getCode());
    }

    @Override
    protected IPage<CommentEntity> getReplys(int commentId) {
        return commentMapper.getReplyByLike(
                new Page<>(0, replySize),
                commentId, CommentConstants.PARENT_TYPE_COMMENT);
    }
}
