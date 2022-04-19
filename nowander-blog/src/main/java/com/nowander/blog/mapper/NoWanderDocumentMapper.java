package com.nowander.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.pojo.po.NoWanderDocument;
import org.apache.ibatis.annotations.Param;

/**
 * @author wtk
 * @date 2022-04-19
 */
public interface NoWanderDocumentMapper<T extends NoWanderDocument> extends BaseMapper<T> {
    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    IPage<T> pageByLike(@Param("page") Page<?> page);
}
