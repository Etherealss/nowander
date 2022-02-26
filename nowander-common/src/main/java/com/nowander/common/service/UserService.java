package com.nowander.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.enums.RedisKey;
import com.nowander.common.mapper.UserMapper;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, User> {

    RedisTemplate<String, Object> redisTemplate;
    UserMapper userMapper;


    public void logout() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisTemplate.delete(RedisKey.USER_TOKEN + ((User) principal).getUsername());
        // 清空
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
