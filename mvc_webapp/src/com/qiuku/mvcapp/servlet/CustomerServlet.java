package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiuku.mvcapp.domain.Customer;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.qiuku.mvcapp.dao.DAO;
import com.qiuku.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.CriteriaCustomer;



public class CustomerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
	
	// private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();
	
    /*多请求解决方案1： customerServlet?method=add/query/delete/update
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		switch(method) {
		case "add":add(request,response);break;
		case "query":query(request,response);break;
		case "delete":delete(request,response);break;
		case "update": update(request,response);break;
		}
	}*/
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取ServletPath: /edit.do 或 /delete.do
		String servletPath = request.getServletPath();
		// 2.去除 / 和 .do, 得到edit或delete这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0,methodName.length()-3);
		
		try {
			// 3.利用反射技术获取methodName对应的方法
			Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			// 4.利用反射调用对应的方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			// e.printStackTrace();
			// 可以有一些响应
			response.sendRedirect("/WEB-INF/error_404.jsp");
		}
		
	}
    
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("edit");
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// update
    	System.out.println("update");
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// save()
    	System.out.println("add");
	}
	
	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get(),getAll,getForListWithCriteriaCustomer()
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		CriteriaCustomer criteriaCustomer = new CriteriaCustomer(name, address, phone);
		
		// 1.调用CustomerDAO的getForListWithCriteriaCustomer()方法得到符合查询条件的Customer数据集
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(criteriaCustomer);
		// 2.把Customer数据集作为request属性放入请求中
		request.setAttribute("customers", customers);
		// 3.把请求转发到index.jsp(且不能使用重定向)
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// delete
    	// System.out.println("delete");
    	int id = 0;
    	
    	// try ... catch的作用: 防止String类型的request.getParameter("id")不能转为int类型
    	try {
			id = Integer.parseInt(request.getParameter("id"));
			System.out.println("delete customer" + id);
			customerDAO.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	response.sendRedirect("query.do");
	}
    
}
