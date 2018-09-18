/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var appElement = document.querySelector('[ng-controller=menu]');
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("menu", function($scope, $http) {		
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId ;			
		});
		$http.get("../../web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});

	});
};
//基站运行记录
xh.excelToBsRun = function() {
	xh.maskShow();
	$("#btn-run").button('loading');
	$.ajax({
		url : '../../bsstatus/ExcelToStationStatus',
		type : 'get',
		dataType : "json",
		data : {},
		async : false,
		success : function(data) {

			$("#btn-run").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-run").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.server_excel = function(time) {
	xh.maskShow();
	$("#btn-run").button('loading');
	$.ajax({
		url : '../../server/excel_report',
		type : 'get',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#btn-run").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-run").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.bs_month_excel = function(time) {
	xh.maskShow();
	$("#excel-month-bs-btn").button('loading');
	$.ajax({
		url : '../../report/month/excel_month_bs',
		type : 'get',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-bs-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-bs-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
