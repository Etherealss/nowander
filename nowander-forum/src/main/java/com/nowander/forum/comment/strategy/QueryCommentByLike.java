package com.nowander.forum.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.basesystem.user.SysUser;
import com.nowander.forum.comment.Comment;
import com.nowander.common.enums.CommentConstants;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryCommentByLike extends QueryCommentStrategy {

    public QueryCommentByLike(int commentSize, int replySize, int curPage, SysUser curSysUser) {
        super(commentSize, replySize, curPage, curSysUser, false);
    }

    @Override
    protected IPage<Comment> getComments(int parentId, int parentIdType) {
        return commentMapper.getCommentByLike(
                new Page<>(curPage, commentSize), parentId, parentIdType);
    }

    @Override
    protected IPage<Comment> getReplys(int commentId) {
        return commentMapper.getReplyByLike(
                new Page<>(0, replySize),
                commentId, CommentConstants.PARENT_TYPE_COMMENT);
    }
}
