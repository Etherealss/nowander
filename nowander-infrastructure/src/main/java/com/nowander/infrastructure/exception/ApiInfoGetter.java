package com.nowander.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * @author wang tengkun
 * @date 2022/3/18
 */
public interface ApiInfoGetter {
    int getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
