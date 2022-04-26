package com.nowander.forum.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    public IPage<T> page(int curPage, int size, String orderBy) {
        IPage<T> page;
        Page<T> p = new Page<>(curPage, size);
        switch (orderBy) {
            case "time":
                page = blogMapper.selectPage(p,
                        new QueryWrapper<T>().orderByDesc("create_time"));
                break;
            case "like":
                page = blogMapper.pageByLike(p);
                break;
            case "all":
            default:
                page = blogMapper.selectPage(p, null);
        }
        return page;
    }

}
