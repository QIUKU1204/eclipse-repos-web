<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>确认订单</title>
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
			<form action="bookServlet?method=commit" class="form-inline" method="post">
	        <div class="col-sm-12">
	           <table>
	               <tr>
	                   <th>订单编号：</th><td>未分配</td>
	               </tr>
	               <tr>
	                   <th>订单状态：</th><td>未付款</td>
	               </tr>
	               <tr>
	                   <th>收货人：</th>
	                   <c:if test="${requestScope.nameErr != null }">
	                   <td><input type="text" class="form-control" name="name" style="width: 320px; 
	                   		   height: 30px" placeholder="${requestScope.nameErr }"></td>
	                   </c:if>
	                   <c:if test="${requestScope.nameErr == null }">
	                   <td><input type="text" class="form-control" name="name" style="width: 320px;
	                           height: 30px" placeholder="请输入收货人"></td>
	                   </c:if>
	               </tr>
	               <tr>
	                   <th>联系电话：</th>
	                   <c:if test="${requestScope.teleErr != null }">
	                   <td><input type="text" class="form-control" name="telephone" style="width: 320px; 
	                   		   height: 30px" placeholder="${requestScope.teleErr }"></td>
	                   </c:if>
	                   <c:if test="${requestScope.teleErr == null }">
	                   <td><input type="text" class="form-control" name="telephone" style="width: 320px;
	                           height: 30px" placeholder="请输入联系电话"></td>
	                   </c:if>
	               </tr>
	               <tr>
	                   <th>收货地址：</th>
	                   <c:if test="${requestScope.addrErr != null }">
	                   <td><input type="text" class="form-control" name="address" style="width: 320px; 
	                   		   height: 30px" placeholder="${requestScope.addrErr }"></td>
	                   </c:if>
	                   <c:if test="${requestScope.addrErr == null }">
	                   <td><input type="text" class="form-control" name="address" style="width: 320px;
	                           height: 30px" placeholder="请输入收货地址"></td>
	                   </c:if>
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
	                <c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
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
	        	<div class="col-sm-offset-3 col-sm-1 text-right"></div>
	                <div class="col-sm-4 line-center" id="bookNumber">
	                	<strong>商品总数: &nbsp;${sessionScope.ShoppingCart.bookNumber }</strong></div>
	                <div class="col-sm-2 line-center" id="totalMoney">
	                	<strong>合计: &nbsp;<span class="text-danger">
	                		¥${sessionScope.ShoppingCart.totalMoney }</span></strong></div>
	            	<div class="col-sm-2 line-center">
	          			<button class="btn btn-success">提交订单</button>
	                </div>
	        </div>
	        </form>
	    </div>
	</div>
	
	<br/><br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>	
	
</body>
</html>