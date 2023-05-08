package com.wpz.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.wpz.community.service.*.*(..))") // 定义切点
    public void pointcut(){

    }

    @Before("pointcut()") // 在连接点开始前记日志
    public void before(){
        System.out.println("before");
    }

    @Before("pointcut()") // 在连接点后记日志
    public void after(){
        System.out.println("after");
    }

    @AfterReturning("pointcut()") // 在连接点开始前记日志
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()") // 在连接点开始前记日志
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }
}
