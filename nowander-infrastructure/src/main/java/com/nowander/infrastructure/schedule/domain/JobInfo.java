package com.nowander.infrastructure.schedule.domain;

import com.nowander.infrastructure.enums.ScheduleConstants;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author wtk
 * @date 2022-01-30
 */
@Data
public class JobInfo implements Serializable {
    /** 任务ID */
    private Long jobId;

    /** 任务名称 */
    private String jobName;

    /** 任务组名 */
    private String jobGroup;

    /** 调用目标字符串 */
    private String invokeTarget;

    /** 调用目标方法对象 */
    private Method targetMethod;
    /**
     * 调用目标对象，用于反射
     */
    private Object targetBean;
    /**
     * 调用目标对象方法的参数，用于反射
     */
    private Object[] invokeParams;

    /** cron执行表达式 */
    private String cronExpression;

    /** cron计划策略 */
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** 是否并发执行（0允许 1禁止） */
    private Boolean canConcurrent = true;

    /** 任务状态（0正常 1暂停） */
    private String status = ScheduleConstants.Status.NORMAL.getValue();

}
