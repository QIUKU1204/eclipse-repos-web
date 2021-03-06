package com.qiuku.mvcapp.servlet;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.qiuku.mvcapp.domain.CriteriaCustomer;
import com.qiuku.mvcapp.domain.Customer;
import com.qiuku.mvcapp.domain.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.qiuku.mvcapp.dao.DAO;
import com.qiuku.mvcapp.dao.factory.CustomerDAOFactory;
import com.qiuku.mvcapp.dao.factory.UserDAOFactory;
import com.qiuku.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.qiuku.mvcapp.dao.impl.UserDAOJdbcImpl;
import com.qiuku.mvcapp.dao.CustomerDAO;
import com.qiuku.mvcapp.dao.UserDAO;



public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// 静态成员在内存中只有一个副本
	private CustomerDAO customerDAO = CustomerDAOFactory.getInstance().getCustomerDAO();
	
	// 1.Servlet直接调用DAO层的方法(耦合性高)
	// UserDAOJdbcImpl 的实例对象在每次请求 UserServlet 时都要创建;
	/*private UserDAO userDAO = new UserDAOImpl();*/
	// 2.使用工厂模式与工厂类(耦合性低)
	// UserDAOJdbcImpl 的实例对象在第一次请求 UserServlet 时创建一次即可;
	private UserDAO userDAO = UserDAOFactory.getInstance().getUserDAO();
	
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
			response.sendRedirect(request.getContextPath() + "/WEB-INF/error_404.jsp");
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
		String newname = request.getParameter("name");
		String newaddress = request.getParameter("address");
		String newphone = request.getParameter("phone");
		String oldname =request.getParameter("oldname");
		String oldaddress =request.getParameter("oldaddress");
		String oldphone =request.getParameter("oldphone");
		try {
			
			id=Integer.parseInt(request.getParameter("id"));
			Customer customer = new Customer(oldname,newaddress,newphone);
			customer.setId(id);
			if(newname==""||newaddress==""||newphone==""){ // 如果为空
				request.setAttribute("message", "Cannot be empty");
				if(newaddress=="")
					customer.setAddress(oldaddress);
				if(newphone=="")
					customer.setPhone(oldphone);
				request.setAttribute("customer", customer);
				request.getRequestDispatcher("/updateCustomer.jsp").forward(request, response);
				return ;
			}
			// 若此处使用else if(count>0), 则没有改名即被认为是重名
			else if(!oldname.equals(newname)) { // 如果改名了
				// 若在此方法内发生java.sql.SQLException，程序会由于空指针异常而中断，并转向catch块;
				long count = customerDAO.getCountWithName(newname); //检查是否重名
				if (count>0) { // 如果改的名字与表中其他用户名重名
					request.setAttribute("message", "Name " + newname + " is already occupied");
					request.setAttribute("customer", customer);
					request.getRequestDispatcher("/updateCustomer.jsp").forward(request, response);
					return ;
				}
				// 改名且不重名
				customer.setName(newname);
				// 若在此方法内发生java.sql.SQLException，程序不会中断，也不会转向catch块;
				customerDAO.update(customer);
				System.out.println("修改更新成功！");
		    	response.sendRedirect("query.do");
			}
			else {
				// 剩下的情况是: 1.什么都没改，一切保持原样；2.没改名，但改了地址或电话；
				customer.setName(newname);
				// 若在此方法内发生java.sql.SQLException，程序不会中断，也不会转向catch块;
				customerDAO.update(customer);
				System.out.println("修改更新成功！");
				response.sendRedirect("query.do");
				System.out.println("可以继续执行呢！不会中断哦！");
			}
		}
		catch (Exception e) {
			// java.lang.NullPointerException 这是一个空指针异常
			// 由于customerDAO.getCountWithName()方法产生SQL异常并返回空值null而导致的
			System.out.println(e);
		}
        System.out.println("好了，再见吧...");
	}
	
	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");
		// 当用户名、密码或验证码为空时的处理
		if (username == null || username == "") {
			request.setAttribute("message1", "用户名不能为空");
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
		if (password == null || password == "") {
			request.setAttribute("message2", "密码不能为空");
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
		if (paramCode == null || paramCode == "") {
			request.setAttribute("message3", "验证码不能为空");
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}

		// 2. 获取 Session 中的 CHECK_CODE_KEY 属性值;ֵ
		String sessionCode = (String) request.getSession().getAttribute("CHECK_CODE_KEY");
		// 3. 对比是否一致;
		if (!(paramCode != null && paramCode.equals(sessionCode))) {
			// 注意，在使用session的情况下，当验证成功时，也会显示验证码错误的信息
			/*
			 * request.getSession().setAttribute("message", "验证码错误！");
			 * request.getSession().setAttribute("flag", "false");
			 * response.sendRedirect(request.getContextPath() + "/index.jsp");
			 */

			request.setAttribute("message3", "验证码错误!");
			// 1. 重定向的 / 表示 http://服务器ip:端口/
			// 2. 请求转发的 / 表示 http://服务器ip:端口/项目名
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
		
		
		long count = userDAO.getCountWithName(username);
		
		if(count > 0) { // 如果重名
			request.setAttribute("message1", "账号已被注册");
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
		}
		else { // 如果不为空&&不重名
			User user = new User(username, password);
			userDAO.save(user);
			request.setAttribute("message4", "注册成功, 快去登录吧!");
			request.setAttribute("username", username);
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
		}			
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 获取请求参数: CHECK_CODE_PARAM_NAME;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");
		// 当用户名、密码或验证码为空时的处理
		if (username == null || username == "") {
			request.setAttribute("message1", "用户名不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		if (password == null || password == "") {
			request.setAttribute("message2", "密码不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		if (paramCode == null || paramCode == "") {
			request.setAttribute("message3", "验证码不能为空");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}

		// 2. 获取 Session 中的 CHECK_CODE_KEY 属性值;ֵ
		String sessionCode = (String) request.getSession().getAttribute("CHECK_CODE_KEY");
		// 3. 对比是否一致;
		if ( !(paramCode != null && paramCode.equals(sessionCode)) ) {
			// 注意，在使用session的情况下，当验证成功时，也会显示验证码错误的信息
			/*request.getSession().setAttribute("message", "验证码错误！");
			request.getSession().setAttribute("flag", "false");
			response.sendRedirect(request.getContextPath() + "/index.jsp");*/
			
			request.setAttribute("message3", "验证码错误!");
			//1. 重定向的  / 表示 http://服务器ip:端口/
		    //2. 请求转发的  / 表示 http://服务器ip:端口/项目名
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}		
		
		long count = userDAO.getCountWithName(username);
		if (count == 0) {
			request.setAttribute("message1", "用户名错误!");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		
		User user = userDAO.get(username);
		// 1. 对比 输入密码 和 数据库中的密码 是否一致
		if (!password.equals(user.getPassword())) {
			request.setAttribute("message2", "密码错误!");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		
		// 2. 把 username 作为request属性放入请求中
		request.setAttribute("username", user.getUsername());
		System.out.println(user.getUsername());
		// 3. 把请求转发到index.jsp(不能使用重定向)
		request.getRequestDispatcher("/book-store/index.jsp").forward(request, response);
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
