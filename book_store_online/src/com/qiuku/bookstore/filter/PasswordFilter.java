package com.qiuku.bookstore.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.factory.UserDAOFactory;
import com.qiuku.bookstore.dao.impl.UserDAOJdbcImpl;
import com.qiuku.bookstore.domain.User;


public class PasswordFilter implements Filter {

	// ps: 该静态实例对象同时只能在一处被使用，否则调用对象的方法时会产生空指针异常
	// private UserDAO userDAO2 = UserDAOFactory.getInstance().getUserDAO();
	private UserDAO userDAO2 = new UserDAOJdbcImpl();
	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (password == null || password == "") {
			request.setAttribute("message2", "密码不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		User user = userDAO2.get(username);
		// 1. 对比 输入密码 和 数据库中的密码 是否一致
		if (!password.equals(user.getPassword())) {
			request.setAttribute("message2", "密码错误");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
