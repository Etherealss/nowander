package com.nowander.blog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.common.pojo.IdentifiedEntity;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("article")
public class Article extends IdentifiedEntity {

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
     * 内容
     */
    private String content;

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
     * 收藏数
     */
    private Integer collected;
}
