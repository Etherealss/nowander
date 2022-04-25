package com.nowander.like.likerecord;

import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.pojo.InputConverter;
import com.nowander.like.likerecord.LikeRecord;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-04-24
 */
@Data
public class LikeRecordCommand implements InputConverter<LikeRecord> {
    @NotNull
    private LikeTargetType targetType;
    @NotNull
    private Integer targetId;
    @NotNull
    private Boolean isLike;
}
