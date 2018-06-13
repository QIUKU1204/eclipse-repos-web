<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.qiuku.bookstore.domain.User"%>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>购物车</title>
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
    <!-- JavaScript代码 -->
	<script type="text/javascript">
		$(function(){
			// Ajax 修改单个商品的数量:
			// 1. 选择页面中所有的 text, 并为其添加 onchange 响应函数
			$(":text").change(function(){
				var quantityVal = $.trim(this.value);
				var flag = false;
				
				var reg = /^\d+$/g;
				var quantity = -1;
				if(reg.test(quantityVal)){
					quantity = parseInt(quantityVal);
					if(quantity >= 0){
						flag = true;
					}
				}
				
				if(quantity < 0 ){ // quantity < 0 时, flag = false;
					alert("输入的数量不合法！");
					// 2. attr方法返回 被选元素 的属性值
					// 接着设置该元素的value为这个属性值
					$(this).val($(this).attr("class"));
					return;
				}
				// 3. 获取当前节点的父节点的父节点div
				var $div = $(this).parent().parent();
				var title = $.trim($div.find("div:first").text());
	
				if(quantity == 0){ // quantity = 0 时
					var flag2 = true;
					// var flag2 = confirm("确定要删除 " + title + " 的信息吗？");
					if(flag2){
						// 得到了 a 节点
						var $a = $div.find("div:last").find("a");
						// 执行 a 节点的 onclick 响应函数. 
						$a[0].click();
						return;
					}
				}
				
				// 当 quantity > 0时
				var flag = confirm("确定要修改 " + title + " 数量吗？");
				if(!flag){
					$(this).val($(this).attr("class"));
					return;
				}
				// 4. 请求地址为: bookServlet
				var url = "bookServlet";
				// 5. 请求参数为: method:updateItemQuantity, id:name属性值, quantity:val, time:new Date()
				var idVal = $.trim(this.name);
				var args = {"method":"updateItemQuantity", "id":idVal, "quantity":quantityVal, "time":new Date()};
				
				// 6. 在 updateItemQuantity 方法中, 获取id, quanity, 再获取购物车对象, 调用 service 的方法做修改
				// 7. 最终传回 JSON 数据: itemMoney, bookNumber, totalMoney;
				
				// 8. 更新当前页面的 itemMoney、bookNumber 及 totalMoney
				$.post(url, args, function(data){
					var itemMoney = data.itemMoney;
					var bookNumber = data.bookNumber;
					var totalMoney = data.totalMoney;
					
					$("#itemMoney" + idVal).text("¥" + itemMoney);
					$("#bookNumber").text("商品总数:  " + bookNumber);
					$("#totalMoney").text("¥" + totalMoney);
				},"JSON");
				
			});
					
		})
	</script>
	<script type="text/javascript">
   		 $(function(){
    		$(".delete").click(function(){
    			var $div = $(this).parent().parent();
				var title = $.trim($div.find("div:first").text());
    			// var title = $(this).parent().parent().find("div:first").text();
    			var flag = confirm("确定要删除 " + title + " 的信息吗？");
    			return flag;
    		})
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
	            <span class="glyphicon glyphicon-shopping-cart">购物车</span></h1>
	        </div>
	        
	        <div class="list-group">
	            <div class="col-sm-12 thumbnail">
	                <div class="col-sm-3 line-center">图书</div>
	                <div class="col-sm-1 line-center">单价</div>
	                <div class="col-sm-4 line-center">数量 </div>
	                <div class="col-sm-2 line-center">小计</div>
	                <div class="col-sm-2 line-center">操作</div>
	            </div>
	            <c:if test="${empty sessionScope.ShoppingCart.items }">
					<div class="col-sm-12  list-group-item">
						<div class="col-sm-12 line-center">
							购物车空空的~快去挑选喜欢的商品吧!
						</div>
					</div>
				</c:if>
	            <c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
	            <div class="col-sm-12  list-group-item">
	                <%-- <div class="col-sm-1 line-center" style="width: 60px;height: 50px;">
	                    <img src="<%=request.getContextPath()%>/images/Book.png" style="height: 100%;" alt=""/>
	                </div>  --%>
	                <div class="col-sm-3 line-center">
	                	<a href="bookServlet?method=getBook&id=${item.book.id}" target="_blank">
	                		<span class="glyphicon glyphicon-book" aria-hidden="true">
  						</span> ${item.book.title }</a>
	                	<%-- <a href="bookServlet?method=clear" 
	            			class="btn btn-info active" role="button">${item.book.title }</a> --%>
	                </div>
	                <div class="col-sm-1 line-center">
	                	<span class="text-danger">¥${item.book.price }</span></div>
	                <div class="col-sm-4 line-center">
	                    <button type="button" class="btn btn-default">
	                        <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
	                    </button>
	                    <!-- input text -->
	                    <input class="${item.quantity }" type="text" size="2" 
	                    	name="${item.book.id }" value="${item.quantity }"/>
	                    <button type="button" class="btn btn-default">
	                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
	                    </button>
	                </div>
	                
	                <c:set var="intVar" value="${item.quantity }" />
	                <div class="col-sm-2 line-center">
	                	<span class="text-danger" id="itemMoney${item.book.id}">
	                		¥${item.book.price * intVar }</span>
	               	</div>
	                
	                <div class="col-sm-2 line-center">
	                	<a href="bookServlet?method=remove&id=${item.book.id }" 
	                		class="delete"><button class="btn btn-danger">删除</button></a>
	                </div>
	            </div>
	            </c:forEach>
	
	            <div class="col-sm-12 thumbnail">
	                <div class="col-sm-offset-3 col-sm-1 text-right"></div>
	                <div class="col-sm-4 line-center">
	                	<strong id="bookNumber">商品总数: &nbsp;${sessionScope.ShoppingCart.bookNumber }</strong></div>
	                <div class="col-sm-2 line-center">
	                	<strong>合计: &nbsp;<span class="text-danger" id="totalMoney">
	                		¥${ sessionScope.ShoppingCart.totalMoney }</span></strong></div>
	            	<c:if test="${!empty sessionScope.ShoppingCart.items }">
	            	<div class="col-sm-2 line-center">
	                	<a href="bookServlet?method=clear"><button class="btn btn-danger">清空购物车</button></a>
	                </div></c:if>
	            </div>
	        </div>
	         
	        <div class="col-sm-offset-7 col-sm-5" style="padding: 30px;">
	            <div class="col-sm-6 btn btn-block">
	            	<a href="bookServlet?method=getBooks">
	            	<button type="button" class="btn btn-primary btn-lg btn-block">继续购物</button></a>	
	            </div>
	            <c:if test="${!empty sessionScope.ShoppingCart.items }">
	            <div class="col-sm-6 btn btn-block">
	            	<a href="bookServlet?method=forwardPage&page=order">
	            	<button type="button" class="btn btn-primary btn-lg btn-block">购物车结算</button></a>	
	            </div></c:if>
	        </div>
	    </div>
	</div>
		
	<br/><br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom ">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>	
	
</body>
</html>