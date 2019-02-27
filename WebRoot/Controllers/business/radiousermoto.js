/**
 * 无线用户配置
 */
if (!("xh" in window)) {
	window.xh = {};
};
/*
 * test
 */
$(function(){
	
});
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
xh.load = function() {
	var app = angular.module("app", []);
	var C_ID = $("#C_ID").val();
	var E_name = $("#RadioUserAlias").val();
	var pageSize = $("#page-limit").val();

	app.controller("radiouser", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		$scope.page=1;
		$scope.moto=0;
		$scope.m_radio="";
		
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		
		$http.get(
				"../../usermoto/list?C_ID=" + C_ID + "&E_name=" + E_name
						+ "&is_moto="+$scope.moto+"&start=0&limit=" + pageSize).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
		$scope.radioList=function(){
			$http.get("../../usermoto/radioList").success(
					function(response) {
						$scope.radioList = response.items;
						
					});
		}
		$scope.securityGroupList=function(){
			$http.get("../../usermoto/securityGroupList").success(
					function(response) {
						$scope.securityGroupList = response.items;
						
					});
		}

		
		/* 刷新数据 */
		$scope.refresh = function() {
			$("#C_ID").val("");
			$("#RadioUserAlias").val("");
			$scope.search($scope.page);
			$scope.radioList();
		};
		$scope.search_moto = function() {
			$scope.moto=1;
			$scope.search(1);
			
		};
		$scope.search_eastcom = function() {
			$scope.moto=0;
			$scope.search(1);
			
		};
		/*显示添加基站model*/
		$scope.addModel = function(){
			$('#add').modal('show');
		};
		$scope.radio = function(dd){
			$scope.m_radio=dd;
		};
		$scope.editModel = function(id) {
			/*进入模态框默认选中第一步*/
			$('#edit').modal('show');
			$scope.editData = $scope.data[id];
		};
		/* 显示修改基站model */
		$scope.showEditModel = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length != 1) {
				toastr.error("只能选择一条数据", '提示');
				return;
			}
			console.log("edit=" + checkVal[0]);
			/* $scope.edit(parseInt(checkVal[0])); */
			/*进入模态框默认选中第一步*/
			$('#edit a[href="#step_1"]').tab('show');
			$("#edit").modal('show');
			$scope.editData = $scope.data[parseInt(checkVal[0])];
		};
		/* 删除基站 */
		$scope.del = function(id) {
			swal({
				title : "提示",
				text : "确定要删除该moto用户信息吗？",
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
						url : '../../usermoto/deleteByRadioUserId',
						type : 'post',
						dataType : "json",
						data : {
							ids : id
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
			var pageSize = $("#page-limit").val();
			var C_ID = $("#C_ID").val();
			var E_name = $("#RadioUserAlias").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			console.log("limit=" + limit);
			xh.maskShow();
			$http.get(
					"../../usermoto/list?C_ID=" + C_ID + "&E_name=" + E_name + "&is_moto="+$scope.moto+"&start="
							+ start + "&limit=" + limit).success(
					function(response) {
						xh.maskHide();
						$scope.data = response.items;
						$scope.totals = response.totals;
						$scope.page=page;
						xh.pagging(page, parseInt($scope.totals), $scope);
					});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var C_ID = $("#C_ID").val();
			var E_name = $("#RadioUserAlias").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../usermoto/list?C_ID=" + C_ID + "&E_name=" + E_name + "&is_moto="+$scope.moto+"&start="
							+ start + "&limit=" + pageSize).success(
					function(response) {
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
						$scope.page=page;
						$scope.data = response.items;
						$scope.totals = response.totals;
					});

		};
		$scope.radioList();
		$scope.securityGroupList();
	});
};
xh.add = function() {
	xh.maskShow();
	$("#add_user_btn").button('loading')
	$.ajax({
		url : '../../usermoto/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			xh.maskHide();
			$("#add_user_btn").button('reset');

			if (data.success) {
				$('#add').modal('hide');			
				toastr.success(data.message, '提示');
				xh.refresh();
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			$("#add_user_btn").button('reset');
		}
	});
};
/* 修改基站信息 */
xh.update = function() {
	xh.maskShow();
	$.ajax({
		url : '../../usermoto/update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#editForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			xh.maskHide();

			if (data.success) {
				$('#edit').modal('hide');			
				toastr.success(data.message, '提示');
				xh.refresh();
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
		}
	});
};
/* 批量删除基站 */
xh.delMore = function() {
	var checkVal = [];
	$("[name='tb-check']:checkbox").each(function() {
		if ($(this).is(':checked')) {
			checkVal.push($(this).attr("value"));
		}
	});
	if (checkVal.length < 1) {
		toastr.error("请至少选择一条数据", '提示');
		return;
	}
	swal({
		title : "提示",
		text : "确定要删除该moto用户信息吗？",
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
				url : '../../usermoto/deleteByRadioUserId',
				type : 'post',
				dataType : "json",
				data : {
					ids : checkVal.join(",")
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						xh.refresh();
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
				}
			});
		}
	});

};

xh.refresh = function() {
	var appElement = document.querySelector('[ng-controller=radiouser]');
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
/*
 * $http({ method : "POST", url : "../../bs/list", data : { bsId : bsId, name :
 * name, start : start, limit : pageSize }, headers : { 'Content-Type' :
 * 'application/x-www-form-urlencoded' }, transformRequest : function(obj) { var
 * str = []; for ( var p in obj) { str.push(encodeURIComponent(p) + "=" +
 * encodeURIComponent(obj[p])); } return str.join("&"); }
 * }).success(function(response) { xh.maskHide(); $scope.data = response.items;
 * $scope.totals = response.totals; });
 */
