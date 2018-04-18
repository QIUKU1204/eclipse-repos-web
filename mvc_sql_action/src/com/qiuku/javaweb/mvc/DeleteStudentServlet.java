package com.qiuku.javaweb.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteStudentServlet
 */
@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flowId = request.getParameter("flowId");
		System.out.println(flowId);
		StudentDao studentDao = new StudentDao();
		studentDao.deleteByFlowId(Integer.parseInt(flowId));
		
		request.getRequestDispatcher("/WEB-INF/delete_success.jsp").forward(request, response);
	}

}
