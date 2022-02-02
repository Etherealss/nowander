package com.nowander.framework.schedule;

import com.nowander.framework.schedule.domain.JobInfo;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author ruoyi
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, JobInfo jobInfo) throws Exception {
        JobInvokeUtil.invokeJob(jobInfo);
    }
}
