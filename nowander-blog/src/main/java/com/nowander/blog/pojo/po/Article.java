package com.nowander.blog.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("article")
public class Article extends NoWanderDocument {

    /**
     * 收藏数
     */
    private Integer collected;
}
