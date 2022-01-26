package com.nowander.common.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论
 * </p>
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

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
    private Integer type;

    /**
     * 评论为0，回复为1，子回复为2
     */
    private Integer commentStyle;

    /**
     * 正常为1，已删除为0
     */
    private Integer state;

    /**
     * 点赞数
     */
    private Integer liked;

    private Date createTime;


}
