package com.nowander.like.cache;

import com.nowander.common.enums.RedisKeyPrefix;
import com.nowander.like.pojo.po.LikeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户点赞行为缓存
 * 点赞是高频操作，如果每次一有用户点赞就缓存到数据库，那么点赞的持久化操作会非常频繁
 * 因此此处先将用户点赞的记录（也就是谁点赞了啥）缓存起来，之后定时批量写入数据库
 * @author wtk
 * @date 2022-01-26
 */
@Component
public class LikeRecordCache {
    @Resource
    private RedisTemplate<String, String> redis;
    private final HashOperations<String, String, Boolean> hash;

    @Autowired
    public LikeRecordCache(RedisTemplate<String, String> redis) {
        this.redis = redis;
        hash = redis.opsForHash();
    }

    public void setRecentLike(LikeRecord likeRecord, Boolean isLike) {
        hash.put(RedisKeyPrefix.RECENT_LIKE_RECORD, likeRecord.getLikeRecordKey(), isLike);
    }

    public void setLike(LikeRecord likeRecord, Boolean isLike) {
        hash.put(RedisKeyPrefix.LIKE_RECORD, likeRecord.getLikeRecordKey(), isLike);
    }

    /**
     * CAS 更新点赞记录
     * @param likeRecord
     * @param isLike
     * @return CAS是否成功
     */
    @Deprecated
    public boolean safeLikeOrUnlike(LikeRecord likeRecord, Boolean isLike) {
        /*
         监听
         因为Hash的key下有很多的hashkey，不适合用于watch
         所以这里直接用hashkey，然后后续要先写string的hashkey，再写hash的hashkey
         */
        redis.watch(likeRecord.getWatchLikeRecord());
        // 开启事务
        redis.multi();
        redis.opsForValue().set(likeRecord.getWatchLikeRecord(), isLike.toString());
        // 如果上面这个操作没问题，那么说明watch没有冲突
        Boolean o = hash.get(RedisKeyPrefix.RECENT_LIKE_RECORD, likeRecord.getLikeRecordKey());
        if (o == null) {
            // 数据一致，将数据库的点赞记录添加到缓存中
            hash.put(RedisKeyPrefix.RECENT_LIKE_RECORD, likeRecord.getLikeRecordKey(), isLike);
            redis.exec();
            return true;
        } else if (!isLike.equals(o)) {
            // 数据不一致，丢弃事务
            redis.discard();
            return false;
        } else {
            // 缓存和数据库的数据一样，没有必要改动
            return true;
        }
    }

    /**
     * 是否已有点赞记录（与数据库的数据一致）
     * @param likeRecord
     * @return
     */
    public Boolean getLike(LikeRecord likeRecord) {
        return hash.get(RedisKeyPrefix.LIKE_RECORD, likeRecord.getLikeRecordKey());
    }

    /**
     * 是否有点赞记录（该记录尚未持久化到数据库）
     * @param likeRecord
     * @return
     */
    public Boolean getRecentLike(LikeRecord likeRecord) {
        return hash.get(RedisKeyPrefix.RECENT_LIKE_RECORD, likeRecord.getLikeRecordKey());
    }

    public void delLike(LikeRecord likeRecord) {
        hash.delete(RedisKeyPrefix.LIKE_RECORD, likeRecord.getLikeRecordKey());
    }

    public void delRecentLike(LikeRecord likeRecord) {
        hash.delete(RedisKeyPrefix.RECENT_LIKE_RECORD, likeRecord.getLikeRecordKey());
    }

    public List<LikeRecord> getAndDelAllRecentLikeRecord() {
        // 只有Recent点赞记录需要持久化
        return getAndDelAllLikeRecord(RedisKeyPrefix.RECENT_LIKE_RECORD);
    }

    public Set<String> getAllKeys() {
        return hash.keys(RedisKeyPrefix.RECENT_LIKE_RECORD);
    }

    /**
     * 获取并删除所有
     * @return
     */
    public List<LikeRecord> getAndDelAllLikeRecord(String keyType) {
        Set<String> keys = hash.keys(keyType);
        List<LikeRecord> res = new ArrayList<>(keys.size());
        for (String key : keys) {
            Boolean value = hash.get(keyType, key);
            hash.delete(keyType, key);
            res.add(new LikeRecord(key, value));
        }
        return res;
    }
}
