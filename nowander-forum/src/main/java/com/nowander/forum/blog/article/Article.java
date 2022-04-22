package com.nowander.forum.blog.article;

import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.forum.blog.NoWanderBlog;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("article")
public class Article extends NoWanderBlog {

    /**
     * 收藏数
     */
    private Integer collected;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", labels=" + labels +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", collected=" + collected +
                '}';
    }
}
