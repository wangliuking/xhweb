/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var appElement = document.querySelector('[ng-controller=index]');
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
		};
		//系统告警数目
		$scope.alarmCount=function(){
			$http.get("bsstatus/bsOffVoiceCount").success(function(response) {
				$scope.AlarmTotals=response.totals;
				console.log("bsss-->"+$scope.AlarmTotals)
				if($scope.AlarmTotals>0){
					xh.playMap3();
				}else{
					xh.stopMap3();
				}
			
			});
		};
		//更新基站断站告警
		$scope.alarmChange=function(){
			$.ajax({
				url : 'bsstatus/bsOffVoiceChange',
				type : 'POST',
				dataType : "json",
				data : {},
				async : false,
				success : function(data) {
				},
				error : function() {
				}
			});
		};
		//更新基站断站告警状态
		$scope.updateAlarm=function(){
			$.ajax({
				url : 'bsstatus/updateAlarm',
				type : 'POST',
				dataType : "json",
				data : {},
				async : false,
				success : function(data) {
				},
				error : function() {
				}
			});
		};
		$scope.alarmCount();
		setInterval(function(){
			$scope.alarmCount();
			$scope.alarmChange();          
			}, 10000);  //每隔 10 秒 
		
		

	});
};
xh.playMap3=function() {
	var audio = document.getElementById("bgMusic");
	//重新播放
	audio.currentTime = 0;
	audio.play();
};
xh.stopMap3=function() {
	var audio = document.getElementById("bgMusic");
	var $scope = angular.element(appElement).scope();
	//停止
	audio.pause();
	audio.currentTime = 0;
	$scope.updateAlarm();
};

