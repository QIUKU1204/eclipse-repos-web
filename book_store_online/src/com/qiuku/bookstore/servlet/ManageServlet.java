package com.qiuku.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;
import java.util.Set;
import java.util.regex.*;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jasper.tagplugins.jstl.core.Out;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.qiuku.bookstore.domain.Account;
import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.Trade;
import com.qiuku.bookstore.domain.User;
import com.qiuku.bookstore.service.AccountService;
import com.qiuku.bookstore.service.BookService;
import com.qiuku.bookstore.service.UserService;
import com.qiuku.bookstore.web.CriteriaBook;
import com.qiuku.bookstore.web.Page;
import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.UserDAOImpl;

public class ManageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	// 在Servlet与DAO之间添加一个Service层: 其中定义了业务方法;
	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		System.out.println(methodName);
		try {
			// 利用反射技术获取methodName对应的方法
			Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			// 利用反射调用对应的方法
			method.invoke(this, request, response);
		} catch (Exception e) {
			System.out.println(e);
			// WEB-INF目录下的资源只能通过请求转发访问到, 重定向无法访问
			// response.sendRedirect(request.getContextPath() + "/WEB-INF/error_404.jsp");
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
		}
		
	}
	
	
	/**
	 * TODO 以下是反射将会调用的方法
	 */
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 获取请求参数: manager, password
		String manager = request.getParameter("manager");
		String password = request.getParameter("password");
		
		// 当用户名、密码或验证码为空时的处理
		if (manager == null || manager.trim().equals("")) {
			request.setAttribute("managerErr", "管理员账号不能为空");
		}
		if (password == null || password.trim().equals("")) {
			request.setAttribute("passwordErr", "密码不能为空");
		}
		if(request.getAttribute("managerErr") != null 
				|| request.getAttribute("passwordErr") != null) {
			request.getRequestDispatcher("/manage/login.jsp").forward(request, response);
			return;
		}
		
		// 当管理员账号错误时
		if (!manager.trim().equals("root")) {
			request.setAttribute("managerErr", "管理员账号错误!");
			request.getRequestDispatcher("/manage/login.jsp").forward(request, response);
			return;
		}
		if (!password.trim().equals("network")) {
			request.setAttribute("passwordErr", "密码错误!");
			request.getRequestDispatcher("/manage/login.jsp").forward(request, response);
			return;
		}
		
		// 2. 把 username 放入 session 中
		request.getSession().setAttribute("manager", manager);
		
		// 3. 把请求转发到index.jsp(不能使用重定向)
		request.getRequestDispatcher("/WEB-INF/manage/index.jsp").forward(request, response);
	}

	protected void forward(HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException {
		String page = request.getParameter("page");
		if(page.trim().equals("bookManage")) {
			response.sendRedirect(request.getContextPath() + "/manageServlet?method=getBooks");
			return;
		}
		else if(page.trim().equals("userManage")) {
			response.sendRedirect(request.getContextPath() + "/manageServlet?method=getUsers");
			return;
		}
		else if(page.trim().equals("tradeManage")){
			response.sendRedirect(request.getContextPath() + "/manageServlet?method=getTrades");
			return;
		}
		else if(page.trim().equals("index")){
			request.getRequestDispatcher("/WEB-INF/manage/index.jsp").forward(request, response);
			return;
		}
	}
	
	protected void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String pageNoStr = request.getParameter("pageNo");
		String priceStr = request.getParameter("price");
		String storeNumberStr = request.getParameter("storeNumber");
		
		int id = 0;
		int pageNo = 1;
		float price = 0;
		int storeNumber = 0;
		
		try { // book.id
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		try { // page.pageNo
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		try { // book.price
			price = Float.parseFloat(priceStr);
		} catch (NumberFormatException e) {
		}
		try { // book.storeNumber
			storeNumber = Integer.parseInt(storeNumberStr);
		} catch (NumberFormatException e) {
		}
		// 创建一个Book对象保存传入的请求参数
		Book book = new Book(id, price, storeNumber);
		// 更新图书的售价和库存
		bookService.updateBook(book);
		response.sendRedirect(request.getContextPath() + "/manageServlet?method=getBooks&pageNo=" + pageNoStr);
	}
	
	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查询参数
		String pageNoStr = request.getParameter("pageNo");

		// 默认查询条件
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try { // pageNo
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		// 封装传入的查询条件, 若没有查询条件, 则默认为第1页, 价格区别 0 - MAX_VALUE;
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);
		// 给 books.jsp 返回一个 Page 对象;
		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/WEB-INF/manage/bookManage.jsp").forward(request, response);
	}
	
	protected void updateTradeStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String tradeIdStr = request.getParameter("id");
		System.out.println(tradeIdStr);
		String status = "已发货";
		
		int tradeId = 0;
		try { // trade.tradeId
			tradeId = Integer.parseInt(tradeIdStr);
		} catch (NumberFormatException e) {
		}
		
		userService.updateTradeStatus(tradeId, status);
		response.sendRedirect(request.getContextPath() + "/manageServlet?method=getTrades");
	}
	
	protected void getTrades(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// 调用 UserService 的 getTrades 方法获取 trades 表的所有记录：
		Set<Trade> tradeSet = userService.getTrades();
		// tradeSet.iterator();
		if (tradeSet == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		request.setAttribute("tradeSet", tradeSet);
		request.getRequestDispatcher("/WEB-INF/manage/tradeManage.jsp").forward(request, response);
	}
	
	protected void getUsers(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 调用 UserService 的 getUsers 方法获取 users 表的所有记录：
		Set<User> userSet = userService.getUsers();
		if (userSet == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		for(User user: userSet) {
			Account account = accountService.getAccount(user.getUserId());
			user.setAccount(account);
		}
		request.setAttribute("userSet", userSet);
		request.getRequestDispatcher("/WEB-INF/manage/userManage.jsp").forward(request, response);
	}
	
	protected void updateBalance(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String idStr = request.getParameter("id");
		String incrementStr = request.getParameter("increment");
		int accountId = 0;
		float increment = 0;
		try { // account.accountId
			accountId = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		try { // increment
			increment = Float.parseFloat(incrementStr);
		} catch (NumberFormatException e) {
		}
		accountService.recharge(accountId, increment);
		response.sendRedirect(request.getContextPath() + "/manageServlet?method=getUsers");
	}
	
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	} 
}
