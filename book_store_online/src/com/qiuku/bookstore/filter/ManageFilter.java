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

import com.qiuku.bookstore.domain.ShoppingCart;

public class ManageFilter implements Filter {

	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest)request;
		HttpServletResponse response2 = (HttpServletResponse)response;
		
		request2.setCharacterEncoding("UTF-8");
		// 若可以从 request 中获取请求参数 manager
		// 则把登录信息存储到 Cookie 中，并设置 Cookie 的最大时效为 900S
		String manager = request2.getParameter("manager");
		
		if(manager != null && !manager.trim().equals("")){
			Cookie cookie = new Cookie("manager", URLEncoder.encode(manager, "UTF-8"));
			cookie.setMaxAge(1800);
			response2.addCookie(cookie);
		}
		// 从Cookie中读取 manager
		else{ // manager == null
			Cookie [] cookies = request2.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie : cookies){
					String cookieName = cookie.getName();
					if("manager".equals(cookieName)){
						String val = cookie.getValue();
						manager = val;
						HttpSession session = request2.getSession();
						session.setAttribute("manager", URLDecoder.decode(manager, "UTF-8"));
					}
				}
			}			
		}
		
		// 若 login，或者存在 cookie，则通过当前Filter
		if(manager != null && !manager.trim().equals("")){
			// pass the request along the filter chain
			chain.doFilter(request2, response2);
		}else{
			// 若既没有 login，也没有 Cookie，则重定向到 login.jsp
			response2.sendRedirect(request2.getContextPath() + "/manage/login.jsp");
			// System.out.println(request2.getContextPath() + "/book-store/login.jsp");
		}	

	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
