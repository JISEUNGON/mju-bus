package com.mjubus.server.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    @Before("execution(* com.mjubus.server..*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("[START] | " + joinPoint.toString());
    }

    @AfterReturning("execution(* com.mjubus.server..*(..))")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("[END] | " + joinPoint.toString());
    }

    @AfterThrowing(value = "execution(* com.mjubus.server..*(..))", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        log.info("[END_WITH_FAIL] | " + joinPoint.toString());
        log.info("[END_WITH_FAIL] | exception: [" + exception + "]");
    }
}
