package com.qiuku.bookstore.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;


public class UnallowFilter implements Filter {

	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest)request;
		HttpServletResponse response2 = (HttpServletResponse)response;
		
		request2.setCharacterEncoding("UTF-8");
		HttpSession session = request2.getSession();
		String username = (String) session.getAttribute("username");
		
		// 判断当前session中是否有username
		if(username != null && !username.trim().equals("")){
			// 若有则通过当前Filter
			chain.doFilter(request2, response2);
			return;
		}
		// 若没有则重定向到login.jsp
		response2.sendRedirect(request2.getContextPath() + "/book-store/login.jsp");
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
