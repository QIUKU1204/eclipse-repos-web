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

public class CheckLoginFilter implements Filter {

	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest)request;
		HttpServletResponse response2 = (HttpServletResponse)response;
		request2.setCharacterEncoding("UTF-8");
		// 若可以从request中获取请求参数username
		// 把登录信息存储到 Cookie 中，并设置 Cookie 的最大时效为 30S
		String username = request2.getParameter("username");
		if(username != null && !username.trim().equals("")){
			Cookie cookie = new Cookie("username", URLEncoder.encode(username, "UTF-8"));
			cookie.setMaxAge(300);
			response2.addCookie(cookie);
			HttpSession session = request2.getSession();
			session.setAttribute("username", username);
			ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
			if(sc == null){
				sc = new ShoppingCart();
				session.setAttribute("ShoppingCart", sc);
			}
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
						HttpSession session = request2.getSession();
						session.setAttribute("username", URLDecoder.decode(username, "UTF-8"));
						ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
						if(sc == null){
							sc = new ShoppingCart();
							session.setAttribute("ShoppingCart", sc);
						}
					}
				}
			}			
		}
		
		// 若 login，或者存在 cookie，则通过当前Filter
		if(username != null && !username.trim().equals("")){
			// pass the request along the filter chain
			chain.doFilter(request2, response2);
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
