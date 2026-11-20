package org.example.autoloc.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String SERVICE_METHODS = "execution(* org.example.autoloc.services..*(..))";

    @Before(SERVICE_METHODS)
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        logger.info("Appel de {} avec les paramètres {}",
                joinPoint.getSignature().getName(), Arrays.deepToString(joinPoint.getArgs()));
    }

    @After(SERVICE_METHODS)
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        logger.info("Fin d'exécution de {}", joinPoint.getSignature().getName());
    }
}
