/**
 * Tetra告警显示
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
var appElement = document.querySelector('[ng-controller=bsAlarm]');
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
	/*var bsId = $("#bsId").val();
	var bsName = $("#name").val();*/
	var pageSize = $("#page-limit").val();
	app.controller("bsAlarm", function($scope, $http) {
		xh.maskShow();
		$scope.count = "15";// 每页数据显示默认值
		$scope.operationMenu = true; // 菜单变色

		//加载故障等级统计图
		xh.bsBar();
		/*
		 * 获取基站告警信息 "../../bslarm/list?bsId=" + bsId + "&name=" + bsName +
		 * "&dealEn=0" + "&start=0&limit=" + pageSize
		 */
		$http.get("../../bsAlarm/list?start=0&limit=" + pageSize).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.data;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		/* 显示警告详情 */
		$scope.showDetails = function(id){
			$("#bsAlarmDetails").modal('show');
			$scope.bsAlarmData=$scope.data[id];
		};
		$scope.alarmType=function(){
			$http.get("../../bsAlarm/data/bsAlarmLevelChart").success(
					function(response) {
					
						$scope.alarmTypeData = response.items;
					});
		}
		/* 查询历史告警数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			/*var bsId = $("#bsId").val();
			var bsName = $("#name").val();
			var dealEn = $("#dealEn").val();*/
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../bsAlarm/list?start="+start+"&limit=" + pageSize).success(
					function(response) {
						xh.maskHide();
						$scope.data = response.data;
						$scope.totals = response.totals;
						xh.pagging(page, parseInt($scope.totals), $scope);
					});
		};
		/* 确认故障 */
		$scope.identifyBsAlarm = function(id){
			swal({
				title : "确认告警",
				text : "确认该告警吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#82AF6F",
				confirmButtonText : "确认",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../bsAlarm/identifyBsA',
						type : 'post',
						dataType : "json",
						data : {
							bsId : id
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success("已确认", '提示');
								$scope.refresh();
							} else {
								swal({
									title : "提示",
									text : "告警确认失败",
									type : "error"
								});
							}
						},
						error : function() {
							$scope.refresh();
						}
					});
				}
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			/*var bsId = $("#bsId").val();
			var bsName = $("#name").val();*/
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../bsAlarm/list?start="+start+"&limit=" + pageSize)
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
						$scope.data = response.data;
						$scope.totals = response.totals;
					});

		};
		$scope.alarmType();
	});
};
// 刷新数据
xh.refresh = function() {
	var appElement = document.querySelector('[ng-controller=bsAlarm]');
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
/**
 * 故障等级统计图 
 */
xh.bsBar = function() {
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#bs-bar").width(parseInt($("#bs-bar").parent().width())+70);
		$("#bs-bar").height(200);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		chart = ec.init(document.getElementById('bs-bar'));
		var option = {
			    title : {
			        text: 'Tera系统告警类型统计'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['Tera系统告警类型统计']
			    },
			    
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            
			            data : []
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'Tera系统告警类型统计',
			            type:'bar',
			            barWidth: 30,//固定柱子宽度
			            data:[],
			            itemStyle:{
			            	normal:{
			            		color:'green',
			            		cursor:'pointer'
			            	}},
			            
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            }
			        }
			    ]
			};
		
		$.ajax({
			url : '../../bsAlarm/data/bsAlarmTypeChart',
			type : 'GET',
			dataType : "json",
			async : true,
			data:{
			},
			success : function(response) {

				/*var data = response.items;
				option.series[0].data = data;*/
				var data = response.items;
				var y=[],x=[];
				
				for(var i=0;i<data.length;i++){
					y.push(data[i].neType);
					x.push(data[i].num);
					
				}
				option.xAxis[0].data=y;
				option.series[0].data=x;
				chart.setOption(option);
				chart.on('click',function(params){
					var name=params.name;
					window.location.href="bsstatus.html?zone="+name;
				})
			},
			error : function() {
			}
		});

	});
	

};
/**
 * 故障类型统计
 */
xh.loadbsAlarmTypePie = function(){
	// 设置容器宽高
	var resizeBarContainer = function(){
		$("#bsAlarm-type-pie").width(parseInt($("#bsAlarm-type-pie").parent().width()));
		$("#bsAlarm-type-pie").height(300);
	}
	resizeBarContainer();
	
	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if(chart != null){
		chart.clear();
		chart.dispose();
	}
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('bsAlarm-type-pie'));
		chart.showLoading({
			text : '正在努力的读取数据中...'
		});
		var option = {
			title : {
				x : 'center',
				text : '故障类型统计图 ',
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
				data : [ '一般', '紧急', '非常紧急' ]
			},
		
			calculable : false,
			backgroundColor : background,
			series : [ {
				name : '类型',
				type : 'pie',
				radius : '50%',
				center : [ '50%', '60%' ],
				itemStyle : {
					normal : {
						color : function(params) {
							// build a color map as your need.
							var colorList = [ '#EC706B', '#DC3C37', '#852422' ];
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
			url : '../../bsAlarm/data/bsAlarmTypeChart',
			data : {},
			type : 'get',
			dataType : "json",
			async : false,
			success : function(response) {
				var data = response.items;
				// option.xAxis[0].data = xAxisData;
				option.series[0].data = data;
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
			visiblePages : 10,
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