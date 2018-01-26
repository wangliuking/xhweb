/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
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
		/*xh.maskShow();*/
		$scope.count = "15";//每页数据显示默认值
		$scope.starttime=xh.getBeforeDay(7);
		$scope.endtime=xh.getOneDay();
		
		/*获取800M移动基站巡检表信息*/
		$scope.mbs=function(){	
			var pageSize = $("#page-limit").val();
			$http.get("../../app/mbsinfo?start=0&limit="+pageSize).
			success(function(response){
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
				xh.mbs_pagging(1, parseInt($scope.mbsTotals),$scope,pageSize);
			});
		}
		/* 刷新数据 */
		$scope.mbs_refresh = function() {
			$scope.mbs_search(1);
		};
		/* 显示mbsWin */
		$scope.showMbsWin = function(id) {
			$scope.mbsOneData = $scope.mbsData[id];
			$("#mbsWin").modal('show');
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
		
		/* 查询数据 */
		$scope.mbs_search = function(page) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mbsinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
				xh.mbs_pagging(page, parseInt($scope.mbsTotals),$scope,pageSize);
			});
		};
		//分页点击
		$scope.pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mbsinfo?start=0&limit="+limit).
			success(function(response){
				$scope.mbs_start = (page - 1) * pageSize + 1;
				$scope.mbs_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.mbs_lastIndex = totals;
					} else {
						$scope.mbs_start = 0;
						$scope.mbs_lastIndex = 0;
					}
				}
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
			});
			
		};
		/*$scope.mbs();*/
	});
};
$(document).ready(function(){ 
	var $scope = angular.element(appElement).scope();
	$scope.mbs();
	}); 

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
xh.mbs_pagging = function(currentPage,totals, $scope,pageSize) {
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
		$(".mbs-page-paging").html('<ul class="pagination mbs-pagination"></ul>');
		$('.mbs-pagination').twbsPagination({
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

