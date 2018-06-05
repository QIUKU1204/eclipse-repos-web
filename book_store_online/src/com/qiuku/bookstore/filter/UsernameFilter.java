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
import com.qiuku.bookstore.dao.impl.UserDAOImpl;
import com.qiuku.bookstore.domain.User;


public class UsernameFilter implements Filter {
	
	// ps: 该静态实例对象同时只能在一处被使用，否则调用对象的方法时会产生空指针异常
	// private UserDAO userDAO = UserDAOFactory.getInstance().getUserDAO();
    private UserDAO userDAO2 = new UserDAOImpl();
	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		if (username == null || username == "") {
			request.setAttribute("message1", "用户名不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		
		long count = userDAO2.getCountWithName(username);
		if (count == 0) {
			request.setAttribute("message1", "用户名错误");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

    /*
     * TODO FilterConfig对象代表当前filter在web.xml中的配置信息
     */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init...");
	}

}
