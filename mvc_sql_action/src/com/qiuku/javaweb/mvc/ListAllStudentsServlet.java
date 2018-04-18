package com.qiuku.javaweb.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListAllStudentsServlet
 */
@WebServlet("/listAllStudents")
public class ListAllStudentsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudentDao studentDao = new StudentDao();
		List<Student> students = studentDao.getAll();
		
		request.setAttribute("students", students);
		request.getRequestDispatcher("/display_student_info.jsp").forward(request, response);
	}

}
