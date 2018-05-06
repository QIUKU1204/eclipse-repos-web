<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Session2!</title>
</head>
<body>

    <% 
		HttpSession session2 = request.getSession(true);
		out.println(session);
		out.print("<br>");
		
		// 获取 Session 的最大时效, 默认为 30 分钟. 
		out.print(session2.getMaxInactiveInterval());
		// 销毁当前HttpSession对象
		session.invalidate();
	%>

</body>
</html>