<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.List" import="com.qiuku.bookstore.domain.User"%>
<html>
<head>

</head>
<body>
	
<%

	response.sendRedirect(request.getContextPath() + "/bookServlet?method=getBooks");

%>  

</body>
</html>