package com.nowander.infrastructure.schedule;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.nowander.infrastructure.schedule.domain.JobInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务执行工具
 * @author ruoyi
 */
public class JobInvokeUtil {
    /**
     * 执行方法
     * @param jobInfo 系统任务
     */
    public static void invokeJob(JobInfo jobInfo) throws Exception {
        if (jobInfo.getTargetMethod() == null || jobInfo.getTargetBean() == null) {
            // 通过字符串指定要调用的方法
            invokeByString(jobInfo);
        } else {
            // 通过Method对象指定要调用的方法
            invokeByGivingValue(jobInfo);
        }
    }

    /**
     * 通过JobInfo中给定的参数直接反射
     * @param jobInfo
     * @throws Exception 一般是任务调用异常
     */
    private static void invokeByGivingValue(JobInfo jobInfo) throws Exception {
        Assert.notNull(jobInfo);
        Assert.notNull(jobInfo.getTargetMethod());
        Assert.notNull(jobInfo.getTargetBean());
        try {
            jobInfo.getTargetMethod().invoke(jobInfo.getTargetBean(), jobInfo.getInvokeParams());
        } catch (InvocationTargetException e) {
            throw (Exception) e.getCause();
        }
    }

    /**
     * 通过字符串指定要调用的方法
     * @param jobInfo
     * @throws Exception
     */
    private static void invokeByString(JobInfo jobInfo) throws Exception {
        String invokeTarget = jobInfo.getInvokeTarget();
        if (StrUtil.isBlank(invokeTarget)) {
            throw new NullPointerException("invokeTarget不能为null");
        }
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if (!isValidClassName(beanName)) {
            Object bean = SpringUtil.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        } else {
            Object bean = Class.forName(beanName).newInstance();
            invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 调用任务方法
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (methodParams != null && methodParams.size() > 0) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为为class包名
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StrUtil.count(invokeTarget, '.') > 1;
    }

    /**
     * 获取bean名称
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StrUtil.subBefore(invokeTarget, '(', true);
        return StrUtil.subBefore(beanName, ".", true);
    }

    /**
     * 获取bean方法
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StrUtil.subBefore(invokeTarget, '(', true);
        return StrUtil.subAfter(methodName, '.', true);
    }

    /**
     * 获取method方法参数相关列表
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StrUtil.subBetween(invokeTarget, "(", ")");
        if (StrUtil.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classs = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StrUtil.trimToEmpty(methodParam);
            // String字符串类型，包含'
            if (StrUtil.contains(str, "'")) {
                classs.add(new Object[]{StrUtil.replace(str, "'", ""), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if (StrUtil.equals(str, "true") || StrUtil.equalsIgnoreCase(str, "false")) {
                classs.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，包含L
            else if (StrUtil.containsIgnoreCase(str, "L")) {
                classs.add(new Object[]{Long.valueOf(StrUtil.replaceIgnoreCase(str, "L", "")), Long.class});
            }
            // double浮点类型，包含D
            else if (StrUtil.containsIgnoreCase(str, "D")) {
                classs.add(new Object[]{Double.valueOf(StrUtil.replaceIgnoreCase(str, "D", "")), Double.class});
            }
            // 其他类型归类为整形
            else {
                classs.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return classs;
    }

    /**
     * 获取参数类型
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classs[index] = (Class<?>) os[1];
            index++;
        }
        return classs;
    }

    /**
     * 获取参数值
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classs = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classs[index] = os[0];
            index++;
        }
        return classs;
    }
}
