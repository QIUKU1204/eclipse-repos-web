<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的交易记录</title>
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
            margin-left: 20px;
            margin-right: 20px;
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
		.row input{
            width: 50px;
        }
        .list-group-item:hover{
            background: #27ae60;
        }
        .list-group-item div:first-child:hover{
            cursor: pointer;
        }
	</style>
	<script>
        function myClick(n){
        }
        function btnClick(){
            alert("btn");
            return false;
        }
        $(function(){

        })
    </script>
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
	
	
	<!--content-->
	<div class="container">
	    <div class="row thumbnail center">
	        <div class="col-sm-12">
	            <h1 class="text-center" style="margin-bottom: 30px">
	            <span class="glyphicon glyphicon-folder-close">我的订单</span></h1>
	        </div>
	        <div class="col-sm-12 thumbnail">
	            <div class="col-sm-1 line-center">订单号</div>
	            <div class="col-sm-2 line-center">支付时间</div>
	            <div class="col-sm-2 line-center">订单状态</div>
	            <div class="col-sm-2 line-center">商品数量 </div>
	            <div class="col-sm-2 line-center">订单总价</div>
	            <div class="col-sm-3 line-center">操作</div>
	        </div>
	        <div class="list-group">
	        <c:forEach items="${user.trades }" var="trade">
	            <div class="col-sm-12  list-group-item" >
	                <div class="col-sm-1 line-center" onclick="myClick(1)">
	                	<a href="userServlet?method=getTrade&tradeId=${trade.tradeId }" 
	                		target="_blank">${trade.tradeId }</a></div>
	                <div class="col-sm-2 line-center">${trade.tradeTime }</div>
	                <div class="col-sm-2 line-center">已付款</div>
	                <div class="col-sm-2 line-center">${trade.totalBook } </div>
	                <div class="col-sm-2 line-center"><strong>¥${trade.totalMoney }</strong></div>
	                <div class="col-sm-3 line-center">
	                    <button class="btn btn-danger">删除订单</button>
	                </div>
	            </div>
	        </c:forEach>
	    	</div>
		</div>
	</div>
	
	<br/><br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
</body>
</html>