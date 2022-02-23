package com.nowander.blog.pojo.po;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.pojo.IdentifiedEntity;
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
public class Posts extends IdentifiedEntity {

    /**
     * 分类
     */

    private Integer category;

    /**
     * 提问者id
     */
    private Integer authorId;

    private String title;

    /**
     * 问题描述
     */
    private String content;


    private String label1;


    private String label2;


    private String label3;


    private String label4;


    private String label5;

    /**
     * 关注数
     */
    private Integer follow;


}
