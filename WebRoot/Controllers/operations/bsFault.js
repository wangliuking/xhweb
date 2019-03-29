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
	var pageSize = $("#page-limit").val()==null?30:$("#page-limit").val();
	app.controller("xhcontroller", function($scope, $http) {
		$scope.count = "30";//每页数据显示默认值
		$scope.pageValue=1;
		$scope.page=1;
		/*$scope.starttime=xh.getBeforeDay(7);
		$scope.endtime=xh.getOneDay();*/
		$scope.nowDate=xh.getOneDay();
		$scope.showMore=false;
		var bsId=$("#bsId").val();
		var starttime=$("#starttime").val();
		var endtime=$("#endtime").val();
		
		
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId ;
			$scope.roleType = response.roleType ;
			$scope.userName=response.userName;
			
		});
		/*获取故障信息*/
		
		$scope.getInfo=function(){
			var starttime=$("input[name='startTime']").val();
			var endtime=$("input[name='endTime']").val();
			var alarmType=0;
			var sysType=0;
			var level_value =[],alarmTag_value=[]; 
			//告警级别
			$('input[name="level"]:checked').each(function(){ 
			level_value.push($(this).val()); 
			});
			//告警状态
			alarmTag_value.push(0); 
			/*$('input[name="alarmTag"]:checked').each(function(){ 
				alarmTag_value.push($(this).val()); 
			});*/
			
			$http.get("../../bsstatus/bsFaultList?bsId=&" +
					"level="+level_value.join(",")+"&sysType="+sysType+"&alarmType_value="+alarmType+"&" +
					"alarmTag_value="+alarmTag_value.join(",")+"&starttime="+starttime+"&endtime="+endtime+"&start=0&limit=30").
			success(function(response){
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(1, parseInt($scope.totals),$scope);
			});
		}
		
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
		$scope.refresh_search = function() {
			$scope.search($scope.page);
			$("#search").modal('hide');
		};
		/* 显示链接修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
			$("#edit").modal('show');
		};
        /* 显示核减model */
        $scope.checkCutModel = function(id) {
            $scope.faultDataById = $scope.data[id];
			var serialId = $scope.data[id].id;
			var bsId = $scope.data[id].bsId;
            var name = $scope.data[id].name;
            var breakTime = $scope.data[id].time;
            var restoreTime = $scope.data[id].faultRecoveryTime;
            $.ajax({
                url : '../../checkCut/createCheckSheet?id='+serialId+"&bsId="+bsId+"&name="+name+"&breakTime="+breakTime+"&restoreTime="+restoreTime,
                type : 'GET',
                dataType : "json",
                async : false,
                success : function(response) {
                    $scope.sheetData = response.items;
                    var data= response.items;  //开始时间
                    var date1 = data.breakTime;
                    var date2 = data.restoreTime;    //结束时间
					var temp = date2+"";
					//console.log("temp : "+temp);

                    if (temp == "undefined") {
                    	console.log("undefined")
                        $scope.sheetData.restoreTime = "";
                    }else{
                        $scope.calcData = calTime(date1,date2);
					}

                    $("#add").modal('show');
                },
                error : function() {
                }
            });
        };

        /* 显示警告详情 */
        $scope.startCalc = function(){
            var date1 = $("#breakTime").val();
            var date2 = $("#restoreTime").val();
            $scope.calcData = calTime(date1,date2);
        };

		/* 显示警告详情 */
		$scope.showDetails = function(id){
			$("#bsAlarmDetails").modal('show');
			$scope.bsAlarmData=$scope.data[id];
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
		//禁止核减
		$scope.stop_check=function(){
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					var index=$(this).attr("index");
					var d=$scope.data[index];
					if(d.checkTag==0){
						checkVal.push($(this).attr("value"));
					}
					
				}
			});
			if (checkVal.length<1) {
				swal({
					title : "提示",
					text : "至少选择一条没有核减过的数据",
					type : "error"
				});
				return;
			}
			swal({
				title : "提示",
				text : "确认禁止对该基站核减吗？",
				type : "success",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			 
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../bsstatus/stop_check_bs',
						type : 'post',
						dataType : "json",
						data : {
							id : checkVal.join(",")
						},
						async : false,
						success : function(data) {
							if (data.count>0) {
								toastr.success("成功", '提示');
								$scope.refresh();

							} else {
								toastr.error("失败", '提示');
							}
						},
						error : function() {
							toastr.error("服务器响应失败", '提示');
						}
					});
				}
			});
			
		}
		//确认告警
		$scope.sureOk=function(){
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("value"));
				}
			});
			if (checkVal.length<1) {
				swal({
					title : "提示",
					text : "至少选择一条数据",
					type : "error"
				});
				return;
			}
			swal({
				title : "提示",
				text : "确认已经知晓该告警",
				type : "success",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			 
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../bsAlarm/sureAlarm',
						type : 'post',
						dataType : "json",
						data : {
							id : checkVal.join(",")
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

							} else {
								toastr.error(data.message, '提示');
							}
						},
						error : function() {
							toastr.error("服务器响应失败", '提示');
						}
					});
				}
			});
			
		}
		$scope.sureOneOk=function(index){
			var id=$scope.data[index].id
			
			$.ajax({
				url : '../../bsAlarm/sureAlarm',
				type : 'post',
				dataType : "json",
				data : {
					id : id
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("服务器响应失败", '提示');
				}
			});
			
		}
		//派单
		$scope.showOrderWin = function(index){
			
			$scope.nowDate=xh.nowDate();
			$scope.userList();
			if(index>=0){
				$scope.orderData=$scope.data[index];
			}else{
				$scope.orderData=[];
			}
			
			
			$("#order").modal('show');
		};
		/*通知发电*/
        $scope.showGorderWin = function(index){
			var data=$scope.data[index];
			$scope.userList();
			$scope.orderData=$scope.data[index];
			
			$("#gorder").modal('show');
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
			$scope.pageValue=page;
			var pageSize = $("#page-limit").val();
			var bsId=$("#bs-Id").val();
			var bsName=$("#bsName").val();
			var starttime=$("input[name='startTime']").val();
			var endtime=$("input[name='endTime']").val();
			var alarmType=$("input[name='alarmType']:checked").val();
			var sysType=$("input[name='sysType']:checked").val();
			var level_value =[],alarmTag_value=[]; 
			//告警级别
			$('input[name="level"]:checked').each(function(){ 
			level_value.push($(this).val()); 
			}); 
			//告警状态
			$('input[name="alarmTag"]:checked').each(function(){ 
				alarmTag_value.push($(this).val()); 
			});
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../bsstatus/bsFaultList?bsId="+bsId+"&bsName="+bsName+"&level="+level_value.join(",")+"&sysType="+sysType+"&alarmType_value="+alarmType+"&" +
					"alarmTag_value="+alarmTag_value.join(",")+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit="+limit).
			success(function(response){
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page;
				xh.pagging(page, parseInt($scope.totals),$scope);
			});
		};
		//分页点击
		$scope.pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var bsId=$("#bs-Id").val();
			var bsName=$("#bsName").val();
			var starttime=$("input[name='startTime']").val();
			var endtime=$("input[name='endTime']").val();
			var alarmType=$("input[name='alarmType']:checked").val();
			var sysType=$("input[name='sysType']:checked").val();
			var level_value =[],alarmTag_value=[]; 
			//告警级别
			$('input[name="level"]:checked').each(function(){ 
			level_value.push($(this).val()); 
			}); 
			//告警状态
			$('input[name="alarmTag"]:checked').each(function(){ 
				alarmTag_value.push($(this).val()); 
			});
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../bsstatus/bsFaultList?bsId="+bsId+"&bsName="+bsName+"&level="+level_value.join(",")+"&sysType="+sysType+"&alarmType_value="+alarmType+"&" +
					"alarmTag_value="+alarmTag_value.join(",")+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit="+limit).
			success(function(response){
				$scope.pageValue=page;
				
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
				$scope.page=page;
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
			
		};
		$scope.getInfo();
		setInterval(function(){
			$scope.search($scope.pageValue);
		}, 30000);
		
		
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
xh.gorder=function(){
	var form=$("#gorder-form");
	var userid=form.find("span[name='userid']").text();
	if(userid==""){
		toastr.error("接单人不能为空", '提示');
		return;
	}
	var formData={
		id:form.find("div[name='id']").text()==""?0:form.find("div[name='id']").text(),
		bsid:form.find("div[name='bsId']").text(),
		bsname:form.find("div[name='name']").text(),
		userid:userid,
		note:form.find("div[name='note']").text(),
		time:form.find("div[name='time']").text(),
		recv_user_name:form.find("div[id='recv-user-name']").text(),
		send_user_name:form.find("div[id='send-user-name']").text()
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
				toastr.success(data.message, '提示');
				form.find("div[name='note']").text("")
				xh.refresh();
			}else{
				toastr.error(data.message, '提示');
			}
		},
		failure : function(response) {
			toastr.error("参数错误", '提示');
		}
	});
}
xh.order=function(){
	var userid=$("input[name='userid']").val();
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
	if($("#order").find("input[name='dispatchman']")==""){
		toastr.error("派单人不能为空", '提示');
		return;
	}
	var formData={
		id:$("#order").find("input[name='id']").val()==""?0:$("#order").find("input[name='id']").val(),
		from:$("#order").find("span[name='from']").text(),
		zbdldm:$("#order").find("span[name='zbdldm']").text(),
		bsid:$("#order").find("input[name='bsId']").val(),
		bsname:$("#order").find("input[name='name']").val(),
		userid:userid,
		dispatchtime:$("#order").find("input[name='dispatchtime']").val(),
		dispatchman:$("#order").find("input[name='dispatchman']").val(),
		errtype:errtype,
		errlevel:errlevel,
		errfoundtime:$("#order").find("input[name='errfoundtime']").val(),
		errslovetime:$("#order").find("input[name='errslovetime']").val(),
		progress:$("#order").find("textarea[name='progress']").val(),
		proresult:$("#order").find("textarea[name='proresult']").val(),
		workman:$("#order").find("input[name='workman']").val(),
		auditor:$("#order").find("input[name='auditor']").val(),
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
				xh.refresh();
			}else{
				toastr.error("派单失败", '提示');
			}
			

		},
		failure : function(response) {
			toastr.error("派单失败", '提示');
		}
	});
}
/*xh.worder=function(){
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
}*/
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
	var startTime=$("#faultRecord").find("input[name='startTime']").val();
	console.log("starttime"+startTime)
	var endTime=$("#faultRecord").find("input[name='endTime']").val();
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

/*运维组发起请求审核*/
xh.add = function() {
    $.ajax({
        url : '../../checkCut/updateCheckTag',
        type : 'POST',
        dataType : "json",
        async : false,
        data : $("#addForm").serializeArray(),
        success : function(data) {
            $.ajax({
                url : '../../checkCut/createCheckCut',
                type : 'POST',
                dataType : "json",
                async : true,
                data : $("#addForm").serializeArray(),
                success : function(data) {
                    $("#add_btn").button('reset');
                    if (data.result ==1) {
                        $('#add').modal('hide');
                        $("input[name='result']").val(1);
                        xh.refresh();
                        toastr.success(data.message, '提示');
                    } else {
                        toastr.error(data.message, '提示');
                    }
                },
                error : function() {
                    $("#add_btn").button('reset');
                }
            });
        }
    });
};

/* 上传文件 */
xh.upload = function(index) {
    if (index == -1) {
        path = 'filePath-1';
        note = 'uploadResult-1';
    }
    if (index == 1) {
        path = 'filePath1';
        note = 'uploadResult1';
    }
    if (index == 2) {
        path = 'filePath2';
        note = 'uploadResult2';
    }
    if (index == 3) {
        path = 'filePath3';
        note = 'uploadResult3';
    }
    if (index == 4) {
        path = 'filePath4';
        note = 'uploadResult4';
    }
    if ($("#" + path).val() == "") {
        toastr.error("你还没选择文件", '提示');
        return;
    }
    xh.maskShow();
    $.ajaxFileUpload({
        url : '../../checkCut/upload', // 用于文件上传的服务器端请求地址
        secureuri : false, // 是否需要安全协议，一般设置为false
        fileElementId : path, // 文件上传域的ID
        dataType : 'json', // 返回值类型 一般设置为json
        type : 'POST',
        success : function(data, status) // 服务器成功响应处理函数
        {
            // var result=jQuery.parseJSON(data);
            console.log(data.filePath)
            xh.maskHide();
            if (data.success) {
                $("#"+note).html(data.message);
                $("input[name='result']").val(1);
                $("input[name='fileName']").val(data.fileName);
                $("input[name='path']").val(data.filePath);
            } else {
                $("#"+note).html(data.message);
            }

        },
        error : function(data, status, e)// 服务器响应失败处理函数
        {
            alert(e);
        }
    });
};

/*修改核减申请表*/
xh.sheetChange = function() {
    var bean={
        id:$("div[name='id']").text(),
        bsId:$("input[name='bsId']").val(),
        name:$("input[name='name']").val(),
        hometype:$("input[name='hometype']").val(),
        transfer:$("input[name='transfer']").val(),
        transferCompare:$("input[name='transferCompare']").val(),
        transferOne:$("input[name='transferOne']").val(),
        transferTwo:$("input[name='transferTwo']").val(),
        powerOne:$("input[name='powerOne']").val(),
        powerTimeOne:$("input[name='powerTimeOne']").val(),
        powerTwo:$("input[name='powerTwo']").val(),
        powerTimeTwo:$("input[name='powerTimeTwo']").val(),
        maintainTime:$("input[name='maintainTime']").val(),
        isPower:$("input[name='isPower']").val(),
        firstDesc:$("input[name='firstDesc']").val(),
        desc:$("div[name='desc']").text(),
        breakTime:$("input[name='breakTime']").val(),
        restoreTime:$("input[name='restoreTime']").val(),
        checkCutTime:$("input[name='checkCutTime']").val(),
        alarmTime:$("input[name='alarmTime']").val(),
        situation:$("div[name='situation']").text(),
        rules:$("input[name='rules']").val(),
        period:$("div[name='period']").text(),
        applyTime:$("div[name='applyTime']").text(),
        suggest:$("div[name='suggest']").text(),
        persion3:$("div[name='persion3']").text(),
        persion1:$("div[name='persion1']").text(),
        persion2:$("div[name='persion2']").text()
    }
    $.ajax({
        url : '../../checkCut/sheetChange',
        type : 'POST',
        dataType : "json",
        data : {"bean" : JSON.stringify(bean)},
        success : function(data) {
            $("#checkCut-btn").button('reset');
            $('#sheet').modal('hide');
            xh.refresh();
            toastr.success(data.message, '提示');
        },
        error : function() {
            $("#checkCut-btn").button('reset');
        }
    });
};

function calTime(date1,date2){
    var date3 = new Date(date2).getTime() - new Date(date1).getTime();   //时间差的毫秒数
    //------------------------------
    //计算出相差天数
    var days=Math.floor(date3/(24*3600*1000))
    //计算出小时数
    var leave1=date3%(24*3600*1000)    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000))
    //计算相差分钟数
    var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000))
    //计算相差秒数
    var leave3=leave2%(60*1000)      //计算分钟数后剩余的毫秒数
    var seconds=Math.round(leave3/1000)
    //alert(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
    return Math.round(date3/(1000*60));
}
