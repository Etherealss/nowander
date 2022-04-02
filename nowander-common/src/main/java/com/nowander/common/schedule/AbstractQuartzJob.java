package com.nowander.common.schedule;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.nowander.common.enums.ScheduleConstants;
import com.nowander.common.schedule.domain.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 写这个类的主要目的是要从JobDataMap中获取JobInfo
 * @author wtk
 * @date 2022-01-30
 */
@Slf4j(topic = "schedule")
public abstract class AbstractQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobInfo jobInfo = getJobInfo(context);
            DateTime start = DateUtil.date();
            doBefore(context, jobInfo, start);
            this.doExecute(context, jobInfo);
            doAfter(context, jobInfo, start);
        } catch (Exception e) {
            log.warn("任务执行异常", e);
            throw new JobExecutionException("任务执行异常", e);
        }
    }

    private void doBefore(JobExecutionContext context, JobInfo jobInfo, Date date) {
        log.info("--- START --- 定时任务'{}'开始执行。当前时间：{}。JobInfo消息：{}",
                jobInfo.getJobName(), date, jobInfo);
    }

    private void doAfter(JobExecutionContext context, JobInfo jobInfo,  Date start) {
        DateTime finish = DateUtil.date();
        log.info("--- FINISH --- 定时任务 '{}' 执行完毕。耗时：{}ms。完成时间：{}。JobInfo消息：{}",
                jobInfo.getJobName(), finish.getTime() - start.getTime(), finish, jobInfo);
    }

    /**
     * 从JobDataMap中获取JobInfo
     * @param context
     * @return
     */
    private JobInfo getJobInfo(JobExecutionContext context) {
        JobInfo jobInfo = new JobInfo();
        Object o = context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        BeanUtils.copyProperties(o, jobInfo);
        return jobInfo;
    }

    /**
     * 模板方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param jobInfo 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, JobInfo jobInfo) throws Exception;

}
