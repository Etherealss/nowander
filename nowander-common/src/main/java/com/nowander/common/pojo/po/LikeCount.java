package com.nowander.common.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_count")
@NoArgsConstructor
public class LikeCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer targetId;

    private Integer targetType;

    private Integer count;

    private Date gmtUpdate;

    public LikeCount(Integer targetId, Integer targetType, Integer count) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.count = count;
    }

    public LikeCount(String redisLikeCountKey, int count) {
        String[] split = redisLikeCountKey.split("::");
        this.targetId = Integer.valueOf(split[0]);
        this.targetType = Integer.valueOf(split[1]);
        this.count = count;
    }

}
