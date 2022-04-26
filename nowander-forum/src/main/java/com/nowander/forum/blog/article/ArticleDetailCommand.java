package com.nowander.forum.blog.article;

import com.nowander.infrastructure.enums.Module;
import com.nowander.infrastructure.pojo.InputConverter;
import lombok.Data;

import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
public class ArticleDetailCommand implements InputConverter<Article> {

    /**
     * 分类
     */
    private Integer category;

    /**
     * 分区
     */
    private Module module;

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

}
