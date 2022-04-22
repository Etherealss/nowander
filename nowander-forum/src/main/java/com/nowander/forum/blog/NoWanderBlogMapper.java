package com.nowander.forum.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author wtk
 * @date 2022-04-19
 */
public interface NoWanderBlogMapper<T extends NoWanderBlog> extends BaseMapper<T> {
    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    IPage<T> pageByLike(@Param("page") Page<?> page);
}
