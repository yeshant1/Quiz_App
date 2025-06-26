package com.lpu.leaderboard_service.aspect;

import com.lpu.leaderboard_service.exception.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.lpu.leaderboard_service.service.LeaderBoardService.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore() {
        logger.info("Method execution started");
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Exception occurred: ", exception);
        if (exception instanceof CustomException) {
            CustomException customException = (CustomException) exception;
            logger.error("HTTP Status: " + customException.getStatus());
        }
    }
}
