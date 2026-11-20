package org.example.autoloc.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
    private static final String SERVICE_METHODS = "execution(* org.example.autoloc.services..*(..))";

    @Around(SERVICE_METHODS)
    public Object measureServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNanos = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedMillis = (System.nanoTime() - startNanos) / 1_000_000;
            String methodName = joinPoint.getSignature().getName();
            if (elapsedMillis > 1_000) {
                logger.warn("La méthode {} a dépassé 1000 ms : {} ms", methodName, elapsedMillis);
            } else {
                logger.info("Temps d'exécution de {} : {} ms", methodName, elapsedMillis);
            }
        }
    }
}
