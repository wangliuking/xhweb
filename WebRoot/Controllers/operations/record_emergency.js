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
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		/*$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			console.log("loginuser="+$scope.loginUser);
			$scope.loginUserRoleId = response.roleId;
		});*/
		//获取派单列表
		$http.get("../../userneed/emergency_list?start=0&limit=" + $scope.count).success(
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
							url : '../../userneed/del_emergency',
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
		$scope.print=function(index) {
			$scope.editData = $scope.data[index];
			var LODOP = getLodop();
			LODOP.PRINT_INIT("用户需求处理");
			LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
			LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print").innerHTML);
			 LODOP.PREVIEW();  	
		};
		$scope.download = function(index) {
			$scope.editData = $scope.data[index];
			var path=$scope.editData.file_path;
			var name=$scope.editData.file_name;	
			var downUrl = "../../uploadFile/downfile?filePath="+path+"&fileName=" + name;
			if(xh.isfile(path)){
				window.open(downUrl, '_self',
				'width=1,height=1,toolbar=no,menubar=no,location=no');
			}else{
				toastr.error("文件不存在", '提示');
			}
		};
		$scope.showUploadW = function(index) {
			$scope.editData = $scope.data[index];
			$("#upload").modal('show');
			
			
		};

		$scope.excel = function(index) {
			xh.maskShow("正在分析数据，请耐心等待");
			$.ajax({
				url : '../../userneed/excel',
				type : 'POST',
				dataType : "json",
				contentType:'application/json;charset=utf-8',
				async : true,
				data:JSON.stringify($scope.data[index]),
				success : function(data) {
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
			var starttime=$("#start_time").val();
			var endtime=$("#end_time").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../userneed/emergency_list?start="+start+"&starttime="+starttime+"" +
					"&endtime="+endtime+"&limit=" + pageSize).success(function(response) {
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
			var starttime=$("#start_time").val();
			var endtime=$("#end_time").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../userneed/emergency_list?start="+start+"&starttime="+starttime+"" +
					"&endtime="+endtime+"&limit=" + pageSize).success(function(response) {
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
		url : '../../userneed/add_emergency',
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
		url : '../../userneed/upload_emergency',
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
		url : '../../userneed/update_emergency',
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
