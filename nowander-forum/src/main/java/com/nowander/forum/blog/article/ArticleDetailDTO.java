package com.nowander.forum.blog.article;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
     * 标签
     */
    private List<String> labels;

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
