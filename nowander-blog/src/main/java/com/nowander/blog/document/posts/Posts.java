package com.nowander.blog.document.posts;


import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.blog.document.NoWanderDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 问贴
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("posts")
public class Posts extends NoWanderDocument {

    /**
     * 问题描述
     */
    private String content;

    /**
     * 关注数
     */
    private Integer follow;

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", labels=" + labels +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", follow=" + follow +
                '}';
    }



}


