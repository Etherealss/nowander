package com.nowander.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.nowander.common.config.JwtConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wtk
 * @date 2021-10-28
 */
@Slf4j
public class TokenUtil {

    private static JwtConfig jwtConfig;
    static {
        SpringUtil.getBean(JwtConfig.class);
    }

}
