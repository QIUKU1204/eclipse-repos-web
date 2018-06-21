package com.qiuku.bookstore.servlet;

import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// 实体类
import com.qiuku.bookstore.domain.Account;
import com.qiuku.bookstore.domain.Book;
import com.qiuku.bookstore.domain.ShoppingCart;
import com.qiuku.bookstore.domain.ShoppingCartItem;
import com.qiuku.bookstore.domain.Trade;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 在Servlet与DAO之间添加一个Service层: 其中定义了业务方法;
	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();
	// private BookDAO bookDAO = new BookDAOImpl();

	// 通过反射技术调用Servlet方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		System.out.println(methodName);
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			// TODO: invoke()
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			throw new RuntimeException(e);
		}
	}

	// 验证余额是否充足
	public StringBuffer validateBalance(HttpServletRequest request, Account account) {
		// 获取一个 字符串缓冲区对象 errors, 存放错误信息
		StringBuffer errors = new StringBuffer("");
		// 从 session 中获取购物车对象
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		// 比较 购物车商品总额 与 账户余额
		if (cart.getTotalMoney() > account.getBalance()) {
			errors.append("余额不足!");
		}
		return errors;
	}

	// 验证库存是否充足
	public StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		StringBuffer errors = new StringBuffer("");
		// 从 session 中获取购物车对象
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		for (ShoppingCartItem sci : cart.getItems()) {
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if (quantity > storeNumber) {
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
		return errors;
	}

	// 验证用户名和信用卡是否匹配
	public StringBuffer validateUser(User user, String creditCard) {
		boolean flag = false;
		StringBuffer errors = new StringBuffer("");
		if (user != null) {
			String realCreditCard = user.getCreditCard();
			// 将整数 int 转换成字符串 String
			// 1. String s = "" + i;
			// 2. String s = String.valueOf(i);
			// 3. String s = Integer.toString(i);
			if (creditCard.trim().equals(realCreditCard)) {
				flag = true;
			}
		}
		if (!flag) {
			errors.append("用户名和信用卡不匹配!");
		}
		return errors;
	}
	
	// 验证支付密码
	public StringBuffer validatePassword(User user, String password) {
		StringBuffer errors = new StringBuffer("");
		// 使用 password != user.getPassword() 出错
		// 必须先 trim 请求参数, 再 equal 另一个值
		if(!password.trim().equals(user.getPassword())) {
			errors.append("支付密码错误!");
		}
		return errors;
	}

	// 验证表单域是否符合基本的规则: 是否为空;
	public StringBuffer validateFormField(String username, String creditCard, String password) {
		StringBuffer errors = new StringBuffer("");
		// 用户名是否为空
		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空;");
		}
		// 账号是否为空
		if (creditCard == null || creditCard.trim().equals("")) {
			errors.append("信用卡不能为空;");
		}
		// 密码是否为空
		if (password == null || password.trim().equals("")) {
			errors.append("支付密码不能为空;");
		}
		// 正则表达式
		String pattern = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";
		boolean flag = Pattern.matches(pattern, creditCard);
		// 信用卡卡号匹配失败
		if (creditCard != null && !creditCard.trim().equals("") && flag != true) {
			errors.append("信用卡格式错误;");
		}

		return errors;
	}

	// Ajax 修改购物车商品数量
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		} catch (Exception e) {
		}

		if (id > 0 && quantity > 0)
			bookService.updateItemQuantity(sc, id, quantity);

		ShoppingCartItem sci = sc.getBooks().get(id);
		
		// TODO 创建一个 HashMap 集合类对象存放 JSON 键值对数据
		Map<String, Object> result = new HashMap<>();
		result.put("itemMoney", sci.getItemMoney());
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		// TODO 创建一个 Gson 对象
		Gson gson = new Gson();
		// TODO 调用 gson 的 toJson 方法将 result 集合转换为 JSON 格式的字符串
		String jsonStr = gson.toJson(result);
		// TODO 设置响应报文
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
	}

	/**
	 * TODO 以下是通过反射技术会调用到的业务方法
	 */

	/**
	 * TODO 清空购物车
	 */
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		/*
		 * // 当cart.jsp在WEB-INF目录外时
		 * request.getRequestDispatcher("/pages/cart.jsp").forward(request, response);
		 */
		// 当cart.jsp在WEB-INF目录内时
		response.sendRedirect(request.getContextPath() + "/pages/cart.jsp");
	}

	/**
	 * TODO 移除指定的购物项
	 */
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		// 从购物车中移除指定的购物项
		bookService.removeItemFromShoppingCart(sc, id);
		// 若移除后购物车为空, 则转发到
		if (sc.isEmpty()) {
			// request.getRequestDispatcher("/pages/cart.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath() + "/pages/cart.jsp");
			return;
		}
		// request.getRequestDispatcher("/pages/cart.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/pages/cart.jsp");
	}

	/**
	 * TODO 根据传入的请求参数page, 转发到指定的JSP页面: 如cash.jsp, cart.jsp;
	 */
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/pages/" + page + ".jsp").forward(request, response);
	}

	/**
	 * TODO 将商品加入购物车
	 */
	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先判断是否有用户在线
		String username = (String) request.getSession().getAttribute("username");
		if (username == null) {
			// 若没有
			// 传回 JSON 数据
			Map<String, Object> result = new HashMap<>();
			result.put("warning", "请先登录!");
			Gson gson = new Gson();
			String jsonStr = gson.toJson(result);
			response.setContentType("text/javascript");
			response.getWriter().print(jsonStr);
			return;
		}
		// 若有则继续addToCart
		// 获取商品的 id
		String idStr = request.getParameter("id");
		// 从 Session 中获取购物车对象
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean flag = false;
		if (id > 0) {
			// 调用 BookService 的 addToCart() 方法把商品放到购物车中
			flag = bookService.addToCart(id, sc);
		}
		if (flag) {
			// 传回 JSON 数据
			Map<String, Object> result = new HashMap<>();
			result.put("bookNumber", sc.getBookNumber());
			Gson gson = new Gson();
			String jsonStr = gson.toJson(result);
			response.setContentType("text/javascript");
			response.getWriter().print(jsonStr);
			return;
		}
		// 加入失败
		request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
	}

	/**
	 * TODO 查看指定书籍的详细信息
	 */
	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		Book book = null;
		if (id > 0) {
			book = bookService.getBook(id);
		}

		if (book == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}

		request.setAttribute("book", book);
		request.getRequestDispatcher("/pages/book.jsp").forward(request, response);
	}

	/**
	 * TODO 根据查询条件获取并返回一个Page对象
	 */
	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查询参数
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		// 默认查询条件
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try { // pageNo
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		try { // minPrice
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}

		try { // maxPrice
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}

		// 封装传入的查询条件, 若没有查询条件, 则默认为第1页, 价格区别 0 - MAX_VALUE;
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);
		// 给 books.jsp 返回一个 Page 对象;
		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/pages/books.jsp").forward(request, response);
	}

	/**
	 * 在book.jsp页面直接购买一本图书
	 */
	protected void buy(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 1. 获取图书 id
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Book book = null;
		if (id > 0) {
			book = bookService.getBook(id);
		}
		if (book == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		request.getSession().setAttribute("book", book);
		request.getRequestDispatcher("/pages/order1.jsp").forward(request, response);
	}

	/**
	 * 从购物车页面下订单的业务逻辑
	 */
	protected void order(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/pages/order.jsp").forward(request, response);
	}

	/**
	 * 提交订单的业务逻辑
	 */
	protected void commit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String address = request.getParameter("address");
		// 当收货信息为空时的处理
		if (name == null || name == "") {
			request.setAttribute("nameErr", "收货人不能为空");
		}
		if (telephone == null || telephone == "") {
			request.setAttribute("teleErr", "联系电话不能为空");
		}
		if (address == null || address == "") {
			request.setAttribute("addrErr", "收货地址不能为空");
		}
		if(request.getAttribute("nameErr") != null 
				|| request.getAttribute("teleErr") != null
				|| request.getAttribute("addrErr") != null) {
			System.out.println(request.getParameter("type"));
			if (request.getParameter("type") != null) {
				request.getRequestDispatcher("/pages/order1.jsp").forward(request, response);
				return;
			}
			request.getRequestDispatcher("/pages/order.jsp").forward(request, response);
			return;
		}
		// 创建一个 Trade 对象用于存储收货相关的信息
		Trade tempTrade = new Trade(name, telephone, address);
		request.getSession().setAttribute("tempTrade", tempTrade);
		if (request.getParameter("type") != null) {
			ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
			Book book = BookStoreWebUtils.getBook(request);
			bookService.clearShoppingCart(shoppingCart);
			bookService.addToCart(book.getId(), shoppingCart);
		}
		request.getRequestDispatcher("/pages/cash.jsp").forward(request, response);
	}

	/**
	 * TODO 结账的业务逻辑
	 */
	protected void cash(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// System.out.println(request.getAttribute("trade"));
		String username = request.getParameter("username");
		String creditCard = request.getParameter("creditCard");
		String password = request.getParameter("password");
		
		// 简单表单验证
		StringBuffer errors = validateFormField(username, creditCard, password);
		// 验证未通过则返回错误信息
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/pages/cash.jsp").forward(request, response);
			return;
		}
		
		// 必须先验证表单域再获取以下对象
		User user = userService.getUser(username);
		System.out.println(user.getPassword());
		Account account = accountService.getAccount(user.getUserId());
		int accountId = account.getAccountId();
		
		if (errors.toString().equals("")) {
			// 用户名和信用卡验证
			errors = validateUser(user, creditCard);
			if (errors.toString().equals("")) {
				// 支付密码验证
				errors = validatePassword(user, password);
				if (errors.toString().equals("")) {
					// 库存验证
					errors = validateBookStoreNumber(request);
					if (errors.toString().equals("")) {
						// 余额验证
						errors = validateBalance(request, account);
					}
				}
			}
			
		}
		// 验证未通过则返回错误信息
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/pages/cash.jsp").forward(request, response);
			return;
		}
		// 验证通过, 执行具体的业务逻辑操作
		// 从 session 中获取购物车 ShoppingCart 对象和 Trade 对象
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		Trade tempTrade = (Trade) request.getSession().getAttribute("tempTrade");
		if (shoppingCart.isEmpty()) {
			request.setAttribute("errors", "您的订单为空");
			request.getRequestDispatcher("/pages/cash.jsp").forward(request, response);
			return;
		}
		// 使用ShoppingCart中的内容进行结算, 需将购物车与支付分离
		bookService.cash(shoppingCart, tempTrade, username, accountId);
		// response.sendRedirect(request.getContextPath() + "/pages/success.jsp");
		request.getRequestDispatcher("/pages/success.jsp").forward(request, response);
	}

	/**
	 * 转向 UserServlet
	 */
	protected void getUserInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("userServlet?method=getUserInfo").forward(request, response);
	}
}
