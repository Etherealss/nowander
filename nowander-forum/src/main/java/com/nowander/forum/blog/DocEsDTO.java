package com.nowander.forum.blog;

import lombok.Data;
import org.springframework.data.annotation.Id;
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
@Document(indexName = "doc")
public class DocEsDTO {

    /**
     * 文章Id
     */
    @Id
    private Integer id;

    /**
     * 文章还是帖子？
     */
    @Field(type = FieldType.Text)
    private String docType;

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
     * 收藏数
     */
    @Field(type = FieldType.Integer)
    private Integer collected;

}
