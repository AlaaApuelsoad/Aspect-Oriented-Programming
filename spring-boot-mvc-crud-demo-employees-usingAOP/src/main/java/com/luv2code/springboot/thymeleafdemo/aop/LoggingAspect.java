package com.luv2code.springboot.thymeleafdemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    //setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    private void forControllerPackage(){}


    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDaoPackage(){}

    //Combine Pointcuts
    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){

        //display method we are calling
        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("====> in @Before: calling method: "+theMethod);

        //display the arguments to the method

        //get the arguments
        Object[] args = joinPoint.getArgs();

        //loop through and displays args
        for (Object temArg : args){
            myLogger.info("====> argument: "+temArg);
        }
    }

    //add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint joinPoint,Object theResult){

        // display method we are returning from
        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("====> in @AfterReturning: from method: "+theMethod);

        // display data returned
        myLogger.info("====> result : "+theResult);
    }

    // add @AfterThrowing  advice
    @AfterThrowing(pointcut = "forAppFlow()",throwing = "theException")
    public void afterThrowing(JoinPoint joinPoint,Throwable theException){

        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("===> in @AfterThrow: from method: "+theMethod);
        myLogger.info("===> exception: "+theException);
    }


}
