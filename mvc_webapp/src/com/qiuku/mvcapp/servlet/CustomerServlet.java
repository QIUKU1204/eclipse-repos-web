package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.qiuku.mvcapp.domain.Customer;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.qiuku.mvcapp.dao.DAO;
import com.qiuku.mvcapp.dao.factory.CustomerDAOFactory;
import com.qiuku.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.CriteriaCustomer;



public class CustomerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
	
	private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();
	
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
		request.setCharacterEncoding("UTF-8");
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
			// java.lang.reflect.InvocationTargetException
			System.out.println(e);
			response.sendRedirect("/WEB-INF/error_404.jsp");
		}
		
	}
    
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("edit");
		String idStr = request.getParameter("id");
		try {
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			request.setAttribute("customer", customer);
		} catch (NumberFormatException e) {} 
		
		request.getRequestDispatcher("/updateCustomer.jsp").forward(request, response);
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// update
		System.out.println("update");
		Integer id =0;
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String oldname =request.getParameter("oldname");
		String oldaddress =request.getParameter("oldaddress");
		String oldphone =request.getParameter("oldphone");
		// long count1 = customerDAO.getCountWithName(name); //检查是否重名
		try {
			id=Integer.parseInt(request.getParameter("id"));
			Customer customer = new Customer(oldname,address,phone);
			customer.setId(id);
			if(name==""||address==""||phone==""){ // 如果为空
				request.setAttribute("message", "Cannot be empty");
				if(address=="")
					customer.setAddress(oldaddress);
				if(phone=="")
					customer.setPhone(oldphone);
				request.setAttribute("customer", customer);
				request.getRequestDispatcher("/updateCustomer.jsp").forward(request, response);
				return ;
			}
			else if(!oldname.equalsIgnoreCase(name)) { // 如果改名了
				// 若在此方法内发生java.sql.SQLException，程序会由于空指针异常而中断，并转向catch块;
				long count = customerDAO.getCountWithName(name); //检查是否重名
				response.sendRedirect("query.do");
				if(count>0) {
					request.setAttribute("message", "Name " + name + " is already occupied");
					request.setAttribute("customer", customer);
					request.getRequestDispatcher("/updateCustomer.jsp").forward(request, response);
					return ;
				}
			}
			// 如果不为空且不重名
			customer.setName(name);
			// 若在此方法内发生java.sql.SQLException，程序不会中断，也不会转向catch块;
			customerDAO.update(customer);
			response.sendRedirect("query.do");
			System.out.println("可以继续执行呢！不会中断哦！");
		}
		catch (Exception e) {
			// java.lang.NullPointerException 这是一个空指针异常
			// 由于customerDAO.getCountWithName()方法产生SQL异常并返回空值null而导致的
			System.out.println(e);
		}
        System.out.println("好了，再见吧...");
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// save()
    	System.out.println("add");
    	String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		// 当getCountWithName()方法内发生SQL异常并返回空值null时，此处将产生空指针异常;
		long count = customerDAO.getCountWithName(name);
		if(name==""||address==""||phone==""){ // 如果为空
			request.setAttribute("message", "Cannot be empty!");
			request.setAttribute("flag", "f");
			request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
		}
		else if(count>0) { // 如果已存在
			request.setAttribute("message", "Name " + name + " is already occupied!");
			request.setAttribute("flag", "f");
			request.getRequestDispatcher("/addCustomer.jsp").forward(request, response);
		}
		else {
			Customer customer = new Customer(name,address,phone);
			customerDAO.save(customer);
			request.setAttribute("flag", "s");
			request.setAttribute("message", "add new customer " + name + " successfully!");
			request.getRequestDispatcher("/addCustomer.jsp").forward(request, response);
		}			
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
