<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>后台菜单</title>
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
    	html, body {
			font-family: 'Microsoft YaHei', sans-serif;
			font-weight: 100;
			width: 100%;
			height: 100%;
			margin: 0;
			padding: 0;
			color: #333;
		}
    </style>
</head>
<body>

<div class="container" >
	<div class="row">
	<div class="jumbotron center" style="width: 400px; height: 400px; margin-top: 80px;
		background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
		<div class="line-center">
  			<h4 style="color: red;">后台管理菜单</h4>
		</div><hr/>
		<div class="col-sm-12">
			<a href="manageServlet?method=forward&page=bookManage" role="button"
				class="btn btn-info btn-lg btn-block active center">图书管理</a>
		</div><br/><hr/><br>
		<div class="col-sm-12">
			<a href="manageServlet?method=forward&page=userManage" role="button"
				class="btn btn-default btn-lg btn-block active center">用户管理</a>
		</div><br/><hr/><br/>
		<div class="col-sm-12">
			<a href="manageServlet?method=forward&page=tradeManage" role="button"
				class="btn btn-warning btn-lg btn-block active center">订单管理</a>
		</div>
	
	</div>
	</div>
</div>
</body>
</html>