<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" import="java.net.URLDecoder" %>
<%@ include file="/commons/common.jsp" %>  
    
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎</title>
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
            display: table;
			margin-left: auto;
  			margin-right: auto;
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
            width: 200px;
        }
        .list-group-item:hover{
            background: #27ae60;

        }
        .list-group-item div:first-child:hover{

            cursor: pointer;
        }
        th{
            text-align: right;
            width: 10%;;
            padding: 10px;
        }
        td{
            text-align: left;
            width: 30%;;
            padding: 10px;
        }
        .table th{
            text-align: center;
        }
        .table td{
            text-align: center;
        }
    </style>
    <script>
        $(function(){
            $('#myTabs a').click(function (e) {
                $(this).tab('show')
            });
        })
    </script>
    <script>
    $(function(){
		// Ajax 修改用户密码
		// 1. 当 id="alterPass" 的 button 被 click
		$("#alterPass").click(function(){
			// 2. 请求地址为: bookServlet
			var url = "userServlet";
			// 3. 请求参数为如下
			var oldPass = $("#oldPass").val();
			var newPass = $("#newPass").val();
			var args = {"method":"alterUserPass", "oldPass":oldPass, "newPass":newPass};
			// 4. alert弹窗提示
			$.post(url, args, function(data){
				var oldPassErr = data.oldPassErr;
				var newPassErr = data.newPassErr;
				if(oldPassErr != null){
					alert(oldPassErr);
				}
				if(newPassErr != null){
					alert(newPassErr);
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
	<div class="container">
	    <div class="row thumbnail center col-sm-12">
	        <div class="col-sm-12">
	            <h1 class="text-center" style="margin-bottom: 30px">个人中心</h1>
	        </div>
	
	        <ul class="nav nav-tabs nav-justified" id="myTabs">
	            <li class="active"><a href="bookServlet?method=getUserInfo#userHome" >个人中心</a></li>
	            <li><a href="bookServlet?method=getUserInfo#orderManager">订单管理</a></li>
	            <li><a href="bookServlet?method=getUserInfo#editPassword">密码修改</a></li>
	            <li><a href="bookServlet?method=getUserInfo#editInfo">信息编辑</a></li>	       	     
	        </ul>
	
	        <!-- Tab panes -->
	        <div class="tab-content">
	        	<!-- 个人中心 -->
	            <div role="tabpanel" class="tab-pane active" id="userHome">
	            	<h5 style="color: green;" class="line-center">
	            		欢迎, ${sessionScope.username }!</h5>
	            	<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">用户ID:</div>
					    <div class="col-sm-3 line-center">${user.userId }</div>
					</div>
					<div class="list-group">
					<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">用户名:</div>
					    <div class="col-sm-3 line-center">${user.username }</div>
					</div>
					<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">用户密码:</div>
					    <div class="col-sm-3 line-center">${user.password }</div>
					</div>
					<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">绑定邮箱:</div>
					    <div class="col-sm-3 line-center">${user.email }</div>
					</div>
					<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">绑定信用卡:</div>
					    <div class="col-sm-3 line-center">${user.creditCard }</div>
					</div>
					<div class="col-sm-12 list-group-item">
					    <div class="col-sm-2 col-sm-offset-3 line-center">账户余额:</div>
					    <div class="col-sm-3 line-center">¥${account.balance }</div>
					</div>
					</div>
	            </div> <!-- 个人中心 -->
	            <!-- 订单管理 -->
	            <div role="tabpanel" class="tab-pane" id="orderManager">
	            	<h5 style="color: green;" class="line-center">您的所有订单</h5>
	            	<div class="col-sm-12 thumbnail">
			            <div class="col-sm-1 line-center">订单号</div>
			            <div class="col-sm-2 line-center">支付时间</div>
			            <div class="col-sm-2 line-center">订单状态</div>
			            <div class="col-sm-2 line-center">商品数量 </div>
			            <div class="col-sm-2 line-center">订单总价</div>
			            <div class="col-sm-3 line-center">操作</div>
	        		</div>
	        		<div class="list-group">
	        		<c:if test="${empty user.trades }">
						<div class="col-sm-12  list-group-item">
							<div class="col-sm-12 line-center">
								没有符合的订单哦~快去首页选购下单吧!
							</div>
						</div>
					</c:if>
			        <c:forEach items="${user.trades }" var="trade">
			            <div class="col-sm-12  list-group-item" >
			                <div class="col-sm-1 line-center" onclick="myClick(1)">
			                	<a href="userServlet?method=getTrade&tradeId=${trade.tradeId }" 
			                		target="_blank">${trade.tradeId }</a></div>
			                <div class="col-sm-2 line-center">${trade.tradeTime }</div>
			                <div class="col-sm-2 line-center">${trade.status }</div>
			                <div class="col-sm-2 line-center">${trade.totalBook } </div>
			                <div class="col-sm-2 line-center"><strong>¥${trade.totalMoney }</strong></div>
			                <div class="col-sm-3 line-center">
			                    <button class="btn btn-danger" id="deleteTrade${trade.tradeId }">
			                    	删除订单</button>
			                </div>
			            </div>
			        </c:forEach>
	        		</div>
	    		</div> <!-- 订单管理 --> 
	    		<!-- 密码修改 -->
	            <div role="tabpanel" class="tab-pane" id="editPassword">
	            	<h5 style="color: green;" class="line-center">密码修改</h5>
	            	<!-- <form action="userServlet?method=alterUserPass" method="post"> -->
		            <div class="col-sm-12 thumbnail">
		            	
		            	<div class="col-sm-12 line-center">
				            <div class="col-sm-1 col-sm-offset-4"><del>原密码</del></div>
	    					<div class="col-sm-4">
	    						<input type="password" class="form-control" id="oldPass" 
	    						name="oldPass" placeholder="old password">
	    					</div>			                     
		        		</div>
		        		<div class="col-sm-12 line-center">				         
				            <div class="col-sm-1 col-sm-offset-4">新密码</div>
	    					<div class="col-sm-4">
	    						<input type="password" class="form-control" id="newPass" 
	    						name="newPass" placeholder="new password">
	    					</div>	           
		        		</div>
		        		<div class="col-sm-12 line-center">
		        			<div class="col-sm-2 col-sm-offset-5">
		        			<button type="submit" class="btn btn-info" id="alterPass"
		        				style="width: 200px;">确定修改</button>
		        			</div>
		        		</div>
		        		
		        	</div>
	        		<!-- </form> -->	
	            </div> <!-- 密码修改 -->
	            <!-- 资料编辑-->
	            <div role="tabpanel" class="tab-pane" id="editInfo">
	            	<p class="line-center">资料编辑(暂不支持)</p>
	            </div> <!-- 资料编辑 -->
	            	            
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