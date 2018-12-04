/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var alarmbs = true;
var alarmji = true;
var play = false;
var appElement = document.querySelector('[ng-controller=index]');
xh.load = function() {
	var app = angular.module("app", []);
	app.filter('bsName', function() { // 可以注入依赖
		return function(text) {

			if (text.indexOf("，") > 0) {
				return text.split("，")[0];
			} else {
				return text;
			}

		};
	});
	app.filter('alarmContent', function() { // 可以注入依赖
		return function(devciceId, text) {

			if (text.indexOf("，") > 0) {
				return text.split("，")[2];
			} else {
				return "四期基站，EPS配电箱，交流电断开";
			}

		};
	});
	app.controller("index", function($scope, $http) {
		$scope.totals = 0;
		$scope.mshow = 0;
		$scope.voiceTag = 0;
		if (xh.getcookie("skin") != null) {
			$('body').attr('id', xh.getcookie("skin"));
		} else {
			$('body').attr('id', "skin-blur-ocean");
		}
		$http.get("web/user/password").success(function(response) {
			if (!response.ispass) {
				/*
				 * layer.open({ type: 1, shade: false, title: false, //不显示标题
				 * content: $(".password-tip") });
				 */
				$("#checkPassWin").modal('show');
			}
		});

		/*
		 * $(".side-menu a").live('click',function(){
		 * $scope.mshow=$(this).attr("value"); })
		 */

		$http.get("web/webMenu").success(function(response) {
			$scope.menu = response.items;

		});
		$scope.news_fun = function() {
			$http.get("web/user/news").success(function(response) {
				$scope.news = response.news;
			});
		}

		// 获取登录用户
		$http.get("web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId;
			$scope.roleType = response.roleType;

		});
		$http.get("center/email/noReadEmailCount").success(function(response) {
			$scope.email = response.totals

		});
		$http.get("order/orderNoComCount").success(function(response) {
			$scope.order = response.totals

		});
		$scope.alarmCount = function() {
			$http.get("bsAlarm/voiceAlarm").success(function(response) {
				$scope.AlarmTotals = response.totals;
				var count = response.totals;
				if ($scope.roleType == 3 || $scope.roleType == 0) {
					if (count>0) {
						play = true;
						xh.playMap3();
						$scope.voiceTag = 1;
						$("#close-bell").attr('src', 'Resources/images/icon/32/bell.png')

					} else {
						xh.stopMap3();
						play = false;
						$scope.voiceTag = 0;
						$("#close-bell").attr('src', 'Resources/images/icon/32/bell-off.png')
					}
				}
			});
		};
		$scope.voice_not_check = function() {
			$http.get("bsstatus/not_check").success(function(response) {
				$scope.CheckAlarmTotals = response.count;
				var count = response.count;
				if ($scope.roleType == 3 || $scope.roleType == 0) {
					if (count > 0) {
						xh.playCheckMap3();
						$scope.voiceCheckTag = 1;
						
						$("#close-check-bell").attr('src', 'Resources/images/icon/32/bell3.png')
					} else {
						xh.stopCheckMap3();
						$scope.voiceCheckTag = 0;
						$("#close-check-bell").attr('src', 'Resources/images/icon/32/bell-off.png')
					}
				}
				
			});
		};
		$scope.voice_not_order = function() {
			$http.get("bsstatus/not_order").success(function(response) {
				$scope.OrderAlarmTotals = response.count;
				var count = response.count;
				if ($scope.roleType == 3 || $scope.roleType == 0) {
					if (count > 0) {
						xh.playOrderMap3();
						$scope.voiceOrderTag = 1;
						$("#close-order-bell").attr('src', 'Resources/images/icon/32/bell4.png')
					} else {
						xh.stopOrderMap3();
						$scope.voiceOrderTag = 0;
						$("#close-order-bell").attr('src', 'Resources/images/icon/32/bell-off.png')
					}
				}
				
			});
		};

		// 更新基站断站告警
		$scope.alarmChange = function() {
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
		// 更新基站断站告警状态
		$scope.updateAlarm = function() {
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
		$scope.stop = function() {
			event.stopPropagation();
		};
		$scope.news_fun();
		$scope.alarmCount();
		setInterval(function() {
			$scope.alarmCount();
			$scope.news_fun();
		}, 10000);
		setInterval(function() {
			$scope.voice_not_check();
		}, 2*60*60*1000);
		setInterval(function() {
			$scope.voice_not_order();
		}, 10*60*1000);

		/*
		 * setInterval(function(){ $scope.alarmCount(); }, 15000); //每隔 10 秒
		 * setInterval(function(){ $scope.alarmChange(); }, 20000); //每隔 10 秒
		 */

	});
};
xh.playMap3 = function() {
	var audio = document.getElementById("bgMusic");
	// 重新播放
	audio.currentTime = 0;
	audio.play();
};
xh.playCheckMap3 = function() {
	var audio = document.getElementById("mp3_check");
	// 重新播放
	audio.currentTime = 0;
	audio.play();
};
xh.playOrderMap3 = function() {
	var audio = document.getElementById("mp3_order");
	// 重新播放
	audio.currentTime = 0;
	audio.play();
};

xh.stopMap3 = function() {
	var audio = document.getElementById("bgMusic");
	var $scope = angular.element(appElement).scope();
	// 停止
	if (audio != null) {
		audio.pause();
		audio.currentTime = 0;
		$scope.voiceTag = 0;
		$("#close-bell").attr('src', 'Resources/images/icon/32/bell-off.png')
		if (play) {
			$scope.updateAlarm();
		}
	}
};
xh.stopCheckMap3 = function() {
	var audio = document.getElementById("mp3_check");
	var $scope = angular.element(appElement).scope();
	// 停止
	if (audio != null) {
		audio.pause();
		audio.currentTime = 0;
		$scope.voiceCkeckTag = 0;
		$("#close-check-bell").attr('src',
				'Resources/images/icon/32/bell-off.png')
	}
};
xh.stopOrderMap3 = function() {
	var audio = document.getElementById("mp3_order");
	var $scope = angular.element(appElement).scope();
	// 停止
	if (audio != null) {
		audio.pause();
		audio.currentTime = 0;
		$scope.voiceOrderTag = 0;
		$("#close-order-bell").attr('src', 'Resources/images/icon/32/bell-off.png')
	}
};
xh.aa = function() {
	alarmbs = $("input[name='alarmbs']").is(':checked');
	alarmji = $("input[name='alarmji']").is(':checked');
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
