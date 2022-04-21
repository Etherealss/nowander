package com.nowander.blog.document.posts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.document.NoWanderDocumentMapper;
import com.nowander.blog.document.posts.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
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
