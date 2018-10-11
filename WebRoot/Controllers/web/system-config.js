/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
var appElement = document.querySelector('[ng-controller=sys]');
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
	app.controller("sys", function($scope, $http) {
		
	$scope.config=function(){
		$http.get("../../web/user/sysconfig").
		success(function(response){
			$scope.data = response;
		});
	}
		
	$scope.config();	
	});
	
	$("input[type='checkbox']").change(function(){
		
		var checked=$(this).is(':checked');
		var value=checked?"on":"off";
		xh.up($(this).attr("name"),value);
	
	})
};

/*修改配置*/
xh.up = function(name,value) {
	$.ajax({
		url : '../../web/user/up_sys_config',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			name:name,
			value:value
		},
		success : function(data) {

			toastr.success("配置成功", '提示');
		},
		error : function() {
			toastr.error("配置失败", '提示');
		}
	});
};

