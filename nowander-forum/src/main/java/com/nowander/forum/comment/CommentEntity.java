package com.nowander.forum.comment;

import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.basesystem.user.security.SecurityUtil;
import com.nowander.infrastructure.enums.CommentParentType;
import com.nowander.infrastructure.enums.CommentType;
import com.nowander.infrastructure.pojo.entity.IdentifiedEntity;
import com.nowander.basesystem.user.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class CommentEntity extends IdentifiedEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论作者用户id
     */
    private Integer authorId;

    /**
     * 表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复
     */
    private Integer parentId;

    /**
     * 该记录指向的目标Id，可以指向评论或回复对象
     */
    private Integer targetId;

    /**
     * 评论/回复内容
     */
    private String content;

    /**
     * 文章为0，问贴为1
     */
    private CommentParentType parentType;

    /**
     * 评论为0，回复为1，子回复为2
     */
    private CommentType commentType;

    /**
     * 正常为1，已删除为0
     */
    private Integer state;

    public Boolean getIsAuthor() {
        SysUser user = SecurityUtil.getLoginUser();
        return user != null && user.getId().equals(authorId);
    }
}
