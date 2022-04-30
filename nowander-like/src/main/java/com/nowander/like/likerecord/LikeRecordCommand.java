package com.nowander.like.likerecord;

import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.pojo.Converter;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-04-24
 */
@Data
public class LikeRecordCommand implements Converter<LikeRecord> {
    @NotNull
    private LikeTargetType targetType;
    @NotNull
    private Integer targetId;
    @NotNull
    private Boolean isLike;
}
