<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.qiuku.bookstore.domain.User"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>支付成功</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,600" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css"/>
	<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/flat-ui/2.3.0/js/flat-ui.min.js"></script>
    <!-- 当包含该jsp时, 删除操作的弹窗点击取消仍会继续执行 -->
    <!-- 作用: 对于click超链接的动作, 给该url添加两个后缀参数, 然后重定向到这个url -->
	<%-- <%@ include file="/commons/queryCondition.jsp" %> --%>	
	<style>
        .row{
            margin-left: 20px;
            margin-right: 20px;
        }
        .center{
			text-align: center;
		}
        .line-center{
            line-height:50px;
            text-align: center;
        }
        .bottom{
			position:relative; 
            bottom:0;
            width: 100%;
		}
        .row input{
            width: 50px;
            height: 40px;
        }
    </style>
</head>
<body>
	
	<!-- Static navbar -->
	<div class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">当当书店</a>
			</div>

			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li role="presentation" class="active"><a href="index.jsp">首页</a></li>
					<li role="presentation"><a href="userServlet?method=getTrades&username=${sessionScope.username }"
												target="_blank">我的订单</a></li>
					<li role="presentation"><a href="bookServlet?method=getUserInfo"
												target="_blank">个人中心</a></li>
					<li role="presentation"><a href="index.jsp">友情链接</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right hidden-sm">
					<c:if test="${sessionScope.username != null}">
					<li role="presentation">
						<a href="bookServlet?method=getUserInfo" target="_blank">
							<img alt="Brand" src="<%= request.getContextPath() %>/images/po.jpg"
								class="img-circle" style="width: 30px; height: 30px">
						</a>
					</li>
					<li role="presentation"><a href="<%=request.getContextPath()%>/book-store/exit.jsp">
						<small><span class="glyphicon glyphicon-off" aria-hidden="true"></span>退出</small></a></li>
					</c:if>
					<c:if test="${sessionScope.username == null}">
					<li role="presentation"><a href="<%=request.getContextPath()%>/book-store/login.jsp" 
						target="_blank">登录</a></li>
					<li role="presentation"><a href="<%=request.getContextPath()%>/book-store/signup.jsp" 
						target="_blank">注册</a></li>
					</c:if>
					<li role="presentation">
						<a href="bookServlet?method=forwardPage&page=cart" target="_blank">
						<span class="glyphicon glyphicon-shopping-cart">购物车</span>
						<c:if test="${sessionScope.ShoppingCart != null}">
							<span class="badge" id="bookNumber1"> ${sessionScope.ShoppingCart.bookNumber } </span>
						</c:if>
						<c:if test="${sessionScope.ShoppingCart == null}">
							<span class="badge" id="bookNumber2"></span>
						</c:if>
						</a>
					</li>					
				</ul>
			</div><!--/.nav-collapse -->	   
		</div><!-- container -->
	</div>

	<!--content-->
	<div class="container">
	    <div class="row thumbnail center col-sm-12">
	        <div class="col-sm-12">
	            <h1 class="text-center" style="margin-bottom: 30px">
	            <span class="glyphicon glyphicon-ok-circle">支付成功</span></h1>
	        </div>
	        <div class="col-sm-12 thumbnail">
	        	<p class="line-center">
	        		<strong>${sessionScope.username }</strong>,&nbsp;
	        		<span class="text-danger">您的订单支付成功, 快去看看吧! </span>
	        	</p>
	        </div>
	        <img class="thumbnail" alt="" src="<%=request.getContextPath()%>/images/lovely.gif"> 
	   </div>
	</div>	   
	
	<br/><br/><br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
</body>
</html>