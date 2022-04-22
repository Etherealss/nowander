package com.nowander.basesystem.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserManage extends ServiceImpl<UserMapper, SysUser> {

}
