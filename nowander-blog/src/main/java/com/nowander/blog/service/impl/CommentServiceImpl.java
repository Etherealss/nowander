package com.nowander.blog.service.impl;

import com.wanderfour.nowander.pojo.po.Comment;
import com.nowander.blog.mapper.CommentMapper;
import com.nowander.blog.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
