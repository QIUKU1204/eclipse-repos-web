<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单详细</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,600" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css"/>
	<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/flat-ui/2.3.0/js/flat-ui.min.js"></script>
    <%-- <%@ include file="/commons/queryCondition.jsp" %>	 --%>
    <style>
        .row{
            margin-left: 20px;
            margin-right: 20px;;
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
        }
        .list-group-item:hover{
            background: #27ae60;

        }
        .list-group-item div:first-child:hover{

            cursor: pointer;
        }
        th{
            text-align: right;
            width: 200px;;
        }
        td{
            text-align: left;
            padding: 10px;
        }
        .table th{
            text-align: center;
        }
        .table td{
            text-align: center;
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
	            <span class="glyphicon glyphicon-globe">订单详情</span></h1>
	        </div>
	
	        <div class="col-sm-12 ">
	           <table>
	               <tr>
	                   <th>订单编号：</th><td>${trade.tradeId }</td>
	               </tr>
	               <tr>
	                   <th>订单状态：</th><td>${trade.status }</td>
	               </tr>
	               <tr>
	                   <th>收货人姓名：</th><td>${trade.name }</td>
	               </tr>
	               <tr>
	                   <th>收货人地址：</th><td>${trade.address }</td>
	               </tr>
	               <tr>
	                   <th>收货人电话：</th><td>${trade.telephone }</td>
	               </tr>
	           </table>
	        </div>
	        <div class="col-sm-12">
	            <table class="table table-striped table-condensed">
	                <tr>
	                    <th>书名</th>
	                    <th>单价</th>
	                    <th>数量</th>
	                    <th>小计</th>
	                </tr>
	                <c:forEach items="${trade.items }" var="item">
	                <tr>
	                    <td><a href="bookServlet?method=getBook&id=${item.book.id}" target="_blank">
	                			<span class="glyphicon glyphicon-book" aria-hidden="true">
  								</span> ${item.book.title }</a></td>
	                    <td><span class="text-danger">¥${item.book.price }</span></td>
	                    <td>${item.quantity }</td>
	                    <td><span class="text-danger">¥${item.book.price * item.quantity }</span></td>
	                </tr>
                	</c:forEach>
            	</table>
	        </div>
	
	        <div class="col-sm-12 thumbnail">
	                <div class="col-sm-offset-6 col-sm-3 line-center" id="bookNumber">
	                	<strong>商品总数: &nbsp;${trade.totalBook}</strong></div>
	                <div class="col-sm-3 line-center" id="totalMoney">
	                	<strong>合计: &nbsp;<span class="text-danger">
	                		¥${trade.totalMoney}</span></strong>
	                </div>
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