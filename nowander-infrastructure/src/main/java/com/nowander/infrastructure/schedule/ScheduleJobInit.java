package com.nowander.infrastructure.schedule;

import com.nowander.infrastructure.schedule.domain.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 初始化定时任务
 * @author wtk
 * @date 2022-02-01
 */
@Slf4j
@Configuration
public class ScheduleJobInit {

    @Autowired
    private List<JobInfo> jobInfos;
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void initScheduleJob() {
        for (JobInfo jobInfo : jobInfos) {
            try {
                ScheduleUtil.createScheduleJob(scheduler, jobInfo);
            } catch (Exception e) {
                log.error("定时任务执行失败：", e);
            }
        }
    }
}
