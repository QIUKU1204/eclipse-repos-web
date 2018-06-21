<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登录管理员后台</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,600" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<!-- <link rel="stylesheet" href="https://cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css"/> -->
	<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- <script src="https://cdn.bootcss.com/flat-ui/2.3.0/js/flat-ui.min.js"></script> -->
    <style>
    	.center{
  			display: table;
			margin-left: auto;
  			margin-right: auto;
    	}
		.line-center{
            text-align: center;
        }
    
    </style>
</head>
<body>

<div class="container" >
	<div class="jumbotron center" style="width: 400px; height: 400px; margin-top: 80px;
		background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
		
		<div class="line-center">
  			<strong>管理员登录</strong>
		</div><hr/>
		
		<form action="manageServlet?method=login" method="post">
		<div class="form-group">
    		<label for="text1"><small>账号</small></label>
    		<input type="text" class="form-control" id="text1" 
    			name="manager" value="${param.manager }" placeholder="管理员账号">
  		</div>
  		<c:if test="${requestScope.managerErr != null}">
  		<span id="usernameErr" style="color:red">${requestScope.managerErr }</span>
  		</c:if>
  		<div class="form-group">
    		<label for="password1"><small>密码</small></label>
    		<input type="password" class="form-control" id="password1" 
    			name="password" value="${param.password}" placeholder="管理员密码">
  		</div>
  		<c:if test="${requestScope.passwordErr != null}">
  		<span id="passwordErr" style="color:red">${requestScope.passwordErr }</span>
  		</c:if><br>
  		<button type="submit" class="btn btn-default">登录</button>
		</form>
	
	</div>
</div>
</body>
</html>