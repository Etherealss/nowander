package com.nowander.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.pojo.po.Article;
import com.nowander.blog.pojo.po.Posts;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Repository
public interface PostsMapper extends NoWanderDocumentMapper<Posts> {

    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    @Override
    IPage<Posts> pageByLike(@Param("page") Page<?> page);

}
