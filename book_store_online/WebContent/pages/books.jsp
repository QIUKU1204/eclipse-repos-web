<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>当当书店</title>
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
			margin-top: 20px;
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
	<!-- JavaScript代码 -->
	<script>
		$(function(){
			$('#myTabs a').click(function (e) {
				e.preventDefault()
				$(this).tab('show')
			})
		})
	</script>
	<script type="text/javascript">
		$(function(){
			// 当pageNo改变时跳转到相应的页面
			$("#pageNo").change(function(){
				var val = $(this).val();
				val = $.trim(val);
				
				//1. 校验 val 是否为数字 1, 2, 而不是 a12, b
				var flag = false;
				var reg = /^\d+$/g;
				var pageNo = 0;
				
				if(reg.test(val)){
					//2. 校验 val 在一个合法的范围内： 1-totalPageNumber
					pageNo = parseInt(val);
					if(pageNo >= 1 && pageNo <= parseInt("${bookpage.totalPageNumber }")){
						flag = true;
					}
				}
				
				if(!flag){
					alert("输入的不是合法的页码.");
					$(this).val("");
					return;
				}
				
				// 3. 页面跳转
				var href = "bookServlet?method=getBooks&pageNo=" + pageNo 
						+ "&minPrice=${param.minPrice }"
						+ "&maxPrice=${param.maxPrice }";
				window.location.href = href;
			});
		})	
	</script>
	<script type="text/javascript">
		$(function(){
			// Ajax 添加商品
			// 1. 在所有的 button 上做停留
			$("button").each(function(){
				var bookId = $.trim(this.name);
				// $("#addToCart" + bookId).click(function(){
				// this.onclick = function(){
				$("#addToCart" + bookId).click(function(){
					// 2. 请求地址为: bookServlet
					var url = "bookServlet";
					// 3. 请求参数为如下
					var idVal = $.trim(this.name);
					var args = {"method":"addToCart","id":idVal, "time":new Date()};
					// 4. 更新当前页面的特定内容
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
			});		
		})
	</script>
	<script type="text/javascript">
    	$(function () {
  	  		$('[data-toggle="popover"]').popover()
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
	<div class="container" style="align:center">
		<!-- 巨幕显示 -->
		<div class="jumbotron" style="background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
        	<h1>当当书店</h1>
        	<p>书籍是人类进步的阶梯</p>
        	<p><a tabindex="0" class="btn btn-primary btn-lg" role="button" 
        		data-toggle="popover" data-trigger="focus" title="Dismissible popover" 
        		data-content="And here's some amazing content. It's very engaging. Right?">
        		了解更多</a></p>
    	</div>

	    <ul class="nav nav-tabs" id="myTabs">
	        <li role="presentation" class="active"><a href="#" >计算机</a></li>
	        <li role="presentation"><a href="#">都市言情</a></li>
	        <li role="presentation"><a href="#">军事科技</a></li>
	        <li role="presentation"><a href="#">历史人文</a></li>
	        <li role="presentation"><a href="#">都市言情</a></li>
	        <li role="presentation"><a href="#">军事科技</a></li>
	        <li role="presentation"><a href="#">历史人文</a></li>
	    </ul>

		<div class="row">
			<c:if test="${empty bookpage.list }">
				<div class="center">
					<mark>找不到你想要的商品!</mark>
				</div>
			</c:if>
			<!-- 商品显示 -->
			<c:forEach items="${bookpage.list }" var="book">
				<div class="col-sm-4 col-md-3">
				<div class="thumbnail">
					<a href="bookServlet?method=getBook&id=${book.id}" target="_blank">
						<img class="img-rounded" style="width: 100%; height: 200px; display: block;" alt="100%x200"
						src="<%=request.getContextPath()%>/images/crawler.png">
					</a>
					<div class="caption center">
						<h3><a href="bookServlet?method=getBook&id=${book.id}" target="_blank">${book.title }</a></h3>
						<p><span>${book.author }</span><span>&nbsp;著</span></p>
						<p><span>¥</span><span>${book.price }</span></p>
						<p><button class="btn btn-primary btn-block" role="button" 
							name="${book.id}" id="addToCart${book.id}">加入购物车</button>
						</p>
					</div>
				</div>
				</div>
			</c:forEach>
		</div>
		<br/>
		<!-- 价格区间 -->
		<div class="line-center">
			<form class="form-inline" action="bookServlet?method=getBooks" method="post">
				<div class="form-group">
					<label class="control-label" for="inputSuccess4">价格 &nbsp;</label>
		    		<input type="text" name="minPrice" size="3" style="height: 22px" placeholder="¥" />
		  				-
		    		<input type="text" name="maxPrice" size="3" style="height: 22px" placeholder="¥" />
		  		</div>
				<button type="submit" class="btn btn-primary btn-xs" style="height: 24px">确定</button>
			</form>
		</div>
		
		<!-- 分页显示 -->
		<nav class="center" aria-label="Page navigation">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<small>共 ${bookpage.totalPageNumber } 页</small>
			&nbsp;&nbsp;
	        <ul class="pagination">
	        	<c:if test="${bookpage.hasPrev }">
	        	<li>
					<a href="bookServlet?method=getBooks&pageNo=1
					&minPrice=${param.minPrice }&maxPrice=${param.maxPrice }" aria-label="Previous">
					<span aria-hidden="true">首页</span></a>
				</li>
				<li>
					<a href="bookServlet?method=getBooks&pageNo=${bookpage.prevPage }
					&minPrice=${param.minPrice }&maxPrice=${param.maxPrice }" aria-label="Previous">
					<span aria-hidden="true">&laquo;上一页</span></a>
				</li>
				</c:if>
	            <c:if test="${bookpage.hasNext }">
	        	<li>
					<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }
					&minPrice=${param.minPrice }&maxPrice=${param.maxPrice }" aria-label="Next">
					<span aria-hidden="true">下一页&raquo;</span></a>
				</li>
				<li>
					<a href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }
					&minPrice=${param.minPrice }&maxPrice=${param.maxPrice }" aria-label="Next">
					<span aria-hidden="true">末页</span></a>
				</li>
				</c:if>
	        </ul>
	        &nbsp;
	       	<small>到第 <input type="text" size="3" id="pageNo" style="height: 25px" placeholder="${bookpage.pageNo}"/> 页</small>
	    </nav>
    
	</div>
	<br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
</body>
</html>