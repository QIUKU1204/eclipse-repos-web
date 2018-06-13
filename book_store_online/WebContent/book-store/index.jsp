<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.List" import="com.qiuku.bookstore.domain.User"%>
<html>
<head>

</head>
<body>

<%-- <div class="container" style="position: absolute; left: 300px; right: 300px; top: 80px">
	<div class="row" style="margin-left: 200px; padding-left: 200px; margin-right: 200px; padding-right: 200px">
		<div class="col-xs-12" style="text-align: center; padding: 25px; background-image: url('<%=request.getContextPath()%>/book-store/bg.jpg')">

			<h2><font color="#FF9797">当当书店</font><br />
				<!-- cookie的值不能是中文, 若为中文则需要编码为字节码 -->
				<!-- 此处使用 cookie.username.value 时中文用户名需要重新编码为字节码 --> 
				<small style="color: #D9B300">${cookie.username.value}, 欢迎来到知识的海洋 ! </small>
				<small style="color: #D9B300">${requestScope.username} , 欢迎来到知识的海洋 ! </small>
			</h2><br/> 
			<a href="#" class="thumbnail"> <img alt="" src="<%=request.getContextPath()%>/images/lovely.gif"></a> 
			<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
			切换账号? <a href="<%=request.getContextPath()%>/book-store/login.jsp">返回登录页</a>
		</div>
	</div>
</div> --%>
	
<%

	response.sendRedirect(request.getContextPath() + "/bookServlet?method=getBooks");

%>
    
</body>
</html>