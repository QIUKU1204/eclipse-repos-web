<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>支付确认</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,600" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css"/>
	<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/flat-ui/2.3.0/js/flat-ui.min.js"></script>
    <%-- <%@ include file="/commons/queryCondition.jsp" %> --%>
	<style>
		.row{
			margin-top: 20px;;
		}
		.center{
			text-align: center;
		}
		.line-center{
            line-height: 50px;
            text-align: center;
        }
        .bottom{
			position:relative; 
            bottom:0;
            width: 100%;
		}
		.pagination{
			background: #16A085;
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
		h1, h2, h3, h4, h5, h6 {
			font-weight: 100;
			align-content: center;
			margin: 0 !important;
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
					<li role="presentation"><a href="<%= request.getContextPath() %>/pages/userinfo.jsp"
												target="_blank">个人中心</a></li>
					<li role="presentation"><a href="index.jsp">友情链接</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right hidden-sm">
					<c:if test="${sessionScope.username != null}">
					<li role="presentation">
						<a href="<%=request.getContextPath()%>/book-store/login.jsp" target="_blank">
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
						<c:if test="${sessionScope.ShoppingCart.bookNumber != null}">
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
	
		
	<div class="container">
	<div class="row">
	<div class="col-sm-2 line-center" style="padding-right: 0px; padding-left: 0px">
		<div class="tile tile-hot">
            <img src="<%=request.getContextPath()%>/images/ribbon.svg" alt="ribbon" class="tile-hot-ribbon">
            <img src="<%=request.getContextPath()%>/images/chat.svg" alt="Chat" class="tile-image">
            <h3 class="tile-title">合计: <span class="text-danger">
	                ¥${ sessionScope.ShoppingCart.totalMoney}</span></h3>
	         &nbsp;&nbsp;&nbsp;&nbsp;
            <p>您一共购买了 ${sessionScope.ShoppingCart.bookNumber } 本书籍</p>
            <c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
            <p><a href="bookServlet?method=getBook&id=${item.book.id}" target="_blank">
	            <span class="glyphicon glyphicon-book" aria-hidden="true">
	            </span> ${item.book.title }</a> &nbsp; × ${item.quantity }</p>
        	</c:forEach>
        </div>
	</div>
	<div class="col-sm-10" style="padding-right: 115px; padding-left: 115px">
	<div class="login" style="width: 940px">
	    <div class="login-screen" style="padding-top: 90px" >
	       <div class="login-icon">
	           <img src="<%=request.getContextPath()%>/images/icon.png" alt="Welcome to Mail App" />
	           <h4>Welcome to <small>Pay Page</small></h4>
	           <c:if test="${requestScope.errors != null }">
			   <span id="ErrInfo" style="text-align: center; color: red"><small>${requestScope.errors }</small></span>
			   </c:if>
	       </div>
	
		   
	       <form class="login-form" action="bookServlet?method=cash" method="post">
	           <div class="form-group">
	              <input type="text" class="form-control login-field" name="username"
	              	  value="" placeholder="用户名" id="login-name" />
	              <label class="login-field-icon fui-user" for="login-name"></label>
	           </div>
				
			   <div class="form-group">
	              <input type="text" class="form-control login-field" name="accountId"
	              	  value="" placeholder="信用卡" id="login-name" />
	              <label class="login-field-icon fui-credit-card" for="login-name"></label>
	           </div>
	            
	           <div class="form-group">
	              <input type="password" class="form-control login-field" name="password" 
	              	  value="" placeholder="支付密码" id="login-pass" />
	              <label class="login-field-icon fui-lock" for="login-pass"></label>
	           </div>
	           <button type="submit" class="btn btn-primary btn-lg btn-block">支付</button>
	           <a class="login-link" href="bookServlet?method=forwardPage&page=cash">
	           		Lost your password?</a>
	        </form>
	    </div>
	</div></div>
	</div></div>
	<br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
</body>
</html>