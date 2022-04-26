package com.nowander.forum.blog.posts;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.forum.blog.NoWanderBlogMapper;
import com.nowander.forum.blog.NoWanderBlogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class PostsService extends NoWanderBlogService<Posts> {
    private final PostsMapper postsMapper;

    public PostsService(NoWanderBlogMapper<Posts> blogMapper, PostsMapper postsMapper) {
        super(blogMapper);
        this.postsMapper = postsMapper;
    }

    public Posts getById(Integer id) {
        return postsMapper.selectById(id);
    }
}
