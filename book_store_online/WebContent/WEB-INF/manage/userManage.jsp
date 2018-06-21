<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
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
	
	<div class="row center" style="margin-top: 80px;
		background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
		
	<div class="col-sm-12 line-center">
  		<h3 style="color: blue;"><strong>用户管理后台</strong></h3>
 		<a href="manageServlet?method=forward&page=index" role="button"
				class="btn btn-warning btn-sm active">后台菜单</a>
	</div>
	
	<div class="col-sm-12 thumbnail">
	    <div class="col-sm-1 line-center">用户ID</div>
	    <div class="col-sm-2 line-center">用户名</div>
	    <div class="col-sm-1 line-center">用户密码</div>
	    <div class="col-sm-3 line-center">绑定邮箱</div>
	    <div class="col-sm-2 line-center">绑定信用卡</div>
	    <div class="col-sm-2 line-center">账户余额</div>
	    <div class="col-sm-1 line-center">操作</div>
	</div>
	
	<c:forEach items="${userSet }" var="user">
	<div class="col-sm-12 list-group-item">
		<form action="manageServlet?method=updateBalance&id=${user.userId }" method="post">
		<div class="col-sm-1 line-center">
	        <span class="text-danger">${user.userId }</span>
  		</div>
  		<div class="col-sm-2 line-center">
	        <span style="color: green;">${user.username }</span>
	    </div>
	    <div class="col-sm-1 line-center">
	        <span class="text-danger">${user.password }</span>
	    </div>
	    <div class="col-sm-3 line-center">
	        <span class="text-danger">${user.email }</span>
	    </div>
	    <div class="col-sm-2 line-center">
	        <span class="text-danger">${user.creditCard }</span>
	    </div>
	    <div class="col-sm-2 line-center">
	        <span class="text-danger">¥${user.account.balance }</span>
	        &nbsp;
	        <input class="" type="text" size="3" 
	            name="increment" placeholder="充值数额"/>
	    </div>
	    <div class="col-sm-1 line-center">
	        <button type="submit" class="btn btn-info active">充值</button>
	    </div>
	    </form>
	</div>
	</c:forEach>
	
	</div><!-- row -->
	
	<br/>
	
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
	
</div>
</body>
</html>