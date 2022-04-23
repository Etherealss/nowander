package com.nowander.basesystem.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.nowander.infrastructure.pojo.IdentifiedEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 类名叫做SysUser是为了避免与其他类库的User类重复
 * @author wtk
 * @since 2022-01-05
 */
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class SysUser extends IdentifiedEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱注册
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 用户头像路径
     */
    private String avatar;

    /**
     * 高中/大学/教师/其他
     */
    private Integer userType;

    /**
     * 获赞数
     */
    private Integer likedCount;

    /**
     * 收藏数
     */
    private Integer collectedCount;

    /**
     * 在读学校/毕业学校
     */
    private String school;

    /**
     * 选课/专业
     */
    private String major;

    /**
     * 权限
     */
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    @TableField(exist = false)
    private String token;

    /**
     * 账号是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否激活
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
