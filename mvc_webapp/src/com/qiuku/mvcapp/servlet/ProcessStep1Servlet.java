package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProcessStep1Servlet
 */
@WebServlet("/processStep1")
public class ProcessStep1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.
		String [] books = request.getParameterValues("book");
		// 2.
		HttpSession session = request.getSession();
		session.setAttribute("books", books);
		
		// 2.
		System.out.println(request.getContextPath() + "/Shopping-Cart/step2.jsp");
		response.sendRedirect(request.getContextPath() + "/Shopping-Cart/step2.jsp");
	}

}
