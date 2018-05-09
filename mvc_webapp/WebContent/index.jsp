<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.qiuku.mvcapp.domain.Customer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mvc Web App</title>
<script type="text/javascript" src="Scripts/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function(){
    	$(".delete").click(function(){
    		var content = $(this).parent().parent().find("td:eq(1)").text();
    		var flag = confirm("确定要删除" + content + "的信息吗？");
    		return flag;
    	})
    })
</script>
</head>
<body>

    <% request.setCharacterEncoding("UTF-8"); %>

    <%	Object msg = request.getAttribute("message");
        Object flag = request.getAttribute("flag");
		if(msg != null && flag == "false"){
	%>
	<br>
	<font color="red"><%=msg %></font>
	<br>
	<% 
		} 
		else if(msg != null && flag == "success"){
	%>
	<br>
	<font color="green"><%=msg %></font>
	<br>
	<% 
		} 
	%>
	<br/>
	
    <form action ="query.do" method="post">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<td colspan="2">CustomerName: <input type = "text" name="name" value="<%=request.getParameter("name")==null? "":request.getParameter("name")%>"></td>
			</tr>
			<tr>
				<td colspan="2">Address:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type = "text" name="address" value="<%=request.getParameter("address")==null? "":request.getParameter("address")%>"></td>
			</tr> 
			<tr>
				<td colspan="2">Phone:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input type = "text" name="phone" value="<%=request.getParameter("phone")==null? "":request.getParameter("phone")%>"></td>
			</tr>
			<tr>
			    <td >CheckCode: <input type="text" name="CHECK_CODE_PARAM_NAME" 
			                       value="<%=request.getParameter("CHECK_CODE_PARAM_NAME")==null? "":request.getParameter("CHECK_CODE_PARAM_NAME")%>"></td>
		        <!-- 生成验证码图片 -->
		        <td ><img alt="" src="<%= request.getContextPath() %>/validateColorServlet"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Query"></td> <td><a href = "addCustomer.jsp">Add New Customer</a></td>
			</tr>
		</table>
	</form>
	
	<br><br><hr>
	
	<%
		List<Customer> customers = (List<Customer>)request.getAttribute("customers");
		if(customers!=null&&customers.size()>0){
	%>
	
	<br><br>
	
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>
				<th>CustomerName</th>
				<th>Address</th>
				<th>Phone</th>
				<th>Edit\Delete</th>
			</tr>
			<%
				for(Customer customer:customers){
			%>
				<tr>
					<td><%=customer.getId() %></td>
					<td><%=customer.getName() %></td>
					<td><%=customer.getAddress() %></td>
					<td><%=customer.getPhone() %></td>
					<td><a href="edit.do?id=<%=customer.getId()%>">Edit\</a>
						<a href="delete.do?id=<%=customer.getId()%>" class="delete">Delete</a></td>
				</tr>
			<% 
				}
		}
			%>
		</table>
</body>
</html>