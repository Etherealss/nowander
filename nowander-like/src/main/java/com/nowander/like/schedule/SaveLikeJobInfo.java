package com.nowander.like.schedule;

import com.nowander.infrastructure.enums.ScheduleConstants;
import com.nowander.infrastructure.schedule.domain.JobInfo;
import com.nowander.like.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义定时任务，在 ScheduleJobInitConfiguration 中通过注入获取
 * @author wtk
 * @date 2022-02-01
 */
@Configuration
public class SaveLikeJobInfo {
    @Bean
    public JobInfo getSaveLikeRecordJobInfo(LikeService likeService,
                                            @Value("${app.schedule.like-record.cron}") String cron) throws NoSuchMethodException {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobId(ScheduleConstants.JOB_ID_SAVE_LIKE_RECORD);
        jobInfo.setJobName("点赞记录持久化");
        jobInfo.setJobGroup(ScheduleConstants.JOB_JROUP_SAVE_LIKE);
        jobInfo.setCronExpression(cron);
        jobInfo.setTargetMethod(LikeService.class.getMethod("saveRecentLikeRecord"));
        jobInfo.setTargetBean(likeService);
        return jobInfo;
    }

    @Bean
    public JobInfo getSaveLikeCountJobInfo(LikeService likeService,
                                           @Value("${app.schedule.like-count.cron}") String cron) throws NoSuchMethodException {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobId(ScheduleConstants.JOB_ID_SAVE_LIKE_COUNT);
        jobInfo.setJobName("点赞统计持久化");
        jobInfo.setJobGroup(ScheduleConstants.JOB_JROUP_SAVE_LIKE);
        jobInfo.setCronExpression(cron);
        jobInfo.setTargetMethod(LikeService.class.getMethod("saveRecentLikeCount"));
        jobInfo.setTargetBean(likeService);
        return jobInfo;
    }
}
