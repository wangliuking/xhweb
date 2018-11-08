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

	app.controller("bs", function($scope, $http) {

	});
};
/* 添加基站信息 */
xh.add = function() {
	var appElement = document.querySelector('[ng-controller=bs]');
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../bs/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.success) {

				swal({
					title : "提示",
					text : "添加基站信息成功",
					type : "success",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "返回基站页面",
					cancelButtonText : "继续添加基站"
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
