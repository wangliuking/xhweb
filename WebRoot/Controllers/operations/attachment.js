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
		$scope.page=1;
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId ;
		});
		
		/* 获取信息 */
		$http.get("../../attachment/attachmentList?start=0&limit=" + pageSize).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
	    $scope.config=function(){
	    	$http.get("../../attachment/attachmentList_config?start=0&limit=500").success(
					function(response) {
						xh.maskHide();
						$scope.configData = response.items;
					});
	    }
	    $scope.configOne=function(str){
	    	$scope.configOneData=null;
	    	for(var i=0;i<$scope.configData.length;i++){
	    		if(str==$scope.configData[i].attachment_name){
	    			$scope.configOneData=$scope.configData[i]
	    		}
	    	}

	    	console.log($scope.configOneData)
	    }

		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
	   
		/* 显示链接修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
		};
		$scope.showExel = function(id) {
			if(id==1){
				$scope.title="生成月备品备件文件"
			}else{
				$scope.title="导出月备品备件"
			}
			$("#excel-one").modal("show");
		};
		/* 显示按钮修改model */
		$scope.showEditModel = function() {
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
			$scope.editData.roleId = $scope.editData.roleId.toString();

		};

		/* 删除*/
		$scope.del = function(id) {
			swal({
				title : "提示",
				text : "确定要删除记录吗？",
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
						url : '../../attachment/del',
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
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var pageSize = $("#page-limit").val();
			var user=$("input[name='user']").val();
			var roleId=$("select[name='roleId']").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$scope.page=page;
			xh.maskShow();
			$http.get("../../attachment/attachmentList?start=0&limit=" + limit)
					.success(function(response) {
						xh.maskHide();
						$scope.data = response.items;
						$scope.totals = response.totals;
						xh.pagging(page, parseInt($scope.totals), $scope);
					});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var user=$("input[name='user']").val();
			var roleId=$("select[name='roleId']").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$scope.page=page;

			xh.maskShow();
			$http.get(
					"../../attachment/attachmentList?start=" + start + "&limit="
							+ limit).success(function(response) {
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
		 $scope.config();
		
	});
};

xh.configOne=function(str){
	var $scope = angular.element(appElement).scope();
	$scope.configOne(str)
}

/* 添加*/
xh.add = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../attachment/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			data:xh.serializeJson($("#addForm").serializeArray()) 
		},
		success : function(data) {

			if (data.success) {
				$('#add').modal('hide');
				xh.refresh();
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
/* 修改 */
xh.update = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../attachment/update',
		type : 'POST',
		dataType : "json",
		async : false,
		data : {
			data:xh.serializeJson($("#editForm").serializeArray()) 
		},
		success : function(data) {
			if (data.success) {
				$('#edit').modal('hide');
				toastr.success(data.message, '提示');
				xh.refresh();

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
/* 批量删除用户 */
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
		url : '../../attachment/del',
		type : 'post',
		dataType : "json",
		data : {
			id : checkVal.join(",")
		},
		async : false,
		success : function(data) {
			if (data.success) {
				toastr.success(data.message, '提示');
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
xh.excelOne = function() {
	var $scope = angular.element(appElement).scope();
	var time=$("#excel-one").find("input[name='time']").val();
	$("#excel-one-btn").button('loading');
	xh.maskShow("数据导出中");
	$.ajax({
		url : '../../attachment/excelOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			time:time
		},
		success : function(data) {
			xh.maskHide();
			$("#excel-one-btn").button('reset');
			if (data.success) {
				$('#edit-one').modal('hide');
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				

			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
			xh.maskHide();
			$("#excel-one-btn").button('reset');
		}
	});
};
xh.excelTwo = function() {
	var $scope = angular.element(appElement).scope();
	var time=$("#excel-two").find("input[name='time']").val();
	$("#excel-two-btn").button('loading');
	xh.maskShow("数据导出中");
	$.ajax({
		url : '../../attachment/excelOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			time:time
		},
		success : function(data) {
			xh.maskHide();
			$("#excel-two-btn").button('reset');
			if (data.success) {
				$('#excel-two').modal('hide');
				//window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				swal({
					title : "提示",
					text : "文件生成成功",
					type : "info"
				});

			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
			xh.maskHide();
			$("#excel-two-btn").button('reset');
		}
	});
};
// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

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

