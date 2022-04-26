package com.nowander.forum.blog.posts;


import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.forum.blog.NoWanderBlog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 问贴
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("posts")
public class PostsEntity extends NoWanderBlog {

    /**
     * 问题描述
     */
    private String content;

    /**
     * 关注数
     */
    private Integer follow;
}


