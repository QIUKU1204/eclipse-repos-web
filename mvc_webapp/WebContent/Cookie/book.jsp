<%@ page import="java.util.*" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Latest View!</title>
</head>
<body>

    <% request.setCharacterEncoding("UTF-8"); %>
    <!-- 使用Cookie显示最近浏览商品 -->

    <h4>Book Detail Page</h4>
	
	Book: <%= request.getParameter("book") %>
	
	<br><br>
	
	<a href="books.jsp">Return</a>
	
	<% 
		String book = request.getParameter("book");
	
		// 把书籍信息以Cookie方式回传给浏览器，并删除一个 Cookie
		// 1. 确定要被删除的 Cookie：
		// 删除前提: ATGUIGU_BOOK_ 开头的 Cookie 数量大于或等于 5，
		Cookie [] cookies = request.getCookies();
		
		// 保存所有 ATGUIGU_BOOK_ 开头的 Cookie
		List<Cookie> bookCookies = new ArrayList<Cookie>();
		// 用来保存和 books.jsp 传入的 book 匹配的那个 Cookie
		Cookie tempCookie = null;
		
		if(cookies != null && cookies.length > 0){
			for(Cookie c: cookies){
				String cookieName = c.getName();
				if(cookieName.startsWith("ATGUIGU_BOOK_")){
					bookCookies.add(c);
					
					if(c.getValue().equals(book)){
						tempCookie = c;
					}
				}
			}
		}
		
		// 若从 books.jsp 页面传入的 book 不在 ATGUIGU_BOOK_ 数组 的 Cookie 中，
		// 则删除较早的那个 Cookie，即 ATGUIGU_BOOK_  数组的第一个 Cbookie
		if(bookCookies.size() >= 5 && tempCookie == null){
			tempCookie = bookCookies.get(0);
		}
		
		// 若在其中，则删除ATGUIGU_BOOK_ 数组中的匹配 Cookie
		if(tempCookie != null){
			tempCookie.setMaxAge(0);
			response.addCookie(tempCookie);
		}
	
		// 把从 books.jsp 传入的 book 作为一个 Cookie 返回
		
		Cookie cookie = new Cookie("ATGUIGU_BOOK_" + book, book);
		response.addCookie(cookie);
		
	%>

</body>
</html>