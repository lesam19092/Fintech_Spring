package com.example.fintech_spring.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class LogExecutionTimeAspect {

    @Around("execution(* *(..)) && (@within(com.example.fintech_spring.aspect.LogExecutionTime) || @annotation(com.example.fintech_spring.aspect.LogExecutionTime))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long initTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - initTime;


        if (joinPoint.getSignature() instanceof MethodSignature signature) {
            String methodName = signature.getMethod().getName();
            String className = signature.getDeclaringTypeName();

            log.trace("Class: {}, Method: {} ,Method executed in: {} ms ", className, methodName, executionTime);
        }
        return proceed;
    }
}

