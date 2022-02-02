package com.nowander.blog.service.impl;

import com.nowander.blog.mapper.CommentMapper;
import com.nowander.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.pojo.po.Comment;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{

}
