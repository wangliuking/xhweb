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
xh.load = function() {
	var app = angular.module("app", []);
	/*
	 * var type = $("#type").val(); var name = $("#name").val(); var model =
	 * $("#model").val(); var serialNumber = $("#serialNumber").val(); var from =
	 * $("#from").val(); var status = $("#status").val(); var pageSize =
	 * $("#page-limit").val();
	 */

	var pageSize = $("#page-limit").val();
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);
	app
			.controller(
					"xhcontroller",
					function($scope, $http, $location) {
						xh.maskShow();
						$scope.count = "15";// 每页数据显示默认值
						$scope.businessMenu = true; // 菜单变色

						// 获取登录用户
						$http.get("../../web/loginUserInfo").success(
								function(response) {
									xh.maskHide();
									$scope.loginUser = response.user;
									$scope.loginUserRoleId = response.roleId;
								});

						/* 获取申请记录表 */
						$http
								.get(
										"../../net/selectAll?start=0&limit="
												+ pageSize).success(
										function(response) {
											xh.maskHide();
											$scope.data = response.items;
											$scope.totals = response.totals;
											xh.pagging(1,
													parseInt($scope.totals),
													$scope);
										});

						/* 获取主管部门领导列表 */
						$http
								.get("../../web/user/getUserList?roleId=10001")
								.success(
										function(response) {
											$scope.userData_MainManager = response.items;
											$scope.userTotals_MainManager = response.totals;
											if ($scope.userTotals_MainManager > 0) {
												$scope.user_M = $scope.userData_MainManager[0].user;
											}
										});
						/* 刷新数据 */
						$scope.refresh = function() {
							$scope.search(1);
							$("#table-checkbox").prop("checked", false);
						};
						/* 跳转到申请进度页面 */
						$scope.toProgress = function(id) {
							$scope.editData = $scope.data[id];
							$scope.checkData = $scope.editData;
							/*
							 * $http.get("../../net/applyProgress?id="+$scope.editData.id).
							 * success(function(response){ $scope.progressData =
							 * response.items;
							 * 
							 * });
							 */
							$scope.progressData = $scope.editData;
							$("#progress").modal('show');
						};
						/* 显示协议签署窗口 */
						$scope.checkSign = function(id) {
							$("#joinNet_register").modal('show');
						};
						/* 显示添加用户窗口 */
						$scope.addUser = function(id) {
							$scope.joinNetProcessId = $scope.data[id].id;
							// 获取无线用户业务属性
							$http
									.get(
											"../../radiouserbusiness/list?start=0&limit="
													+ pageSize)
									.success(
											function(response) {
												$scope.userbusinessData = response.items;
												$scope.userbusinessTotals = response.totals;
												if ($scope.userbusinessTotals > 0) {
													$scope.userbusinessName = $scope.userbusinessData[0].id;
												}
											});
							// 获取无线用户互联属性
							$http
									.get(
											"../../radiouserseria/list?start=0&limit="
													+ pageSize)
									.success(
											function(response) {
												$scope.userseriaData = response.items;
												$scope.userseriaTotals = response.totals;
												if ($scope.userseriaTotals > 0) {
													$scope.userseriaName = $scope.userseriaData[0].name;
												}
											});

							$("#add").modal('show');
						};
						/* 显示添加组窗口 */
						$scope.addGroup = function(id) {
							$scope.joinNetProcessId = $scope.data[id].id;
							// 获取无线用户业务属性
							$http
									.get(
											"../../radiouserbusiness/list?start=0&limit="
													+ pageSize)
									.success(
											function(response) {
												$scope.userbusinessData = response.items;
												$scope.userbusinessTotals = response.totals;
												if ($scope.userbusinessTotals > 0) {
													$scope.userbusinessName = $scope.userbusinessData[0].id;
												}
											});
							// 获取无线用户互联属性
							$http
									.get(
											"../../radiouserseria/list?start=0&limit="
													+ pageSize)
									.success(
											function(response) {
												$scope.userseriaData = response.items;
												$scope.userseriaTotals = response.totals;
												if ($scope.userseriaTotals > 0) {
													$scope.userseriaName = $scope.userseriaData[0].name;
												}
											});
							$("#addTalkGroup").modal('show');
						};
						$scope.download = function(id) {
							xh.download(id);
						}
						/* 显示审核窗口 */
						$scope.checkWin = function(id) {
							$scope.checkData = $scope.data[id];
							// $http.get("../../web/user/userlist10002").
							$http
									.get(
											"../../web/user/getUserList?roleId=10002")
									.success(
											function(response) {
												$scope.userData = response.items;
												$scope.userTotals = response.totals;
												if ($scope.userTotals > 0) {
													$scope.user = $scope.userData[0].user;
												}
											});
							if ($scope.loginUserRoleId == 10001
									&& $scope.checkData.checked == 0) {
								$("#checkWin1").modal('show');
							}
							if ($scope.loginUserRoleId == 10002
									&& $scope.checkData.checked == 1) {
								$("#checkWin2").modal('show');
							}
							if ($scope.loginUserRoleId == 10002
									&& $scope.loginUser == $scope.checkData.user3
									&& $scope.checkData.checked == 2) {
								$("#checkWin3").modal('show');
							}
							if ($scope.loginUserRoleId == 10002
									&& $scope.loginUser == $scope.checkData.user4
									&& $scope.checkData.checked == 3) {
								$("#checkWin4").modal('show');
							}
							if ($scope.loginUserRoleId == 10002
									&& $scope.loginUser == $scope.checkData.user4
									&& $scope.checkData.checked == 3) {
								$("#checkWin4").modal('show');
							}
							if ($scope.loginUser == $scope.checkData.userName
									&& $scope.loginUserRoleId == 1000
									&& $scope.checkData.checked == 4) {
								$("#checkWin5").modal('show');
							}
							if ($scope.loginUserRoleId == 1000
									&& $scope.checkData.checked == 0) {
								$("#checkWin6").modal('show');
							}
							if ($scope.loginUserRoleId == 10001
									&& $scope.checkData.checked == 1) {
								$("#checkWin7").modal('show');
							}
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
							$http
									.get(
											"../../net/selectAll?start=0&limit="
													+ limit)
									.success(
											function(response) {
												xh.maskHide();
												$scope.data = response.items;
												$scope.totals = response.totals;
												xh
														.pagging(
																page,
																parseInt($scope.totals),
																$scope);
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
									"../../net/selectAll?start=" + start
											+ "&limit=" + limit).success(
									function(response) {
										xh.maskHide();
										$scope.start = (page - 1) * pageSize
												+ 1;
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
/* 申请入网 */
xh.addJoinNet = function() {
	$.ajax({
		url : '../../net/insertNet',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			formData : xh.serializeJson($("#addForm").serializeArray())
		// 将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.result == 1) {
				$('#add').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 主管部门审核 */
xh.check1 = function() {
	$.ajax({
		url : '../../net/checkedOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm1").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin1').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 管理方审核 */
xh.check2 = function() {
	$.ajax({
		url : '../../net/checkedTwo',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm2").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin2').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 上传编组方案 */
xh.check3 = function() {
	if (parseInt($("input[name='result']").val()) !== 1) {
		toastr.error("你还没有上传编组方案不能提交", '提示');
		return;
	}
	$.ajax({
		url : '../../net/uploadFile',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm3").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin3').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 审核编组方案 */
xh.check4 = function() {
	$.ajax({
		url : '../../net/checkFile',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm4").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin4').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 用户确认编组方案 */
xh.check5 = function() {
	$.ajax({
		url : '../../net/sureFile',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm5").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin5').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 上传公函 */
xh.check6 = function() {
	if (parseInt($("input[name='result']").val()) !== 1) {
		toastr.error("你还没有上传公函不能提交", '提示');
		return;
	}
	$.ajax({
		url : '../../net/uploadGH',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm6").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin6').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 上传通知函 */
xh.check7 = function() {
	if (parseInt($("input[name='result']").val()) !== 1) {
		toastr.error("你还没有上传通知函不能提交", '提示');
		return;
	}
	$.ajax({
		url : '../../net/uploadNote',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#checkForm7").serializeArray(),
		success : function(data) {

			if (data.result == 1) {
				$('#checkWin7').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/* 上传文件 */
xh.upload = function(index) {
	if (index == 1) {
		path = 'filePathBZ';
		note = 'uploadResultBZ';
	}
	if (index == 2) {
		path = 'filePathGH';
		note = 'uploadResultGH';
	}
	if (index == 3) {
		path = 'filePathNote';
		note = 'uploadResultNote';
	}
	
	if ($("#" + path).val() == "") {
		toastr.error("你还没选择文件", '提示');
		return;
	}

	xh.maskShow();
	$.ajaxFileUpload({
		url : '../../net/upload', // 用于文件上传的服务器端请求地址
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
				alert(data.success);
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

/* 经办人添加无线用户 */
xh.addUser = function() {
	$.ajax({
		url : '../../radiouser/add',
		type : 'POST',
		dataType : "json",
		async : false,
		data : $("#addUserForm").serializeArray(),
		success : function(data) {

			if (data.result == 0) {
				$('#add').modal('hide');

				for (var i = 1; i < 10; i++) {
					console.log(1);
				}
				toastr.success("添加无线用户成功", '提示');
				xh.refresh();
			} else {
				toastr.error("添加无线用户失败", '提示');
			}
		},
		error : function() {
		}
	});
}
/* 经办人添加组 */
xh.addTGroup = function() {
	$.ajax({
		url : '../../talkgroup/add',
		type : 'POST',
		dataType : "json",
		async : false,
		data : $("#addTalkGroupForm").serializeArray(),
		success : function(data) {

			if (data.result == 0) {
				$('#addTalkGroup').modal('hide');

				for (var i = 1; i < 10; i++) {
					console.log(1);
				}
				toastr.success("添加用户组成功", '提示');
				xh.refresh();
			} else {
				toastr.error("添加用户组失败", '提示');
			}
		},
		error : function() {
		}
	});
}
/* 经办人编写入网登记表 */
xh.regist = function() {

	$.ajax({
		url : '../../net/registNet',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			formData : xh.serializeJson($("#registerForm").serializeArray())
		// 将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.result == 1) {
				$('#joinNet_register').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};

xh.download = function(id) {
	var $scope = angular.element(appElement).scope();
	$scope.checkData = $scope.data[id];
	var filename = $scope.checkData.fileName;
	alert($scope.loginUserRoleId);
	if(id != -1){
		if($scope.loginUserRoleId == 10001){
			filename = $scope.checkData.fileName_GH;
			alert("10001:" + filename);
		}
		else if($scope.loginUserRoleId == 10002 && $scope.checkData.checked==1){
			filename = $scope.checkData.fileName_Note;
			alert("10002:" + filename);
		}
	}
	console.log("filename=>" + filename);
	alert(filename);
	var downUrl = "../../net/download?fileName=" + filename;
	alert(downUrl);
	window.open(downUrl, '_self',
			'width=1,height=1,toolbar=no,menubar=no,location=no');
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
