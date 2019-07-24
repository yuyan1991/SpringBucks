package com.qrqs.springbucks.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PerformanceAspect {
    @Around("repositoryOps()")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "???";
        String result = "Y";
        try {
            name = pjp.getSignature().toShortString();
            return pjp.proceed();
        } catch (Throwable e) {
            result = "N";
            throw e;
        } finally {
            log.info("{} with result {} costs :: {}", name, result, System.currentTimeMillis() - startTime);
        }
    }

    @Pointcut("execution(* com.qrqs.springbucks.database.repositories..*(..))")
    public void repositoryOps() {
    }
}
