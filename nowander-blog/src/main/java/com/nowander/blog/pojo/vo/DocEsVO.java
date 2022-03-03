package com.nowander.blog.pojo.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Data
@Document(indexName = "doc")
public class DocEsVO {

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
     * 标签1
     */
    @Field(type = FieldType.Text)
    private String label1;

    /**
     * 标签2
     */
    @Field(type = FieldType.Text)
    private String label2;

    /**
     * 标签3
     */
    @Field(type = FieldType.Text)
    private String label3;

    /**
     * 标签4
     */
    @Field(type = FieldType.Text)
    private String label4;

    /**
     * 标签5
     */
    @Field(type = FieldType.Text)
    private String label5;

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
