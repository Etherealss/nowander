package com.nowander.infrastructure.exception;

import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.pojo.BaseEnum;

/**
 * @author wtk
 * @date 2022-04-24
 */
public class EnumIllegalException extends BaseException {

    public EnumIllegalException(Class<? extends BaseEnum> clazz, Object param) {
        super(ApiInfo.ERROR_PARAM, "参数 '" + param.toString() + "'不匹配枚举类'" + clazz.getCanonicalName() + "'");
    }
}
