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


public class ExitFilter implements Filter {

	
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
		if(username != null){
			// 删除session中的username和ShoppingCart属性
			session.setAttribute("username", null);
			session.setAttribute("ShoppingCart", null);
			/*// 删除username这个cookie
			Cookie newCookie = new Cookie("username", null);
			newCookie.setMaxAge(0);
			newCookie.setPath("/");
			response2.addCookie(newCookie);*/
			// 通过当前Filter
			chain.doFilter(request2, response2);
			return;
		}
		response2.sendRedirect(request2.getContextPath() + "/book-store/login.jsp");
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
