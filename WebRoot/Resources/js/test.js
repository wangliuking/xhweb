require.config({
	paths : {
		echarts : 'lib/echarts'
	}
});
var background="#272625";

//基于准备好的dom，初始化echarts实例
var chart = null;
if (chart != null) {
	chart.clear();
	chart.dispose();
}
var app = angular.module("app", []);
app.controller("test", function($scope, $http) {
	
});
var temperature=function (data){
	require([ 'echarts', 'echarts/chart/line' ],
			function(ec) {
				chart = ec.init(document.getElementById('barDemo'));
				chart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var option = {
					    title : {
					        text: '指标折线对比'
					        //subtext: '亚光'
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['单位一','单位二']
					    },
					    
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : ['周一','周二','周三','周四','周五','周六','周日']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel : {
					                formatter: '{value} °C'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'单位一',
					            type:'line',
					            data:[3, 4, 5, 7, 9, 6, 2],
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        },
					        {
					            name:'单位二',
					            type:'line',
					            data:[1, 3, 2, 5, 3, 2, 0],
					            markPoint : {
					                data : [
					                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name : '平均值'}
					                ]
					            }
					        }
					    ]
					};
				chart.hideLoading();
				chart.setOption(option);
			});
	
	//柱状图
	require([ 'echarts', 'echarts/chart/bar' ],
			function(ec) {
				chart = ec.init(document.getElementById('barTest'));
				chart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var option = {
					    title : {
					        text: '指标柱状对比',
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['单位一','单位二']
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'单位一',
					            type:'bar',
					            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        },
					        {
					            name:'单位二',
					            type:'bar',
					            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
					            markPoint : {
					                data : [
					                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
					                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name : '平均值'}
					                ]
					            }
					        }
					    ]
					};
				chart.hideLoading();
				chart.setOption(option);
			});
}
temperature();
