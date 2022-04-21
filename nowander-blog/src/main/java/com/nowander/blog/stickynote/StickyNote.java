package com.nowander.blog.stickynote;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.nowander.common.pojo.BaseEntity;
import com.nowander.common.pojo.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 便利贴
 * </p>
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sticky_note")
public class StickyNote extends IdentifiedEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 便利贴作者id
     */
    private Integer authorId;

    /**
     * 便利贴文本内容
     */
    private String content;

    /**
     * 获赞数
     */
    private Integer likeCount;

    /**
     * 其他属性
     */
    private Object props;

}
