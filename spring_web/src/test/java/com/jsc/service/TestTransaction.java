package com.jsc.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})

public class TestTransaction extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Before
	public void init(){
		Session session = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
	}
	
	@Test
	public void testQuery(){
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session session = holder.getSession();
		
		
		
	}
	
	@After
	public void close(){
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
//		holder.getSession().close();
		SessionFactoryUtils.closeSession(holder.getSession());
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	}
	
	
}
