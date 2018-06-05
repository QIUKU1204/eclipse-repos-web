package com.qiuku.bookstore.servlet;

import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 实体类
import com.qiuku.bookstore.domain.Account;
import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.ShoppingCart;
import com.qiuku.bookstore.domain.ShoppingCartItem;
import com.qiuku.bookstore.domain.User;
// service层
import com.qiuku.bookstore.service.AccountService;
import com.qiuku.bookstore.service.BookService;
import com.qiuku.bookstore.service.UserService;

import com.qiuku.bookstore.web.BookStoreWebUtils;
import com.qiuku.bookstore.web.CriteriaBook;
import com.qiuku.bookstore.web.Page;
import com.google.gson.Gson;

public class BookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// 在Servlet与DAO之间添加一个Service层: 其中定义了业务方法;
	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	private AccountService accountService= new AccountService();
	// private BookDAO bookDAO = new BookDAOImpl();
			
	// 通过反射技术调用Servlet方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			// TODO: invoke()
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 简单验证
	    // 验证表单域的值是否符合基本的规范: 是否为空, 是否可以转为 int 类型, 是否是一个 email;
		// 不需要查询数据库或调用任何的业务方法.
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		StringBuffer errors = validateFormField(username, accountId);
		
		//表单验证通过
		if(errors.toString().equals("")){
			errors = validateUser(username, accountId);
			
			//用户名和账号验证通过
			if(errors.toString().equals("")){
				errors = validateBookStoreNumber(request);
				
				//库存验证通过
				if(errors.toString().equals("")){
					errors = validateBalance(request, accountId);
				}
			}
		}
		
		if(!errors.toString().equals("")){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
		//验证通过执行具体的逻辑操作
		bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId); 
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}
	
	// 验证余额是否充足
	public StringBuffer validateBalance(HttpServletRequest request, String accountId){
		// 获取一个 字符串缓冲区对象 errors, 存放错误信息
		StringBuffer errors = new StringBuffer("");
		// 从 session 中获取购物车对象
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		// 根据 账户id 获取 Account 对象
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		
		if(cart.getTotalMoney() > account.getBalance()){
			errors.append("余额不足!");
		}
		return errors;
	}
	
	// 验证库存是否充足
	public StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		// 从 session 中获取购物车对象
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci: cart.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
		return errors;
	}
	
	//验证用户名和账号是否匹配
	public StringBuffer validateUser(String username, String accountId){
		boolean flag = false;
		StringBuffer errors2 = new StringBuffer("");
		// UserService
		User user = userService.getUser(username);
		if(user != null){
			int userId = user.getUserId();
			// 将整数 int 转换成字符串 String
			// 1. String s = "" + i;
			// 2. String s = String.valueOf(i);
			// 3. String s = Integer.toString(i);
			if(accountId.trim().equals("" + userId)){
				flag = true;
			}
		}
		if(!flag){
			errors2.append("用户名和账号不匹配");
		}
		return errors2;
	}
	
	//验证表单域是否符合基本的规则: 是否为空;
	public StringBuffer validateFormField(String username, String accountId){
		StringBuffer errors = new StringBuffer("");
		// 用户名是否为空
		if(username == null || username.trim().equals("")){
			errors.append("用户名不能为空<br>");
		}
		// 账号是否为空
		if(accountId == null || accountId.trim().equals("")){
			errors.append("账号不能为空");			
		}
		
		return errors;
	}

	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取 id, quanity 等请求参数, 再获取购物车对象, 
		// 最后调用 service层 的业务方法做修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		int id = -1;
		int quantity = -1;
		
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {}
		
		if(id > 0 && quantity > 0)
			bookService.updateItemQuantity(sc, id, quantity);
		
		//5. 传回 JSON 数据: bookNumber:xx, totalMoney
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc, id);
		
		if(sc.isEmpty()){
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
	
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 获取商品的 id
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		boolean flag = false;
		
		if(id > 0){
			// 从 Session 中获取购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			// 调用 BookService 的 addToCart() 方法把商品放到购物车中
			flag = bookService.addToCart(id, sc);
		}
		
		if(flag){
			// 若加入购物车成功, 则通过调用 getBooks() 方法转发到 books.jsp
			getBooks(request, response);
			return;
		}
		// 加入失败
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
	}
	
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		Book book = null;
		if(id > 0)
			book = bookService.getBook(id);
			// book = bookDAO.getBook(id);
		
		if(book == null){
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
	}
	
	protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 查询参数
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		// 默认查询条件
		int pageNo = 1;
		int minPrice= 0;
		int maxPrice = Integer.MAX_VALUE;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {}
		
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		
		// 封装传入的查询条件, 若没有查询条件, 则默认为第1页, 价格区别 0 - MAX_VALUE;
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);
		// 给 books.jsp 返回一个 Page 对象;
		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}
	
}
