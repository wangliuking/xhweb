/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var alarmbs=true;
var alarmji=true;
var appElement = document.querySelector('[ng-controller=index]');
xh.load = function() {
	var app = angular.module("app", []);
	app.filter('bsName', function() { // 可以注入依赖
		return function(text) {
			
			if(text.indexOf("，")>0){
				return text.split("，")[0];
			}else{
				return text;
			}
			
		};
	});
	app.filter('alarmContent', function() { // 可以注入依赖
		return function(text) {
			
			if(text.indexOf("，")>0){
				return text.split("，")[2];
			}else{
				return text;
			}
			
		};
	});
	app.controller("index", function($scope, $http) {
		$scope.totals=0;
		$scope.mshow=0;
		if(xh.getcookie("skin")!=null){
			$('body').attr('id', xh.getcookie("skin"));
		}else{
			$('body').attr('id', "skin-blur-ocean");
		}
		
		$(".side-menu a").live('click',function(){
			$scope.mshow=$(this).attr("value");
		})
		
		
		
		$http.get("web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});
		
		$scope.alarmInfo=function(){
			$http.get("bsstatus/bsVoiceAlarm").success(function(response) {
				$scope.alarm=response.items;
				$scope.totals=response.totals;
			
			});
		}
		$scope.hideMenu=function(){
			$("body").toggleClass("hide-menu");
		};
		
	
		//系统告警数目
		$scope.alarmCount=function(){
			$http.get("bsstatus/bsOffVoiceCount").success(function(response) {
				$scope.AlarmTotals=response.totals;
				var bs_count=response.bsbreakTotals;
				var ji_count=response.jiTotals;
				var count=0;
				if(alarmbs){
					count+=bs_count
				}
				if(alarmji){
					count+=ji_count
				}
				
				if(count>0){
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
		$scope.stop=function(){
		    event.stopPropagation();
		};
	/*	$scope.showAlarmWin=function(){
			
			
			var html={
					  type: 2,
					  title:":注册组/注册终端",
					  area: ['500px', '400px'],
					 
					  shade: 0,
					  maxmin:false,
					  
					  skin: 'layui-layer-rim', //加上边框					  
					  content: ["../../Views/operations/bsstatus-group-user-box.html?bsId="+1, 'no']
					};
			layer.open(html);
		}*/

		$scope.alarmCount();
		$scope.alarmInfo();
		setInterval(function(){
			$scope.alarmCount();
			$scope.alarmChange();
			$scope.alarmInfo();
			
			}, 10000); //每隔 10 秒 
		

		
		

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
	if(audio!=null){
		audio.pause();
		audio.currentTime = 0;
	}
	
	$scope.updateAlarm();
};
xh.aa=function(){
	alarmbs=$("input[name='alarmbs']").is(':checked');
	alarmji=$("input[name='alarmji']").is(':checked');
}

