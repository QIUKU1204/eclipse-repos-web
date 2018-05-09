<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.qiuku.mvcapp.domain.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Customer</title>
</head>
<body>
    
    <% request.setCharacterEncoding("UTF-8"); %>
    
    <%	Object msg = request.getAttribute("message");
		if(msg != null){
	%>
	<br>
	<font color="red"><%=msg %></font>
	<br>
	<% 
		}
		Customer customer =(Customer)request.getAttribute("customer");
	%>
	<form action ="update.do" method="post">
	    <table>
	       <tr>
				<td><input type="hidden" name="id" value=<%= customer.getId()%>></td>
				<td><input type="hidden" name="oldname" value=<%= customer.getName()%> ></td>
				<td><input type="hidden" name="oldaddress" value=<%= customer.getAddress() %>></td>
				<td><input type="hidden" name="oldphone" value=<%= customer.getPhone()%> ></td>
			</tr>
		</table>
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<td>CustomerName:</td>
				<td><input type = "text" name="name" value="<%=customer.getName()%>"></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type = "text" name="address" value="<%=customer.getAddress()%>"></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input type = "text" name="phone" value="<%=customer.getPhone()%>"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Update"></td>
				<td><a href="index.jsp">Return to MainPage...</a></td>
			</tr>
		</table>
	</form>
	<br><br><hr>
	

</body>
</html>