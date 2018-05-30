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

import org.apache.jasper.tagplugins.jstl.core.Out;


public class CheckLoginFilter implements Filter {

	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request2 = (HttpServletRequest)request;
		HttpServletResponse response2 = (HttpServletResponse)response;
		request2.setCharacterEncoding("UTF-8");
		// 若可以从request中获取请求参数username
		// 把登录信息存储到 Cookie 中，并设置 Cookie 的最大时效为 30S
		String username = request2.getParameter("username");
		// String username = request2.getAttribute("username");
		// System.out.println(username);
		if(username != null && !username.trim().equals("")){
			Cookie cookie = new Cookie("username", URLEncoder.encode(username, "UTF-8"));
			cookie.setMaxAge(30);
			response2.addCookie(cookie);
		}
		// 从Cookie中读取用户信息，若存在则打印欢迎信息
		else{
			Cookie [] cookies = request2.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie : cookies){
					String cookieName = cookie.getName();
					if("username".equals(cookieName)){
						String val = cookie.getValue();
						username = val;
						request.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
					}
				}
			}			
		}
		
		// 空字符串是""，会创建一个对象，有内存空间；而null，不会创建对象，没有内存空间
		// String.trim()方法remove字符串的首尾空白字符 (leading and trailing whitespace)	
		if(username != null && !username.trim().equals("")){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}else{
			// 若既没有 login，也没有 Cookie，则重定向到 login.jsp
			response2.sendRedirect(request2.getContextPath() + "/book-store/login.jsp");
			// System.out.println(request2.getContextPath() + "/book-store/login.jsp");
		}	

	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
