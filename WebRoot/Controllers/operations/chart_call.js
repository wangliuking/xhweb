/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
var appElement = document.querySelector('[ng-controller=user]');
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

	app.controller("user", function($scope, $http) {
		
		$scope.count = "15";//每页数据显示默认值
		$scope.securityMenu=true; //菜单变色
		$scope.starttime=xh.getNowMonth();
		$scope.endtime=xh.getOneDay();
		var time=$("#starttime").val()==""?xh.getNowMonth():$("#starttime").val();
		$scope.chart_bs_call=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_call?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.bs_call_data = response.items;
				$scope.bs_totals = response.totals;
			});
		}
		$scope.chart_vpn_call=function(){
			xh.maskShow();
			$http.get("../../call/chart_vpn_call?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.vpn_call_data = response.items;
				$scope.vpn_totals = response.totals;
			});
		}
		$scope.chart_bs_level_area_call=function(){
			xh.maskShow();
			$http.get("../../call//chart_bs_level_area_call?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.area_call_data = response.areaitems;
				$scope.level_call_data = response.levelitems;
				$scope.area_totals = response.totals;
			});
		}
		$scope.chart_bs_zone_call=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_zone_call?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.zone_call_data = response.items;
				$scope.zone_totals = response.totals;
			});
		}
		$scope.chart_bs_zone_top10_call=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_zone_top10_call?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.zone_top10_call_data = response.items;
				$scope.zone_top10_totals = response.totals;
			});
		}
		
		
		$scope.chart_bs_call_top10=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_call_top10?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.bs_top10_call_data = response.items;
				$scope.bs_top10_totals = response.totals;
			});
		}
		$scope.chart_bs_userreg_top10=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_userreg_top10?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.bs_userreg_top10_data = response.items;
				$scope.bs_userreg_top10_totals = response.totals;
			});
		}
		$scope.chart_bs_queue_top10=function(){
			xh.maskShow();
			$http.get("../../call/chart_bs_queue_top10?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.bs_queue_top10_data = response.items;
				$scope.bs_queue_top10_totals = response.totals;
			});
		}
        $scope.call_top10=function(){
        	$scope.chart_bs_call_top10();
        	$scope.chart_bs_userreg_top10();
        	$scope.chart_bs_queue_top10();
		}
		
		/* 刷新数据 */
		$scope.refresh = function() {
		
		};
		$scope.chart_bs_call();
		
		
		
	});
};

/* POST获取参数*/
xh.postData= function() {
	$.ajax({
		url : 'http://192.168.120.150:5555/web/loginUserInfo',
		type : 'get',
		dataType : "json",
		async :false,
		/*data : {
			method:gameList,
			gameid:14,
			pageNumber:1
		},*/
		success : function(data) {

			toastr.success("success", '提示');
		},
		error : function() {
			swal({
				title : "提示",
				text : "error:500",
				type : "error"
			});
		}
	});
};


/* 添加用户*/
xh.add = function() {
	$.ajax({
		url : '../../web/user/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#addForm").serializeArray(),
		success : function(data) {

			if (data.success) {
				$('#add').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
/* 修改基站信息 */
xh.update = function() {
	$.ajax({
		url : '../../web/user/update',
		type : 'POST',
		dataType : "json",
		async : false,
		data : $("#editForm").serializeArray(),
		success : function(data) {
			if (data.success) {
				$('#edit').modal('hide');
				toastr.success(data.message, '提示');
				xh.refresh();

			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
/* 批量删除用户*/
xh.delMore = function() {
	var checkVal = [];
	$("[name='tb-check']:checkbox").each(function() {
		if ($(this).is(':checked')) {
			checkVal.push($(this).attr("value"));
		}
	});
	if (checkVal.length < 1) {
		swal({
			title : "提示",
			text : "请至少选择一条数据",
			type : "error"
		});
		return;
	}
	$.ajax({
		url : '../../web/user/del',
		type : 'post',
		dataType : "json",
		data : {
			userId : checkVal.join(",")
		},
		async : false,
		success : function(data) {
			if (data.success) {
				toastr.success("删除用户成功", '提示');
				xh.refresh();
			} else {
				swal({
					title : "提示",
					text : "失败",
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

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

