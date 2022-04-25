package com.nowander.infrastructure.pojo.command;

import com.nowander.infrastructure.enums.LikeTargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-04-24
 */
@Data
public class LikeRecordCommand {
    @NotNull
    private LikeTargetType targetType;
    @NotNull
    private Integer targetId;
    @NotNull
    private Boolean isLike;
}
