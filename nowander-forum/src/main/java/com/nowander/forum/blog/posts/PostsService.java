package com.nowander.forum.blog.posts;

import com.nowander.forum.blog.NoWanderBlogMapper;
import com.nowander.forum.blog.NoWanderBlogService;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class PostsService extends NoWanderBlogService<PostsEntity> {
    private final PostsMapper postsMapper;

    public PostsService(NoWanderBlogMapper<PostsEntity> blogMapper, PostsMapper postsMapper) {
        super(blogMapper);
        this.postsMapper = postsMapper;
    }

    public PostsEntity getById(Integer id) {
        return postsMapper.selectById(id);
    }
}
