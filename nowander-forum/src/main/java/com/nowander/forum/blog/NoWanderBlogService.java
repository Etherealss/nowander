package com.nowander.forum.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.infrastructure.enums.OrderType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Slf4j
public class NoWanderBlogService<T extends NoWanderBlog> {

    private final NoWanderBlogMapper<T> blogMapper;

    public NoWanderBlogService(NoWanderBlogMapper<T> blogMapper) {
        this.blogMapper = blogMapper;
    }

    public NoWanderBlog getById(Integer id) {
        return blogMapper.selectById(id);
    }

    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    public IPage<T> page(int curPage, int size, OrderType orderBy) {
        IPage<T> page;
        Page<T> p = new Page<>(curPage, size);
        switch (orderBy) {
            case TIME:
                page = blogMapper.selectPage(p,
                        new QueryWrapper<T>().orderByDesc("create_time"));
                break;
            case LIKE:
                page = blogMapper.pageByLike(p);
                break;
            default:
                page = blogMapper.selectPage(p, null);
        }
        return page;
    }

}
