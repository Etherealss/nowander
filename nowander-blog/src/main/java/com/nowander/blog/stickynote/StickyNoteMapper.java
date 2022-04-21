package com.nowander.blog.stickynote;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface StickyNoteMapper extends BaseMapper<StickyNote> {

}
