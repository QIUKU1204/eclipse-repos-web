<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Step2 ！</title>
</head>
<body>

    <h4>Step2: 请输入寄送地址和信用卡信息: </h4>
	
	<form action="<%= request.getContextPath() %>/processStep2" method="post">
	    <!-- cellpadding属性设置内边距；   cellspacing属性设置外边距(默认值为2)；  border属性设置有无边框-->
		<table cellpadding="5" cellspacing="0" border="1">
		
			<tr>
				<td colspan="2">寄送信息</td>
			</tr>
			
			<tr>
				<td>姓名</td>
				<td><input type="text" name="name"/></td>
			</tr>
			
			<tr>
				<td>地址</td>
				<td><input type="text" name="address"/></td>
			</tr>
			
			<tr>
				<td colspan="2">信用卡信息</td>
			</tr>
			
		<tr>
			<td>类型</td>
			<td>
				<input type="radio" name="cardType" value="Visa"/>Visa
				<input type="radio" name="cardType" value="Master"/>Master
			</td>
		</tr>
		
		<tr>
			<td>卡号</td>
			<td>
				<input type="text" name="card"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="submit" value="提交"/></td>
		</tr>
			
	</table>
	</form>

</body>
</html>