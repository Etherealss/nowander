package com.nowander.forum.comment.reply.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.comment.reply.ReplyDto;
import com.nowander.forum.comment.Comment;
import com.nowander.forum.comment.QueryCommentAndReplyStrategy;
import com.nowander.basesystem.user.SysUser;
import com.nowander.infrastructure.utils.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-02-05
 */
public abstract class QueryReplyStrategy extends QueryCommentAndReplyStrategy {
    public QueryReplyStrategy(int replySize, int curPage, SysUser curSysUser, boolean getRefer) {
        super(0, replySize, curPage, curSysUser, getRefer);
    }

    /**
     * 通过不同的方式获取回复记录
     * @param parentCommentId
     * @return
     */
    protected abstract IPage<Comment> getReplys(int parentCommentId);

    private Comment getRefer(int commentId) {
        return commentMapper.selectById(commentId);
    }

    public IPage<ReplyDto> queryReply(int parentId) {
        // 获取评论
        IPage<Comment> replys = this.getReplys(parentId);

        // 将 IPage<Comment> 转为 IPage<CommentDto
        IPage<ReplyDto> page = new Page<>();
        PageUtil.copyPage(page, replys);

        // 获取评论下的回复
        List<ReplyDto> dtos = new ArrayList<>(commentSize);
        for (Comment reply : replys.getRecords()) {
            // 判断当前用户是否为作者
            if (curSysUser != null) {
                reply.setIsAuthor(reply.getAuthorId().equals(curSysUser.getId()));
            }
            // 添加评论作者的id，后续会通过这些id获取User记录，保存到Map中
            this.addAuthorId2Set(reply.getAuthorId());

            ReplyDto dto = new ReplyDto();
            dto.setReply(reply);
            dto.setReferComment(getRefer(reply.getTargetId()));
            dtos.add(dto);
        }
        page.setRecords(dtos);
        return page;
    }

}