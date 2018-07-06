/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var alarmbs=true;
var alarmji=true;
var play=false;
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
		return function(devciceId,text) {
			
			if(text.indexOf("，")>0){
				return text.split("，")[2];
			}else{
				return "四期基站，EPS配电箱，交流电断开";
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
				$("#alarmcount").html($scope.totals);
				$scope.a_bs=[],$scope.a_ji=[],$scope.a_w=[],$scope.a_msc=[];
				for(var i=0,j=response.totals;i<j;i++){
					if(response.items[i].bsId!=null && response.items[i].link==1){
						$scope.a_bs.push(response.items[i])
					}
					if(response.items[i].bsId!=null && response.items[i].link==null){
						if(response.items[i].deviceId=='170300000000001' ||(response.items[i].deviceId==null && response.items[i].DevNode=='0011')){
							$scope.a_w.push(response.items[i])
						}
						if(response.items[i].deviceId=='080200000000001' ||(response.items[i].deviceId==null && response.items[i].DevNode=='0051')){
							$scope.a_ji.push(response.items[i])
						}
					}
					if(response.items[i].bsId==null){
						$scope.a_msc.push(response.items[i])
					}
				}
				$scope.count_bs=$scope.a_bs.length
				$scope.count_ji=$scope.a_ji.length
				$scope.count_w=$scope.a_w.length
				$scope.count_msc=$scope.a_msc.length
				
			
			});
		}
		$scope.hideMenu=function(){
			$("body").toggleClass("hide-menu2");
		};
		// 获取登录用户
		$http.get("web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId ;
			
		});
		$http.get("center/email/noReadEmailCount").success(function(response) {
			$scope.email = response.totals
			
		});
		$http.get("order/orderNoComCount").success(function(response) {
			$scope.order = response.totals
			
		});
		
	
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
				
				
				if($scope.loginUserVpnId==null || $scope.loginUserVpnId==''){
					if(count>0){
						play=true;
						xh.playMap3();
						
					}else{
						xh.stopMap3();
						play=false;
					}
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
		$scope.alarmCount();
		$scope.alarmInfo();
		setInterval(function(){
			$scope.alarmInfo();		
			}, 10000); //每隔 10 秒 
		setInterval(function(){
			$scope.alarmCount();	
			}, 15000); //每隔 10 秒 
		setInterval(function(){
			$scope.alarmChange();	
			}, 20000); //每隔 10 秒 
		

		
		

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
		if(play){
			$scope.updateAlarm();
		}
		
	}
	
	
};
xh.aa=function(){
	alarmbs=$("input[name='alarmbs']").is(':checked');
	alarmji=$("input[name='alarmji']").is(':checked');
}
/* 获取cookie */
xh.getcookie = function(name) {
	var strcookie = document.cookie;
	var arrcookie = strcookie.split(";");
	for (var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0].match(name) == name)
			return arr[1];
	}
	return "";
};

