<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>  

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图书管理</title>
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
				var href = "manageServlet?method=getBooks&pageNo=" + pageNo;
				window.location.href = href;
			});
		})	
	</script>
</head>
<body>

<div class="container" >
	
	<div class="row center" style="margin-top: 80px;
		background-image: url('<%= request.getContextPath() %>/images/bg.jpg')">
		
	<div class="col-sm-12 line-center">
  		<h3 style="color: green;"><strong>图书管理后台</strong></h3>
 		<a href="manageServlet?method=forward&page=index" role="button"
				class="btn btn-warning btn-sm active">后台菜单</a>
	</div>
	
	<div class="col-sm-12 thumbnail">
	    <div class="col-sm-3 line-center">图书</div>
	    <div class="col-sm-4 line-center">单价</div>
	    <div class="col-sm-4 line-center">库存 </div>
	    <div class="col-sm-1 line-center">操作</div>
	</div>
	<c:forEach items="${bookpage.list }" var="book">
	<div class="col-sm-12 list-group-item">
		<form action="manageServlet?method=updateBook&id=${book.id }&pageNo=${bookpage.pageNo}" method="post">
		<div class="col-sm-3 line-center">
			<a href="bookServlet?method=getBook&id=${book.id }" target="_blank">
	        	<span class="glyphicon glyphicon-book" aria-hidden="true">
  				</span> ${book.title }</a>
  		</div>
  		<div class="col-sm-4 line-center">
	        <span class="text-danger">¥${book.price }</span>
	        <input class="" type="text" size="2" 
	            name="price" value="${book.price }"/>
	    </div>
	    <div class="col-sm-4 line-center">
	        <span class="text-danger">${book.storeNumber }</span>
	        <input class="" type="text" size="2" 
	            name="storeNumber" value="${book.storeNumber }"/>
	    </div>
	    <div class="col-sm-1 line-center">
	        <button type="submit" class="btn btn-danger active">修改</button>
	    </div>
	    </form>
	</div>
	</c:forEach>
	
	<!-- 分页显示 -->
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
	</nav> <!-- 分页 -->
	
	</div><!-- row -->
	<br/>
	<!--footer-->
	<div class="navbar navbar-default navbar-static-bottom bottom">
		<br/><p class="line-center"><mark>版权声明区</mark></p>
	</div>
</div>
</body>
</html>