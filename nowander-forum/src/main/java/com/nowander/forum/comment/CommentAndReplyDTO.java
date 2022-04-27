package com.nowander.forum.comment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-02-04
 */
@Data
public class CommentAndReplyDTO {

    /**
     * 顶层评论
     */
    private CommentEntity parentComment;

    /**
     * 评论的回复列表
     */
    private IPage<CommentEntity> replys;

    /**
     * 该评论下的总回复数量
     */
    private Integer replysTotalCount;
}
