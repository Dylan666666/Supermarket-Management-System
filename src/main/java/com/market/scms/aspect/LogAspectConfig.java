package com.market.scms.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志 AOP
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/29 14:51
 */
@Aspect
@Component
@Slf4j
@Configuration
public class LogAspectConfig {

    @Resource
    private HttpServletRequest request;
    
    @Pointcut("execution(public * com.market.scms.web.*.*.*(..))")
    public void webLog(){}
    
    private Long startTimeMillis = null;

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容  
        startTimeMillis = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容  
        log.info("\n\n==================== 新的操作接入 ======================\n\n");
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        log.info("被切的方法声明:" + joinPoint.getSignature().toString());
        String[] arr1 = joinPoint.getSignature().toString().replace(".","/").split("/");
        log.info(arr1[arr1.length - 1]);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容  
        if (ret != null) {
            log.info("方法的返回值 : " + ret);
        }
        log.info("\n\n==================== 方法执行完毕 ======================\n\n");
    }

    /**
     * 后置异常通知
     * @param jp
     */
    @AfterThrowing(value = "webLog()", throwing = "ex")
    public void throwsError(JoinPoint jp, Throwable ex){
        log.error("\n\n==================== 方法异常 ======================\n\n");
        String logTemplate = "执行失败 \n" +
                "异常请求开始---Send Request URL: {}, Method: {}, Params: {} " +
                "\n异常请求方法---ClassName: {}, [Method]: {}, execution time: {}ms \n" +
                "异常请求结束---Exception Message: {}";
        log.error(logTemplate, request.getRequestURL(), request.getMethod(),
                JSON.toJSONString(jp.getArgs()), jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                (System.currentTimeMillis() - startTimeMillis), ex.getMessage());
    }
    
}
