package com.jsc.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DaoSupport;

/**
 * 统一异常处理类
 * 基于SpringAOP
 * @author yangyang
 *
 */
//@Aspect
public class ExceptionAspect {

	
	public void afterThrowding(Method method, Object[] args, Object target, Exception ex) throws Throwable{
		
		Logger log = Logger.getLogger(target.getClass());
		log.error("**************************************************************");  
        log.error("Error happened in class: " + target.getClass().getName());  
        log.error("Error happened in method: " + method.getName());  
        for (int i = 0; i < args.length; i++)  
        {  
            log.error("arg[" + i + "]: " + args[i]);  
        }  
        log.error("Exception class: " + ex.getClass().getName());  
        log.error("ex.getMessage():" + ex.getMessage());  
        ex.printStackTrace();  
        log.info("**************************************************************");  
        
		//判断异常，根据不同异常返回错误
        if(ex.getClass().equals(DataAccessException.class)){
        	throw new BusinessException("数据库操作失败！");
        }
        if(ex.getClass().equals(IllegalArgumentException.class)){
        	throw new BusinessException("参数异常！");
        }
        if(ex.getClass().equals(IOException.class)){
        	throw new BusinessException("资源访问异常/IO异常！");
        }
        if(ex.getClass().equals(SQLException.class)){
        	throw new BusinessException("数据库操作异常！");
        }
        else{
        	throw new BusinessException("程序内部错误，操作失败！" + ex.getMessage());
        }
        
	}
	
}
