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
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);

	app.controller("userstatus", function($scope, $http, $location) {
		
		$scope.bsId = $location.search().bsId;
		$scope.bsName = $location.search().bsName;
		
		console.log($scope.bsId +"====="+$scope.bsName)
		//发起请求开启当前基站视频流
		$.ajax({
			type : "GET",
			url : "../../camera/startById?bsId=" + $scope.bsId,
			dataType : "json",
			success : function(result) {
			}
		});
		//end
		$scope.period = $location.search().period;
		var bsId = $scope.bsId;
		// 摄像头编号
		$scope.cameraId = function() {
			var bsId = $scope.bsId;
			return bsId;
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

		// 获取环控设备状态
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
		$scope.emh();
		setInterval(function() {
			$scope.emh();
			/*
			 * $scope.loadTemp(); $scope.loadDamp();
			 */
			/* $scope.business(); */
		}, 20000);

	});
};
