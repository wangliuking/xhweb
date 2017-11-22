/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("index", function($scope, $http) {
		$scope.totals=0;
		
		$http.get("web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});
		$http.get("bsstatus/bsVoiceAlarm").success(function(response) {
			$scope.alarm=response.items;
			$scope.totals=response.totals;
		
		});
		$scope.hideMenu=function(){
			$("body").toggleClass("hide-menu");
		}
		
		

	});
};

