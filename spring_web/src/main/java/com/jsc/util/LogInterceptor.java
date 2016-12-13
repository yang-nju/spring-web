package com.jsc.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


/**
 * simple spring AOP Advice（原生）
 * aroundAdvisor
 * 
 * 另：实现MethodBeforeAdvice接口得到beforeAdvisor
 * @author yangyang
 *
 */
public class LogInterceptor implements MethodInterceptor {
	
	public Object invoke(MethodInvocation invocation) throws Throwable{
		Logger log = Logger.getLogger(invocation.getClass());
		log.info("------------------operation log------------------");
		log.info(invocation.getMethod().getName() + ":BEGIN!");
		log.info("method arguments : " + Arrays.toString(invocation.getArguments()));
		Object obj = invocation.proceed();
		log.info(invocation.getMethod().getName() + ":END!");
		log.info("------------------end------------------");
		return obj;
	}

	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Logger log = Logger.getLogger(method.getClass());
		log.info("------------------operation log------------------");
		log.info(method.getName() + ":BEGIN!");
		Object obj = methodProxy.invoke(method, args);
		log.info(method.getName() + ":END!");
		log.info("------------------end------------------");
		return obj;
	}
	
}
