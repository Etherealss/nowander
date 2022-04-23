package com.nowander.infrastructure.schedule;

import com.nowander.infrastructure.enums.ScheduleConstants;
import com.nowander.infrastructure.exception.TaskException;
import com.nowander.infrastructure.schedule.domain.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author wtk
 * @date 2022-01-30
 */
@Slf4j(topic = "schedule")
public class ScheduleUtil {
    /**
     * 根据是否可并发执行获取quartz执行类
     * @param jobInfo 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(JobInfo jobInfo) {
        return jobInfo.getCanConcurrent() ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建TriggerKey对象
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(String.valueOf(jobId), jobGroup);
    }

    /**
     * 构建JobKey对象
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(String.valueOf(jobId), jobGroup);
    }

    /**
     * 创建定时任务
     * @param scheduler
     * @param job
     * @throws SchedulerException
     * @throws TaskException
     */
    public static void createScheduleJob(Scheduler scheduler, JobInfo job)
            throws SchedulerException, TaskException {
        log.trace("创建定时任务：{}", job);
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobKey jobKey = getJobKey(jobId, jobGroup);
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder =
                CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 判断是否存在
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(
            JobInfo job, CronScheduleBuilder cb) throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("misfire策略：'" + job.getMisfirePolicy()
                        + "'无法识别", TaskException.Code.CONFIG_ERROR);
        }
    }
}
