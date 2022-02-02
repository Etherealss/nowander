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
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException() {
        this(ApiInfo.SERVER_ERROR);
    }

    public ServiceException(ApiInfo apiInfo) {
        super(apiInfo.getMessage());
        code = apiInfo.getCode();
    }

    public ServiceException(Exception e, String message) {
        super(ApiInfo.SERVER_ERROR.getMessage() + message, e);
        this.code = ApiInfo.SERVER_ERROR.getCode();
    }

    public ServiceException(String message) {
        this(ApiInfo.SERVER_ERROR, message);
    }

    public ServiceException(ApiInfo apiInfo, String message) {
        super(apiInfo.getMessage() + message);
        code = apiInfo.getCode();
    }
}
