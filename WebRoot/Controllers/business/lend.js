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
	
	var pageSize = $("#page-limit").val();
	app.controller("xhcontroller", function($scope,$http) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		$scope.businessMenu=true; //菜单变色
		
		//获取登录用户
		$http.get("../../web/loginUserInfo").
		success(function(response){
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.loginUserRoleId = response.roleId;	
		});
		
		
		/*获取申请记录表*/
		$http.get("../../business/lend/list?start=0&limit=" + pageSize).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			xh.pagging(1, parseInt($scope.totals), $scope);
		});
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
			$("#table-checkbox").prop("checked", false);
		};
		/*跳转到处理页面*/
		$scope.toDeal = function (id) {
			$scope.editData = $scope.data[id];
			window.location.href="lend-deal.html?data_id="+$scope.editData.id + "&manager=" + $scope.editData.user1;
	    };
		/*跳转到申请进度页面*/
		$scope.toProgress = function (id) {
			$scope.progressData = $scope.data[id];
			$http.get("../../business/lend/lendInfoList?lendId="+$scope.progressData.id).
			success(function(response){
				xh.maskHide();
				$scope.dataLend = response.items;
				$scope.lendTotals = response.totals;
			});
			$("#progress").modal('show');
	    };
	    
	    $scope.checkedChange=function(issure){
	    	$scope.issure=issure==1?true:false;
	    	console.log($scope.issure);
	    };
	    /*审核归还通过*/
		$scope.checkSuccess = function (id) {
			$scope.checkedSerialNumber = $scope.dataLend[id].serialNumber;
			$.ajax({
				url : '../../business/lend/returnEquipment',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					lendId:$scope.dataLend[id].lendId,
					checkId:$scope.checkedSerialNumber,
					status:0
				},
				success : function(data) {

					if (data.result ==1) {
						$('#progress').modal('hide');
						xh.refresh();
						toastr.success(data.message, '提示');

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
				}
			});
		}
		/*显示审核窗口*/
		$scope.checkWin = function (id) {
			if(id == -1){
				$http.get("../../web/user/getUserList?roleId=10002").
				success(function(response){
					$scope.userData = response.items;
					$scope.userTotals = response.totals;
					if($scope.userTotals>0){
						$scope.user=$scope.userData[0].user;
					}
				});
				$("#add").modal('show');
			}
			$scope.checkData = $scope.data[id];
			$scope.ch="1";
			if($scope.loginUserRoleId==10002 && $scope.checkData.checked==0){
				$http.get("../../web/user/getUserList?roleId=10002").
				success(function(response){
					$scope.userData = response.items;
					$scope.userTotals = response.totals;
					if($scope.userTotals>0){
						$scope.user=$scope.userData[0].user;
					}
				});
				$("#checkWin1").modal('show');
			}
			if($scope.loginUserRoleId==10002 && $scope.loginUser==$scope.checkData.user1 && $scope.checkData.checked==2){
				//设备清单列表
				$http.get("../../business/lend/lendInfoList?lendId="+$scope.checkData.id).
				success(function(response){
					xh.maskHide();
					$scope.dataLend = response.items;
					$scope.lendTotals = response.totals;
				});
				$("#checkWin3").modal('show');
			}
			if($scope.loginUser==$scope.checkData.user && $scope.checkData.checked==3){
				xh.check4();
			}
			if($scope.loginUser==$scope.checkData.user && $scope.checkData.checked==4){
				//设备清单列表
				$http.get("../../business/lend/lendInfoList?lendId="+$scope.checkData.id).
				success(function(response){
					xh.maskHide();
					$scope.dataLend = response.items;
					$scope.lendTotals = response.totals;
				});
				$("#checkWin5").modal('show');
			}
			/*if($scope.loginUser==$scope.checkData.user && $scope.checkData.checked==4){
				//设备清单列表
				$http.get("../../business/lend/lendInfoList?lendId="+$scope.checkData.id + "status=2").
				success(function(response){
					xh.maskHide();
					$scope.dataLend = response.items;
					$scope.lendTotals = response.totals;
				});
				$("#checkWin6").modal('show');
			}*/
			
	    };
	    /* 用户确认编组方案 */
	    $scope.sureFile = function(id) {
	    	$.ajax({
	    		url : '../../net/sureFile',
	    		type : 'POST',
	    		dataType : "json",
	    		async : false,
	    		data:{id:id},
	    		success : function(data) {
	    			if (data.result === 1) {
	    				toastr.success(data.message, '提示');
	    				xh.refresh();

	    			} else {
	    				toastr.error(data.message, '提示');
	    			}
	    		},
	    		error : function(){
	    		}
	    	});
	    };
		/* 显示修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
			$scope.type = $scope.editData.type.toString();
			$scope.from = $scope.editData.from.toString();
		};
		/* 显示修改model */
		$scope.showEditModel = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length != 1) {
				/*swal({
					title : "提示",
					text : "只能选择一条数据",
					type : "error"
				});*/
				toastr.error("只能选择一条数据", '提示');
				return;
			}
			$("#edit").modal('show');
			$scope.editData = $scope.data[parseInt(checkVal[0])];
			
			$scope.type = $scope.editData.type.toString();
			$scope.from = $scope.editData.from.toString();
		};
		/* 删除 */
		$scope.delBs = function(id) {
			swal({
				title : "提示",
				text : "确定要删除该记录吗？",
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
						url : '../../business/deleteAsset',
						type : 'post',
						dataType : "json",
						data : {
							deleteIds : id
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
		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			/*var type = $("#type").val();
			var name = $("#name").val();
			var model = $("#model").val();
			var serialNumber = $("#serialNumber").val();
			var from = $("#from").val();
			var status = $("#status").val();
			var pageSize = $("#page-limit").val();*/
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../business/lend/list?start="+start+"&limit=" + pageSize).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		//分页点击
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
			$http.get("../../business/lend/list?start="+start+"&limit=" + pageSize).
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
xh.checkedChange=function(){
	var $scope = angular.element(appElement).scope();
    $scope.checkedChange($("#checkForm1").find("select[name='checked']").val());
};
/*申请租借设备*/
xh.add = function() {
	$.ajax({
		url : '../../business/lend/add',
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

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/*主管部门审核*/
xh.check1 = function() {
	$.ajax({
		url : '../../business/lend/checkedOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#checkForm1").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {

			if (data.result ==1) {
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

/*管理部门领导审核租借清单*/

xh.check3 = function(checked) {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../business/lend/checkedOrder',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			lendId:$scope.checkData.id,
			checked:checked,
			user:$scope.checkData.user,
			note2:$("#checkForm3").find("input[name='note2']").val()
		},
		success : function(data) {

			if (data.result ==1) {
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
/*用户确认租借清单*/
xh.check4 = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../business/lend/sureOrder',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			lendId:$scope.checkData.id
		},
		success : function(data) {

			if (data.result ==1) {
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
/*用户归还设备*/
xh.check5 = function(checkIds) {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../business/lend/returnEquipment',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			lendId:$scope.checkData.id,
			checkId:checkIds,
			manager:$scope.checkData.user1,
			status:2
		},
		success : function(data) {

			if (data.result ==1) {
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

/*上传文件*/
xh.upload = function() {
	if($("input[type='file']").val()==""){
		toastr.error("你还没选择文件", '提示');
		return;
	}
	xh.maskShow();
	$.ajaxFileUpload({
		url : '../../net/upload', //用于文件上传的服务器端请求地址
		secureuri : false, //是否需要安全协议，一般设置为false
		fileElementId : 'filePath', //文件上传域的ID
		dataType : 'json', //返回值类型 一般设置为json
		type:'POST',
		success : function(data, status) //服务器成功响应处理函数
		{
			xh.maskHide();
			if(data.success){
				$("#uploadResult").html(data.message);
				$("input[name='result']").val(1);
				$("input[name='fileName']").val(data.fileName);
				$("input[name='path']").val(data.filePath);
			}else{
				$("#uploadResult").html(data.message);
			}
			
		},
		error : function(data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
};
xh.download=function(){
	var $scope = angular.element(appElement).scope();
	var filename=$scope.checkData.fileName;
	console.log("filename=>"+filename);
	var downUrl="../../net/download?fileName="+filename;
	window.open(downUrl,'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
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