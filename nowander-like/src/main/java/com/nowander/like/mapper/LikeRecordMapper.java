package com.nowander.like.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.common.pojo.po.LikeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Repository
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

    int countLikeRecord(@Param("likeRecord") LikeRecord likeRecord);

    void delete(@Param("likeRecord") LikeRecord likeRecord);

}
