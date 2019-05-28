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
		$scope.time="";
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
		
		$scope.change3Input=function(){
			var a=0;
			a+=parseFloat($("#score3Form").find("input[name='s_a1']").val()==""?0:$("#score3Form").find("input[name='s_a1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b1']").val()==""?0:$("#score3Form").find("input[name='s_b1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b2']").val()==""?0:$("#score3Form").find("input[name='s_b2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b3']").val()==""?0:$("#score3Form").find("input[name='s_b3']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b4']").val()==""?0:$("#score3Form").find("input[name='s_b4']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_c1']").val()==""?0:$("#score3Form").find("input[name='s_c1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_c2']").val()==""?0:$("#score3Form").find("input[name='s_c2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_d1']").val()==""?0:$("#score3Form").find("input[name='s_d1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_d2']").val()==""?0:$("#score3Form").find("input[name='s_d2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_e1']").val()==""?0:$("#score3Form").find("input[name='s_e1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_f1']").val()==""?0:$("#score3Form").find("input[name='s_f1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_f2']").val()==""?0:$("#score3Form").find("input[name='s_f2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_g1']").val()==""?0:$("#score3Form").find("input[name='s_g1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_h1']").val()==""?0:$("#score3Form").find("input[name='s_h1']").val());
			$scope.score_sum3=a;
		}
		$scope.change4Input=function(){
			var a=0;
			a+=parseFloat($("#score4Form").find("input[name='s_a1']").val()==""?0:$("#score4Form").find("input[name='s_a1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b1']").val()==""?0:$("#score4Form").find("input[name='s_b1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b2']").val()==""?0:$("#score4Form").find("input[name='s_b2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b3']").val()==""?0:$("#score4Form").find("input[name='s_b3']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b4']").val()==""?0:$("#score4Form").find("input[name='s_b4']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_c1']").val()==""?0:$("#score4Form").find("input[name='s_c1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_c2']").val()==""?0:$("#score4Form").find("input[name='s_c2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_d1']").val()==""?0:$("#score4Form").find("input[name='s_d1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_d2']").val()==""?0:$("#score4Form").find("input[name='s_d2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_e1']").val()==""?0:$("#score4Form").find("input[name='s_e1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_f1']").val()==""?0:$("#score4Form").find("input[name='s_f1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_f2']").val()==""?0:$("#score4Form").find("input[name='s_f2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_g1']").val()==""?0:$("#score4Form").find("input[name='s_g1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_h1']").val()==""?0:$("#score4Form").find("input[name='s_h1']").val());
			$scope.score_sum4=a;
		}
		$scope.sum_money3=function(){
			var a=0;
			a+=parseFloat($("#money3Form").find("input[name='m_a1']").val()==""?0:$("#money3Form").find("input[name='m_a1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a2']").val()==""?0:$("#money3Form").find("input[name='m_a2']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a3']").val()==""?0:$("#money3Form").find("input[name='m_a3']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a4']").val()==""?0:$("#money3Form").find("input[name='m_a4']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_b1']").val()==""?0:$("#money3Form").find("input[name='m_b1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_b2']").val()==""?0:$("#money3Form").find("input[name='m_b2']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_d1']").val()==""?0:$("#money3Form").find("input[name='m_d1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_f1']").val()==""?0:$("#money3Form").find("input[name='m_f1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_g1']").val()==""?0:$("#money3Form").find("input[name='m_g1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_l1']").val()==""?0:$("#money3Form").find("input[name='m_l1']").val());
			$scope.money_sum3=a;
		}
		$scope.sum_money4=function(){
			var a=0;
			a+=parseFloat($("#money4Form").find("input[name='m_a1']").val()==""?0:$("#money4Form").find("input[name='m_a1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_a2']").val()==""?0:$("#money4Form").find("input[name='m_a2']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_a3']").val()==""?0:$("#money4Form").find("input[name='m_a3']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_a4']").val()==""?0:$("#money4Form").find("input[name='m_a4']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_b1']").val()==""?0:$("#money4Form").find("input[name='m_b1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_b2']").val()==""?0:$("#money4Form").find("input[name='m_b2']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_c1']").val()==""?0:$("#money4Form").find("input[name='m_c1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_c2']").val()==""?0:$("#money4Form").find("input[name='m_c2']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_c3']").val()==""?0:$("#money4Form").find("input[name='m_c3']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_d1']").val()==""?0:$("#money4Form").find("input[name='m_d1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_e1']").val()==""?0:$("#money4Form").find("input[name='m_e1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_f1']").val()==""?0:$("#money4Form").find("input[name='m_f1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_g1']").val()==""?0:$("#money4Form").find("input[name='m_g1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_h1']").val()==""?0:$("#money4Form").find("input[name='m_h1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_i1']").val()==""?0:$("#money4Form").find("input[name='m_i1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_j1']").val()==""?0:$("#money4Form").find("input[name='m_j1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_k1']").val()==""?0:$("#money4Form").find("input[name='m_k1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_m1']").val()==""?0:$("#money4Form").find("input[name='m_m1']").val());
			a+=parseFloat($("#money4Form").find("input[name='m_n1']").val()==""?0:$("#money4Form").find("input[name='m_n1']").val());
			$scope.money_sum4=a;
		}
		
		$scope.searchMoney=function(){
			var time=$("input[name='month']").val();
			$scope.time=time;
			console.log($scope.time);
			$http.get("../../check/show_money_detail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.money_data = response.items;
				$scope.money_sum=response.sum;
				
				
			});
		}
		$scope.searchScore=function(){
			var time=$("input[name='month']").val();
			console.log($scope.time);
			$http.get("../../check/show_score_detail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data= response.items;
				$scope.score_sum=response.sum;
				
				
			});
		}
	  
		
	   
		
	});
	
};
xh.searchScore=function(time){
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.searchScore();
	$scope.searchMoney();
}
xh.add = function() {
	var fileName=$("#addWin").find("input[name='fileName']").val();
	var month=$("input[name='month']").val();
	if(fileName==""){
		toastr.error("还没有上传文件", '提示');
		return ;
	}
	if(month==""){
		toastr.error("考核月份不能为空", '提示');
		return ;
	}
	
	$.ajax({
		url : '../../check/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			score3Data:xh.serializeJson($("#score3Form").serializeArray()),
			score4Data:xh.serializeJson($("#score4Form").serializeArray()),
			money3Data:xh.serializeJson($("#money3Form").serializeArray()),
			money4Data:xh.serializeJson($("#money4Form").serializeArray()),
			time:month,
			comment:$("textarea[name='comment']").val(),
			fileName:$("input[name='fileName']").val(),
			filePath:$("input[name='path']").val()
		},
		success : function(data) {
			if (data.success) {
				swal({
					title : "提示",
					text : "提交申请成功",
					type : "success",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "提交申请成功，返回申请列表",
					cancelButtonText : "取消",
				    closeOnCancel : true
				/*
				 * closeOnConfirm : false, closeOnCancel : false
				 */
				}, function(isConfirm) {
					if (isConfirm) {
						window.location.href="operations_check.html"
					}
				});
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};




