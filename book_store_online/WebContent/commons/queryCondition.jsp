<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 作用: 对于click超链接的动作, 给该url添加两个后缀参数, 然后重定向到这个url -->
<script type="text/javascript">
	
	$(function(){
		$("a").each(function(){
			this.onclick = function(){
				var serializeVal = $(":hidden").serialize();
				var href = this.href + "&" + serializeVal;
				// 重定向到新页面, 同时刷新打开的这个页面
				window.location.href = href;			
				return false;
			};
		});
	});	
	
</script>

<input type="hidden" name="minPrice" value="${param.minPrice }"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>
	