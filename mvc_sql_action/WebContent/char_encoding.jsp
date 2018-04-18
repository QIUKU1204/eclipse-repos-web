<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Encoding!</title>
</head>
<body>
    
    <h4>TEST PAGE</h4>
    <br><br>
    
    <% request.setCharacterEncoding("UTF-8"); %>
    
        用户名:<%= request.getParameter("username") %>
    <br><br>
        密码:<%= request.getParameter("password") %>
        
        
</body>
</html>