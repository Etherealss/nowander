package com.nowander.common.exception;


import com.nowander.common.enums.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wtk
 * @description 自定义 服务异常
 * @date 2021-08-12
 */
@Setter
@Getter
@AllArgsConstructor
public abstract class AbstractServiceException extends RuntimeException {

    private int code;

    public AbstractServiceException(ApiInfo apiInfo) {
        super(apiInfo.getMessage());
        code = apiInfo.getCode();
    }

    public AbstractServiceException(String message) {
        this(ApiInfo.SERVER_ERROR, message);
    }

    public AbstractServiceException(ApiInfo apiInfo, String message) {
        super(apiInfo.getMessage() + message);
        code = apiInfo.getCode();
    }

    /**
     * @param apiInfo
     * @param message
     * @param e 原始异常
     */
    public AbstractServiceException(ApiInfo apiInfo, String message, Exception e) {
        super(apiInfo.getMessage() + message, e);
        code = apiInfo.getCode();
    }
}
