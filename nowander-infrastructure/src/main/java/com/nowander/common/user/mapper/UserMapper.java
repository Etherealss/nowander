package com.nowander.common.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.common.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    User selectLoginUserByUsername(@Param("username") int username, @Param("password") String password);

    /**
     *
     * @param username
     * @return
     */
    User selectUserByUsername(@Param("username") String username);

    /**
     * 查询用户权限
     * @param id
     * @return
     */
    List<String> selectUserPermissions(@Param("id") Integer id);
}
