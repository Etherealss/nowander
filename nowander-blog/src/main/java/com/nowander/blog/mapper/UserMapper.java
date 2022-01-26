package com.nowander.blog.mapper;

import com.wanderfour.nowander.pojo.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录
     * @param userId
     * @param password
     * @return
     */
    User selectLoginUser(@Param("userId") int userId, @Param("password") String password);

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
