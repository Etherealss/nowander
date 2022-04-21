package com.nowander.blog.document;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nowander.common.pojo.IdentifiedEntity;
import com.nowander.common.repository.JsonSetTypeHandler;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public abstract class NoWanderDocument extends IdentifiedEntity {
    /**
     * 分区
     */
    protected Integer category;

    /**
     * 作者Id
     */
    protected Integer authorId;

    /**
     * 标题
     */
    protected String title;

    /**
     * 标签
     */
    @TableField(typeHandler = JsonSetTypeHandler.class)
    protected Set<String> labels;
}
