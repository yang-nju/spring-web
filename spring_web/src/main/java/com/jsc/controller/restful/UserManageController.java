package com.jsc.controller.restful;

import java.util.List;




import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jsc.bean.Users;
import com.jsc.service.UserService;

/**
 * This class show the standard style of restful CRUD
 * 批量操作：删除、添加
 * session&cookies
 * @author yangyang
 *
 */


@RestController
@RequestMapping("/user")
public class UserManageController {

	@Autowired
	private UserService userService;
	
	Logger log = Logger.getLogger(UserManageController.class);
	
//	@RequestMapping(value="/user",method=RequestMethod.GET,produces="text/plain;charset=utf-8")
//	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	@RequiresRoles("admin")
//	@RequiresUser
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Users>> list(){
		List<Users> users = userService.findAll();
		
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		session.setAttribute("test", "yagnyang");
		if(users.isEmpty()){
			return new ResponseEntity<List<Users>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<Users> get(@PathVariable("id") String id, HttpServletRequest req){
		log.info("find user by id : " + id);
		Users user = userService.findByID(id);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		System.out.println(session.getId());
		if(user == null){
			throw new RuntimeException("bad crack!");
		}
		return new ResponseEntity<Users>(user,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody Users user, UriComponentsBuilder ucBuilder){
		if(user == null){
			throw new RuntimeException();
		}
		log.info("create user with name : " + user.getUsername());
		if(userService.findByUserName(user.getUsername()) != null){
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		String id = userService.saveUser(user);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(ucBuilder.path("/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<String>(header, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Users> updateUser(@PathVariable("id") String id, @RequestBody Users user){
		if(user == null || user.getId() == null){
			return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
		}
		log.info("update user with id : " + user.getId());
		userService.update(user);
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Users> deleteUser(@PathVariable("id") String id){
		log.info("delete user with id : " + id);
		Users user = userService.findByID(id);
		if(user == null){
			return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
		}
		userService.delByID(id);
		return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<Users> delAllUser(){
		log.info("delete all users");
		userService.delAll();
		return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
	}
	
	
}
