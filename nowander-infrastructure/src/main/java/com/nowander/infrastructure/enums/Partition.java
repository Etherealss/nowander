package com.nowander.infrastructure.enums;

import com.nowander.infrastructure.pojo.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 寒洲
 * @description 三个大分区
 * @date 2020/10/7
 */
@Getter
@AllArgsConstructor
public enum Partition implements BaseEnum {
    LEARNING(1, "learning"),
    MAJOR(2, "major"),
    COLLEGE(3, "college"),
    ;

    final int code;
    final String name;

}
