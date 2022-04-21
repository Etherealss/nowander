package com.nowander.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-02-21
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
}
