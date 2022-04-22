package com.nowander.forum.blog.article;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
@TableName("article_content")
public class ArticleContent {
    private Integer id;
    private String content;
}
