if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
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
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
	var app = angular.module("app", []);

	app.controller("xhcontroller", function($scope, $http) {

		$scope.select_workcontact=function(){
			$http.get("../../select/workcontact").success(function(response) {
				$scope.workcontact_data = response.items;
				$scope.workcontact_data_size = response.items.length;
			});
		}
		$scope.ThresholdMap=function(){
			$http.get("../../select/ThresholdMap").success(function(response) {
				$scope.threshold_data = response.items;
			});
		}
		$scope.workcontact_del = function(index) {
			swal({
				title : "提示",
				text : "确定要删除吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../select/workcontact_del',
						type : 'post',
						dataType : "json",
						data : {
							name : $scope.workcontact_data[index].name
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

							} else {
								toastr.success(data.message, '提示');
							}
						},
						error : function() {
							$scope.refresh();
						}
					});
				}
			});
		};
		$scope.refresh = function() {
			$scope.select_workcontact();
		};
		$scope.select_workcontact();
		$scope.ThresholdMap();
	
	});
};
//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};
xh.refreshThreshold = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.ThresholdMap();
};
/* 添加 */
xh.workcontact_add = function(name) {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../select/workcontact_add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			name:name
		},
		success : function(data) {
			if (data.success) {
				$('#workcontact-add').modal('hide');
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
xh.updateThreshold = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../select/updateThreshold',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			data:xh.serializeJson($("#thresholdForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.success) {
				xh.refreshThreshold();
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


