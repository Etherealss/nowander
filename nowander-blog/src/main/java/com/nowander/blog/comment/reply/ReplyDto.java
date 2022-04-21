package com.nowander.blog.comment.reply;

import com.nowander.blog.comment.Comment;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-02-05
 */
@Data
public class ReplyDto {
    /**
     * 评论的回复
     */
    private Comment reply;
    /**
     * 回复的引用（“被提及的评论”)
     */
    private Comment referComment;
}
