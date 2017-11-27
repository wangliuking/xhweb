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
var background = "#fff";
var frist = 0;
var appElement = document.querySelector('[ng-controller=userstatus]');
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
	app.filter('power', function() { // 可以注入依赖
		return function(text) {
			return text - 15;
		};
	});
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);

	app.controller("userstatus", function($scope, $http, $location) {
		$scope.count = "20";// 每页数据显示默认值
		$scope.bsId = $location.search().bsId;
		$scope.period = $location.search().period;
		

		$http.get("../../bsstatus/bsEmh?siteId=" + $scope.bsId+"&period="+$scope.period).success(
				function(response) {
					$scope.emhData = response;
					$scope.emhAlarm = response.alarmItems;
					
				});
		

		var bsId = $scope.bsId;
		var pageSize = $("#page-limit").val();
		/*$http.get("../../bs/map/dataById?bsId=" + bsId).success(
				function(response) {
					$scope.bsinfoData = response.items[0];
				});*/
		

		// 获取环控设备状态

		$scope.emh = function() {
			$http.get("../../bsstatus/bsEmh?siteId=" + $scope.bsId+"&period="+$scope.period).success(
					function(response) {
						$scope.emhData = response;
					});
		};
		
		//环控摄像头
		$scope.initCamera = function(){
			//查询环控设备的IP
			$http.get("../../gonsuncn/cameraIp?bsId=" + $scope.bsId).success(
					function(response) {
						var tempData = response.items;
						var cameraIp = tempData[0].deviceIP;
						cameraConfig(cameraIp);
					});
			
		};

		// 基站下的注册终端
		$scope.radioUser = function() {
			var bsId = $scope.bsId;
			frist = 0;
			var pageSize = $("#page-limit").val();
			$http.get(
					"../../radio/status/oneBsRadio?bsId=" + bsId
							+ "&start=0&limit=" + pageSize).success(
					function(response) {
						$scope.radioData = response.items;
						$scope.radioTotals = response.totals;
						xh.pagging(1, parseInt($scope.radioTotals), $scope);
					});
		};
		// 基站下的注册组
		$scope.bsGroup = function() {
			var bsId = $scope.bsId;
			var pageSize = $("#page-limit").val();
			$http.get(
					"../../radio/status/oneBsGroup?bsId=" + bsId
							+ "&start=0&limit=" + pageSize).success(
					function(response) {
						$scope.groupData = response.items;
						$scope.groupTotals = response.totals;
					});
		};
		$scope.business=function(){
			$scope.radioUser();
			$scope.bsGroup();
		};
		// 基站下的bsc状态
		$scope.bsc = function() {
			var bsId = $scope.bsId;
			$http.get("../../bsstatus/bsc?bsId=" + bsId).success(
					function(response) {
						$scope.bscData = response.items;
						$scope.bscTotals = response.totals;
					});
		};
		// 基站下的bsr状态
		$scope.bsr = function() {
			var bsId = $scope.bsId;
			$http.get("../../bsstatus/bsr?bsId=" + bsId).success(
					function(response) {
						$scope.bsrData = response.items;
						$scope.bsrTotals = response.totals;
					});
		};
		// 基站下的dpx状态
		$scope.dpx = function() {
			var bsId = $scope.bsId;
			$http.get("../../bsstatus/dpx?bsId=" + bsId).success(
					function(response) {
						$scope.dpxData = response.items;
						$scope.dpxTotals = response.totals;
					});
		};
		// 基站下的psm状态
		$scope.psm = function() {
			var bsId = $scope.bsId;
			$http.get("../../bsstatus/psm?bsId=" + bsId).success(
					function(response) {
						$scope.psmData = response.items;
						$scope.psmTotals = response.totals;
					});
		};
		// 根据基站ID查找基站相邻小区
		$scope.neighborByBsId = function() {
			var bsId = $scope.bsId;
			$http.get("../../bs/neighborByBsId?bsId=" + bsId).success(
					function(response) {
						$scope.neighborData = response.items;
						$scope.neighborTotals = response.totals;
					});
		};
		// 根据基站ID查找基站切换参数
		$scope.handoverByBsId = function() {
			var bsId = $scope.bsId;
			$http.get("../../bs/handoverByBsId?bsId=" + bsId).success(
					function(response) {
						$scope.handoverData = response.items;
						$scope.handoverTotals = response.totals;
					});
		};
		// 根据基站ID查找基站BSR配置信息
		$scope.bsrconfigByBsId= function() {
			var bsId = $scope.bsId;
			$http.get("../../bs/bsrconfigByBsId?bsId=" + bsId).success(
					function(response) {
						$scope.bsrconfigData = response.items;
						$scope.bsrconfigTotals = response.totals;
					});
		};
		// 根据基站ID查找基站传输配置信息
		$scope.linkconfigByBsId = function() {
			var bsId = $scope.bsId;
			$http.get("../../bs/linkconfigByBsId?bsId=" + bsId).success(
					function(response) {
						$scope.linkconfigData = response.items;
						$scope.linkconfigTotals = response.totals;
					});
		};

		$scope.equip = function() {
			$scope.bsc();
			$scope.bsr();
			$scope.dpx();
			$scope.psm();
		};
		//基站配置参数
		$scope.config = function() {
			$scope.neighborByBsId();
			$scope.handoverByBsId();
			$scope.bsrconfigByBsId();
			$scope.linkconfigByBsId();
		};
		


		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
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

		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../radio/status/oneBsRadio?bsId=" + $scope.bsId
							+ "&start=" + start + "&limit=" + pageSize)
					.success(function(response) {
						xh.maskHide();
						$scope.radioData = response.items;
						$scope.radioTotals = response.totals;
						xh.pagging(page, parseInt($scope.radioTotals), $scope);
					});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../radio/status/oneBsRadio?bsId=" + $scope.bsId
							+ "&start=" + start + "&limit=" + pageSize)
					.success(function(response) {
						xh.maskHide();

						$scope.start = (page - 1) * pageSize + 1;
						$scope.lastIndex = page * pageSize;
						if (page == totalPages) {
							if (totals > 0) {
								$scope.lastIndex = totals;
							} else {
								$scope.start = 0;
								$scope.lastIndex = 0;
							}
						}
						$scope.radioData = response.items;
						$scope.radioTotals = response.totals;
					});

		};
		$scope.equip();

	});
};
/* 基站属性 */
xh.bsNature = function(bsId) {
	var $scope = angular.element(appElement).scope();
	$scope.radioUser(bsId);

};
// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
xh.excelToBsstatus = function() {
	window.location.href = "../../bsstatus/ExcelToStationStatus";
};
/* 温度 */

xh.loadDamp2 = function() {
	var $scope = angular.element(appElement).scope();
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
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%RH"
			},
			series : [ {
				name : '湿度',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, 'green' ],
								[ 1, '#ff4500' ] ],
						width : 6
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
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
/* AC电压 */
xh.loadAcv = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#acv-div").width(parseInt($("#acv-div").parent().width()) - 20);
	 * $("#temp-div").height(300); resizeBarContainer(); };
	 */
	var $scope = angular.element(appElement).scope();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById('acv-div'));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}V"
			},
			series : [ {
				name : 'AC电压',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, '#48b' ],
								[ 1, '#ff4500' ] ],
						width : 6
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
				},
				data : [ {
					value : $scope.acv,
					name : 'AC电压'
				} ]
			} ]
		};

		chart.setOption(option);

	});
};
/* AC电流 */
xh.loadAci = function() {
	/*
	 * // 设置容器宽高 var resizeBarContainer = function() {
	 * $("#aci-div").width(parseInt($("#aci-div").parent().width()) - 20);
	 * $("#temp-div").height(300); resizeBarContainer(); };
	 */
	var $scope = angular.element(appElement).scope();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById('aci-div'));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}I"
			},
			series : [ {
				name : 'AC电流',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, '#48b' ],
								[ 1, '#ff4500' ] ],
						width : 2
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
				},
				data : [ {
					value : $scope.aci,
					name : 'AC电流'
				} ]
			} ]
		};

		chart.setOption(option);

	});
};
/* DC电压 */
xh.loadDcv = function() {
	/*
	 * // 设置容器宽高 var resizeBarContainer = function() {
	 * $("#dcv-div").width(parseInt($("#dcv-div").parent().width()) - 20);
	 * $("#temp-div").height(300); resizeBarContainer(); };
	 */
	var $scope = angular.element(appElement).scope();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById('dcv-div'));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}V"
			},
			series : [ {
				name : 'DC电压',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, '#48b' ],
								[ 1, '#ff4500' ] ],
						width : 2
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
				},
				data : [ {
					value : $scope.dcv,
					name : 'DC电压'
				} ]
			} ]
		};

		chart.setOption(option);

	});
};
/* DC电流 */
xh.loadDci = function() {
	// 设置容器宽高
	/*
	 * var resizeBarContainer = function() {
	 * $("#dci-div").width(parseInt($("#dci-div").parent().width()) - 20);
	 * $("#temp-div").height(300); resizeBarContainer(); };
	 */
	var $scope = angular.element(appElement).scope();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById('dci-div'));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}I"
			},
			series : [ {
				name : 'DC电流',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, '#48b' ],
								[ 1, '#ff4500' ] ],
						width : 2
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
				},
				data : [ {
					value : $scope.aci,
					name : 'DC电压'
				} ]
			} ]
		};

		chart.setOption(option);

	});
};
/* 有功功率 */
xh.loadPow = function() {
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#dci-div").width(parseInt($("#dci-div").parent().width()) - 20);
		/* $("#temp-div").height(300); */
		resizeBarContainer();
	};
	var $scope = angular.element(appElement).scope();
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/gauge' ], function(ec) {
		chart = ec.init(document.getElementById('dci-div'));
		/*
		 * chart.showLoading({ text : '正在努力的读取数据中...' });
		 */
		var option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			series : [ {
				name : '业务指标',
				type : 'gauge',
				splitNumber : 10, // 分割段数，默认为5
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						color : [ [ 0.2, '#62cb31' ], [ 0.8, '#48b' ],
								[ 1, '#ff4500' ] ],
						width : 2
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 5, // 属性length控制线长
					lineStyle : { // 属性lineStyle控制线条样式
						color : 'auto'
					}
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : 'auto'
					}
				},
				splitLine : { // 分隔线
					show : true, // 默认显示，属性show控制显示与否
					length : 10, // 属性length控制线长
					lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
						color : 'auto'
					}
				},
				pointer : {
					width : 5
				},
				data : [ {
					value : $scope.aci,
					name : 'DC电压'
				} ]
			} ]
		};

		chart.setOption(option);

	});
};

/* 数据分页 */
xh.pagging = function(currentPage, totals, $scope) {
	var pageSize = $("#page-limit").val();
	var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
			.ceil(parseInt(totals, 10) / pageSize);
	var start = (currentPage - 1) * pageSize + 1;
	var end = currentPage * pageSize;
	if (currentPage == totalPages) {
		if (totals > 0) {
			end = totals;
		} else {
			start = 0;
			end = 0;
		}
	}
	$scope.start = start;
	$scope.lastIndex = end;
	$scope.totals = totals;
	if (totals > 0) {
		$(".page-paging").html('<ul class="pagination"></ul>');
		$('.pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 3,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};

/**
 * 摄像头相关配置
 */
function cameraConfig(cameraIp){
	// 检查插件是否已经安装过
    var iRet = WebVideoCtrl.I_CheckPluginInstall();
	if (-2 == iRet) {
		alert("您的Chrome浏览器版本过高，不支持NPAPI插件！");
		return;
	} else if (-1 == iRet) {
        alert("您还未安装过插件，双击开发包目录里的WebComponentsKit.exe安装！");
		return;
    }

	var oPlugin = {
		iWidth: 800,			// plugin width
		iHeight: 420			// plugin height
	};
	
	// 初始化插件参数及插入插件
	WebVideoCtrl.I_InitPlugin(oPlugin.iWidth, oPlugin.iHeight, {
        bWndFull: true,//是否支持单窗口双击全屏，默认支持 true:支持 false:不支持
        iWndowType: 1,
		cbSelWnd: function (xmlDoc) {
			
		}
	});
	WebVideoCtrl.I_InsertOBJECTPlugin("divPlugin");

	// 检查插件是否最新
	if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
		alert("检测到新的插件版本，双击开发包目录里的WebComponentsKit.exe升级！");
		return;
	}

	var oLiveView = {
		iProtocol: 1,			// protocol 1：http, 2:https
		szIP: cameraIp,	// protocol ip
		szPort: "80",			// protocol port
		szUsername: "admin",	// device username
		szPassword: "gosuncn123",	// device password
		iStreamType: 1,			// stream 1：main stream  2：sub-stream  3：third stream  4：transcode stream
		iChannelID: 1,			// channel no
		bZeroChannel: false		// zero channel
	};

	// 登录设备
	WebVideoCtrl.I_Login(oLiveView.szIP, oLiveView.iProtocol, oLiveView.szPort, oLiveView.szUsername, oLiveView.szPassword, {
		success: function (xmlDoc) {
			// 开始预览
			WebVideoCtrl.I_StartRealPlay(oLiveView.szIP, {
				iStreamType: oLiveView.iStreamType,
				iChannelID: oLiveView.iChannelID,
				bZeroChannel: oLiveView.bZeroChannel
			});
		}
	});

	// 关闭浏览器
	$(window).unload(function () {
		WebVideoCtrl.I_Stop();
	});
}

