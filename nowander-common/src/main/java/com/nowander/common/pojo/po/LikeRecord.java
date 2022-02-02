package com.nowander.common.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.nowander.common.enums.RedisKey;
import com.nowander.common.exception.ServiceException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_record")
@NoArgsConstructor
public class LikeRecord implements Serializable {

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
        likeRecordKey = likeRecordKey(userId, targetId, targetType);
        likeCountKey = likeCountKey(targetId, targetType);
    }

    public LikeRecord(String likeRecordKey) {
        String[] split = likeRecordKey.split("::");
        if (split.length != 3) {
            throw new ServiceException("LikeRecord构造失败！传入的参数不对！");
        }
        userId = Integer.valueOf(split[0]);
        targetType = Integer.valueOf(split[1]);
        targetId = Integer.valueOf(split[2]);
    }

    public LikeRecord(String likeRecordKey, Boolean state) {
        this(likeRecordKey);
        this.state = state;
    }

    private static String likeRecordKey(Integer userId, Integer targetId, Integer targetType) {
        return RedisKey.RECENT_LIKE_RECORD
                + userId + "::"
                + targetType + "::"
                + targetId;
    }

    private static String likeCountKey(Integer targetId, Integer targetType) {
        return RedisKey.LIKE_COUNT
                + targetType + "::"
                + targetId;
    }
}
