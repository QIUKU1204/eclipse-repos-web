<%@ page import="com.qiuku.mvcapp.domain.SessionCustomer" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm ！</title>
</head>
<body>

    <% 
        SessionCustomer customer = (SessionCustomer)session.getAttribute("customer");
		String [] books = (String[])session.getAttribute("books");
	%>
	<!-- cellpadding属性设置内边距；   cellspacing属性设置外边距(默认值为2)；  border属性设置有无边框-->
	<table cellpadding="7" cellspacing="0" border="1">
		<tr>
			<td>客户姓名</td>
			<td><%= customer.getName() %></td>
		</tr>
		<tr>
			<td>寄送地址</td>
			<td><%= customer.getAddress() %></td>
		</tr>
		<tr>
			<td>信用卡卡号</td>
			<td><%= customer.getCard() %></td>
		</tr>
		<tr>
			<td>信用卡类型</td>
			<td><%= customer.getCardType() %></td>
		</tr>
		<tr>
			<td>Books:</td>
			<td>
				<% 
					for(String book: books){
						out.print("《"+book+"》");
						out.print("<br>");
					}
				%>
			</td>
		</tr>
	</table>

</body>
</html>