package com.nowander.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class IdentifiedEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
}