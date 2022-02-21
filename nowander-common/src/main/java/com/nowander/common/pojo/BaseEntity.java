package com.nowander.common.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-02-21
 */
@Setter
@Getter
public abstract class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
