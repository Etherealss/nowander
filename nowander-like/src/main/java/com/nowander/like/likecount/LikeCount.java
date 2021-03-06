package com.nowander.like.likecount;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.enums.RedisKeyPrefix;
import com.nowander.infrastructure.pojo.entity.BaseEntity;
import com.nowander.infrastructure.enums.BaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author wtk
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_count")
@NoArgsConstructor
public class LikeCount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer targetId;

    private LikeTargetType targetType;

    private Integer count;

    @TableField(exist = false)
    private String likeCountKey;

    public String getLikeCountKey() {
        if (likeCountKey == null) {
            likeCountKey = RedisKeyPrefix.LIKE_COUNT + targetType.getCode() + "::" + targetId;
        }
        return likeCountKey;
    }

    public LikeCount(Integer targetId, LikeTargetType targetType, Integer count) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.count = count;
    }

    public LikeCount(String redisLikeCountKey, int count) {
        String[] split = redisLikeCountKey.split("::");
        this.targetType = BaseEnum.fromCode(LikeTargetType.class, Integer.parseInt(split[1]));
        this.targetId = Integer.valueOf(split[2]);
        this.count = count;
    }

    /**
     * @param add null值会被视为0
     */
    public void addCount(@Nullable Integer add) {
        int a = add == null ? 0 : add;
        if (count == null) {
            count = a;
        } else {
            count += a;
        }
    }

}
