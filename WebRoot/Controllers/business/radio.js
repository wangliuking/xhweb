
if (!("xh" in window)) {
	window.xh = {};
};
/*
 * test
 */
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
	var pageSize = $("#page-limit").val();

	app.controller("radiouser", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		$scope.page=1;
		
		//获取登录用户
		$http.get("../../web/loginUserInfo").
		success(function(response){
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.loginUserRoleId = response.roleId;	
		});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		
		
		$scope.dataList=function(){
			var id=$("#C_ID").val();
			$http.get("../../radio/list?id="+id+"&start=0&limit="+pageSize).success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(1, parseInt($scope.totals), $scope);
			})
		}
		$scope.getMethodOne=function(){
			var id1=$("input[name='id1']").val();
			var id2=$("input[name='id2']").val();
			if(id1=="" || id2==""){
				toastr.error("终端ID号范围不正确", '提示');
				return;
			}
			if(id1>id2){
				toastr.error("终端ID号范围不正确", '提示');
				return;
			}
			if(id2-id1>500){
				toastr.error("一次批量最大500个ID", '提示');
				return;
			}
			xh.maskShow();
			$("textarea[name='log']").val("");
			for(var i=id1;i<=id2;i++){
				$http.get("../../radio/radioGet?radioId="+i).success(function(response){
					xh.maskHide();
					var data = response;
					if(data.success){
						$("textarea[name='log']").val($("textarea[name='log']").val()+"\n"+"获取终端ID:"+data.data.radioID+"成功");
					}else{
						//toastr.info("获取终端ID:"+i+"失败", '提示');
						$("textarea[name='log']").val($("textarea[name='log']").val()+"\n"+"获取终端ID:"+data.data.radioID+"失败");
					}
				})
			}
			$scope.dataList();
			xh.maskHide();
		}
		$scope.getMethodTwo=function(){
			var id3=$("textarea[name='id3']").val();
			if(id3==""){
				toastr.error("内容不能为空", '提示');
				return;
			}
			xh.maskShow();
			$("textarea[name='log']").val("");
			var idstr=id3.split("|");
			
			for(var i=0;i<idstr.length;i++){
				var id=idstr[i];
				$http.get("../../radio/radioGet?radioId="+id).success(function(response){
					xh.maskHide();
					var data = response;
					if(data.success){
						$("textarea[name='log']").val($("textarea[name='log']").val()+"\n"+"获取终端ID:"+data.data.radioID+"成功");
					}else{
						//toastr.info("获取终端ID:"+i+"失败", '提示');
						$("textarea[name='log']").val($("textarea[name='log']").val()+"\n"+"获取终端ID:"+data.data.radioID+"失败");
					}
				})
			}
			$scope.dataList();
			xh.maskHide();
		}
		$scope.securityGroupList=function(){
			$http.get("../../usermoto/securityGroupList").success(
					function(response) {
						$scope.securityGroupList = response.items;
						
					});
		}
		
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
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
		/* 显示修改基站model */
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
		/* 删除*/
		$scope.del = function(id) {
			swal({
				title : "提示",
				text : "确定要删除该无线终端信息吗？",
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
						url : '../../radio/delete',
						type : 'post',
						dataType : "json",
						data : {
							formData : JSON.stringify($scope.data[id])
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
							$scope.refresh();
						}
					});
				}
			});
		};
		/* 批量删除基站 */
		$scope.delMore = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length < 1) {
				toastr.error("请至少选择一条数据", '提示');
				return;
			}
			for(var i=0;i<checkVal.length;i++){
				
				$.ajax({
					url : '../../radio/delete',
					type : 'post',
					dataType : "json",
					data : {
						formData : JSON.stringify($scope.data[checkVal[i]])
					},
					async : false,
					success : function(data) {
						if (data.success) {
							toastr.success("删除"+data.data+"成功", '提示');
							$scope.refresh();
						} else {
							toastr.error(data.message+"["+data.data+"]", '提示');
						}
					},
					error : function() {
						$scope.refresh();
					}
				});
			}
			

		};
		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var id=$("#C_ID").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../radio/list?id="+id+"&start="+start+"&limit="+pageSize).success(
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
			var id=$("#C_ID").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../radio/list?id="+id+"&start="+start+"&limit="+pageSize).success(
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
		
		$scope.dataList();
		$scope.securityGroupList();
	});
};
/* 添加 */
xh.add = function() {
	xh.maskShow();
	$.ajax({
		url : '../../radio/add',
		type : 'POST',
		dataType : "json",
		async : false,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			xh.maskHide();
			if (data.success) {
				$('#add').modal('hide');
				$("#addForm")[0].reset();
				//$(".modal-backdrop").remove();
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
/* 修改 */
xh.update = function() {
	$.ajax({
		url : '../../radio/update',
		type : 'POST',
		dataType : "json",
		async : false,
		data:{
			formData:xh.serializeJson($("#editForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			if (data.success) {
				$('#edit').modal('hide');
				toastr.success(data.message, '提示');
				xh.refresh();
			} else {
				toastr.error("修改失败", '提示');
			}
		},
		error : function() {
		}
	});
};

xh.mark_moto = function() {
	var checkVal = [];
	$("[name='tb-check']:checkbox").each(function() {
		if ($(this).is(':checked') && $(this).attr("moto")==0) {
			checkVal.push($(this).attr("value"));
		}
	});
	if (checkVal.length < 1) {
		toastr.error("请至少选择一条不是moto手台的数据", '提示');
		return;
	}
	swal({
		title : "提示",
		text : "确定要将选中的手台标记为moto手台",
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
				url : '../../radiouser/mark_moto',
				type : 'post',
				dataType : "json",
				data : {
					id : checkVal.join(","),
					is_moto:1
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success("标记成功", '提示');
						xh.refresh();
					} else {
						toastr.error("标记失败", '提示');
					}
				},
				error : function() {
				}
			});
		}
	});

};
xh.cancel_mark_moto = function() {
	var checkVal = [];
	$("[name='tb-check']:checkbox").each(function() {
		if ($(this).is(':checked') && $(this).attr("moto")==1) {
			checkVal.push($(this).attr("value"));
		}
	});
	if (checkVal.length < 1) {
		toastr.error("请至少选择一条是moto手台的数据", '提示');
		return;
	}
	swal({
		title : "提示",
		text : "确定要将选中的手台取消标记为moto手台",
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
				url : '../../radiouser/mark_moto',
				type : 'post',
				dataType : "json",
				data : {
					id : checkVal.join(","),
					is_moto:0
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success("取消标记成功", '提示');
						xh.refresh();
					} else {
						toastr.error("取消标记失败", '提示');
					}
				},
				error : function() {
				}
			});
		}
	});

};

// 刷新数据
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
