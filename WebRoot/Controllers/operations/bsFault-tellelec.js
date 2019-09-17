
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
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);
	app.controller("xhcontroller", function($scope, $http,$location) {
		$scope.bsId = $location.search().bsId;
		$scope.name = $location.search().name;
		$scope.time = $location.search().time;
		$scope.id = $location.search().id;
		$scope.users=[];
		$scope.recvUsers=[];
		$scope.copyUsers=[];
		localStorage.setItem("nowinput", "");
		$scope.contacts=function(){
			$http.get("../../web/user/contacts").
			success(function(response){
				$scope.data = response.items;
			});
		};
		$scope.nowInput=function(tt){
			localStorage.setItem("nowinput", tt);
			console.log(localStorage.getItem("nowinput"));
		}
		$(document).on("click", ".groupclose", function(event) {
			event.preventDefault();
			var c=$(this).closest('li');
			var x=c.find(".select-all");
			var b=c.find(".groupSub");
			var icon=$(this).find("i:first");
			c.siblings().find(".groupSub").hide(200);
			c.siblings().find(".select-all").hide(200);
			c.siblings().find(".groupclose").find(".fa-minus-square").toggleClass('fa-minus-square').toggleClass('fa-plus-square');
			
			b.slideToggle(200);
			x.slideToggle(200);
			icon.toggleClass('fa-minus-square').toggleClass('fa-plus-square');
		});
		$(document).on("click", ".groupSub li", function(event) {
			event.preventDefault();
			var nowinput=localStorage.getItem("nowinput");
			if(nowinput==undefined || nowinput==""){
				nowinput="recvUser"
			}
			var user=$(this).attr("user");
			var userName=$(this).attr("text");
			var a=$("textarea[name='"+nowinput+"']").val();
			if(a.indexOf(userName)==-1){
				$("textarea[name='"+nowinput+"']").val(a+userName+";");
			}
		});
		$(document).on("click", ".select-all", function(event) {
			event.preventDefault();
		
			var nowinput=localStorage.getItem("nowinput");
			if(nowinput==undefined || nowinput==""){
				nowinput="recvUser"
			}
			var index=$(this).attr("index");
			
			var dd=$scope.data[index].userList;
			console.log(dd)
			for(var i=0;i<dd.length;i++){
				var a=$("textarea[name='"+nowinput+"']").val();
			
				if(a.indexOf(dd[i].userName)==-1){
					$("textarea[name='"+nowinput+"']").val(a+dd[i].userName+";");
				}
			}
		});
		$scope.contacts();
			
		
	});
};
xh.gorder=function(){
	var $scope = angular.element(appElement).scope();
	var a=$("textarea[name='recvUser']").val().split(";");
	var b=$("textarea[name='copyUser']").val().split(";");
	if(recvUser==""){
		toastr.error("接单人不能为空", '提示');
		return;
	}
	var data=$scope.data;
	
	var recvUser=[];
	var copyUser=[];
	for(var i=0;i<data.length;i++){
		var userL=data[i].userList;
		if(userL.length>0){
			for(var j=0;j<userL.length;j++){
				for(var k=0;k<a.length;k++){
					if(a[k]==userL[j].userName){
						recvUser.push(userL[j].user);
					}
				}
				for(var l=0;l<a.length;l++){
					if(b[l]==userL[j].userName){
						copyUser.push(userL[j].user);
					}
				}
			}
		}
	}
	if(recvUser.length<1){
		toastr.error("接单人填写出错，请重新填写", '提示');
		return;
	}
	var formData={
		id:$scope.id,
		bsid:$("input[name='bsId']").val(),
		bsname:$("input[name='name']").val(),
		note:$("textarea[name='note']").val(),
		time:$("input[name='time']").val(),
		recv_user:recvUser.join(","),
		copy_user:copyUser.join(","),
		recv_user_name:$("textarea[name='recvUser']").val(),
		copy_user_name:$("textarea[name='copyUser']").val()
	}
	$.ajax({
		url : '../../elec/gorder',
		data :formData,
		type : 'post',
		dataType : "json",
		async : false,
		success : function(response) {
			var data = response;
			if(data.success){
				
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				//toastr.success(data.message, '提示');
				parent.layer.msg("派单成功");
				parent.xh.refresh();
			}else{
				toastr.error(data.message, '提示');
			}
		},
		failure : function(response) {
			toastr.error("参数错误", '提示');
		}
	});
}


