package com.jsc.dao;

import java.util.List;

import com.jsc.bean.Users;


public interface IUserDAO {

	public String save(Users user);
	
	public Users findById(String id);
	
	public List<Users> findAll();
	
	public void update(Users user);
	
	public void delete(Users user);
	
	public void delAll();
	
}
