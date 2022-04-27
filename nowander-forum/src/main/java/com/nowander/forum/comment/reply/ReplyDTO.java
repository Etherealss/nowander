package com.nowander.forum.comment.reply;

import com.nowander.forum.comment.CommentEntity;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-02-05
 */
@Data
public class ReplyDTO {
    /**
     * 评论的回复
     */
    private CommentEntity reply;
    /**
     * 回复的引用（“被提及的评论”)
     */
    private CommentEntity referComment;
}
