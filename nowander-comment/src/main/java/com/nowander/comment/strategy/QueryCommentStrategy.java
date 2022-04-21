package com.nowander.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.comment.CommentDto;
import com.nowander.comment.Comment;
import com.nowander.comment.QueryCommentAndReplyStrategy;
import com.nowander.common.pojo.po.User;
import com.nowander.common.utils.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-02-05
 */
public abstract class QueryCommentStrategy extends QueryCommentAndReplyStrategy {

    public QueryCommentStrategy(int commentSize, int replySize, int curPage, User curUser, boolean getRefer) {
        super(commentSize, replySize, curPage, curUser, getRefer);
    }

    /**
     * 获取评论
     * @param parentId
     * @param parentIdType
     * @return
     */
    protected abstract IPage<Comment> getComments(int parentId, int parentIdType);

    /**
     * 获取评论下的回复记录
     * @param commentId
     * @return
     */
    protected abstract IPage<Comment> getReplys(int commentId);

    public IPage<CommentDto> queryComments(int parentId, int parentIdType) {
        // 获取评论
        IPage<Comment> comments = getComments(parentId, parentIdType);

        // 将 IPage<Comment> 转为 IPage<CommentDto
        IPage<CommentDto> page = new Page<>();
        PageUtil.copyPage(page, comments);

        // 包装评论信息，根据需要获取评论下的回复
        List<CommentDto> dtos = new ArrayList<>(commentSize);
        for (Comment comment : comments.getRecords()) {
            // 判断当前用户是否为作者
            if (curUser != null) {
                comment.setIsAuthor(comment.getAuthorId().equals(curUser.getId()));
            }
            // 添加评论作者的id，后续会通过这些id获取User记录，保存到Map中
            this.addAuthorId2Set(comment.getAuthorId());
            CommentDto dto = new CommentDto();
            dto.setParentComment(comment);
            // 获取评论下的回复记录
            if (replySize > 0) {
                IPage<Comment> replys = getReplys(comment.getId());
                dto.setReplys(replys);
            }
            dtos.add(dto);
        }
        page.setRecords(dtos);
        return page;
    }
}
