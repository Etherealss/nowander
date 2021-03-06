package com.nowander.like.likerecord;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.enums.RedisKeyPrefix;
import com.nowander.infrastructure.exception.internal.BugException;
import com.nowander.infrastructure.pojo.entity.BaseEntity;
import com.nowander.infrastructure.enums.BaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_record")
@NoArgsConstructor
public class LikeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 目标类型
     */
    private LikeTargetType targetType;
    /**
     * 点赞的目标id
     */
    private Integer targetId;
    /**
     * 点赞用户id
     */
    private Integer userId;


    /**
     * 点赞状态
     */
    @TableField(exist = false)
    private Boolean state;

    @TableField(exist = false)
    private String likeRecordKey;
    @TableField(exist = false)
    private String likeCountKey;
    @TableField(exist = false)
    private String watchLikeRecord;

    public String getWatchLikeRecord() {
        if (watchLikeRecord == null) {
            watchLikeRecord = RedisKeyPrefix.WACTH_LOCK + "::" + likeRecordKey;
        }
        return watchLikeRecord;
    }

    public LikeRecord(LikeTargetType targetType, Integer targetId, Integer userId) {
        this.targetType = targetType;
        this.targetId = targetId;
        this.userId = userId;
    }

    public LikeRecord(String likeRecordKey) {
        String[] split = likeRecordKey.split("::");
        if (split.length != 4) {
            throw new BugException("LikeRecord构造失败！传入的参数不对！");
        }
        targetType = BaseEnum.fromCode(LikeTargetType.class, Integer.parseInt(split[1]));
        targetId = Integer.valueOf(split[2]);
        userId = Integer.valueOf(split[3]);
    }

    public LikeRecord(String likeRecordKey, Boolean state) {
        this(likeRecordKey);
        this.state = state;
    }

    public String getLikeRecordKey() {
        if (likeRecordKey == null) {
            likeRecordKey = RedisKeyPrefix.LIKE_RECORD
                    + targetType.getCode() + "::"
                    + targetId + "::"
                    + userId;
        }
        return likeRecordKey;
    }

    public String getRecentLikeRecordKey() {
        if (likeRecordKey == null) {
            likeRecordKey = RedisKeyPrefix.RECENT_LIKE_RECORD
                    + targetType.getCode() + "::"
                    + targetId + "::"
                    + userId;
        }
        return likeRecordKey;
    }

    public String getLikeCountKey() {
        if (likeCountKey == null) {
            likeCountKey = RedisKeyPrefix.LIKE_COUNT
                    + targetType.getCode() + "::"
                    + targetId;
        }
        return likeCountKey;
    }

    public String getRecentLikeCountKey() {
        if (likeCountKey == null) {
            likeCountKey = RedisKeyPrefix.RECENT_LIKE_COUNT
                    + targetType.getCode() + "::"
                    + targetId;
        }
        return likeCountKey;
    }
}
