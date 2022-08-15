package test.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import test.LikeRecordTest1;
import test.LikeRecordTest1Mapper;
import test.LikeRecordTest1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtk
 * @since 2022-08-11
 */
@Service
@Slf4j
public class LikeRecordTest1ServiceImpl extends ServiceImpl<LikeRecordTest1Mapper, LikeRecordTest1> implements LikeRecordTest1Service {

    @PostConstruct
    public void test() {
        delete();
    }

    private void delete() {
        LikeRecordTest1Mapper baseMapper = this.baseMapper;
        List<QueryWrapper<LikeRecordTest1>> list = new ArrayList<>(1000);
        Random random = new Random();
        for (int i = 0; i < 5000; i++) {
            QueryWrapper<LikeRecordTest1> wrapper = new QueryWrapper<>();
            for (int j = 0; j < 3; j++) {
                int userId = i + (random.nextInt(10) & 1);
                wrapper.eq("user_id", i).eq("target_id", i).eq("target_type", j);
                list.add(wrapper);
            }
        }
        log.info("删除...");
        long start = System.currentTimeMillis();
        int count = 0;
        for (QueryWrapper<LikeRecordTest1> wrapper : list) {
            try {
                int s = baseMapper.delete(wrapper);
                count += s;
            } catch (Exception ignored) {}
        }
        long end = System.currentTimeMillis();
        log.info("删除完成。删除成功：{} 耗时：{} ms，删除单条消息耗时：{} ms", count, end - start, (end - start) / list.size());

    }
    private void insert() {
        LikeRecordTest1Mapper baseMapper = this.baseMapper;
        List<LikeRecordTest1> list = new ArrayList<>();
        LikeRecordTest1 entity;
        for (int i = 0; i < 5000; i++) {
            for (int j = 0; j < 3; j++) {
                entity = new LikeRecordTest1();
                entity.setUserId(i);
                entity.setTargetId(i + 1);
                entity.setTargetType(j);
                list.add(entity);
            }
        }
        log.info("插入...");
        long start = System.currentTimeMillis();
        for (LikeRecordTest1 likeRecordTest1 : list) {
            try {
                baseMapper.insert(likeRecordTest1);
            } catch (Exception ignored) {}
        }
        long end = System.currentTimeMillis();
        log.info("插入完成。耗时：{} ms，插入单条消息耗时：{} ms", end - start, (end - start) / list.size());

    }
}
