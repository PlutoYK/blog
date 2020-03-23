package com.ykcoding.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ykcoding.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI().toString();
        String ip = request.getLocalAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request {}",requestLog);
    }

    @After("log()")
    public void after(){
        logger.info("=========after=========");
    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void afterReturning(Object result){
        logger.info("return ====={}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        Object[] atrgs;

        @Override
        public String toString() {
            return "requestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", atrgs=" + Arrays.toString(atrgs) +
                    '}';
        }

        public RequestLog(String url, String ip, String classMethod, Object[] atrgs) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.atrgs = atrgs;
        }
    }
}
