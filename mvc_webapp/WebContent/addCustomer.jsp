<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.qiuku.mvcapp.dao.CustomerDAO" import="com.qiuku.mvcapp.domain.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Customer</title>
</head>
<body>

    <% request.setCharacterEncoding("UTF-8"); %>
    
    <%	Object msg = request.getAttribute("message");
        Object flag = request.getAttribute("flag");
		if(msg != null && flag == "f"){
	%>
	<br>
	<font color="red"><%=msg %></font>
	<br>
	<% 
		} 
		else if(msg != null && flag == "s"){
	%>
	<br>
	<font color="green"><%=msg %></font>
	<br>
	<% 
		} 
	%>
	<h4>Add Customer</h4>
	<form action ="add.do" method="post">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<td>CustomerName:</td>
				<td><input type = "text" name="name" value="<%=request.getParameter("name")==null? "":request.getParameter("name")%>"></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type = "text" name="address" value="<%=request.getParameter("address")==null? "":request.getParameter("address")%>"></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type = "text" name="phone" value="<%=request.getParameter("phone")==null? "":request.getParameter("phone")%>"></td>
			</tr>
			<tr>
				<td ><input type="submit" value="Add"></td>
				<td><a href="index.jsp">Return to MainPage...</a></td>
			</tr>
		</table>
	</form>
	<br><br><hr>
	
	
</body>
</html>