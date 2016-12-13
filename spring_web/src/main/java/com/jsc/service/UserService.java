package com.jsc.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.jsc.bean.*;
import com.jsc.dao.hibernate.UsersDAO;

@Service
public class UserService{

	@Autowired
	private UsersDAO userDao;
	
	private Logger log = Logger.getLogger(UserService.class);
	
	public Users findByUserName(String username)
	{	
		
		
		System.out.println(username);
		List ls = userDao.findByProperty("username", username);
		if(ls == null || ls.size() == 0){
			return null;
		}
		Users user = (Users)ls.get(0);
		System.out.println(user.getUsername());
		return user;
	}
	
	public String saveUser(Users user){
		 return userDao.save(user);
	}
	
	public Set<String> findRoleStr(String username){return null;}
	public Set<Permission> findPermisionStr(String username){return null;}
	
	public void delAll(){
		return;
	}
	
	public void update(Users user){
		userDao.update(user);
	}
	
	public void delByID(String id){
		Users user = userDao.findById(id);
		if(user!= null){
			userDao.delete(user);
		}
	}
	
	public List<Users> findAll(){
		return userDao.findAll();
	}
	
	public Users findByID(String id){
		return userDao.findById(id);
	}
	
	
}
