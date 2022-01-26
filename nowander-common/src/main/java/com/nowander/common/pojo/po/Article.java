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
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分区
     */
    private Integer category;

    /**
     * 作者Id
     */
    private Integer authorId;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签1
     */
    private String label1;

    /**
     * 标签2
     */
    private String label2;

    /**
     * 标签3
     */
    private String label3;

    /**
     * 标签4
     */
    private String label4;

    /**
     * 标签5
     */
    private String label5;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;

    /**
     * 点赞数
     */
    private Integer liked;

    /**
     * 收藏数
     */
    private Integer collected;


}
