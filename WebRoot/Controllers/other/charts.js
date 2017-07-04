/**
 * 数据图形分析
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
var background="#272625";

// data : [ '外借', '报废','维修','在库','在用','待报废','遗失']
/* 初始化数据 */
xh.initialize = function() {
	var app = angular.module("app", []);
	app.controller("charts", function($scope, $http) {
		$scope.chartMenu=true;
		/* 资产状态图形统计分析 */
		xh.loadStatusBar();
		/* 基站状态统计图 */
		xh.loadBsStatusPie();
		/* 终端状态统计图 */
		xh.loadUserStatusPie();
		// 基站分布图
		xh.bsPostion();
		/* 当天呼叫记录统计图 */
		xh.loadHourCallLine();
		/* 系统告警*/
		xh.alarmBar();

	});
};
/* 资产状态图形统计分析 */
xh.loadStatusBar = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#status-bar").width(parseInt($("#statusbar").width()) - 20);
	 * $("#status-bar").height(300);
	 * $("#status-bar").width(parseInt($("#statusbar").width()));
	 * $("#status-bar").height(parseInt($("#statusbar").height())); };
	 * resizeBarContainer();
	 */
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/bar' ],
			function(ec) {
				chart = ec.init(document.getElementById('status-bar'));
				chart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var option = {
					title : {
						x : 'center',
						text : '现有资产状态统计图',
						subtext : '',
						textStyle : {
							color : '#fff'
						}
					},
					tooltip : {
						trigger : 'axis'
					},
					/*
					 * legend : { data : [ '外借', '报废', '维修', '在库', '在用', '待报废',
					 * '遗失' ] },
					 */
					calculable : false,
					backgroundColor : background,
					xAxis : [ {
						type : 'category',// 类目型
						show : true,
						axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        },
						data : [ '外借', '报废', '维修', '在库', '在用', '待报废', '遗失' ]
					} ],
					yAxis : [ {
						type : 'value',
						axisLabel : {
							formatter : '{value}',
							textStyle : {
								color : '#fff'
							},
						},
						splitArea : {
							show : true
						// 分隔区域
						}
					} ],
					series : [ {
						name : '数量',
						type : 'bar',
						barWidth : 30,
						itemStyle : {
							normal : {
								color : function(params) {
									// build a color map as your need.
									var colorList = [ '#C1232B', '#B5C334',
											'#FCCE10', '#E87C25', '#27727B',
											'#FE8463', '#9BCA63', '#FAD860',
											'#F3A43B', '#60C0DD', '#D7504B',
											'#C6E579', '#F4E001', '#F0805A',
											'#26C0C0' ];
									return colorList[params.dataIndex];
								},
								label : {
									show : true,
									position : 'top',
									formatter : '{b}\n{c}'
								}
							}
						},
						data : []
					} ]
				};

				$.ajax({
					url : '../../chart/assetStatusByChart',
					data : {},
					type : 'get',
					dataType : "json",
					async : false,
					success : function(response) {
						var data = response.items;
						var xAxisData = [], seriesData = [];
						for (var i = 0; i < data.length; i++) {
							xAxisData.push(data[i].name);
							seriesData.push(data[i].value);
						}
						// option.xAxis[0].data = xAxisData;
						option.series[0].data = seriesData;
						chart.hideLoading();
						chart.setOption(option);

					},
					failure : function(response) {
					}
				});

			});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		// resizeBarContainer();
		chart.resize();
	};
};
/* 基站状态统计图 */
xh.loadBsStatusPie = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#bsStatus-pie").width(parseInt($("#statusbar").width()) - 20);
	 * $("#bsStatus-pie").height(300);
	 * $("#bsStatus-pie").width(parseInt($("#statusbar").width()));
	 * $("#bsStatus-pie").height(parseInt($("#statusbar").height())); };
	 * resizeBarContainer();
	 */
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('bsStatus-pie'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
			title : {
				x : 'center',
				text : '基站状态统计图',
				subtext : '',
				textStyle : {
					color : '#fff'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				textStyle : {
					color : '#fff'
				},
				data : [ '基站断开', '正常基站', '未启用' ]
			},
			calculable : false,
			backgroundColor : background,
			series : [ {
				name : '基站数量',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				itemStyle : {
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = [ '#C1232B', 'green', '#FCCE10' ];
							return colorList[params.dataIndex];
						},
						label : {
							show : true,
							position : 'top',
							formatter : '{b}\n{c}'
						}
					}
				},
				data : []
			} ]
		};

		$.ajax({
			url : '../../chart/bsStatusByChart',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				var data = response.items;
				// option.xAxis[0].data = xAxisData;
				option.series[0].data = data;
				option.title.subtext = "当前基站总数:" + response.totals;
				chart.hideLoading();
				chart.setOption(option);

			},
			failure : function(response) {
			}
		});

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		// resizeBarContainer();
		chart.resize();
	};
};
/* 终端状态统计图 */
xh.loadUserStatusPie = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#userStatus-pie").width(parseInt($("#statusbar").width()) - 20);
	 * $("#userStatus-pie").height(300);
	 * $("#userStatus-pie").width(parseInt($("#statusbar").width()));
	 * $("#userStatus-pie").height(parseInt($("#statusbar").height())); };
	 * resizeBarContainer();
	 */
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('userStatus-pie'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
			title : {
				x : 'center',
				text : '终端状态统计图',
				subtext : '',
				textStyle : {
					color : '#fff'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				textStyle : {
					color : '#fff'
				},
				data : [ '离线', '在线' ]
			},
			/*
			 * toolbox : { show : true, feature : { dataView : { show : true,
			 * readOnly : false }, restore : { show : true }, saveAsImage : {
			 * show : true } } },
			 */
			calculable : false,
			backgroundColor : background,
			series : [ {
				name : '数量',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				itemStyle : {
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = [ '#C1232B', 'green', '#FCCE10' ];
							return colorList[params.dataIndex];
						},
						label : {
							show : true,
							position : 'top',
							formatter : '{b}\n{c}'
						}
					}
				},
				data : []
			} ]
		};

		$.ajax({
			url : '../../chart/userStatusByChart',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				var data = response.items;
				// option.xAxis[0].data = xAxisData;
				option.series[0].data = data;
				/* option.title.subtext="当前基站总数:"+response.totals; */
				chart.hideLoading();
				chart.setOption(option);

			},
			failure : function(response) {
			}
		});

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};
};
// 基站分布图
xh.bsPostion = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#bsPostion-map").width(parseInt($("#statusbar").width()));
	 * $("#bsPostion-map").height(parseInt($("#statusbar").height())); };
	 * resizeBarContainer();
	 */
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/map' ], function(ec) {
		chart = ec.init(document.getElementById('bsPostion-map'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		// 初始化地图
		/*
		 * var BMapExt = new BMapExtension(domMain, BMap, require('echarts'),
		 * require('zrender')); var map = BMapExt.getMap(); var container =
		 * BMapExt.getEchartsContainer(); var point = new
		 * BMap.Point(startPoint.x, startPoint.y); map.centerAndZoom(point, 5);
		 * map.enableScrollWheelZoom(true);
		 */
		var option = {
			title : {
				text : '基站成都市分布地图',
				subtext : '',
				x : 'center',
				textStyle : {
					color : '#fff'
				}
			},
			tooltip : {
				trigger : 'item'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				textStyle : {
					color : '#fff'
				},
				data : [ '基站成都市分布' ]
			},
			backgroundColor : background,
			dataRange : {
				min : 0,
				max : 1500,
				calculable : true,
				color : [ 'red', 'yellow', 'red' ]
			},
			series : [ {
				name : '基站分布',
				type : 'map',
				mapType : '四川|成都市',
				hoverable : false,
				roam : true,
				data : [],
				markPoint : {
					symbolSize : 5, // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize *
									// 2
					itemStyle : {
						normal : {
							borderColor : '#272625',
							borderWidth : 1, // 标注边线线宽，单位px，默认为1
							label : {
								show : false
							}
						},
						emphasis : {
							borderColor : '#1e90ff',
							borderWidth : 5,
							label : {
								show : true
							}
						}
					},
					data : []
				},
				geoCoord : {}
			}/*, {
	            name: 'Top5',
	            type: 'map',
	            mapType: 'china',
	            data:[],
	            markPoint : {
	                symbol:'emptyCircle',
	                symbolSize : function (v){
	                    return 10 + v/100
	                },
	                effect : {
	                    show: true,
	                    shadowBlur : 0
	                },
	                itemStyle:{
	                    normal:{
	                        label:{show:false}
	                    }
	                },
	                data : [
	                    {name: "政府三办", value: 193}
	                ]
	            }
	        } */]
		};

		$.ajax({
			url : '../../chart/bsPostion',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				var data = response.bsInfoData;
				// option.xAxis[0].data = xAxisData;geoCoord
				option.series[0].markPoint.data = data;
				option.series[0].geoCoord = response.bsLatLngData[0];
				/* option.title.subtext="当前基站总数:"+response.totals; */
				chart.hideLoading();
				chart.setOption(option);

			},
			failure : function(response) {
			}
		});
	});
};
/* 当天呼叫记录统计图 */
xh.loadHourCallLine = function() {
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/line' ], function(ec) {
		chart = ec.init(document.getElementById('hourCall-line'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
			    title : {
			    	text : '今日通话直线图',
					subtext : '',
					x : 'center',
					textStyle : {
						color : '#fff'
					}
			    },
			    tooltip : {
			        trigger: 'axis',
			       /* axisPointer:{
			            show: false,
			            type : 'cross',
			            lineStyle: {
			                type : 'dashed',
			                width : 1
			            }
			        },*/
			        formatter : function (params) {
			            return params.seriesName + ' : [ '
			                   + params.value[0] + ', ' 
			                   + params.value[1] + ' ]';
			        }
			    },
			    legend: {
			    	orient : 'vertical',
					x : 'left',
					textStyle : {
						color : '#fff'
					},
			        data:['通话时间','通话次数']
			    },
			    backgroundColor : background,
			    calculable : true,
			    xAxis : [
			        {
			            type: 'value',
			            axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        }
			        }
			    ],
			    yAxis : [
			        {
			            type: 'value',
			            axisLine: {
			                lineStyle: {
			                    color: '#fff'
			                }
			            }
			        }
			    ],
			    series : [
			        {
			            name:'通话时间',
			            type:'line',
			            data:[]
			        },
			        {
			            name:'通话次数',
			            type:'line',
			            data:[]
			        }
			    ]
			};
			                    

		$.ajax({
			url : '../../chart/hourCallTime',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				// option.xAxis[0].data = xAxisData;
				option.series[0].data = response.callTime;
				option.series[1].data = response.callNum;
				/* option.title.subtext="当前基站总数:"+response.totals; */
				chart.hideLoading();
				chart.setOption(option);

			},
			failure : function(response) {
			}
		});

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};
};
//系统告警
xh.alarmBar=function(){
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		chart = ec.init(document.getElementById('alarm-bar'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		
var option = {
	    title : {
	        text: '今日告警统计',
	    	subtext : '',
			x : 'center',
			textStyle : {
				color : '#fff'
			}
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    calculable : false,
	    backgroundColor : background,
	    xAxis : [
	        {
	            type : 'value',
	            axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                },
	            boundaryGap : [0, 0.01]
	        }
	    ],
	    yAxis : [
	        {
	            type : 'category',
	            axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                },
	            data : ['一般告警','次要告警','重要告警','环监告警']
	        }
	    ],
	    series : [
	        {
	            name:'告警',
	            type:'bar',
	            barWidth : 10,
	            itemStyle : {
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = ['#FCCE10','#ec4d2d','red', '#C1232B', ];
							return colorList[params.dataIndex];
						}/*,
						label : {
							show : true,
							position : 'top',
							formatter : '{b}\n{c}'
						}*/
					}
				},
	            data:[12, 3, 2, 23]
	        }
	    ]
	};
		$.ajax({
			url : '../../chart/bsStatusByChart',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				var data = response.items;
				// option.xAxis[0].data = xAxisData;
				/*option.series[0].data = data;
				option.title.subtext = "当前基站总数:" + response.totals;*/
				chart.hideLoading();
				chart.setOption(option);

			},
			failure : function(response) {
			}
		});

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		// resizeBarContainer();
		chart.resize();
	};
};