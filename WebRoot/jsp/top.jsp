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
	

</body>

<script type="text/javascript">
	var series1;
	var series2;
	var citys;

	function jsFun(data) {
		var jsdata = eval("(" + data + ")");
		series1.setData(eval(jsdata.todayColumn));
		series2.setData(eval(jsdata.todaySpline));
		citys.setCategories(eval(jsdata.todayCity));
		
	}

	function init() {
		var action = "${pageContext.request.contextPath}/servlet/TopServlet";
		$('#myForm').attr("action", action);
		$('#myForm').submit();
	}
</script>


<script type="text/javascript">
$(function () {
    $('#container').highcharts({
        chart: {
            zoomType: 'xy',
			renderTo : 'container',
			events : {
				load : function() {
					series1 = this.series[0];
					series2 = this.series[1];
					citys=this.xAxis[0];
					init();
				}
			},

        },
        title: {
            text: '当日销售排行-TOP5'
        },
        subtitle: {
            text: '(>北京、上海、天津、武汉、太原、繁峙、深圳、南京、合肥<)'
        },
        xAxis: [{
            categories: [],
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value} 个',
                style: {
                    color: Highcharts.getOptions().colors[1]
                },
                overflow : 'justify'
            },
            title: {
                text: '订单数',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '销售额',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} 元',
                style: {
                    color: Highcharts.getOptions().colors[0]
                },
                overflow : 'justify'
            },
            opposite: true
        }],
        plotOptions: {
            column : {
            	dataLabels : {
            		enabled : true
            	}
            },
            spline : {
            	dataLabels : {
            		enabled : true
            	}
            }
        },
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 0,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: '销售额',
            type: 'column',
            yAxis: 1,
            data: [],
            tooltip: {
                valueSuffix: '元'
            }
        }, {
            name: '订单数',
            type: 'spline',
            data: [],
            tooltip: {
                valueSuffix: '个'
            }
        }]
    });
});
</script>
</html>