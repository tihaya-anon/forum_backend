package com.anon.backend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLoggingAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before("execution(* com.anon.backend.controller..*.*(..))")
  public void logBeforeMethod(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().toShortString();
    String args = Arrays.toString(joinPoint.getArgs());

    logger.info("Method: {}, Args: {}", methodName, args);
  }

  @AfterReturning(pointcut = "execution(* com.anon.backend.controller..*.*(..))", returning = "result")
  public void logAfterMethod(JoinPoint joinPoint, Object result) {
    String methodName = joinPoint.getSignature().toShortString();

    logger.info("Method: {}, Result: {}", methodName, result);
  }
}
