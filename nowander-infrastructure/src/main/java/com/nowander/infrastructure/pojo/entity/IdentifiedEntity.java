package com.nowander.infrastructure.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nowander.infrastructure.pojo.entity.BaseEntity;
import lombok.*;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class IdentifiedEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;
}
