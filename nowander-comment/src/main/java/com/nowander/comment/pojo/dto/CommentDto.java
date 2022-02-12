package com.nowander.comment.pojo.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.comment.pojo.po.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author wtk
 * @date 2022-02-04
 */
@Data
public class CommentDto {

    /**
     * 顶层评论
     */
    private Comment parentComment;

    /**
     * 评论的回复列表
     */
    private IPage<Comment> replys;

    /**
     * 该评论下的总回复数量
     */
    private int replysTotalCount;
}
