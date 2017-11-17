package com.jsc.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 开启跨域访问
 * 通过过滤器给所有的httpresponse添加跨域访问头
 * Add CORS specific headers in each response
 * solve the problem "NO 'Access-Control-Allow-Origin'" problem
 * @author yangyang
 *
 */
public class CORSFilter implements Filter{

//	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		System.out.println("CORS filter on..........");
		HttpServletResponse response = (HttpServletResponse)arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		//继续链传递
        arg2.doFilter(arg0, arg1);
	}

//	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
