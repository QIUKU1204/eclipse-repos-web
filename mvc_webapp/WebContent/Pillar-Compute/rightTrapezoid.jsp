<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计算直角梯形中的柱子高度</title>
</head>
<body>

    <h2>你好，这里是直角梯形柱子计算！</h2>
    <% request.setCharacterEncoding("UTF-8"); %>
    
    <%	Object msg = request.getAttribute("message");
		if(msg != null){
	%>
	<h3><font color="red"><%=msg %></font></h3>
	<br>
	<% 
		} 
	%>
    <form action ="rightTrapezoid.do" method="post">
		<table style="font-size:20px;">
			<tr>
				<td>输入梯形上底h:</td>
				<td><input type = "text" name="H1" value="<%=request.getParameter("H1")==null?"":request.getParameter("H1") %>"></td> 
			</tr>
			<tr>
				<td>输入梯形下底H:</td>
				<td><input type = "text" name="H2" value="<%=request.getParameter("H2")==null?"":request.getParameter("H2") %>"></td>
			</tr>
			<tr>
				<td>输入梯形的高L:</td>
				<td><input type = "text" name="L" value="<%=request.getParameter("L")==null?"":request.getParameter("L") %>"></td>
			</tr>
			<tr>
				<td>输入L1......Ln:</td>
				<td><input type = "text" name="Ln" value="<%=request.getParameter("Ln")==null?"":request.getParameter("Ln") %>" ></td>
				<td><font color="green">输入规范: 要计算多少根柱子就应输入多少个下底边长;Ln必须小于等于L;数值之间使用英文逗号分隔;</font></td>
			</tr>
			<tr>
				<td><input type="submit" value="开始计算"></td>
			</tr>
		</table>
	</form>
	
	<br><br><hr>
	
	<%
	    float[] HnArray = (float[])request.getAttribute("HnArray");
		if(HnArray!=null&&HnArray.length>0){
	%>
	
	<br>
	
	<h3>输出结果: 柱子高度H1......Hn</h3>
	
		<table border="1" cellpadding="10" cellspacing="0">
			
			<tr>
				<%
				for(float Hn: HnArray){
			    %>
					<th><%=Hn %></th>
			</tr>
			<% 
				}
		}
			%>

		</table>
	
	<br>
	
	<h3><a href="/Pillar-Compute/column.jsp">返回初始页面</a></h3>

</body>
</html>