package com.nowander.starter.controller.forum;


import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousPostMapping;
import com.nowander.forum.comment.CommentEntity;
import com.nowander.forum.comment.CommentService;
import com.nowander.infrastructure.enums.CommentParentType;
import com.nowander.infrastructure.enums.CommentType;
import com.nowander.infrastructure.enums.OrderType;
import com.nowander.infrastructure.web.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@ResponseAdvice
public class CommentController {

    private CommentService commentService;

    @PostMapping("/publish")
    public void publish(@RequestBody CommentEntity commentEntity) {
        commentService.save(commentEntity);
    }

    @DeleteMapping("/delete/{commentId}")
    public void delete(@PathVariable("commentId") Integer commentId, SysUser sysUser) {
        commentService.deleteComment(commentId, sysUser.getId());
    }

    /**
     * 获取评论
     * @param curPage 当前页码
     * @param parentId 评论目标ID
     * @param parentType 评论目标类型，文章下的评论还是问贴下的评论？
     * @param orderBy 排序方式
     * @param commentRows 评论显示数
     * @param replyRows 评论的回复显示数
     * @param sysUser 当前用户
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousGetMapping("/pages/comments/{parentType}/{parentId}/{curPage}")
    public Map<String, Object> pageComment(
            @PathVariable(value = "parentType") CommentParentType parentType,
            @PathVariable(value = "parentId") Integer parentId,
            @PathVariable(value = "curPage") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "time") OrderType orderBy,
            @RequestParam(value = "commentRows", defaultValue = "3") Integer commentRows,
            @RequestParam(value = "replyRows", defaultValue = "3") Integer replyRows,
            SysUser sysUser) {
        return commentService.pageComments(parentId, parentType, curPage, commentRows, replyRows, orderBy, sysUser);
    }

    /**
     * 获取评论下的回复
     * @param curPage 当前页码
     * @param commentId 评论ID
     * @param orderBy 排序方式
     * @param replyRows 回复显示数
     * @param sysUser 当前用户
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousGetMapping("/pages/replys/{commentId}/{curPage}")
    public Map<String, Object> pageRepky(
            @PathVariable(value = "curPage") Integer curPage,
            @PathVariable(value = "commentId") Integer commentId,
            @RequestParam(value = "orderBy", required = false) OrderType orderBy,
            @RequestParam(value = "replyRows", required = false) Integer replyRows,
            SysUser sysUser) {
        return commentService.pageReplys(commentId, curPage, replyRows, orderBy, sysUser);
    }
}

