package com.nowander.common.schedule;

import com.nowander.common.schedule.domain.JobInfo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 * @author wtk
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, JobInfo sysJob) throws Exception {
        JobInvokeUtil.invokeJob(sysJob);
    }
}
