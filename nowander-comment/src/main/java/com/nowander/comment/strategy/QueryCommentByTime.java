package com.nowander.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.comment.Comment;
import com.nowander.common.enums.CommentConstants;
import com.nowander.common.pojo.po.User;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryCommentByTime extends QueryCommentStrategy {

    public QueryCommentByTime(int commentSize, int replySize, int curPage, User curUser) {
        super(commentSize, replySize, curPage, curUser, false);
    }

    @Override
    protected IPage<Comment> getComments(int parentId, int parentIdType) {
        return commentMapper.getCommentByTime(
                new Page<>(curPage, commentSize), parentId, parentIdType);
    }

    @Override
    protected IPage<Comment> getReplys(int commentId) {
        return commentMapper.getReplyByTime(
                new Page<>(0, replySize),
                commentId, CommentConstants.PARENT_TYPE_COMMENT);
    }
}
