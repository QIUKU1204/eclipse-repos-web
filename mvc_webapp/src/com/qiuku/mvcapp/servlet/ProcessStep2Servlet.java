package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiuku.mvcapp.domain.SessionCustomer;

/**
 * Servlet implementation class ProcessStep2Servlet
 */
@WebServlet("/processStep2")
public class ProcessStep2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String cardType = request.getParameter("cardType");
		String card = request.getParameter("card");
		
		SessionCustomer customer = new SessionCustomer(name, address, cardType, card);
		
		// 2.
		HttpSession session = request.getSession();
		session.setAttribute("customer", customer);
		
		// 3.
		response.sendRedirect(request.getContextPath() + "/Shopping-Cart/confirm.jsp");
	}

}
