<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/highcharts-3d.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/exporting.js"></script>
<title>区域动态销量</title>
</head>
<body>
	<form action="" method="post" id="myForm" target="myiframe"></form>
	<iframe id="myiframe" name="myiframe" style="display: none;"></iframe>
	<div id="container" style="height: 800px"></div>
	<div id="sliders">
		<table>
			<tr>
				<td>X轴旋转角度</td>
				<td><input id="R0" type="range" min="0" max="45" value="15" />
					<span id="R0-value" class="value"></span></td>
			</tr>
			<tr>
				<td>Y轴旋转角度</td>
				<td><input id="R1" type="range" min="0" max="45" value="15" />
					<span id="R1-value" class="value"></span></td>
			</tr>
		</table>
	</div>

</body>

<script type="text/javascript">
	var series1;
	var series2;

	function jsFun(data) {
		var jsdata = eval("(" + data + ")");
		series1.setData(eval(jsdata.todayData));
		series2.setData(eval(jsdata.hisData));
	}

	function init() {
		var action = "${pageContext.request.contextPath}/servlet/AreaServlet";
		$('#myForm').attr("action", action);
		$('#myForm').submit();
	}
</script>


<script type="text/javascript">
	$(function() {
		// Set up the chart
		var chart = new Highcharts.Chart({
			chart : {
				renderTo : 'container',
				events : {
					load : function() {
						series1 = this.series[0];
						series2 = this.series[1];
						init();
					}
				},
				type : 'column',
				margin : 75,
				options3d : {
					enabled : true,
					alpha : 15,
					beta : 15,
					depth : 50,
					viewDistance : 25
				}
			},
			title : {
				text : '区域实时金额'
			},
			subtitle : {
				text : '按天统计'
			},
			plotOptions : {
				column : {
					depth : 25,
					dataLabels : {
						enabled : true
					}
				}
			},
			xAxis : {
				categories : [ '北京', '上海', '太原', '天津', '繁峙' ],
				title : {
					text : '区域',
					align : 'high'
				}
			},
			yAxis : {
				min : 0,
				labels : {
					overflow : 'justify'
				},
				title : {
					text : '销售额(元)',
					align : 'high'
				}

			},
			series : [ {
				name : '当前',
				color : 'blue',
				data : []
			}, {
				name : '上周同期',
				color : '#00b050',
				data : []
			} ]
		});
		function showValues() {
			$('#R0-value').html(chart.options.chart.options3d.alpha);
			$('#R1-value').html(chart.options.chart.options3d.beta);
		}
		$('#R0').on('change', function() {
			chart.options.chart.options3d.alpha = this.value;
			showValues();
			chart.redraw(false);
		});
		$('#R1').on('change', function() {
			chart.options.chart.options3d.beta = this.value;
			showValues();
			chart.redraw(false);
		});
		showValues();
	});
</script>
</html>