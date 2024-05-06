package com.example.blog.aop;

import com.example.blog.component.RequestCounter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Javadoc COMMENT. */
@SuppressWarnings("checkstyle:Indentation")
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final RequestCounter requestCounter;

    public LoggingAspect(RequestCounter requestCounter) {
        this.requestCounter = requestCounter;
    }

    /** UserController. */
    @After("execution(* com.example.blog.controllers.UserController.*(..))")
    public void loggerUserController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("User Request: {}.{}", className, methodName);
    }

    /** BlogController. */
    @After("execution(* com.example.blog.controllers.BlogController.*(..))")
    public void loggerBlogController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Blog Request: {}.{}", className, methodName);
    }

    /** ExceptionHandler. */
    @After("execution(* com.example.blog.exception.ExceptionHandler.*(..))")
    public void logException(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.error("ERROR: {}.{}", className, methodName);
    }

    @After("execution(* com.example.blog.services.UserService.getUsers())")
    public void logRequestCounter() {
        logger.info("NEW REQUEST: " + requestCounter.increment());
    }

    /*
    @PreDestroy
    public void logRequestCount() {
        logger.info("Total number of requests: {}", requestCounter.getCounter());
    }

     */
}