package com.jsc.util;

//import org.aopalliance.intercept.Joinpoint;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 基于AspectJ和Annotation实现
 * 需要启用<aop:aspectj-autoproxy />
 * 可采用基于xml的配置方式
 * 
 * @Before – Run before the method execution
 * @After – Run after the method returned a result
 * @AfterReturning – Run after the method returned a result, intercept the returned result as well.
 * @AfterThrowing – Run after the method throws an exception
 * @Around – Run around the method execution, combine all three advices above.
 * 
 * @author yangyang
 *
 */
@Aspect
public class LogAspect {

	//定义切入点，简化注解配置
//	@Pointcut("execution(* *.*.*.service..*.insert*(..)) || execution(* *.*.*.service..*.update*(..)) ")
//	public void insertPointcut() {}
//	
//	
	
	
	@Before("execution(* com.jsc.shiro.test.*.*(..))")
	public void logBefore(JoinPoint joinPoint){
		System.out.println("logBefore() is running!");
		System.out.println("hijacked method : " + joinPoint.getSignature().getName());
		System.out.println("args : " + joinPoint.getArgs());
		System.out.println("******");
		
		Logger log = Logger.getLogger(joinPoint.getClass());
		
		log.info("logBefore() is running!");
		log.info("hijacked method : " + joinPoint.getSignature().getName());
		log.info("args : " + joinPoint.getArgs());
		log.info("******");
	}
	
	//采用定义切点的方式，与上述方法等价
//	@Around("insertPointcut()")
	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable{
		
		System.out.println("logAround() is running!");
		System.out.println("hijacked method : " + joinPoint.getSignature().getName());
		System.out.println("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
			
		System.out.println("Around before is running!");
		joinPoint.proceed(); //continue on the intercepted method
		System.out.println("Around after is running!");
			
		System.out.println("******");
	}
	
}
