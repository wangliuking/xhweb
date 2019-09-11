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
	var app = angular.module("app", []);
	var pageSize = $("#page-limit").val();

	app.filter('timeFormat', function() { // 可以注入依赖
		return function(text) {
			if(text!=null && text!=""){
				var date=new Date();
				date.setTime(text);
				var m_year=date.getFullYear();
				var m_month=date.getMonth()+1;
				var m_day=date.getDate();
				var m_hour=date.getHours();
				var m_minute=date.getMinutes();
				var m_sec=date.getSeconds();
				var time=m_year;
				time+="-";
				time+=m_month>=10?m_month:("0"+m_month);
				time+="-";
				time+=m_day>=10?m_day:("0"+m_day);
				time+=" ";
				time+=m_hour>=10?m_hour:("0"+m_hour);
				time+=":";
				time+=m_minute>=10?m_minute:("0"+m_minute);
				time+=":";
				time+=m_sec>=10?m_sec:("0"+m_sec);
				return time;
			}else{
				return "";
			}
		};
	});

	app.filter('useTime', function() { // 可以注入依赖
		return function(text) {
			if(text.gen_start_time==null || text.gen_end_time==null){
				return "";
			}
			var a=text.gen_start_time;
			var b=text.gen_end_time;
			var x=new Date(a);
			var y=new Date(b);
			var r=(y.getTime()-x.getTime())/1000;
			if(r<60){
				return r+"秒";
			}else{
				if(r/60<60){
					return parseInt(r/60)+"分"+parseInt(r%60)+"秒";
				}else{
					return parseInt(r/3600)+"小时"+parseInt(r/60%60)+"分"+parseInt(r%60)+"秒"
				}
			}
		};
	});
	

	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		var bsId=$("#bsId").val();
		var starttime=$("#starttime").val();
		var endtime=$("#endtime").val();
		$scope.page=1;
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.loginUserRoleId = response.roleId;
			$scope.roleType = response.roleType;
		});
		$http.get(
				"../../elec/list?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start=0&limit=" + $scope.count).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
		$scope.bs_offine_record = function(a,b,c) {
			$http.get(
					"../../elec/bs_offline_record?bsId="+a+"&starttime="+b+"&endtime="+c).success(
					function(response) {
						$scope.rs = response.items;
						$scope.rs_count = response.totals;
					});
		};
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};

		/*显示详细信息*/
		$scope.editModel = function(id) {
			$("#detail").modal('show');
			$scope.editData = $scope.data[id];
			$scope.bs_offine_record($scope.editData.bsId,$scope.editData.gen_start_time,$scope.editData.gen_end_time);
		};
		$scope.showDetail=function(index){
			$("#gorder-detail").modal('show');
			$scope.detailData = $scope.data[index];
		}
		$scope.resend=function(index){
			var data=$scope.data[index];
			
			var formData={
				id:data.bsfault_id,
				bsid:data.bsId,
				bsname:data.name,
				userid:data.recv_user,
				note:data.comment,
				time:date_format(data.poweroff_time),
				recv_user_name:data.userName,
				send_user_name:data.send_userName,
				serialnumber:data.serialnumber,
				dispatchtime:data.dispatchtime
			}
			$.ajax({
				url : '../../elec/resend_order',
				data :formData,
				type : 'post',
				dataType : "json",
				async : false,
				success : function(response) {
					var data = response;
					if(data.success){
						toastr.success(data.message, '提示');
						$scope.refresh();
					}else{
						toastr.error(data.message, '提示');
					}
				},
				failure : function(response) {
					toastr.error("参数错误", '提示');
				}
			});
		}
		$scope.showPic=function(){
			var data=$scope.detailData;
			$scope.pic=data.gen_on_pic.split("|");
			$scope.pic_one=$scope.pic[0];
			$("#picWin").modal('show');
			
		}
		$scope.showOffPic=function(){
			var data=$scope.detailData;
			$scope.pic=data.gen_off_pic.split("|");
			$scope.pic_one=$scope.pic[0];
			$("#picWin").modal('show');
			
		}
		$scope.pic_on_click=function(e){
			$scope.pic_one=e;
		}
		/*审核*/
		$scope.showCheckWin=function(index){
			$("#checkWin").modal('show');
			$scope.checkData=$scope.data[index];
		}
		$scope.showCheckWin2=function(index){
			$("#checkWin2").modal('show');
			$scope.checkData=$scope.data[index];
		}
		$scope.checkOne=function(){
			$.ajax({
				url : '../../elec/checkOne',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					id:$scope.checkData.id,
					userid:$scope.checkData.recv_user,
					serialnumber:$scope.checkData.serialnumber,
					bsId:$scope.checkData.bsId,
					status:$("#checkForm").find("select[name='checked']").val(),
					note1:$("#checkForm").find("textarea[name='note1']").val()
				},
				success : function(data) {

					if (data.success) {
						$scope.refresh();
						$("#checkWin").modal('hide');
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.success("服务器响应失败", '提示');
				}
			});
			
		};
		$scope.checkTwo=function(){
			$.ajax({
				url : '../../elec/checkTwo',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					id:$scope.checkData.id,
					userid:$scope.checkData.recv_user,
					serialnumber:$scope.checkData.serialnumber,
					bsId:$scope.checkData.bsId,
					status:$("#checkForm2").find("select[name='checked']").val(),
					note1:$("#checkForm2").find("textarea[name='note2']").val()
				},
				success : function(data) {

					if (data.success) {
						$scope.refresh();
						$("#checkWin2").modal('hide');
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.success("服务器响应失败", '提示');
				}
			});
			
		};
		
		$scope.check=function(){
			$.ajax({
				url : '../../elec/check',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					id:$scope.checkData.id,
					userid:$scope.checkData.recv_user,
					serialnumber:$scope.checkData.serialnumber,
					bsId:$scope.checkData.bsId,
					status:$("#checkForm").find("select[name='checked']").val(),
					note1:$("#checkForm").find("textarea[name='note1']").val()
				},
				success : function(data) {

					if (data.success) {
						$scope.refresh();
						$("#checkWin").modal('hide');
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.success("服务器响应失败", '提示');
				}
			});
			
		};
		$scope.cancel_order=function(index){
			var id=$scope.data[index].id;
			var serialnumber=$scope.data[index].serialnumber;
			swal({
				title : "提示",
				text : "确定要取消该单号吗？",
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
						url : '../../elec/cancelOrder',
						type : 'POST',
						dataType : "json",
						async : true,
						data:{
							id:id,
							userid:$scope.data[index].recv_user,
							status:$scope.data[index].status,
							serialnumber:serialnumber
						},
						success : function(data) {

							if (data.success) {
								$scope.refresh();
								toastr.success(data.message, '提示');
							} else {
								toastr.error(data.message, '提示');
							}
						},
						error : function() {
							toastr.success("服务器响应失败", '提示');
						}
					});
				}
			});
			
		};

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var bsId=$("#bsId").val();
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
			console.log("limit=" + limit);
			xh.maskShow();
			$http.get(
					"../../elec/list?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit=" + pageSize).success(function(response) {
				xh.maskHide();
				$scope.page=page;
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var bsId=$("#bsId").val();
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
			$http.get(
					"../../elec/list?bsId="+bsId+"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit=" + pageSize).success(function(response) {
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
		
	});
};
//刷新数据
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

function  date_format(text){
	if(text!=null && text!=""){
		var date=new Date();
		date.setTime(text);
		var m_year=date.getFullYear();
		var m_month=date.getMonth()+1;
		var m_day=date.getDate();
		var m_hour=date.getHours();
		var m_minute=date.getMinutes();
		var m_sec=date.getSeconds();
		var time=m_year;
		time+="-";
		time+=m_month>=10?m_month:("0"+m_month);
		time+="-";
		time+=m_day>=10?m_day:("0"+m_day);
		time+=" ";
		time+=m_hour>=10?m_hour:("0"+m_hour);
		time+=":";
		time+=m_minute>=10?m_minute:("0"+m_minute);
		time+=":";
		time+=m_sec>=10?m_sec:("0"+m_sec);
		return time;
	}else{
		return "";
	}
}
