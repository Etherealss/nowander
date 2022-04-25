package com.nowander.forum.blog;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nowander.infrastructure.enums.Partition;
import com.nowander.infrastructure.pojo.IdentifiedEntity;
import com.nowander.infrastructure.repository.JsonSetTypeHandler;
import lombok.*;

import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@ToString(callSuper = true)
public abstract class NoWanderBlog extends IdentifiedEntity {

    /**
     * 分区
     */
    protected Partition partition;
    /**
     * 分类
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
