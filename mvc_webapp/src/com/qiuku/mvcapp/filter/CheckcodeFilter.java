package com.qiuku.mvcapp.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckcodeFilter implements Filter {

	
	public void destroy() {
		System.out.println("destroy...");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");
		if (paramCode == null || paramCode == "") {
			request.setAttribute("message3", "验证码不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		// 2. 获取 Session 中的 CHECK_CODE_KEY 属性值;ֵ
		String sessionCode = (String) ((HttpServletRequest) request).getSession().getAttribute("CHECK_CODE_KEY");
		// 3. 对比是否一致;
		if (!(paramCode != null && paramCode.equals(sessionCode))) {
			// 注意，在使用session的情况下，当验证成功时，也会显示验证码错误的信息
			/*
			 * request.getSession().setAttribute("message", "验证码错误！");
			 * request.getSession().setAttribute("flag", "false");
			 * response.sendRedirect(request.getContextPath() + "/index.jsp");
			 */

			request.setAttribute("message3", "验证码错误！");
			// 1. 重定向的 / 表示 http://服务器ip:端口/
			// 2. 请求转发的 / 表示 http://服务器ip:端口/项目名
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
