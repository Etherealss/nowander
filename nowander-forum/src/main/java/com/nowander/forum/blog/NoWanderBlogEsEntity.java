package com.nowander.forum.blog;

import com.nowander.forum.blog.article.ArticleEntity;
import com.nowander.forum.blog.article.content.ArticleContentEntity;
import com.nowander.forum.blog.posts.PostsEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Data
@Document(indexName = "blog")
public class NoWanderBlogEsEntity {

    /**
     * 文章或问贴Id
     */
    @Id
    private Integer id;

    /**
     * 文章还是帖子？
     */
    @Field(type = FieldType.Text)
    private String blogType;

    /**
     * 分区
     */
    @Field(type = FieldType.Text)
    private Integer category;

    /**
     * 作者Id
     */
    @Field(type = FieldType.Integer)
    private Integer authorId;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @CompletionField
    private String title;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    /**
     * 标签
     */
    @Field(type = FieldType.Keyword)
    private Set<String> labels;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;

    /**
     * 文章收藏数
     */
    @Field(type = FieldType.Integer)
    private Integer collected;

    /**
     * 问贴关注数
     */
    @Field(type = FieldType.Integer)
    private Integer follow;

    public static NoWanderBlogEsEntity build(ArticleEntity article, ArticleContentEntity content) {
        NoWanderBlogEsEntity esEntity = new NoWanderBlogEsEntity();
        BeanUtils.copyProperties(article, esEntity);
        BeanUtils.copyProperties(content, esEntity);
        return esEntity;
    }

    public static NoWanderBlogEsEntity build(PostsEntity postsEntity) {
        NoWanderBlogEsEntity esEntity = new NoWanderBlogEsEntity();
        BeanUtils.copyProperties(postsEntity, esEntity);
        return esEntity;
    }

}
