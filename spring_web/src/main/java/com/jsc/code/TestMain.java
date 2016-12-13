package com.jsc.code;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jsc.dao.hibernate.GenericHibernateDao;

public class TestMain {

	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
//		Person p = context.getBean("person", Person.class);
//		p.info();
		
		GenericHibernateDao  dao = context.getBean(GenericHibernateDao.class);
		dao.save(null);
		
	}
	
}
