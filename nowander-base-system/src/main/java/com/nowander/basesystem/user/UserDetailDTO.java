package com.nowander.basesystem.user;

import com.nowander.infrastructure.enums.UserType;
import lombok.*;

/**
 * 展示用户详细信息的DTO
 * @author wtk
 * @since 2022-01-05
 */
@Data
public class UserDetailDTO {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

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
    private UserType userType;

    /**
     * 获赞数
     */
    private Integer likedCount;

    /**
     * 收藏数
     */
    private Integer collectedCount;
}
