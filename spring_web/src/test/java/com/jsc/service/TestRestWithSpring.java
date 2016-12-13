package com.jsc.service;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jsc.bean.Users;


public class TestRestWithSpring {

	public static final String url = "http://localhost.:8080/spring_web";
	
	//get
	@Test
	public void listAllUsers(){
		try{
			
			RestTemplate rest = new RestTemplate();
			List<LinkedHashMap<String, Object>> userMap = rest.getForObject(url+"/user/", List.class);
			if(userMap != null){
				for(LinkedHashMap<String, Object> map : userMap){
	                System.out.println("User : id=" + map.get("id"));
	            }
			}
			else{
				System.out.println("no user exist!");
			}
			
//			ResponseEntity<List> users = rest.getForEntity(url + "/user/", List.class);
//			System.out.println(users.getBody().size());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	/* GET */
    private void getUser(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        Users user = restTemplate.getForObject(url+"/user/1", Users.class);
        System.out.println(user);
    }
    
    /* POST */
    private void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        Users user = new Users("yang-sp", "123", null, false);
        URI uri = restTemplate.postForLocation(url+"/user/", user, Users.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private void updateUser() {
        System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        Users user = new Users("yang-sp", "123", null, false);
        user.setId("1");
        restTemplate.put(url+"/user/1", user);
        System.out.println(user);
    }
 
    /* DELETE */
    private void deleteUser() {
        System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url+"/user/3");
    }
 
 
    /* DELETE */
    private void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url+"/user/");
    }
	
	
}
