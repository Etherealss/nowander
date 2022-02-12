package com.nowander.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.mapper.UserMapper;
import com.nowander.common.pojo.po.User;
import com.nowander.common.service.UserService;
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
