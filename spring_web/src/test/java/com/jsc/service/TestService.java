package com.jsc.service;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jsc.bean.ResultEntity;
import com.jsc.bean.Users;
import com.jsc.dao.hibernate.GenericHibernateDao;
import com.jsc.shiro.PasswordHelper;

/**
 * 测试类可继承如下两个类：
 * AbstractJUnit4SpringContextTests
 * AbstractTransactionalJUnitSpringContextTests ： 用户测试事务相关
 * 
 * @author yangyang
 *
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring-config.xml"})

//@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)  //标记applicationcontext已经dirty，需要重新加载；主要用于关闭缓存对测试的影响

//以下配置为了消除多用户测试数据耦合，代码执行完后自动回滚事务
@Transactional
@TransactionConfiguration(defaultRollback=false)


public class TestService extends AbstractJUnit4SpringContextTests{
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Autowired
//	private TestDao userService;
	private GenericHibernateDao<Users, String> userService;
	
	@Before
	public void setUp(){
		//
	}
	
	@Test
	public void testAddUser(){
		Users user = new Users("yang1", "123", "", Boolean.FALSE);
		passwordHelper.encryptPassword(user);
//		userService.save(user);
		
		Users user1 = new Users("yang2", "123", "", Boolean.FALSE);
		passwordHelper.encryptPassword(user1);
//		userService.save(user1);
		
		
		
		UUIDHexGenerator idGen = new UUIDHexGenerator();
		
		String sql = "insert into users(id,username,password,salt) values(?,?,?,?)";
		List<Object[]> paras = new ArrayList<Object[]>();
		paras.add(new Object[]{idGen.generate(null, null),"yang1","123","34sfj"});
		paras.add(new Object[]{idGen.generate(null, null),"yang2","123","r3werf"});
		userService.batchInsertBySql(sql, paras, 
				new Type[]{StringType.INSTANCE,StringType.INSTANCE,StringType.INSTANCE,StringType.INSTANCE});
	}
	
	@Test
	public void testUpdate(){
		Users user = userService.findByID("3");
		user.setPassword("321");
		userService.update(user);
	}
	
	
	@Test
	public void testDel(){
		String sql = "delete from users where username = ?";
		
		userService.updateBySql(sql, new String[]{"yang2"}, new Type[]{StringType.INSTANCE});
	}
	
	@Test
	public void testQuery(){
		String sql = "select id,username,locked from users where username like '%yang%'";
		List ls = userService.findBySql(sql, null, null,ResultEntity.class);
		System.out.println(ls.size());
	}
	
	@Test
	public void testHqlQuery(){
		String sql = "select new com.jsc.shiro.bean.ResultEntity(id,username,locked) from Users where username like '%yang%'";
		List ls = userService.find(sql, null, null);
		System.out.println(ls.size());
	}
	
	
	
}
