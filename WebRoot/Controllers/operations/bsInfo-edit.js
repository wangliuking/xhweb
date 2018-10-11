/**
 * 基站配置
 */
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
var appElement = document.querySelector('[ng-controller=bs]');
xh.load = function() {
	var app = angular.module("app", []);
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);
	app.controller("bs", function($scope, $http,$location) {
		var obj =eval('(' + $location.search().data + ')');
		
		$scope.data =obj;
		
	});
};
/* 修改基站信息 */
xh.update = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../bs/update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#updateForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.success) {

				swal({
					title : "提示",
					text : "修改基站信息成功",
					type : "success",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "返回基站页面",
					cancelButtonText : "再次修改基站信息"
				/*
				 * closeOnConfirm : false, closeOnCancel : false
				 */
				}, function(isConfirm) {
					if (isConfirm) {
						window.location.href="../../Views/operations/bsInfo.html";
					}
				});

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
