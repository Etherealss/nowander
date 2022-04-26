package com.nowander.forum.comment.reply.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.comment.CommentEntity;
import com.nowander.infrastructure.enums.CommentConstants;
import com.nowander.basesystem.user.SysUser;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryReplyByTime extends QueryReplyStrategy {
    public QueryReplyByTime(int replySize, int curPage) {
        super(replySize, curPage, true);
    }

    @Override
    protected IPage<CommentEntity> getReplys(int parentCommentId) {
        return commentMapper.getReplyByTime(new Page<>(curPage, replySize),
                parentCommentId, CommentConstants.PARENT_TYPE_COMMENT);
    }
}
