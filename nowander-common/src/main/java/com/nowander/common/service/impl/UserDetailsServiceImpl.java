package com.nowander.common.service.impl;

import com.nowander.common.mapper.UserMapper;
import com.nowander.common.pojo.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;
    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("UserDetailsService 用户信息检查：username = " + username);
        // 根据用户名查询数据库
        User user = userMapper.selectUserByUsername(username);
        //判断
        if (user == null) {
            log.info("登录用户不存在,username = '{}'", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 获取权限
        List<String> pers = userMapper.selectUserPermissions(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(pers.size());
        for (String authority : pers) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("all"));
        user.setAuthorities(grantedAuthorities);
        return user;
    }
}
