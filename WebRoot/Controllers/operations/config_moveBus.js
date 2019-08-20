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
	app.filter('state', function() { // 可以注入依赖
		return function(text) {
			if(text=="0"){
				return "备用"
			}else{
				return "在用"
			}
		};
	});
	app.filter('type', function() { // 可以注入依赖
		return function(text) {
			if(text==1){
				return "车载"
			}else{
				return "便携"
			}
		};
	});
	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		var pageSize = $("#page-limit").val();
		$scope.page=1;
		$scope.time=xh.dateNowFormat("month");
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			console.log("loginuser="+$scope.loginUser);
			$scope.loginUserRoleId = response.roleId;
		});
		$http.get("../../movebus/list?start=0&limit=" + $scope.count).success(
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
		$scope.showFileWin=function(){
			$("input[name='pathName']").click();
		}
		$scope.showPicWin=function(index){
			$("#picWin").modal('show');
			$scope.oneData = $scope.data[index];
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
							url : '../../movebus/del',
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
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../movebus/list?start="+start+
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
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../movebus/list?start="+start+
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
		url : '../../movebus/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray())
		} ,
		/*contentType : 'application/json;charset=utf-8', //设置请求头信息 
*/		success : function(data) {

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
xh.edit = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../movebus/update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#editForm").serializeArray())
		},
		success : function(data) {

			if (data.success) {
				$('#editWin').modal('hide');
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

