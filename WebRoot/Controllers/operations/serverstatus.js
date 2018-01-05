/**
 * 终端状态
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
var background="#fff";
var frist = 0;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
var data=null;
var totals=0;
toastr.options = {
		"debug" : false,
		"newestOnTop" : false,
		"positionClass" : "toast-top-center",
		"closeButton" : true,
		/* 动态效果 */
		"toastClass" : "animated fadeInRight",
		"showDuration" : "300",
		"hideDuration" : "1000",
		/* 消失时间 */
		"timeOut" : "1000",
		"extendedTimeOut" : "1000",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut",
		"progressBar" : true,
	};
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		/*xh.loadUserStatusPie();*/
		/*获取日志信息*/
		$http.get("../../server/list").
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;			
			for(var i=0;i<$scope.totals;i++){
				if($scope.data[i].status==0){
					xh.loadCpu($scope.data[i].cpuLoad,$scope.data[i].typeId)	
				}						
			}
			
		});
		$scope.icp=function(){
			$http.get("../../server/icpStatus").
			success(function(response){
				xh.maskHide();
				$scope.icpStatusData = response.items;
				$scope.icpStatusTotals = response.totals;
				var bs3=0;
				var bs4=0;
				
				for(var i=0;i<$scope.icpStatusTotals;i++){
					if($scope.icpStatusData[i].period==3){
						bs3++;
					}else{
						bs4++;
					}
				}
				$scope.bs3=bs3;
				$scope.bs4=bs4;
				xh.bsBar($scope.bs3,$scope.bs4);
				
			});
		}
		$scope.refresh=function(){
			$http.get("../../server/list").
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;			
				for(var i=0;i<$scope.totals;i++){
					if($scope.data[i].status==0){
						xh.loadCpu($scope.data[i].cpuLoad,$scope.data[i].typeId)	
					}
					
					
				}
				
			});
		}
		$scope.loadTemp = function() {

			// 基于准备好的dom，初始化echarts实例
			var chart = null;
			if (chart != null) {
				chart.clear();
				chart.dispose();
			}
			require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
				chart = ec.init(document.getElementById('temp-div'));
				/*
				 * chart.showLoading({ text : '正在努力的读取数据中...' });
				 */
				var option = {
					backgroundColor : '#1b1b1b',
					tooltip : {
						formatter : "{a} <br/>{b} : {c}℃"
					},
					series : [ {
						name : '温度',
						type : 'gauge',
						min : 0,
						max : 90,
						splitNumber : 10, // 分割段数，默认为5

						axisLine : { // 坐标轴线
							lineStyle : { // 属性lineStyle控制线条样式
								color : [ [ 0.2, 'lime' ], [ 0.8, '#1e90ff' ],
										[ 1, '#ff4500' ] ],
								width : 4,
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						axisTick : { // 坐标轴小标记
							length : 10, // 属性length控制线长
							lineStyle : { // 属性lineStyle控制线条样式
								color : 'auto',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						axisLabel : { // 坐标轴小标记
							textStyle : { // 属性lineStyle控制线条样式
								fontWeight : 'bolder',
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						splitLine : { // 分隔线
							length : 15, // 属性length控制线长
							lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
								width : 3,
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						pointer : { // 分隔线
							shadowColor : '#fff', // 默认透明
							shadowBlur : 5
						},
						title : {
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								fontWeight : 'bolder',
								fontSize : 20,
								fontStyle : 'italic',
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						detail : {
							backgroundColor : 'rgba(30,144,255,0.8)',
							borderWidth : 1,
							borderColor : '#fff',
							shadowColor : '#fff', // 默认透明
							shadowBlur : 5,
							offsetCenter : [ 0, '50%' ], // x, y，单位px
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								/* fontWeight: 'bolder', */
								color : '#fff'
							}
						},
						data : [ {
							value : $scope.emhData.temp,
							name : '温度'
						} ]
					} ]
				};

				chart.setOption(option);

			});
		};
		/* 湿度 */
		$scope.loadDamp = function() {
			// 基于准备好的dom，初始化echarts实例
			var chart = null;
			if (chart != null) {
				chart.clear();
				chart.dispose();
			}
			require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
				chart = ec.init(document.getElementById('damp-div'));
				/*
				 * chart.showLoading({ text : '正在努力的读取数据中...' });
				 */
				var option = {
					backgroundColor : '#1b1b1b',
					tooltip : {
						formatter : "{a} <br/>{b} : {c}℃"
					},
					series : [ {
						name : '湿度',
						type : 'gauge',
						min : 0,
						max : 90,
						splitNumber : 10, // 分割段数，默认为5

						axisLine : { // 坐标轴线
							lineStyle : { // 属性lineStyle控制线条样式
								color : [ [ 0.2, 'lime' ], [ 0.8, '#1e90ff' ],
										[ 1, '#ff4500' ] ],
								width : 4,
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						axisTick : { // 坐标轴小标记
							length : 10, // 属性length控制线长
							lineStyle : { // 属性lineStyle控制线条样式
								color : 'auto',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						axisLabel : { // 坐标轴小标记
							textStyle : { // 属性lineStyle控制线条样式
								fontWeight : 'bolder',
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						splitLine : { // 分隔线
							length : 15, // 属性length控制线长
							lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
								width : 3,
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						pointer : { // 分隔线
							shadowColor : '#fff', // 默认透明
							shadowBlur : 5
						},
						title : {
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								fontWeight : 'bolder',
								fontSize : 20,
								fontStyle : 'italic',
								color : '#fff',
								shadowColor : '#fff', // 默认透明
								shadowBlur : 10
							}
						},
						detail : {
							backgroundColor : 'rgba(30,144,255,0.8)',
							borderWidth : 1,
							borderColor : '#fff',
							shadowColor : '#fff', // 默认透明
							shadowBlur : 5,
							offsetCenter : [ 0, '50%' ], // x, y，单位px
							textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								/* fontWeight: 'bolder', */
								color : '#fff'
							}
						},
						data : [ {
							value : $scope.emhData.damp,
							name : '湿度'
						} ]
					} ]
				};

				chart.setOption(option);

			});
		};
		// 获取环控设备状态
		$scope.bsId = 132;
		$scope.bsName = "交换中心";
		$scope.period=4;

		$scope.emh = function() {
			$http.get(
					"../../bsstatus/bsEmh?siteId=" + $scope.bsId + "&period="
							+ $scope.period).success(function(response) {
				$scope.emhData = response;
				$scope.emhAlarm = response.alarmItems;

				$scope.loadTemp();
				$scope.loadDamp();

			});

		}
		
		
		/*setInterval(function(){
			$scope.refresh();
			
			}, 10000);*/ //每隔 10 秒 
		
	});
};


// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
/* cpu */
xh.loadCpu = function(value,id) {
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById("cpu-"+id));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			backgroundColor : '#fff',
			tooltip : {
				formatter : "cpu占用率 : {c}%"
			},
			series : [ {
				/*name : 'CPU',*/
				type : 'gauge',
				min : 0,
				max : 100,
				
				splitNumber : 5, // 分割段数，默认为5

				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.3, 'green' ], [ 0.6, '#1e90ff' ],
								[ 1, '#ff4500' ] ],
						width : 1,
						shadowColor : '#fff', // 默认透明
						shadowBlur : 10
					}
				},
				axisTick : { // 坐标轴小标记
					length : 10, // 属性length控制线长
					
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto',
						shadowColor : '#fff', // 默认透明
						shadowBlur : 10
					}
				},
				axisLabel : { // 坐标轴小标记
					textStyle : { // 属性lineStyle控制线条样式
						fontWeight : 'bolder',
						color : '#000',
						shadowColor : '#fff', // 默认透明
						shadowBlur : 10
					}
				},
				splitLine : { // 分隔线
					length : 15, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						width : 3,
						color : '#000',
						shadowColor : '#fff', // 默认透明
						shadowBlur : 10
					}
				},
				pointer : { // 分隔线
					shadowColor : '#fff', // 默认透明
					shadowBlur : 5
				},
				title : {
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						fontWeight : 'bolder',
						fontSize : 10,
						fontStyle : 'italic',
						color : '#000',
						shadowColor : '#fff', // 默认透明
						shadowBlur : 10
					}
				},
				detail : {
					/*backgroundColor : 'rgba(30,144,255,0.8)',*/
					borderWidth : 0,
					/*borderColor : '#fff',
					shadowColor : '#fff', // 默认透明
					
*/					
					formatter: 'cpu占用率:{value}%',
					shadowBlur : 5,
					offsetCenter : [ 0, '50%' ], // x, y，单位px
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						 fontWeight: 'bolder',
						 fontSize:'15'
					}
				},
				data : [ {
					value : value
				} ]
			} ]
		};
		/*xh.pie();*/

		chart.setOption(option);

	});
};
xh.bsBar = function(bs3,bs4) {
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#icp-pie").width(350);
		$("#icp-pie").height(300);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		chart = ec.init(document.getElementById('icp-pie'));
		var option = {
			    
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['与交换中心传输断开的基站统计']
			    },
			    
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            
			            data : ['三期基站','四期基站']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'与交换中心传输断开的基站统计',
			            type:'bar',
			            barWidth: 30,//固定柱子宽度
			            data:[bs3,bs4],
			            itemStyle:{
			            	normal:{  
			        　　　　　　　　color: function (params){
			                                var colorList = ['saddlebrown','darkred'];
			                                return colorList[params.dataIndex];
			                   }}},
			            
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            }
			        }
			    ]
			};
		
		chart.setOption(option);

	});
	window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};
	

};
xh.pie = function(data) {
	var resizeBsContainer = function() {
		$("#icp-pie").width(parseInt($("#icp-pie").parent().width()));
		$("#icp-pie").height(150);
	};
	resizeBsContainer();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById("icp-pie"));
		/*chart.showLoading({
			text : '正在努力的读取数据中...'
		});*/
		var option = {
			/*title : {
				x : 'center',
				text : '终端状态统计图',
				subtext : '',
				textStyle : {
					color : '#fff'
				}
			},*/
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			/*legend : {
				orient : 'vertical',
				x : 'left',
				textStyle : {
					color : '#ccc'
				},
				data : [ '离线', '在线' ]
			},*/
			series : [ {
				name : '数量',
				type : 'pie',
				/*radius : ['50%','70%'], */ 
                center: ['50%', '50%'], 
				itemStyle : {
					
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = [ '#C1232B', 'green', '#FCCE10' ];
							return colorList[params.dataIndex];
						},
						label : {
							show : false,
							position : 'top',
							formatter : '{b}\n{c}'
						}
					}
				},
				 data:[{name:"3",value:4},{name:"4",value:5}]
			} ]
		};

		chart.setOption(option);

	});
	// 用于使chart自适应高度和宽度
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
};
