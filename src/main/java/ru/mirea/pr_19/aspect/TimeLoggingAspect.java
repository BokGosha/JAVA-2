package ru.mirea.pr_19.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class TimeLoggingAspect {
    @Around("allServiceMethods()")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        log.info("Method execution time: {}ms", executionTime);

        return result;
    }

    @Pointcut("within(ru.mirea.pr_19.services.impl.*)")
    public void allServiceMethods() {}
}
