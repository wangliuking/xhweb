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
		
		$scope.selectAll=function(){
			console.log("dfdfdfdfdfd")
			/*var checkVal = [];
			var flag = $(this).is(':checked') ? 1 : 0;
			if ($(this).is(':checked')) {
				$("[name='tb-check']").prop("checked", true);// 全选
			} else {
				$("[name='tb-check']").prop("checked", false);// 反选
			}*/
		}
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
		//获取派单列表
	/*	$http.get("../../order/orderlist?start=0&limit=" + pageSize).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});*/
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
	
		$scope.resend=function(index){
			
			var formData=$scope.data[index];
			$.ajax({
				url : '../../order/rewriteOrder',
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
		$scope.del = function(id) {
			var data=$scope.data[id];
			swal({
				title : "提示",
				text : "确定要删除吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../order/del',
						type : 'get',
						dataType : "json",
						data : {
							id : data.id
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

							} else {
								swal({
									title : "提示",
									text : data.message,
									type : "error"
								});
							}
						},
						error : function() {
							$scope.refresh();
						}
					});
				}
			});
		};
		/*显示详细信息*/
		$scope.editModel = function(id) {
			$("#orderWin").modal('show');
			
			$scope.editData = $scope.data[id];
			
		};
		/*下载工作记录*/
		$scope.download = function(path) {
			var index=path.lastIndexOf("/");
			var name=path.substring(index+1,path.length);	
			var downUrl = "../../uploadFile/downfile?filePath="+path+"&fileName=" + name;
			if(xh.isfile(path)){
				window.open(downUrl, '_self',
				'width=1,height=1,toolbar=no,menubar=no,location=no');
			}else{
				toastr.error("文件不存在", '提示');
			}
		};
		/*显示审核*/
		$scope.checkWin=function(index){
			$scope.checkData=$scope.data[index];
			var roleType=3;
			/* 获取主管单位用户列表 */
			$http.get("../../web/role/roleTypeByList?roleType="+roleType).success(
					function(response) {
						$scope.roleData = response.items;
						$scope.roleTotals=response.totals
					});
			
			$("#checkWin1").modal('show');
			
			
			/**/
			
		};
		$scope.showOrderUpdateWin=function(index){
			$scope.updateData=$scope.data[index];
			$("#orderUpdateWin").modal('show');
		}
		//确认派单完成
		$scope.check=function(id){
			console.log($scope.data[id])
			$.ajax({
				url : '../../order/updateOrder',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					id:$scope.data[id].id,
					bsId:$scope.data[id].bsid,
					zbdldm:$scope.data[id].zbdldm,
					from:$scope.data[id].from,
					serialnumber:$scope.data[id].serialnumber,
					status:3,
					alarmId:$scope.data[id].alarmId,
					dispatchtime:$scope.data[id].dispatchtime,
					recvTime:$scope.data[id].recvTime,
					level:$scope.data[id].level,
					userid:$scope.data[id].handleUserid,
					recvUser:$scope.data[id].recv_user,
                    copyUser:$scope.data[id].copy_user
				},
				success : function(data) {

					if (data.success) {
						$scope.refresh();
						toastr.success("已确认该派单中的故障处理意见", '提示');
					} else {
						toastr.success("确认失败", '提示');
					}
				},
				error : function() {
				}
			});
		};
        $scope.checkFail=function(id){
            $.ajax({
                url : '../../order/updateOrder',
                type : 'POST',
                dataType : "json",
                async : true,
                data:{
                    id:$scope.data[id].id,
                    bsId:$scope.data[id].bsid,
                    zbdldm:$scope.data[id].zbdldm,
                    from:$scope.data[id].from,
                    status:-1,
                    serialnumber:$scope.data[id].serialnumber,
                    alarmId:$scope.data[id].alarmId,
					dispatchtime:$scope.data[id].dispatchtime,
					recvTime:$scope.data[id].recvTime,
					level:$scope.data[id].level,
                    userid:$scope.data[id].handleUserid,
                    recvUser:$scope.data[id].recv_user,
                    copyUser:$scope.data[id].copy_user
                },
                success : function(data) {

                    if (data.success) {
                        $scope.refresh();
                        toastr.success("已确认该派单中的故障处理意见", '提示');
                    } else {
                        toastr.success("确认失败", '提示');
                    }
                },
                error : function() {
                }
            });
        };
        $scope.updateOrderData=function(){
        	 $.ajax({
                 url : '../../order/updateOrderData',
                 type : 'POST',
                 dataType : "json",
                 async : true,
                 data:{
                	 formData:JSON.stringify($scope.updateData)
                 },
                 success : function(data) {

                     if (data.success) {
                         $scope.refresh();
                         toastr.success("修改成功", '提示');
                         $("#orderUpdateWin").modal('hide')
                     } else {
                         toastr.success("确认失败", '提示');
                     }
                 },
                 error : function() {
                 }
             });
        }

		$scope.excel = function(page) {
			var bs=$("#bs").val();
			var starttime=$("#starttime").val();
			var endtime=$("#endtime").val();
			if(starttime==""){
				toastr.error("请选择起始时间", '提示');
				return;
			}
			xh.maskShow("正在分析数据，请耐心等待");
			$("#btn-excel").button('loading');
			$.ajax({
				url : '../../order/excel',
				type : 'GET',
				dataType : "json",
				async : true,
				data:{
					bs:bs,
					starttime:starttime,
					endtime:endtime
				},
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

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var bs=$("#bs").val();
			var copy_user = $("#copy_user").val();
			var dispatchman = $("#dispatchman").val();
			var recv_user = $("#recv_user").val();
			var type = $("#type").val();
			var status = $("#status").val();
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
			$http.get("../../order/orderlist?start="+start+"&bs="+bs+"&starttime="+starttime+"" +
					"&copy_user="+copy_user+"&dispatchman="+dispatchman+"&recv_user="+recv_user+"&type="+type+"" +
					"&endtime="+endtime+"&limit=" + pageSize+"&status="+status).success(function(response) {
	
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var bs=$("#bs").val();
			var copy_user = $("#copy_user").val();
			var dispatchman = $("#dispatchman").val();
			var recv_user = $("#recv_user").val();
			var type = $("#type").val();
			var status = $("#status").val();
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
			$http.get("../../order/orderlist?start="+start+"&bs="+bs+"&starttime="+starttime+"" +
					"&copy_user="+copy_user+"&dispatchman="+dispatchman+"&recv_user="+recv_user+"&type="+type+"" +
					"&endtime="+endtime+"&limit=" + pageSize+"&status="+status).success(function(response) {
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
		$scope.search(1);
		setInterval(function(){
			$scope.search($scope.page);
		}, 5000)
		
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
		url : '../../duty/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.result ==1) {
				$('#add').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');
				$("input[name='result']").val("");
            	$("input[name='fileName']").val("");
            	$("input[name='filePath']").val("");
            	$("#uploadResult").html("");
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
/* 批量删除 */
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
	swal({
		title : "提示",
		text : "确定要删除吗？",
		type : "info",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "确定",
		cancelButtonText : "取消"
	/*
	 * closeOnConfirm : false, closeOnCancel : false
	 */
	}, function(isConfirm) {
		if (isConfirm) {
	$.ajax({
		url : '../../order/del',
		type : 'get',
		dataType : "json",
		data : {
			id : checkVal.join(",")
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
		}})
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
xh.print_order=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("故障处理任务单");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print_order").innerHTML);
	 LODOP.PREVIEW();  	
};
