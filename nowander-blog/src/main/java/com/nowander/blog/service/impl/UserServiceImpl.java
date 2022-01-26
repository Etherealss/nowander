package com.nowander.blog.service.impl;

import com.wanderfour.nowander.pojo.po.User;
import com.nowander.blog.mapper.UserMapper;
import com.nowander.blog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
