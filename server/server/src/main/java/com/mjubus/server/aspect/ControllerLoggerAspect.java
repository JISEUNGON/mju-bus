package com.mjubus.server.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class ControllerLoggerAspect {

    @Before("execution(* com.mjubus.server.controller..*(..))")
    public void beforeController(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            log.info("[CONTROLLER] | HttpMethod: /{} URI:{}", request.getMethod(), request.getRequestURI());
        } catch (IllegalStateException illegalStateException) {
            log.info("[CONTROLLER] | No thread-bound request found.");
        }
    }
}
