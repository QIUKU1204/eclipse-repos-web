<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单管理</title>
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
    </style>
    <script type="text/javascript">
		$(function(){
			// 弹窗提示已发货
			// 1. 在所有的 button 上做停留
			$("button").each(function(){
				var tradeId = $.trim(this.name);
				$("#button" + tradeId).click(function(){
					// 2. 获取当前button节点的父节点的父节点form
					var $form = $(this).parent().parent();
					// 3. 提取第二个div节点的文本内容 
					var status = $.trim($form.find("div:eq(1)").text());
					
					if(status == "已发货"){
						alert("已发货！");
					}
						
				});
			});		
		})
	</script>
</head>
<body>

<div class="container" >
	
	<div class="row center" style="margin-top: 80px;
		background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
		
	<div class="col-sm-12 line-center">
  		<h3 style="color: green;"><strong>订单管理后台</strong></h3>
 		<a href="manageServlet?method=forward&page=index" role="button"
				class="btn btn-warning btn-sm active">后台菜单</a>
	</div>
	
	<div class="col-sm-12 thumbnail">
	    <div class="col-sm-1 line-center">订单编号</div>
	    <div class="col-sm-1 line-center">订单状态</div>
	    <div class="col-sm-2 line-center">交易时间</div>
	    <div class="col-sm-2 line-center">收货人</div>
	    <div class="col-sm-2 line-center">联系方式</div>
	    <div class="col-sm-3 line-center">收货地址</div>
	    <div class="col-sm-1 line-center">操作</div>
	</div>
	<c:forEach items="${tradeSet }" var="trade">
	<div class="col-sm-12 list-group-item">
		<form action="manageServlet?method=updateTradeStatus&id=${trade.tradeId }" method="post">
		<div class="col-sm-1 line-center">
	        <span class="text-danger">${trade.tradeId }</span>
  		</div>
  		<div class="col-sm-1 line-center">
	        <span class="status" style="color: green;">${trade.status }</span>
	    </div>
	    <div class="col-sm-2 line-center">
	        <span class="text-danger">${trade.tradeTime }</span>
	    </div>
	    <div class="col-sm-2 line-center">
	        <span class="text-danger">${trade.name }</span>
	    </div>
	    <div class="col-sm-2 line-center">
	        <span class="text-danger">${trade.telephone }</span>
	    </div>
	    <div class="col-sm-3 line-center">
	        <span class="text-danger">${trade.address }</span>
	    </div>
	    <div class="col-sm-1 line-center">
	        <button type="submit" class="btn btn-success" 
	        	name="${trade.tradeId }" id="button${trade.tradeId }">发货</button>
	    </div>
	    </form>
	</div>
	</c:forEach>
	
	<%-- <!-- 分页显示 -->
	<nav class="center" aria-label="Page navigation">
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<small>共 ${bookpage.totalPageNumber } 页</small>
		&nbsp;&nbsp;
	    <ul class="pagination">
	        <c:if test="${bookpage.hasPrev }">
	        <li>
				<a href="manageServlet?method=getBooks&pageNo=1" aria-label="Previous">
				<span aria-hidden="true">首页</span></a>
			</li>
			<li>
				<a href="manageServlet?method=getBooks&pageNo=${bookpage.prevPage }" 
					aria-label="Previous"><span aria-hidden="true">&laquo;上一页</span></a>
			</li>
			</c:if>
	        <c:if test="${bookpage.hasNext }">
	        <li>
				<a href="manageServlet?method=getBooks&pageNo=${bookpage.nextPage }" 
					aria-label="Next"><span aria-hidden="true">下一页&raquo;</span></a>
			</li>
			<li>
				<a href="manageServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }" 
					aria-label="Next"><span aria-hidden="true">末页</span></a>
			</li>
			</c:if>
	    </ul>
	    &nbsp;
	    <small>到第 <input type="text" size="3" id="pageNo" style="height: 25px" 
	    				placeholder="${bookpage.pageNo}"/> 页</small>
	</nav> <!-- 分页 --> --%>
	
	</div><!-- row -->
	
	<br/>
	
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
	
</div>
</body>
</html>