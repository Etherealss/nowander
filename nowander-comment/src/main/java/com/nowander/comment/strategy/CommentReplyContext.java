package com.nowander.comment.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.comment.pojo.dto.CommentDto;
import com.nowander.comment.pojo.dto.ReplyDto;
import com.nowander.comment.strategy.comment.QueryCommentStrategy;
import com.nowander.comment.strategy.reply.QueryReplyStrategy;
import com.nowander.common.pojo.po.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class CommentReplyContext {

    /**
     * 获取评论的策略
     */
    private QueryCommentStrategy commentStrategy;
    /**
     * 获取回复的策略
     */
    private QueryReplyStrategy replyStrategy;


    private CommentReplyContext() {
    }

    /**
     * 获取评论
     * @param strategy
     * @return
     */
    public static CommentReplyContext build4Comment(QueryCommentStrategy strategy) {
        CommentReplyContext context = new CommentReplyContext();
        context.commentStrategy = strategy;
        return context;
    }

    /**
     * 获取回复
     * @param strategy
     * @return
     */
    public static CommentReplyContext build4Reply(QueryReplyStrategy strategy) {
        CommentReplyContext context = new CommentReplyContext();
        context.replyStrategy = strategy;
        return context;
    }

    /**
     * 获取评论数据
     * @param parentId
     * @param parentIdType
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    public Map<String, Object> query(int parentId, int parentIdType) {
        IPage<CommentDto> page = commentStrategy.queryComments(parentId, parentIdType);
        // 完善作者信息并返回
        return complete(page);
    }

    /**
     * 获取回复数据
     * @param commentId
     * @return 包含了页面回复数据以及作者用户信息的map，key分别是page和authors
     */
    public Map<String, Object> query(int commentId) {
        IPage<ReplyDto> page = replyStrategy.queryReply(commentId);
        // 完善作者信息并返回
        return complete(page);
    }

    /**
     * 完善作者信息并返回
     * @param page
     * @return
     */
    private Map<String, Object> complete(IPage<?> page) {
        Map<Integer, User> authors = commentStrategy.getAuthorsData();
        // 初始化为2的话，在第二次put的时候会扩容，初始化为3的话会转化为4
        Map<String, Object> map = new HashMap<>(4);
        map.put("page", page);
        map.put("authors", authors);
        return map;
    }
}
