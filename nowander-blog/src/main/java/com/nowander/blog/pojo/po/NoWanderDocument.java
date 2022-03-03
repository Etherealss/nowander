package com.nowander.blog.pojo.po;

import com.nowander.common.pojo.IdentifiedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Setter
@Getter
@ToString
public abstract class NoWanderDocument extends IdentifiedEntity {
    /**
     * 分区
     */
    private Integer category;

    /**
     * 作者Id
     */
    private Integer authorId;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    Set<String> labels;
}
