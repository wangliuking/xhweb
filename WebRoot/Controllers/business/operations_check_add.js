/**
 * 资产管理
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
	
	var pageSize = $("#page-limit").val();
	app.controller("xhcontroller", function($scope,$http) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		$scope.time="";
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.loginUserRoleId = response.roleId;
			$scope.loginUserRoleType = response.roleType;
		});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		$scope.searchMoney=function(){
			var time=$("input[name='month']").val();
			$scope.time=time;
			console.log($scope.time);
			$http.get("../../check/searchDetail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.money_data = response.items;
				$scope.money_sum=response.sum;
				
				
			});
		}
		$scope.searchScore=function(){
			var time=$("input[name='month']").val();
			console.log($scope.time);
			$http.get("../../check/searchScore?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data= response.items;
				$scope.score_sum=response.sum;
				
				
			});
		}
	  
		
	   
		
	});
	
};
xh.searchScore=function(time){
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.searchScore();
	$scope.searchMoney();
}
xh.add = function() {
	var fileName=$("#addWin").find("input[name='fileName']").val();
	var month=$("input[name='month']").val();
	if(fileName==""){
		toastr.error("还没有上传文件", '提示');
		return ;
	}
	if(month==""){
		toastr.error("考核月份不能为空", '提示');
		return ;
	}
	
	$.ajax({
		url : '../../check/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			scoreData:xh.serializeJson($("#scoreForm").serializeArray()), //将表单序列化为JSON对象
			moneyData:xh.serializeJson($("#moneyForm").serializeArray()) ,
			time:month,
			comment:$("textarea[name='comment']").val(),
			fileName:$("input[name='fileName']").val(),
			filePath:$("input[name='path']").val()
		},
		success : function(data) {
			if (data.success) {
				swal({
					title : "提示",
					text : "提交申请成功",
					type : "success",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "提交申请成功，返回申请列表",
					cancelButtonText : "取消",
						closeOnCancel : false
				/*
				 * closeOnConfirm : false, closeOnCancel : false
				 */
				}, function(isConfirm) {
					if (isConfirm) {
						window.location.href="operations_check.html"
					}
				});
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};




