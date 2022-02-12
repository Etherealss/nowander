package com.nowander.framework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * AOP https://blog.csdn.net/lmb55/article/details/82470388
 * 打印Web请求
 * @author wtk
 * @date 2021-10-27
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    /**
     * 声明切点，我这里启动Controller层下的所有类下的方法就可以打印，
     * 可以使用*来代表任意字符，用..来表示任意个参数
     */
    private final String operateLogPoint = "execution(* com.nowander.*.controller.*.*(..))";

    @Pointcut(operateLogPoint)
    public void webLog() {
    }

    //在方法横向的插入到切点方法执行前
    @Before(value = "webLog()")
    public void beforeControll(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String msg = "请求URL: " + request.getRequestURL().toString() +
                "; 请求方式 : " + request.getMethod() +
                "; IP : " + request.getRemoteAddr() +
                "; 类与方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() +
                "; 参数 : " + Arrays.toString(joinPoint.getArgs());
        log.info(msg);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterControll(Object ret) {
        // 处理完请求，返回内容
    }
}
