package com.nowander.comment.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nowander.comment.pojo.dto.CommentDto;
import com.nowander.comment.pojo.po.Comment;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;

import java.util.Map;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
public interface CommentService extends IService<Comment> {

    /**
     * 获取文章下方的评论
     * @param parentId
     * @param userId
     * @return
     */
    Msg<IPage<CommentDto>> getHotComments(Long parentId, Long userId);

    /**
     * 按分页获取页面所有评论
     * @param parentId
     * @param parentType
     * @param curPage
     * @param pageSize
     * @param replySize
     * @param orderBy
     * @param user
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    Map<String, Object> pageComments(
            Integer parentId, Integer parentType, int curPage,
            int pageSize, int replySize, String orderBy, User user);

    /**
     * 按分页获取评论的所有回复
     * @param commentId
     * @param curPage
     * @param replySize
     * @param orderBy
     * @param user
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    Map<String, Object> pageReplys(Integer commentId, int curPage, int replySize,
                                      String orderBy, User user);

    /**
     * 删除
     * @param commentId
     * @param authorId
     * @return
     */
    void deleteComment(Integer commentId, Integer authorId);

}
