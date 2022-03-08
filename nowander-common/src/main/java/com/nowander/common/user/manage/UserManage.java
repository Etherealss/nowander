package com.nowander.common.user.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.pojo.po.User;
import com.nowander.common.user.mapper.UserMapper;
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
public class UserManage extends ServiceImpl<UserMapper, User> {

}
