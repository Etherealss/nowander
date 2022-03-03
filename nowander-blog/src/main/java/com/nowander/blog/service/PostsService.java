package com.nowander.blog.service;

import com.nowander.blog.pojo.po.Posts;
import com.nowander.blog.mapper.PostsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class PostsService extends ServiceImpl<PostsMapper, Posts> {

}
