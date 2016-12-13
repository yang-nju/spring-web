package com.jsc.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@Controller
@RequestMapping("/admin")
public class LoginController {

//	@RequestMapping("/login")
//	public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model){
//		model.addAttribute("name", "yang");
//		return "login";
//	}
	
	@RequestMapping("/manage")
	public String manage(){
//		SecurityUtils.getSubject().checkRole("admin");
        return "admin/myHello";
	}
	
//	@RequestMapping(value="/login", method=RequestMethod.GET)
//	public String login(){
//		System.out.println(InternalResourceViewResolver.REDIRECT_URL_PREFIX);
//		return "login";
//	}
	
//	@RequestMapping(value="/login",method=RequestMethod.POST)
//	public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Model model){
//		model.addAttribute("username", username);
//		return "index";
//	}
	
	@ExceptionHandler({UnauthorizedException.class})  
	@ResponseStatus(HttpStatus.UNAUTHORIZED)  
	public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {  
	    ModelAndView mv = new ModelAndView();  
	    mv.addObject("exception", e);  
	    mv.setViewName("unauthorized");  
	    return mv;  
	}   
	
	
}
