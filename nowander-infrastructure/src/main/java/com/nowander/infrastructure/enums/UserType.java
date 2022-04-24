package com.nowander.infrastructure.enums;

import com.nowander.infrastructure.pojo.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 寒洲
 * @description 用户类型枚举
 * @date 2020/10/10
 */
@Getter
@AllArgsConstructor
public enum UserType implements BaseEnum {
    SENIOR(1, "高中生"),
    COLLEGE(2, "大学生"),
    TEACHER(3, "教师"),
    OTHERR(4, "其他用户"),
    ;
    private final int code;
    private final String name;

}
