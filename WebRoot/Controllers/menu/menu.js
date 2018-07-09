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