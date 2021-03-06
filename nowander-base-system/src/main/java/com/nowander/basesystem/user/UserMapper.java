package com.nowander.basesystem.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserMapper extends BaseMapper<SysUser> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    SysUser selectLoginUserByUsername(@Param("username") int username, @Param("password") String password);

    /**
     *
     * @param username
     * @return
     */
    SysUser selectUserByUsername(@Param("username") String username);

    /**
     * 查询用户权限
     * @param id
     * @return
     */
    List<String> selectUserPermissions(@Param("id") Integer id);

    /**
     * 更新用户头像
     * @param avatar
     * @param id
     */
    void updateAvatarById(@Param("avatar") String avatar, @Param("id") Integer id);
}
