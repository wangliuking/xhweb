/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
var appElement = document.querySelector('[ng-controller=monitor]');
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("monitor", function($scope, $http) {

		$scope.hideMenu = function() {
			$("body").toggleClass("hide-menu");
		};
		// xh.bsChart();
		xh.map();

		// 更新基站断站告警状态
		$scope.updateAlarm = function() {
			$.ajax({
				url : 'bsstatus/updateAlarm',
				type : 'POST',
				dataType : "json",
				data : {},
				async : false,
				success : function(data) {
				},
				error : function() {
				}
			});
		};

	});
};
xh.map = function() {
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#map").width(parseInt($("#map").parent().width()));
		$("#map").height(500);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}

	
	

	require([ 'echarts', 'echarts/chart/map' ], function(ec) {
		chart = ec.init(document.getElementById('map'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var cityMap = {
			    "成都市": "510100",
			    /*"自贡市": "510300",
			    "攀枝花市": "510400",
			    "泸州市": "510500",
			    "德阳市": "510600",*/
			   /* "绵阳市": "510700",*/
			    /*"广元市": "510800",
			    "遂宁市": "510900",
			    "内江市": "511000",
			    "乐山市": "511100",
			    "南充市": "511300",
			    "眉山市": "511400",
			    "宜宾市": "511500",
			    "广安市": "511600",
			    "达州市": "511700",
			    "雅安市": "511800",
			    "巴中市": "511900",
			    "资阳市": "512000",
			    "阿坝藏族羌族自治州": "513200",
			    "甘孜藏族自治州": "513300",
			    "凉山彝族自治州": "513400",*/
			};
		var curIndx = 0;
		var mapType = [];
		var mapGeoData = require('echarts/util/mapData/params');
		console.log(mapGeoData);
		for (var city in cityMap) {
		    mapType.push(city);
		    // 自定义扩展图表类型
		    mapGeoData.params[city] = {
		        getGeoJson: (function (c) {
		            var geoJsonName = cityMap[c];
		            return function (callback) {
		                $.getJSON('../../lib/echarts/util/mapData/params/' + geoJsonName + '.json', callback);
		            };
		        })(city)
		    };
		}
	/*	var ecConfig = require('echarts/config');
		var zrEvent = require('zrender/tool/event');
		
		document.getElementById('map').onmousewheel = function (e){
		    var event = e || window.event;
		    curIndx += zrEvent.getDelta(event) > 0 ? (-1) : 1;
		    if (curIndx < 0) {
		        curIndx = mapType.length - 1;
		    }
		    var mt = mapType[curIndx % mapType.length];
		    option.series[0].mapType = mt;
		    option.title.subtext = mt + ' （滚轮或点击切换）';
		    chart.setOption(option, true);
		    zrEvent.stop(event);
		};*/
	
		var option = {
			title : {
				subtext : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item'
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '基站异常' ]
			},
			dataRange : {
				  orient: 'horizontal',
			        x: 'right',
			        min: 0,
			        max: 1000,
			        color:['orange','yellow'],
			        text:['高','低'],           // 文本，默认为数值文本
			        splitNumber:0
				/*min : 0,
				max : 500,
				x : 'left',
				y : 'bottom',
				orient: 'horizontal',
			    x: 'right',
                color:['yellow','red'],
             
				text : [ '低', '高' ], // 文本，默认为数值文本
				calculable : true*/
			},
			/*roamController : {
				show : true,
				x : 'right',
				mapTypeControl : {
					'china' : true
				}
			},*/
			series : [ {
				name : '基站异常',
				type : 'map',
				mapType : '成都市',
				roam : false,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					},
					emphasis : {
						label : {
							show : true
						}
					}
				},
				data : [ {
					name : '青羊区',
					value : Math.round(Math.random() * 1000)
				}, {
					name : '平武县',
					value : Math.round(Math.random() * 1000)
				}]
			} ]
		};
		
		$.ajax({
			url : '../../bsstatus/bsZoneAlarm',
			type : 'GET',
			dataType : "json",
			async : true,
			data:{
			},
			success : function(response) {

				var data = response.items;
				// option.xAxis[0].data = xAxisData;
				option.series[0].data = data;
				/* option.title.subtext="当前基站总数:"+response.totals; */
				chart.hideLoading();
				chart.setOption(option);
			},
			error : function() {
			}
		});
		
		
		
		
		
		

	});
	
	

};
xh.bsChart = function() {
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#bs-chart").width(parseInt($("#bs-chart").parent().width()));
		$("#bs-chart").height(300);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('bs-chart'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var labelTop = {
			normal : {
				color : function(params) {
					// build a color map as your need.
					var colorList = [ 'green', 'red', 'green', 'green' ];
					return colorList[params.dataIndex];
				},
				label : {
					show : true,
					position : 'center',
					formatter : "{b} : {c}",
					textStyle : {
						baseline : 'bottom'
					}
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelFromatter = {
			normal : {
				color : function(params) {
					// build a color map as your need.
					var colorList = [ 'green', 'red', 'green', 'green' ];
					return colorList[params.dataIndex];
				},
				label : {
					formatter : function(params) {
						return params.value
					},
					textStyle : {
						baseline : 'top'
					}
				}
			},
		};
		var labelBottom = {
			normal : {
				color : '#ccc',
				label : {
					show : true,
					position : 'center'
				},
				labelLine : {
					show : false
				}
			},
			emphasis : {
				color : 'green'
			}
		};
		var radius = [ 40, 55 ];
		option = {
			legend : {
				x : 'center',
				y : 'center',
				style : 'margin-top:50px;',
				data : [ '基站', '交换中心', '调度台', '系统故障' ]
			},
			title : {
				text : '系统实时监测',
				subtext : '',
				x : 'center'
			},
			color : [ 'red', 'red', 'red', 'red' ],
			series : [ {
				type : 'pie',
				center : [ '10%', '30%' ],
				radius : radius,
				x : '0%', // for funnel
				itemStyle : labelFromatter,
				data : [ {
					name : 'other',
					value : 46,
					itemStyle : labelBottom
				}, {
					name : '基站',
					value : 54,
					itemStyle : labelTop
				} ]
			}, {
				type : 'pie',
				center : [ '40%', '30%' ],
				radius : radius,
				x : '20%', // for funnel
				itemStyle : labelFromatter,
				data : [ {
					name : 'other',
					value : 56,
					itemStyle : labelBottom
				}, {
					name : '交换中心',
					value : 44,
					itemStyle : labelTop
				} ]
			}, {
				type : 'pie',
				center : [ '60%', '30%' ],
				radius : radius,
				x : '40%', // for funnel
				itemStyle : labelFromatter,
				data : [ {
					name : 'other',
					value : 65,
					itemStyle : labelBottom
				}, {
					name : '调度台',
					value : 35,
					itemStyle : labelTop
				} ]
			}, {
				type : 'pie',
				center : [ '90%', '30%' ],
				radius : radius,
				x : '60%', // for funnel
				itemStyle : labelFromatter,
				data : [ {
					name : '系统故障',
					value : 30,
					itemStyle : labelTop
				} ]
			} ]
		};
		chart.hideLoading();
		chart.setOption(option);

	});

};
