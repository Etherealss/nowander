package com.nowander.blog.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
public class ArticleDetailDTO {
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
     * 文章内容
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
