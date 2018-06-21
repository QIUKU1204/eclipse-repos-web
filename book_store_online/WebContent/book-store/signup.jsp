<!DOCTYPE html> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.qiuku.bookstore.domain.User" %>
<html>
<head>
    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户注册</title>  
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,600" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.bootcss.com/flat-ui/2.3.0/css/flat-ui.min.css"/>
	<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/flat-ui/2.3.0/js/flat-ui.min.js"></script>
    <script type="text/javascript">
    	$(function () {
  	  		$('[data-toggle="popover"]').popover()
		})
	</script>
    <style>
    	.center{ 
            display: table;
			margin-left: auto;
  			margin-right: auto;
        }
        html,body {
            font-family: 'Microsoft YaHei', sans-serif;
            font-weight: 100;
            width:100%;
  			height:100%;
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
   
<canvas id="Mycanvas"></canvas>
<script type="text/javascript">
	//定义画布宽高和生成点的个数
	  var WIDTH = window.innerWidth;
	  var HEIGHT = window.innerHeight;
	  var POINT = 35;
	
	  var canvas = document.getElementById('Mycanvas');
	  canvas.width = WIDTH;
	  canvas.height = HEIGHT;
	  var context = canvas.getContext('2d');
	  context.strokeStyle = 'rgba(0,0,0,0.04)',
	  context.strokeWidth = 1,
	  context.fillStyle = 'rgba(0,0,0,0.08)';
	  var circleArr = [];
	
	  //线条：开始xy坐标，结束xy坐标，线条透明度
	  function Line (x, y, _x, _y, o) {
	    this.beginX = x,
	    this.beginY = y,
	    this.closeX = _x,
	    this.closeY = _y,
	    this.o = o;
	  }
	  //点：圆心xy坐标，半径，每帧移动xy的距离
	  function Circle (x, y, r, moveX, moveY) {
	    this.x = x,
	    this.y = y,
	    this.r = r,
	    this.moveX = moveX,
	    this.moveY = moveY;
	  }
	  //生成max和min之间的随机数
	  function num (max, _min) {
	    var min = arguments[1] || 0;
	    return Math.floor(Math.random()*(max-min+1)+min);
	  }
	  // 绘制原点
	  function drawCricle (cxt, x, y, r, moveX, moveY) {
	    var circle = new Circle(x, y, r, moveX, moveY)
	    cxt.beginPath()
	    cxt.arc(circle.x, circle.y, circle.r, 0, 2*Math.PI)
	    cxt.closePath()
	    cxt.fill();
	    return circle;
	  }
	  //绘制线条
	  function drawLine (cxt, x, y, _x, _y, o) {
	    var line = new Line(x, y, _x, _y, o)
	    cxt.beginPath()
	    cxt.strokeStyle = 'rgba(0,0,0,'+ o +')'
	    cxt.moveTo(line.beginX, line.beginY)
	    cxt.lineTo(line.closeX, line.closeY)
	    cxt.closePath()
	    cxt.stroke();
	
	  }
	  //初始化生成原点
	  function init () {
	    circleArr = [];
	    for (var i = 0; i < POINT; i++) {
	      circleArr.push(drawCricle(context, num(WIDTH), num(HEIGHT), num(15, 2), num(10, -10)/40, num(10, -10)/40));
	    }
	    draw();
	  }
	
	  //每帧绘制
	  function draw () {
	    context.clearRect(0,0,canvas.width, canvas.height);
	    for (var i = 0; i < POINT; i++) {
	      drawCricle(context, circleArr[i].x, circleArr[i].y, circleArr[i].r);
	    }
	    for (var i = 0; i < POINT; i++) {
	      for (var j = 0; j < POINT; j++) {
	        if (i + j < POINT) {
	          var A = Math.abs(circleArr[i+j].x - circleArr[i].x),
	            B = Math.abs(circleArr[i+j].y - circleArr[i].y);
	          var lineLength = Math.sqrt(A*A + B*B);
	          var C = 1/lineLength*7-0.009;
	          var lineOpacity = C > 0.03 ? 0.03 : C;
	          if (lineOpacity > 0) {
	            drawLine(context, circleArr[i].x, circleArr[i].y, circleArr[i+j].x, circleArr[i+j].y, lineOpacity);
	          }
	        }
	      }
	    }
	  }
	
	  //调用执行
	  window.onload = function () {
	    init();
	    setInterval(function () {
	      for (var i = 0; i < POINT; i++) {
	        var cir = circleArr[i];
	        cir.x += cir.moveX;
	        cir.y += cir.moveY;
	        if (cir.x > WIDTH) cir.x = 0;
	        else if (cir.x < 0) cir.x = WIDTH;
	        if (cir.y > HEIGHT) cir.y = 0;
	        else if (cir.y < 0) cir.y = HEIGHT;
	
	      }
	      draw();
	    }, 16);
	  }
</script>

<div class="container center" style="position: absolute; left: 0px; right: 0px; 
	top: 80px; bottom: 0px">
	
	    <div class="row" style="margin-left: 200px; padding-left: 200px; 
	    	margin-right: 200px; padding-right: 200px">
	    	
	    	<div class="col-sm-12" style="text-align: center; padding: 25px; background: #ECF5FF">
				
				<div class="page-header" style="margin-top: 10px">
	    			<h3><font color="#F75000">当当书店</font><br/>
	    				<small style="color: #6FB7B7">书籍是人类进步的阶梯</small></h3>
				</div>
  				<h5><span class="glyphicon glyphicon-user" aria-hidden="true"></span>用户注册</h5><br/>
		    	
		    	<form action="<%= request.getContextPath() %>/userServlet?method=signup" method="post">
		    	    <!-- email -->
		    		<div class="input-group">
		    			<span class="input-group-addon" >
		    				<span class="glyphicon glyphicon-envelope" aria-hidden="true">
		    			</span></span>
		    			<label class="sr-only" for="exampleInputEmail">Email address</label>
		    			<input type="email" class="form-control" placeholder="请输入邮箱 @" 
		    				id="exampleInputEmail" name="email" value="${param.email}" 
		    		    	aria-describedby="sizing-addon1">		
		    		</div>
		    		<span id="emailErr" style="color:red">${requestScope.emailErr}</span><br>
		    		<!-- username -->
		    		<div class="input-group">
		    			<span class="input-group-addon" >
		    				<span class="glyphicon glyphicon-user" aria-hidden="true">
		    			</span></span>
		    			<input type="text" class="form-control" placeholder="请输入昵称" 
		    				name="username" value="${param.username}" 
		    				aria-describedby="sizing-addon2">		
		    		</div>
		    		<span id="usernameErr" style="color:red">${requestScope.usernameErr}</span><br>
		    		<!-- password -->
		    		<div class="input-group">
		    			<span class="input-group-addon" >
		    				<span class="glyphicon glyphicon-lock" aria-hidden="true">
		    			</span></span>
		    			<input type="password" class="form-control" placeholder="请输入密码" 
		    				name="password" value="${param.password}" 
		    				aria-describedby="sizing-addon3">
		    		</div>
		    		<span id="passwordErr" style="color:red">${requestScope.passwordErr}</span><br>
		    		<!-- creditCard -->
		    		<div class="input-group">
		    			<span class="input-group-addon" >
		    				<span class="glyphicon glyphicon-credit-card" aria-hidden="true">
		    			</span></span>
		    			<input type="text" class="form-control" placeholder="信用卡XXXX-XXXX-XXXX-XXXX" 
		    				name="creditCard" value="${param.creditCard}" 
		    				aria-describedby="sizing-addon4">
		    		</div>
		    		<span id="creditCardErr" style="color:red">${requestScope.creditCardErr}</span><br>
		    		<!-- checkCode -->
		    		<div class="input-group">
		    			<span class="input-group-addon" >
		    				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true">
		    			</span></span>
		    			<input type="text" class="form-control" placeholder="请输入验证码(区分大小写)" 
		    				name="CHECK_CODE_PARAM_NAME" value="${param.CHECK_CODE_PARAM_NAME}" 
		    				aria-describedby="sizing-addon5">		
		    		</div>
		    		<span id="checkcodeErr" style="color:red">${requestScope.checkcodeErr}</span><br>
		    		<!-- 注册 -->
		    		<button type="submit" class="btn btn-primary" style="width: 289px">注册</button><br/>
		    
		    		<span id="signupOk" style="color:green">${requestScope.successMsg}</span><br>
		    		
		        	<!-- 生成验证码图片 -->
		    		<a href="#" class="thumbnail">
		    		    <img alt="" src="<%= request.getContextPath() %>/validateColorServlet"></a>
		        	<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span> 
		        		已有账号? <a href="<%= request.getContextPath() %>/book-store/login.jsp">去登录</a>
		        </form>
		        
	    	</div> 
	    </div>
</div>
	
	
</body>
</html>