package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckCodeServlet
 */
@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 获取请求参数: CHECK_CODE_PARAM_NAME;
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");

		// 2. 获取 session 中的 CHECK_CODE_KEY 属性值;ֵ
		String sessionCode = (String) request.getSession().getAttribute("CHECK_CODE_KEY");

		System.out.println(paramCode);
		System.out.println(sessionCode);

		// 3. 对比是否一致;
		if (!(paramCode != null && paramCode.equals(sessionCode))) {
			request.getSession().setAttribute("message", "验证码错误！");
			response.sendRedirect(request.getContextPath() + "/Check-Code/index.jsp");
			return;
		}
        
		request.getRequestDispatcher(request.getContextPath() + "/Check-Code/hello.jsp").forward(request, response);
		System.out.println("��������!");
	}

}
