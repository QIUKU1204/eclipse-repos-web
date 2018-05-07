<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Step1 ！</title>
</head>
<body>

    <h4>Step1: 选择要购买的图书: </h4>
	
	<form action="<%= request.getContextPath() %>/processStep1" method="post">
	    <!-- cellpadding属性设置内边距；   cellspacing属性设置外边距(默认值为2)；  border属性设置有无边框-->
		<table border="1" cellpadding="10" cellspacing="0">
			
			<tr>
				<td>书名</td>
				<td>购买</td>
			</tr>
			
			<tr>
				<td>Java</td>
				<td><input type="checkbox" name="book" value="Java"/></td>
			</tr>
			
			<tr>
				<td>Oracle</td>
				<td><input type="checkbox" name="book" value="Oracle"/></td>
			</tr>
			
			<tr>
				<td>Struts</td>
				<td><input type="checkbox" name="book" value="Struts"/></td>
			</tr>
			
			<tr>
			    <!-- <td>标签的colspan属性用于设置单元格合并的列数 -->
				<td colspan="2">
					<input type="submit" value="下一步"/>
				</td>
			</tr>
			
		</table>
	</form>

</body>
</html>