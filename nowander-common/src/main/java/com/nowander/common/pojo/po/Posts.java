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
 * 问贴
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("posts")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提问帖子id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类
     */
    private Integer category;

    /**
     * 提问者id
     */
    private Integer authorId;

    private String title;

    /**
     * 问题描述
     */
    private String content;

    private String label1;

    private String label2;

    private String label3;

    private String label4;

    private String label5;

    private Date createTime;

    private Date updateTime;

    /**
     * 点赞数
     */
    private Integer liked;

    /**
     * 关注数
     */
    private Integer follow;


}
