<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.qiuku.mvcapp.domain.User"%>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>欢迎选购</title>

	<link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" />

	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- <script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js"></script> -->
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>

	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<script type="text/javascript">
    	$(function () {
  	  		$('[data-toggle="popover"]').popover()
		})
	</script>

	<style>
		html, body {
		/* background-image: url("../images/leaf.jpg");
            background-size: cover; */
		/* background-color: #84C1FF; */
			font-family: 'Microsoft YaHei', sans-serif;
			font-weight: 100;
			width: 100%;
			height: 100%;
			margin: 0;
			padding: 0;
			color: #333;
		}

		p {
			color: tomato;
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

	
	<div class="container" style="position: absolute; left: 300px; right: 300px; top: 80px">
		<div class="row" style="margin-left: 200px; padding-left: 200px; margin-right: 200px; padding-right: 200px">
			<div class="col-xs-12" style="text-align: center; padding: 25px; background-image: url('<%=request.getContextPath()%>/book-store/bg.jpg')">

				<h2><font color="#FF9797">当当书店</font><br /> 
					<small style="color: #D9B300">${requestScope.username} , 欢迎来到知识的海洋 ! </small>
				</h2><br/> 
				<a href="#" class="thumbnail"> <img alt="" src="<%=request.getContextPath()%>/images/lovely.gif"></a> 
				<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
				切换账号? <a href="<%=request.getContextPath()%>/book-store/login.jsp">返回登录页</a>
			</div>
		</div>
	</div>
	
    

</body>
</html>