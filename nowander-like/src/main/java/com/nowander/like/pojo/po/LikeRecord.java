package com.nowander.like.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.common.enums.RedisKey;
import com.nowander.common.exception.AbstractServiceException;
import com.nowander.common.exception.ServerException;
import com.nowander.common.pojo.BaseEntity;
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
     * 点赞用户id
     */
    private Integer userId;

    /**
     * 点赞的目标id
     */
    private Integer targetId;

    /**
     * 目标类型
     */
    private Integer targetType;

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
            watchLikeRecord = RedisKey.WACTH_LOCK + "::" + likeRecordKey;
        }
        return watchLikeRecord;
    }

    public LikeRecord(Integer userId, Integer targetId, Integer targetType) {
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
    }


    public LikeRecord(String likeRecordKey) {
        String[] split = likeRecordKey.split("::");
        if (split.length != 3) {
            throw new ServerException("LikeRecord构造失败！传入的参数不对！");
        }
        userId = Integer.valueOf(split[0]);
        targetType = Integer.valueOf(split[1]);
        targetId = Integer.valueOf(split[2]);
    }

    public LikeRecord(String likeRecordKey, Boolean state) {
        this(likeRecordKey);
        this.state = state;
    }

    public String getLikeRecordKey() {
        if (likeRecordKey == null) {
            likeRecordKey = RedisKey.LIKE_RECORD
                    + userId + "::"
                    + targetType + "::"
                    + targetId;
        }
        return likeRecordKey;
    }

    public String getLikeCountKey() {
        if (likeCountKey == null) {
            likeCountKey = RedisKey.LIKE_COUNT
                    + targetType + "::"
                    + targetId;
        }
        return likeCountKey;
    }

}
