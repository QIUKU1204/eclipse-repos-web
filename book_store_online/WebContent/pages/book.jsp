<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${book.title }</title>
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
	<!-- JavaScript代码 -->
	<script type="text/javascript">
		$(function(){
			// Ajax 添加商品
			$("#addToCart").click(function(){
				// 1. 请求地址为: bookServlet
				var url = "bookServlet";
				// 2. 请求参数为如下
				var idVal = $.trim(this.name);
				var args = {"method":"addToCart","id":idVal, "time":new Date()};
				// 3. 更新当前页面的特定内容
				$.post(url, args, function(data){
					var bookNumber = data.bookNumber;
					var warning = data.warning;
					if(bookNumber != null){
						$(".badge").text(bookNumber);
						alert("添加成功~");
					}
					if(warning != null){
						alert("请先登录！");
					}
				},"JSON");		
			});	
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
	<div class="row thumbnail" style="margin-left: 50px; margin-right: 50px" >
	    <div class="col-sm-4">
	            <img style="width: 100%; height: 450px; display: block" 
	            	src="<%=request.getContextPath()%>/images/crawler.png" data-holder-rendered="true" />
	            <div class="caption center">
	                <h3>书名: ${book.title }</h3><br/>
	                <p>作者: ${book.author }</p>
	                <p>售价: ${book.price }RMB</p>
	                <p>出版日期: ${book.publishingDate }</p>
	                <p>评论: ${book.remark }</p>
	                <p><button class="btn btn-primary btn-block" role="button" 
					  		name="${book.id }" id="addToCart">加入购物车</button>	 
					   <a class="btn btn-default btn-block" role="button" 
					   		href="bookServlet?method=buy&id=${book.id }" >
					   		立即购买
					   </a>
					</p>
	            </div>
	    </div>
	    <div class="col-sm-8">
	        <div class="caption">
	        	<h3>图书介绍</h3>
	            <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. 
	            	Donec id elit non mi porta gravida at eget metus. 
	            	Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
	        </div>
	    </div>
	</div>
		
		
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>		
	
</body>
</html>