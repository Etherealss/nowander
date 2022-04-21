package com.nowander.like.likerecord;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

    int countLikeRecord(@Param("likeRecord") LikeRecord likeRecord);

    void delete(@Param("likeRecord") LikeRecord likeRecord);

}
