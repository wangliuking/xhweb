if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
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
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
	var app = angular.module("app", [])
	

	app.filter('qualitystatus', function() { // 可以注入依赖
		return function(text) {
			if (text == 0) {
				return "未签收";
			} else if (text == 1) {
				return "签收";
			}
		};
	});

	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		var pageSize = $("#page-limit").val();
		$scope.page=1;
		$scope.time=xh.dateNowFormat("month");
		$scope.time_day=xh.dateNowFormat("day");
		$scope.type="0";
		/* 获取基站区域*/
		$http.get("../../bs/map/area").success(
				function(response) {
					$scope.bszone = response.items;
				});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.userL = response;
		});
		
		$http.get("../../faultlevel/three_list?bsType=&bsId=&zone=&endtime="+$scope.time+"&" +
				"type="+$scope.type+"&time="+$scope.time+"&start=0&limit=" + $scope.count).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
		$scope.timeJS=function(level){
			
			//var data=$scope.data[index];
			var t1=$("#editForm").find("input[name='send_order_time']").val();
			var t2=$("#editForm").find("input[name='receipt_order_time']").val();
			var t3=$("#editForm").find("input[name='recv_order_time']").val();
		
			console.log("t1:"+t1);
			console.log("t2:"+t2);
			console.log("t3:"+t3);
			/*if(t1=="" || t2=="" || t3==""){
				return;
			}*/
			var d1=new Date(t1);
			var d2=new Date(t2);
			var d3=new Date(t3);
			//接单耗时
			var r1=parseInt((d2.getTime()-d1.getTime())/60000);
			//接单超时
			var r2=0;
			//处理耗时
			var r3=parseInt((d3.getTime()-d2.getTime())/60000);
			//处理超时
			var r4=0;
			var cs_total=0;
			console.log("level:"+level);
		    if(level==1){
		    	if(r3>110){
		    		cs_total=r3-110;
		    	}
		    }else if(level==2){
		    	if(r3>170){
		    		cs_total=r3-170;
		    	}
		    }else if(level==3){
		    	if(r3>290){
		    		cs_total=r3-290;
		    	}
		    }
		    
		    r4=cs_total>0?cs_total:0;
		    console.log("r1:"+r1);
			console.log("r2:"+r2);
			console.log("r3:"+r3);
			console.log("r4:"+r4);
			if(t1!="" && t2!=""){
				$("#editForm").find("input[name='recv_order_use_time']").val(r1);
				$("#editForm").find("input[name='recv_order_cs']").val(r2);
			}
			if(t1!="" && t2!="" && t3!=""){
				$("#editForm").find("input[name='handle_order_user_time']").val(r3);
				$("#editForm").find("input[name='handle_order_cs']").val(r4);
			}
			
		
			
		    
			
		}
	
		$scope.del=function(id){
			$scope.oneData = $scope.data[id];
			swal({
				title : "提示",
				text : "确定要删除记录吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消",
			    closeOnConfirm : true, 
			    closeOnCancel : true,
			    }, function(isConfirm) {
				    if (isConfirm) {
				    	$.ajax({
							url : '../../faultlevel/three_del',
							type : 'post',
							dataType : "json",
							data : {
								id:$scope.oneData.id
							},				
							async : false,
							success : function(data) {
								xh.maskHide();
								//$("#btn-mbs").button('reset');
								if (data.success) {
									toastr.success(data.message, '提示');
									$scope.refresh();
								} else {
									toastr.error(data.message, '提示');
								}
							},
							error : function() {
								xh.maskHide();
								toastr.error("系统错误", '提示');
							}
						});
						
				    }
			});
			
		}

		/*显示详细信息*/
		$scope.show_detail = function(id) {
			$("#detailWin").modal('show');
			$scope.editData = $scope.data[id];
			
		};
		$scope.show_add_win = function(id) {
			$("#addWin").modal('show');
			
		};
		$scope.show_edit = function(id) {
			$scope.editData = $scope.data[id];
			$("#editWin").modal('show');
			
		};
	
		

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var starttime=$("#time").val();
			var endtime=$("#time2").val();
			var zone=$("#zone").val();
			var bsId=$("#bsId").val();
			var bsType=$("#bsType").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../faultlevel/three_list?bsType="+bsType+"&bsId="+bsId+"&zone="+zone+"&endtime="+endtime+"&" +
					"type="+$scope.type+"&start="+start+"&time="+starttime+"" +
					"&limit=" + pageSize).success(function(response) {
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var starttime=$("#time").val();
			var endtime=$("#time2").val();
			var zone=$("#zone").val();
			var bsId=$("#bsId").val();
			var bsType=$("#bsType").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../faultlevel/three_list?bsType="+bsType+"&bsId="+bsId+"&zone="+zone+"&endtime="+endtime+"&" +
					"type="+$scope.type+"&start="+start+"&time="+starttime+"" +
					"&limit=" + pageSize).success(function(response) {
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
				$scope.page=page
			});

		};
	});
};
//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};
/* 添加 */
xh.add = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../faultlevel/one_add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:xh.serializeJson($("#addForm").serializeArray()) ,
		contentType : 'application/json;charset=utf-8', //设置请求头信息 
		success : function(data) {
			if (data.success) {
				$('#addWin').modal('hide');
				xh.refresh();
				$("#addForm")[0].reset();
				$("#addForm").data('bootstrapValidator').resetForm();
				toastr.success(data.message, '提示');
			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
xh.upload = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../faultlevel/three_update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:xh.serializeJson($("#uploadForm").serializeArray()) ,
		contentType : 'application/json;charset=utf-8', //设置请求头信息 
		success : function(data) {

			if (data.success) {
				$('#upload').modal('hide');
				xh.refresh();
				$("#uploadForm")[0].reset();
				$("#uploadForm").data('bootstrapValidator').resetForm();
				$("#uploadResult").html("");
				 toastr.success("文件上传成功", '提示');
			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
xh.edit = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../faultlevel/three_update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:xh.serializeJson($("#editForm").serializeArray()) ,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {

			if (data.success) {
				$('#editWin').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');
			
				$("#editForm").data('bootstrapValidator').resetForm();
			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
/* 数据分页 */
xh.pagging = function(currentPage, totals, $scope) {
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
xh.print=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("用户需求处理");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.excel=function(){
	var type=$("#type").val();
	var starttime=$("#time").val();
	var endtime=$("#time2").val();
	var zone=$("#zone").val();
	var bsId=$("#bsId").val();
	var bsType=$("#bsType").val();
	if(starttime==""){
		toastr.error("时间不能为空", '提示');
		return;
	}
	xh.maskShow("正在分析数据，请耐心等待");
	$("#btn-excel").button('loading');
	$.ajax({
		url : '../../faultlevel/excel_fault_three',
		type : 'get',
		dataType : "json",
		data : {
			time:starttime,
			endtime:endtime,
			zone:zone,
			bsId:bsId,
			bsType:bsType,
			type:type
		},
		async : true,
		success : function(data) {
		
			$("#btn-excel").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-excel").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});
	
};
