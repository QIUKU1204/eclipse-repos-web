package com.qiuku.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.qiuku.bookstore.domain.Account;
import com.qiuku.bookstore.domain.Trade;
import com.qiuku.bookstore.domain.User;
import com.qiuku.bookstore.service.AccountService;
import com.qiuku.bookstore.service.UserService;
import com.google.gson.Gson;
import com.qiuku.bookstore.dao.BaseDAO;
import com.qiuku.bookstore.dao.UserDAO;
import com.qiuku.bookstore.dao.impl.UserDAOImpl;

public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// 1. Servlet直接调用DAO层的方法(耦合性高)
	// UserDAOJdbcImpl 的实例对象在每次请求 UserServlet 时都要创建;
	/*private UserDAO userDAO = new UserDAOImpl();*/
	// 2. 使用工厂模式与工厂类(耦合性低)
	// UserDAOJdbcImpl 的实例对象在第一次请求 UserServlet 时创建一次即可;
	/*private UserDAO userDAO = UserDAOFactory.getInstance().getUserDAO();*/
	// 3. 使用 service 层
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 使用EncodingFilter替代
		/*request.setCharacterEncoding("UTF-8");*/
		String methodName = request.getParameter("method");
		System.out.println(methodName);
		try {
			// 3.利用反射技术获取methodName对应的方法
			Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			// 4.利用反射调用对应的方法
			method.invoke(this, request, response);
		} catch (Exception e) {
			// e.printStackTrace();
			// java.lang.reflect.InvocationTargetException
			System.out.println(e);
			// WEB-INF目录下的资源只能通过请求转发访问到, 重定向无法访问
			// response.sendRedirect(request.getContextPath() + "/WEB-INF/error_404.jsp");
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
		}
		
	}
    
	
	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String creditCard = request.getParameter("creditCard");
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");
		// 正则表达式
		String pattern = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";
		boolean flag = Pattern.matches(pattern, creditCard);
		// 信用卡卡号匹配失败
		if(flag != true) {
			request.setAttribute("creditCardErr", "信用卡格式错误");
		}
		// 当用户名、密码或验证码为空时的处理
		if (username == null || username == "") {
			request.setAttribute("usernameErr", "用户名不能为空");
		}
		if (password == null || password == "") {
			request.setAttribute("passwordErr", "密码不能为空");
		}
		if (email == null || email == "") {
			request.setAttribute("emailErr", "邮箱不能为空");
		}
		if (creditCard == null || creditCard == "") {
			request.setAttribute("creditCardErr", "信用卡不能为空");
		}
		if (paramCode == null || paramCode == "") {
			request.setAttribute("checkcodeErr", "验证码不能为空");
		}
		if(request.getAttribute("usernameErr") != null
				|| request.getAttribute("passwordErr") != null
				|| request.getAttribute("emailErr") != null
				|| request.getAttribute("creditCardErr") != null
				|| request.getAttribute("checkcodeErr") != null) {
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
		
		// 2. 获取 Session 中的 CHECK_CODE_KEY 属性值;ֵ
		String sessionCode = (String) request.getSession().getAttribute("CHECK_CODE_KEY");
		// 3. 对比是否一致;
		if (!(paramCode != null && paramCode.equals(sessionCode))) {
			request.setAttribute("checkcodeErr", "验证码错误!");
			// 1. 重定向的 / 表示 http://服务器ip:端口/
			// 2. 请求转发的 / 表示 http://服务器ip:端口/项目名
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
		
		long count1 = userService.getCountWithName(username);
		long count2 = userService.getCountWithEmail(email);
		if(count1 > 0) { // 如果重名
			request.setAttribute("usernameErr", "昵称已被使用");
		}
		if(count2 > 0) { // 如果邮箱已被使用
			request.setAttribute("emailErr", "邮箱已被使用");
		}
		if(request.getAttribute("usernameErr") != null ||
				request.getAttribute("emailErr") != null) {
			request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
			return;
		}
	
		// 如果不为空&&不重名
		User user = new User(username, password, email, creditCard);
		userService.save(user);
		request.setAttribute("successMsg", "注册成功, 快去登录吧!");
		request.setAttribute("username", username);
		request.getRequestDispatcher("/book-store/signup.jsp").forward(request, response);
		return;				
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 获取请求参数: CHECK_CODE_PARAM_NAME;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String paramCode = request.getParameter("CHECK_CODE_PARAM_NAME");
		// 当用户名、密码或验证码为空时的处理
		if (username == null || username == "") {
			request.setAttribute("usernameErr", "用户名不能为空");
		}
		if (password == null || password == "") {
			request.setAttribute("passwordErr", "密码不能为空");
		}
		if (paramCode == null || paramCode == "") {
			request.setAttribute("checkcodeErr", "验证码不能为空");
		}
		if(request.getAttribute("usernameErr") != null || request.getAttribute("passwordErr") != null
				|| request.getAttribute("checkcodeErr") != null) {
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
			
			request.setAttribute("checkcodeErr", "验证码错误!");
			//1. 重定向的  / 表示 http://服务器ip:端口/
		    //2. 请求转发的  / 表示 http://服务器ip:端口/项目名
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}		
		
		long count = userService.getCountWithName(username);
		if (count == 0) {
			request.setAttribute("usernameErr", "用户名错误!");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		
		User user = userService.getUser(username);
		// 1. 对比 输入密码 和 数据库中的密码 是否一致
		if (!password.equals(user.getPassword())) {
			request.setAttribute("passwordErr", "密码错误!");
			request.getRequestDispatcher("/book-store/login.jsp").forward(request, response);
			return;
		}
		
		// 2. 把 username 作为request属性放入请求中
		request.setAttribute("username", user.getUsername());
		System.out.println(user.getUsername());
		// 3. 把请求转发到index.jsp(不能使用重定向)
		request.getRequestDispatcher("/book-store/index.jsp").forward(request, response);
	}
    
	private void getTrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取 username 请求参数的值
		String username = request.getParameter("username");
		
		if (username == "") {
			request.getRequestDispatcher("/pages/trades.jsp").forward(request, response);
			return;
		}
		
		// 调用 UserService 的 getUserWithTrades方法 获取 User 对象：
		// 要求 User 对象的 trades 是被装配好的, 并且每一个 Trade 对象的 items 也被装配好
		User user = userService.getUserWithTrades(username);
		
		if (user == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		request.setAttribute("user", user);
		request.getRequestDispatcher("/pages/trades.jsp").forward(request, response);
	}
	
	private void getTrade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取 username 请求参数的值
		String tradeId = request.getParameter("tradeId");
		// 调用 UserService 的 getUserWithTrades方法 获取 User 对象：
		// 要求 User 对象的 trades 是被装配好的, 并且每一个 Trade 对象的 items 也被装配好
		// System.out.println("111111111");
		Trade trade = userService.getTrade(Integer.parseInt(tradeId));
		// System.out.println("222222222");
		if (trade == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		request.setAttribute("trade", trade);
		request.getRequestDispatcher("/pages/trade.jsp").forward(request, response);
	}
   
	protected void getUserInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		// 当 session 中没有 username 时(即没有用户在线的情况)
		if(username == null || username.trim().equals("")) {
			response.sendRedirect(request.getContextPath() + "/book-store/login.jsp");
			return;
		}
		User user = userService.getUserWithTrades(username);
		if (user == null) {
			request.getRequestDispatcher("/WEB-INF/error_404.jsp").forward(request, response);
			return;
		}
		Account account = accountService.getAccount(user.getUserId());
		request.setAttribute("account", account);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/pages/userinfo.jsp").forward(request, response);
	}
    
	// Ajax 修改用户密码
	protected void alterUserPass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		// 获取原密码和新密码
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String username = (String) request.getSession().getAttribute("username");
		User user1 = userService.getUser(username);
		
		// TODO 创建一个 HashMap 集合类对象存放 键值对JSON数据
		Map<String, Object> result = new HashMap<>();
		// TODO 创建一个 Gson 对象
		Gson gson = new Gson();
		// TODO 设置响应报文
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		
		// 当为 空值 或 空字符 时的处理
		if (oldPass == null || oldPass.trim().equals("")) {
			result.put("oldPassErr", "原密码不能为空");
		}
		if (newPass == null || newPass.trim().equals("")) {
			result.put("newPassErr", "新密码不能为空");
		}
		if(result.get("oldPassErr") != null ||
				result.get("newPassErr") != null) {
			// TODO 调用 gson 的 toJson 方法将 result 集合转换为 JSON 格式的字符串
			String jsonStr = gson.toJson(result);
			// TODO 传回 JSON 数据
			response.getWriter().print(jsonStr);
			return;
		}
		// 当输入的原密码与该用户实际密码不相同
		if (!oldPass.trim().equals(user1.getPassword())) {
			result.put("oldPassErr", "原密码错误");
			String jsonStr = gson.toJson(result);
			response.getWriter().print(jsonStr);
			return;
		}
		// 当输入的新密码等于原密码
		if (newPass.trim().equals(oldPass.trim())) {
			result.put("oldPassErr", "不能是以前使用过的密码");
			String jsonStr = gson.toJson(result);
			response.getWriter().print(jsonStr);
			return;
		}
		
		// 创建一个 User 对象保存 username 和 newPass
		User user2 = new User();
		user2.setUsername(username);
		user2.setPassword(newPass);
		// 调用 UserService 的 updatePass 方法进行修改
		userService.updatePass(user2);
		
		result.put("oldPassErr", "修改成功");
		String jsonStr = gson.toJson(result);
		response.getWriter().print(jsonStr);
	}
}
