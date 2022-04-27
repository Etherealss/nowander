package com.nowander.forum.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.basesystem.user.SysUser;
import com.nowander.forum.comment.CommentAndReplyDTO;
import com.nowander.forum.comment.CommentEntity;
import com.nowander.forum.comment.QueryCommentAndReplyStrategy;
import com.nowander.infrastructure.enums.CommentParentType;
import com.nowander.infrastructure.utils.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-02-05
 */
public abstract class QueryCommentStrategy extends QueryCommentAndReplyStrategy {

    public QueryCommentStrategy(int commentSize, int replySize, int curPage, boolean getRefer) {
        super(commentSize, replySize, curPage, getRefer);
    }

    /**
     * 获取评论
     * @param parentId
     * @param parentIdType
     * @return
     */
    protected abstract IPage<CommentEntity> getComments(int parentId, CommentParentType parentIdType);

    /**
     * 获取评论下的回复记录
     * @param commentId
     * @return
     */
    protected abstract IPage<CommentEntity> getReplys(int commentId);

    public IPage<CommentAndReplyDTO> queryComments(int parentId, CommentParentType parentIdType) {
        // 获取评论
        IPage<CommentEntity> comments = getComments(parentId, parentIdType);

        // 将 IPage<Comment> 转为 IPage<CommentDto
        IPage<CommentAndReplyDTO> page = new Page<>();
        PageUtil.copyPage(page, comments);

        // 包装评论信息，根据需要获取评论下的回复
        List<CommentAndReplyDTO> dtos = new ArrayList<>(commentSize);
        for (CommentEntity commentEntity : comments.getRecords()) {
            // 添加评论作者的id，后续会通过这些id获取User记录，保存到Map中
            this.addAuthorId2Set(commentEntity.getAuthorId());
            CommentAndReplyDTO dto = new CommentAndReplyDTO();
            dto.setParentComment(commentEntity);
            // 获取评论下的回复记录
            if (replySize > 0) {
                IPage<CommentEntity> replys = getReplys(commentEntity.getId());
                dto.setReplys(replys);
            }
            dtos.add(dto);
        }
        page.setRecords(dtos);
        return page;
    }
}
