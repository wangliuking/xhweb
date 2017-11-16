/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("index", function($scope, $http) {
		$http.get("web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});
		
		

	});
};

