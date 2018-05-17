/**
 * 用户管理
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
	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		/*$scope.starttime=xh.getBeforeDay(7);
		$scope.endtime=xh.getOneDay();*/
		$scope.nowDate=xh.getOneDay();
		var bsId=$("#bsId").val();
		var starttime=$("#starttime").val();
		var endtime=$("#endtime").val();
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId ;
		});
		/*获取故障信息*/
		$http.get("../../bsstatus/bsFaultList?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start=0&limit="+pageSize).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			xh.pagging(1, parseInt($scope.totals),$scope);
		});
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		/* 显示链接修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
			$("#edit").modal('show')
		};
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		/*用户列表*/
		$scope.userList=function(){
			$http.get("../../order/userlist").success(
					function(response) {
						$scope.userData = response.items;
					});
		}
		//派单
		$scope.showOrderWin = function(index){
			
			$scope.nowDate=xh.nowDate();
			$scope.userList();
			$scope.orderData=$scope.data[index];
			
			$("#order").modal('show');
		};
		/* 显示按钮修改model */
	/*	$scope.showEditModel = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length != 1) {
				swal({
					title : "提示",
					text : "只能选择一条数据",
					type : "error"
				});
				return;
			}
			$("#edit").modal('show');
			$scope.editData = $scope.data[parseInt(checkVal[0])];
			$scope.roleId=$scope.editData.roleId.toString();
			
		};*/
		
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var pageSize = $("#page-limit").val();
			var bsId=$("#bsId").val();
			var starttime=$("#starttime").val();
			var endtime=$("#endtime").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../bsstatus/bsFaultList?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals),$scope);
			});
		};
		//分页点击
		$scope.pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var bsId=$("#bsId").val();
			var starttime=$("#starttime").val();
			var endtime=$("#endtime").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../bsstatus/bsFaultList?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit="+limit).
			success(function(response){
				xh.maskHide();
				
				$scope.start = (page - 1) * pageSize + 1;
				$scope.lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.lastIndex = totals;
					} else {
						$scope.start = 0;
						$scope.lastIndex = 0;
					}
				}
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
			
		};
	});
};
xh.update = function() {
	$.ajax({
		url : '../../bsstatus/editBsFault',
		type : 'POST',
		dataType : "json",
		async : false,
		data:{
			formData:xh.serializeJson($("#editForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			if (data.success) {
				$('#edit').modal('hide');
				toastr.success("更新成功", '提示');
				xh.refresh();

			} else {
				toastr.error("更新失败", '提示');
			}
		},
		error : function() {
		}
	});
};
/* 批量删除用户*/
xh.delMore = function() {
	var checkVal = [];
	$("[name='tb-check']:checkbox").each(function() {
		if ($(this).is(':checked')) {
			checkVal.push($(this).attr("value"));
		}
	});
	if (checkVal.length < 1) {
		swal({
			title : "提示",
			text : "请至少选择一条数据",
			type : "error"
		});
		return;
	}
	$.ajax({
		url : '../../web/user/del',
		type : 'post',
		dataType : "json",
		data : {
			userId : checkVal.join(",")
		},
		async : false,
		success : function(data) {
			if (data.success) {
				toastr.success("删除用户成功", '提示');
				xh.refresh();
			} else {
				swal({
					title : "提示",
					text : "失败",
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
xh.order=function(){
	var userid=$("span[name='userid']").text();
	var errtype=$("span[name='errtype']").text();
	var errlevel=$("span[name='errlevel']").text();
	if(userid==""){
		toastr.error("接单人不能为空", '提示');
		return;
	}
	if(errtype=="" || errlevel==""){
		toastr.error("故障类型,等级不能为空", '提示');
		return;
	}
	var formData={
		id:$("div[name='id']").text()==""?0:$("div[name='id']").text(),
		bsid:$("div[name='bsId']").text(),
		bsname:$("div[name='name']").text(),
		userid:userid,
		dispatchtime:$("div[name='dispatchtime']").text(),
		dispatchman:$("div[name='dispatchman']").text(),
		errtype:errtype,
		errlevel:errlevel,
		errfoundtime:$("div[name='errfoundtime']").text(),
		errslovetime:$("div[name='errslovetime']").text(),
		progress:$("div[name='progress']").text(),
		proresult:$("div[name='proresult']").text(),
		workman:$("div[name='workman']").text(),
		auditor:$("div[name='auditor']").text(),
	}
	$.ajax({
		url : '../../order/writeOrder',
		data : {
			formData:JSON.stringify(formData)
			
		},
		type : 'post',
		dataType : "json",
		async : false,
		success : function(response) {
			var data = response;
			if(data.success){
				toastr.success("派单成功", '提示');
			}else{
				toastr.error("派单失败", '提示');
			}
			

		},
		failure : function(response) {
			toastr.error("派单失败", '提示');
		}
	});
}
xh.worder=function(){
	var userid=$("#worder-form").find("span[name='userid']").text()
	if(userid==""){
		toastr.error("接单人不能为空", '提示');
		return;
	}
	var formData={
		id:$("div[name='id']").text(),
		bsid:$("div[name='bsId']").text(),
		bsname:$("div[name='name']").text(),
		userid:userid,
		dispatchtime:$("div[name='dispatchtime']").text(),
		dispatchman:$("div[name='dispatchman']").text(),
		errtype:$("div[name='errtype']").text(),
		errlevel:$("div[name='errlevel']").text(),
		errfoundtime:$("div[name='errfoundtime']").text(),
		errslovetime:$("div[name='errslovetime']").text(),
		progress:$("div[name='progress']").text(),
		proresult:$("div[name='proresult']").text(),
		workman:$("div[name='workman']").text(),
		auditor:$("div[name='auditor']").text(),
	}
	$.ajax({
		url : '../../order/writeOrder',
		data : {
			formData:JSON.stringify(formData)
			
		},
		type : 'post',
		dataType : "json",
		async : false,
		success : function(response) {
			var data = response;
			if(data.success){
				toastr.success("派单成功", '提示');
			}else{
				toastr.error("派单失败", '提示');
			}
			

		},
		failure : function(response) {
			toastr.error("派单失败", '提示');
		}
	});
}
/* 数据分页 */
xh.pagging = function(currentPage,totals, $scope) {
	var pageSize = $("#page-limit").val();
	
	var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
			.ceil(parseInt(totals, 10) / pageSize);
	var start = (currentPage - 1) * pageSize + 1;
	var end = currentPage * pageSize;
	if (currentPage == totalPages) {
		if (totals > 0) {
			end = totals;
		} else {
			start = 0;
			end = 0;
		}
	}
	$scope.start = start;
	$scope.lastIndex = end;
	$scope.totals = totals;
	if (totals > 0) {
		$(".page-paging").html('<ul class="pagination"></ul>');
		$('.pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
//基站故障记录
xh.excelToBsAlarm=function(){
	xh.maskShow();
	$("#btn-write").button('loading');
	var startTime=$("input[name='startTime']").val();
	var endTime=$("input[name='endTime']").val();
	if(startTime=="" || endTime==""){
		toastr.error("时间范围不能为空", '提示');
		$("#btn-write").button('reset');
		xh.maskHide();
		return;
	}
	
	$.ajax({
		url : '../../bsstatus/ExcelToBsAlarm',
		type : 'get',
		dataType : "json",
		data : {
			startTime:startTime,
			endTime:endTime
		},
		async : false,
		success : function(data) {
			xh.maskHide();
			$("#btn-write").button('reset');
			if (data.success) {
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-write").button('reset');
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
xh.nowDate=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 
    
    var h=yesterday.getHours();
    var m=yesterday.getMinutes();
    var s=yesterday.getSeconds();

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    
    if(h<10){
    	h="0"+h;
    }
    if(m<10){
    	m="0"+m;
    }
    if(s<10){
    	s="0"+s;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+h+":"+m+":"+s;   
    return  strYesterday;
}
xh.getOneDay=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+"23:59:59";   
    return  strYesterday;
}


