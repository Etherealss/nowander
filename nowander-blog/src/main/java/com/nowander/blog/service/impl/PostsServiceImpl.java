package com.nowander.blog.service.impl;

import com.nowander.blog.pojo.po.Posts;
import com.nowander.blog.mapper.PostsMapper;
import com.nowander.blog.service.PostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

}
