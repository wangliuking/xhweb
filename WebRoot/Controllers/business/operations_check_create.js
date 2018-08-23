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
		$scope.searchDetail=function(){
			var time=$("#time").val();
			$http.get("../../check/searchDetail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.sum=response.sum;
				
				
			});
		}
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
			//$("#table-checkbox").prop("checked", false);
		};
		
		/*跳转到申请进度页面*/
		$scope.toProgress = function (id) {
			$scope.progressData = $scope.data[id];
			$("#progress").modal('show');
	    };
	    $scope.toback = function () {
	    	window.location.href="operations_check.html";
	    };
	    
	   
	});
	
	
};

//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
xh.searchDetail=function(){
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.searchDetail();
}
/* 添加设备 */
xh.add = function() {
	var time=$("#time").val();
	if(time==""){
		toastr.error("时间不能为空", '提示');
		return ;
	}
	
	$.ajax({
		url : '../../check/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			data:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			if (data.success) {
				toastr.success(data.message, '提示');
				window.location.href="operations_check.html";
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};


// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
