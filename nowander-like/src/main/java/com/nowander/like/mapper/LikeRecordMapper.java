package com.nowander.like.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.like.pojo.po.LikeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
