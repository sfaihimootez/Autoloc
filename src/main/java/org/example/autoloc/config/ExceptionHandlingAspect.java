package org.example.autoloc.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);
    private static final String SERVICE_METHODS = "execution(* org.example.autoloc.services..*(..))";

    @AfterThrowing(pointcut = SERVICE_METHODS, throwing = "exception")
    public void logServiceException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception dans {} : type={}, message={}",
                joinPoint.getSignature().getName(), exception.getClass().getName(), exception.getMessage());
    }
}
