package com.sjh.aspect;

import com.alibaba.fastjson.JSON;
import com.sjh.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP切面类 - 日志打印切面类 <br/>
 * 请注意：AOP切入的方法权限必须得是public/protected,如果之前写的是private会导致注入失败，就有空指针异常了
 *
 * @author 宋佳豪
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.sjh.annotation.SystemLog)") // 确定切点
    public void pt() {

    }

    @Around("pt()") // 确定通知方法, 使用环绕可以在执行前后进行通知
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable { // 抛出异常给统一异常处理

        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        // 获取当前线程的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 获取方法签名,然后获取方法(被打上此注解的方法)上的注解(需传入对应的注解类的字节码对象)
     *
     * @param joinPoint 连接点, 代表被增强方法
     * @return SystemLog
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

}
