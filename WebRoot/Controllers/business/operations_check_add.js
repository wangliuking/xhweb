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
var fileNames=['运维服务团队通讯录','运维资源配置表',
		'本月计划维护作业完成情况','下月计划维护作业','系统运行维护服务月报',
		'基站信息表','运维故障统计','故障核减申请书','通信保障报告','备品备件表',
		'定期维护报告-交换中心月维护','定期维护报告-基站月维护','系统日常维护表',
		'巡检记录汇总表','基站月度巡检表（含调度台及直放站）']
console.log(fileNames[0])
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
		$scope.showFileWin=function(){
			$("input[name='pathName']").click();
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
    var files=[];	
    var addfileNames=new Array();
    var com=new Array();
	$("#fileArea ul li").each(function(index){
	    var name = $(this).children().first().text();
	    var path = $(this).children(".path").text();
	    if(name!="" && path!=""){
	    	var a={
	    			fileName:name,
	    			filePath:path
	    	}
	    	files.push(a);
	    	addfileNames[index]=name.substring(0,name.indexOf("."));
	    }
	   
	});
	var a=addfileNames.toString();
	var index=0;
	for(var i=0;i<fileNames.length;i++){
		if(a.indexOf(fileNames[i])==-1){
			com[index]=fileNames[i];
			index++;
		}
	}
	if(com.length>0){
		swal("提示","你还有文件没有上传完，禁止提交，待传文件:\r\n"+com.join("\r\n"),"info");
		return;
	}
	var month=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	if(files.length<1){
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
			time:month,
			type:type,
			files: JSON.stringify(files)
		},
		success : function(data) {
			if (data.success) {
				swal({
					title : "提示",
					text : "提交申请成功",
					type : "success",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "提交申请成功，返回列表页面",
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
xh.isAdded = function(name) {
	var success=0;
    var files=[];	
    var addfileNames=new Array();
    var com=new Array();
    name=name.split(".")[0];
	$("#fileArea ul li").each(function(index){
	    var name = $(this).children().first().text();
	    var path = $(this).children(".path").text();
	    if(name!="" && path!=""){
	    	var a={
	    			fileName:name,
	    			filePath:path
	    	}
	    	files.push(a);
	    	addfileNames[index]=name.substring(0,name.indexOf("."));
	    }
	   
	});
	var a=addfileNames.toString();
	var b=fileNames.toString();
	//console.log(b)
	if(b.indexOf(name)==-1){
		success=2;//文件名称非必须提交的文件名称
	}
	if(a.indexOf(name)>-1){
		success=1;//数据存在
	}
	return success;
	
};




