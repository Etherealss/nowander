package com.nowander.common.pojo.po;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 问贴
 * @author wtk
 * @since 2022-01-05
 */
@Data
@TableName("posts")
@Document(indexName = "article")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提问帖子id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Id
    private Integer id;

    /**
     * 分类
     */
    @Field(type = FieldType.Text)
    private Integer category;

    /**
     * 提问者id
     */
    private Integer authorId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 问题描述
     */
    private String content;

    @Field(type = FieldType.Text)
    private String label1;

    @Field(type = FieldType.Text)
    private String label2;

    @Field(type = FieldType.Text)
    private String label3;

    @Field(type = FieldType.Text)
    private String label4;

    @Field(type = FieldType.Text)
    private String label5;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;

    /**
     * 关注数
     */
    @Field(type = FieldType.Integer)
    private Integer follow;


}
