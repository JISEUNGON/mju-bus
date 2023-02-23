package com.mjubus.server.aspect;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    @Before("execution(* com.mjubus.server..*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("[START] | where: " + joinPoint.toString());
    }

    @AfterReturning("execution(* com.mjubus.server..*(..))")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("[END] | where: " + joinPoint.toString());
    }

    @AfterThrowing(value = "execution(* com.mjubus.server..*(..))", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        Sentry.captureException(exception);
        log.error("[END_WITH_FAIL] | where: " + joinPoint.toString());
        log.error("[END_WITH_FAIL] | exception: [" + exception + "]", exception);
    }
}
