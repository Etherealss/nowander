package com.nowander.basesystem.user.security.login;

import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.UserMapper;
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
        log.info("UserDetailsService 用户信息检查：username = " + username);
        // 根据用户名查询数据库
        SysUser sysUser = userMapper.selectUserByUsername(username);
        //判断
        if (sysUser == null) {
            log.info("登录用户不存在,username = '{}'", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 获取权限
        List<String> pers = userMapper.selectUserPermissions(sysUser.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(pers.size());
        for (String authority : pers) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("all"));
        sysUser.setAuthorities(grantedAuthorities);
        return sysUser;
    }
}
