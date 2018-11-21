<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<title>测试页面</title>
</head>
<body>
	<!-- 定义一个form表单， 重要的是target="myiframe"，这里把form的请求定向到了myiframe中，然后再myiframe中执行servlet中返回的脚本，调用myiframe的父页面的jsFun，也就是本页面的jsFun -->
	<form action="" method="post" id="myForm" target="myiframe"></form>
	<!-- iframe要隐藏哦 -->
	<iframe id="myiframe" name="myiframe" style="display: none;"></iframe>
	<div id="container" style="height: 800px"></div>


</body>

<script type="text/javascript">
	function jsFun(data) {//一直被后台调用的方法
		$('#container').append("<br/>");
		$('#container').append(data);
	}

	function init() {//用户进入页面后就自动发起form表单的提交，激活长连接
		var action = "${pageContext.request.contextPath}/servlet/TestServlet";
		$('#myForm').attr("action", action);
		$('#myForm').submit();
	}
</script>


<script type="text/javascript">
	$(function() {
		init();
	});
</script>
</html>