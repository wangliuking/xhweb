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
var appElement = document.querySelector('[ng-controller=gonsuncn]');
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
	var pageSize = $("#page-limit").val();
	app.controller("gonsuncn", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";//每页数据显示默认值
		$scope.operationMenu=true; //菜单变色
		var deviceIds=[];
		$(".form-inline input:checked").each(function(i){
			deviceIds.push($(this).val());
		});
		var alarmLevel = $("#alarmLv  option:selected").val();
		var alarmFlag = $("#alarmFlags option:selected").val();
		$http.get("../../gonsuncn/alarmlist?start=0&limit="+pageSize+"&deviceIds="+deviceIds+"&alarmLevel="+alarmLevel+"&alarmFlag="+alarmFlag).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			xh.pagging(1, parseInt($scope.totals),$scope);
		});
		/*告警级别 告警状态*/
		$scope.provs = [{"id":"0","name":"告警级别"},{"id":"1","name":"一级告警"},{"id":"2","name":"二级告警"},{"id":"3","name":"三级告警"}];
		$scope.alarmStatus = [{"id":"0","name":"告警状态"},{"id":"1","name":"告警中"},{"id":"2","name":"告警结束"}];
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		/* 显示链接修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
			$scope.roleId=$scope.editData.roleId.toString();
		};
		/* 显示按钮修改model */
		$scope.showEditModel = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length != 1) {
				swal({
					title : "提示",
					text : "只能选择一条数据",
					type : "error"
				});
				return;
			}
			$("#edit").modal('show');
			$scope.editData = $scope.data[parseInt(checkVal[0])];
			$scope.roleId=$scope.editData.roleId.toString();
			
		};
		
		/*下拉框改变时触发函数*/
		$scope.change = function(x){
		    console.log(x);
		};
		
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			/*获取选中的checkbox和告警等级*/
			var deviceIds=[];
			$(".form-inline input:checked").each(function(i){
				deviceIds.push($(this).val());
			});
			var alarmLevel = $("#alarmLv  option:selected").val();
			var alarmFlag = $("#alarmFlags option:selected").val();
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
			$http.get("../../gonsuncn/alarmlist?start="+start+"&limit="+pageSize+"&deviceIds="+deviceIds+"&alarmLevel="+alarmLevel+"&alarmFlag="+alarmFlag).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals),$scope);
			});
		};
		//分页点击
		$scope.pageClick = function(page,totals, totalPages) {
			var $scope = angular.element(appElement).scope();
			/*获取选中的checkbox和告警等级*/
			var deviceIds=[];
			$(".form-inline input:checked").each(function(i){
				deviceIds.push($(this).val());
			});
			var alarmLevel = $("#alarmLv  option:selected").val();
			var alarmFlag = $("#alarmFlags option:selected").val();
			
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../gonsuncn/alarmlist?start="+start+"&limit="+pageSize+"&deviceIds="+deviceIds+"&alarmLevel="+alarmLevel+"&alarmFlag="+alarmFlag).
			success(function(response){
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
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
			
		};
	});
	
	xh.loadPieDev();
	xh.loadPieLv();
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
/* 传感器统计图 */
xh.loadPieDev = function() {
	// 设置容器宽高
	 var resizeBarContainer = function() {
	  $("#alarmPie").width(parseInt($("#alarmPie").parent().width()));
	  $("#alarmPie").height(200);
	  };
	  resizeBarContainer();
	 
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('alarmPie'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
			    /*title : {
			        text: '基站环控告警统计',
			        subtext: '传感器分类',
			        x:'center'
			    },*/
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:[]
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            /*mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: 1548
			                    }
			                }
			            },*/
			            restore : {show: true}
			            /*saveAsImage : {show: true}*/
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'告警传感器',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '55%'],
			            data:[
			                {value:752, name:'温度'},
			                {value:899, name:'湿度'},
			                {value:567, name:'UPS'},
			                {value:118, name:'电表'},
			                {value:442, name:'水浸'},
			                {value:335, name:'烟雾'},
			                {value:310, name:'红外'},
			                {value:234, name:'门磁'},
			                {value:1548, name:'FSU'}
			            ]
			        }
			    ]
			};
		$.ajax({
			type : "GET",
			url : "../../gonsuncn/alarmForDev",
			dataType : "json",
			success : function(dataMap) {
				var tempData = dataMap.alarmDevice;
				var legendData = [];
				var seriesData = [];
				for(var i=0;i<tempData.length;i++){
					legendData.push(tempData[i].deviceName);	
					var tempMap = {value:tempData[i].alarmNum, name:tempData[i].deviceName};
					seriesData.push(tempMap);
				}
				option.legend.data=legendData;
				option.series[0].data=seriesData;
				
				chart.hideLoading();
				chart.setOption(option);
			}
		});
		

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};
};
/* 级别统计图 */
xh.loadPieLv = function() {
	// 设置容器宽高
	 var resizeBarContainer = function() {
	  $("#alarmLevs").width(parseInt($("#alarmLevs").parent().width()));
	  $("#alarmLevs").height(200);
	  };
	  resizeBarContainer();
	 
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('alarmLevs'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
				/*title : {
			        text: '基站环控告警统计',
			        subtext: '级别分类',
			        x:'center'
			    },*/
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:[]
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            /*mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'center',
			                        max: 1548
			                    }
			                }
			            },*/
			            restore : {show: true}
			            /*saveAsImage : {show: true}*/
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'告警级别',
			            type:'pie',
			            radius : ['50%', '60%'],
			            center: ['50%', '55%'],
			            itemStyle : {
			                normal : {
			                    label : {
			                        show : false
			                    },
			                    labelLine : {
			                        show : false
			                    }
			                },
			                emphasis : {
			                    label : {
			                        show : true,
			                        position : 'center',
			                        textStyle : {
			                            fontSize : '18',
			                            fontWeight : 'bold'
			                        }
			                    }
			                }
			            },
			            data:[]
			        }
			    ]
			};
		
		$.ajax({
			type : "GET",
			url : "../../gonsuncn/alarmForLevel",
			dataType : "json",
			success : function(dataMap) {
				var tempData = dataMap.alarmLevel;
				var legendData = [];
				var seriesData = [];
				for(var i=0;i<tempData.length;i++){
					legendData.push(tempData[i].alarmlevel+"级告警");	
					var tempMap = {value:tempData[i].alarmNum, name:tempData[i].alarmlevel+"级告警"};
					seriesData.push(tempMap);
				}
				option.legend.data=legendData;
				option.series[0].data=seriesData;
				
				chart.hideLoading();
				chart.setOption(option);
			}
		});

	});
	// 用于使chart自适应高度和宽度
	window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};
};

/* 数据分页 */
xh.pagging = function(currentPage,totals, $scope) {
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
			visiblePages : 7,
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


