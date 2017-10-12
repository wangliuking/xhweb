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
		$scope.count = "20";//每页数据显示默认值
		$scope.operationMenu=true; //菜单变色
		/*xh.loadUserStatusPie();*/
		/*获取日志信息*/
		$http.get("../../status/dispatch").
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
		});
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var userId=$("#userId").val();
			var regStatus=$("#regStatus").val();
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
			$http.get("../../server/list").
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
		};
		
	});
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
/* 终端状态统计图 */
xh.loadUserStatusPie = function() {
	// 设置容器宽高
	 var resizeBarContainer = function() {
	  $("#userStatus-pie").width(parseInt($("#userStatus-pie").parent().width()));
	  $("#userStatus-pie").height(300);
	  };
	  resizeBarContainer();
	 
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
			legend : {
				orient : 'vertical',
				x : 'left',
				textStyle : {
					color : '#ccc'
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
			url : '../../operations/data/userstatusChart',
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
