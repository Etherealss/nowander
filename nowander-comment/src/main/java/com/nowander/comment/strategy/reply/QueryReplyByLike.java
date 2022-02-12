package com.nowander.comment.strategy.reply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.comment.pojo.po.Comment;
import com.nowander.common.enums.CommentConstants;
import com.nowander.common.pojo.po.User;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryReplyByLike extends QueryReplyStrategy {
    public QueryReplyByLike(int replySize, int curPage, User curUser) {
        super(replySize, curPage, curUser, true);
    }

    @Override
    protected IPage<Comment> getReplys(int parentCommentId) {
        return commentMapper.getReplyByLike(new Page<>(curPage, replySize),
                parentCommentId, CommentConstants.PARENT_TYPE_COMMENT);
    }
}
