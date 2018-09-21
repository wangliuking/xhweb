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
	app.controller("xhcontroller", function($scope, $http) {
		/*xh.maskShow();*/
		$scope.count = "15";//每页数据显示默认值
		$scope.starttime=xh.getBeforeDay(7);
		$scope.endtime=xh.getOneDay();
		
		/*获取移动基站巡检表信息*/
		$scope.mbs=function(){	
			var pageSize = $("#page-limit").val();
			$http.get("../../app/mbsinfo?start=0&limit="+pageSize).
			success(function(response){
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
				xh.mbs_pagging(1, parseInt($scope.mbsTotals),$scope,pageSize);
			});
		}
		/*获取自建基站巡检表信息*/
		$scope.sbs=function(){	
			var pageSize = $("#page-limit-sbs").val();
			$http.get("../../app/sbsinfo?start=0&limit="+pageSize).
			success(function(response){
				$scope.sbsData = response.items;
				$scope.sbsTotals = response.totals;
				xh.sbs_pagging(1, parseInt($scope.sbsTotals),$scope,pageSize);
			});
		}
		
		$scope.msc_add_win_text=function(){
			$scope.msc_add_win_html=[];
			for(var i=1;i<=17;i++){
				var content="";
				var a="正常",b="异常";
				if(i==1){
					content="机房门窗地面墙壁等是否正常";
				}
				if(i==2){
					content="机房照明、电源插座是否正常";
				}
				if(i==3){
					content="设备灰尘及滤网是否清洁(传输3500等)";
				}
				if(i==4){
					content="机房是否清洁、是否没有杂物或易燃物品";
				}
				if(i==5){
					content="消防设备是否正常";
				}
				if(i==6){
					content="UPS是否正常（断电测试）";
				}
				if(i==7){
					content="蓄电池是否正常（发电测试）";
				}
				if(i==8){
					content="发电机是否正常（包括汽油发电机、柴油发电机）";
				}
				if(i==9){
					content="空调是否正常工作、滤网是否清洁";
				}
				if(i==10){
					content="环境监控系统是否正常";
				}
				if(i==11){
					content="电源线是否老化";
				}
				if(i==12){
					content="设备标签是否规范完、完整、走线是否正常";
				}
				if(i==13){
					content="服务器是否运行正常";
				}
				if(i==14){
					content="设备接地是否正常";
				}
				if(i==15){
					content="服务器、单板、模块安装是否牢固可靠";
				}
				if(i==16){
					content="楼顶吸盘天线是否加固，接头是否正常";
				}
				if(i==17){
					content="服务器数据及配置是否备份";
					a="已备份";
					b="未备份";
				}
				
				
				var b={
						id:i,
						a:a,
						b:b,
						content:content
				}
				$scope.msc_add_win_html.push(b);
			}
		}
		
		
		
		/* 刷新数据 */
		$scope.mbs_refresh = function() {
			$scope.mbs_search(1);
		};
		$scope.sbs_refresh = function() {
			$scope.sbs_search(1);
		};
		$scope.net_refresh = function() {
			$scope.net_search(1);
		};
		$scope.dispatch_refresh = function() {
			$scope.dispatch_search(1);
		};
		$scope.msc_refresh = function() {
			$scope.msc_search(1);
		};
		/* 显示mbsWin */
		$scope.showMbsEditWin = function(id) {
			$scope.mbsOneData = $scope.mbsData[id];
			$("#editMbsWin").modal('show');
		};
		$scope.showMbsWin=function(id){
			$scope.mbsOneData = $scope.mbsData[id];
			$("#mbsWin").modal('show');
		}
		/* 显示sbsWin */
		$scope.showSbsWin = function(id) {
			$scope.sbsOneData = $scope.sbsData[id];
			$("#sbsWin").modal('show');
		};
		$scope.showSbsEditWin = function(id) {
			$scope.sbsOneData = $scope.sbsData[id];
			$("#editSbsWin").modal('show');
		};
		$scope.showMbsWin=function(id){
			$scope.mbsOneData = $scope.mbsData[id];
			$("#mbsWin").modal('show');
		}
		/* 显示netWin */
		$scope.showNetWin = function(id) {
			$scope.netOneData = $scope.netData[id];
			$("#netWin").modal('show');
		};
		/* 显示dispatchWin */
		$scope.showDispatchWin = function(id) {
			$scope.dispatchOneData = $scope.dispatchData[id];
			$("#dispatchWin").modal('show');
		};
		/* 显示mscWin */
		$scope.showMscWin = function(id) {
			$scope.mscOneData = $scope.mscData[id];
			$scope.dd($scope.mscOneData);
			$("#mscDetailWin").modal('show');
		};
		$scope.showMscEditWin = function(id) {
			$scope.mscOneData = $scope.mscData[id];
			$scope.dd($scope.mscOneData);
			$("#mscEditWin").modal('show');
			
			
			
		};
		$scope.dd=function(data){
			/*console.log("data="+JSON.stringify(data))*/
			$scope.msc_add_win_html=[];
			for(var i=1;i<=17;i++){
				var content="";
				var a="正常",b="异常";
				var x="",y="",z="";
				if(i==1){
					content="机房门窗地面墙壁等是否正常";
					x=data.a1;
					y=data.b1;
					z=data.c1;
				}
				if(i==2){
					content="机房照明、电源插座是否正常";
					x=data.a2;
					y=data.b2;
					z=data.c2;
				}
				if(i==3){
					content="设备灰尘及滤网是否清洁(传输3500等)";
					x=data.a3;
					y=data.b3;
					z=data.c3;
				}
				if(i==4){
					content="机房是否清洁、是否没有杂物或易燃物品";
					x=data.a4;
					y=data.b4;
					z=data.c4;
				}
				if(i==5){
					content="消防设备是否正常";
					x=data.a5;
					y=data.b5;
					z=data.c5;
				}
				if(i==6){
					content="UPS是否正常（断电测试）";
					x=data.a6;
					y=data.b6;
					z=data.c6;
				}
				if(i==7){
					content="蓄电池是否正常（发电测试）";
					x=data.a7;
					y=data.b7;
					z=data.c7;
				}
				if(i==8){
					content="发电机是否正常（包括汽油发电机、柴油发电机）";
					x=data.a8;
					y=data.b8;
					z=data.c8;
				}
				if(i==9){
					content="空调是否正常工作、滤网是否清洁";
					x=data.a9;
					y=data.b9;
					z=data.c9;
				}
				if(i==10){
					content="环境监控系统是否正常";
					x=data.a10;
					y=data.b10;
					z=data.c10;
				}
				if(i==11){
					content="电源线是否老化";
					x=data.a11;
					y=data.b11;
					z=data.c11;
				}
				if(i==12){
					content="设备标签是否规范完、完整、走线是否正常";
					x=data.a12;
					y=data.b12;
					z=data.c12;
				}
				if(i==13){
					content="服务器是否运行正常";
					x=data.a13;
					y=data.b13;
					z=data.c13;
				}
				if(i==14){
					content="设备接地是否正常";
					x=data.a14;
					y=data.b14;
					z=data.c14;
				}
				if(i==15){
					content="服务器、单板、模块安装是否牢固可靠";
					x=data.a15;
					y=data.b15;
					z=data.c15;
				}
				if(i==16){
					content="楼顶吸盘天线是否加固，接头是否正常";
					x=data.a16;
					y=data.b16;
					z=data.c16;
				}
				if(i==17){
					content="服务器数据及配置是否备份";
					x=data.a17;
					y=data.b17;
					z=data.c17;
					a="已备份";
					b="未备份";
				}
				
				
				var xx={
						id:i,
						a:a,
						b:b,
						x:x,
						y:y,
						z:z,
						content:content
				}
				$scope.msc_add_win_html.push(xx);
			}
		}
		/* 查询数据 */
		$scope.mbs_search = function(page) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mbsinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
				xh.mbs_pagging(page, parseInt($scope.mbsTotals),$scope,pageSize);
			});
		};
		$scope.sbs_search = function(page) {
			var pageSize = $("#page-limit-sbs").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/sbsinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.sbsData = response.items;
				$scope.sbsTotals = response.totals;
				xh.sbs_pagging(page, parseInt($scope.sbsTotals),$scope,pageSize);
			});
		};
		$scope.net_search = function(page) {
			var pageSize = $("#page-limit-net").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/netinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.netData = response.items;
				$scope.netTotals = response.totals;
				xh.net_pagging(page, parseInt($scope.netTotals),$scope,pageSize);
			});
		};
		$scope.dispatch_search = function(page) {
			var pageSize = $("#page-limit-dispatch").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/dispatchinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.dispatchData = response.items;
				$scope.dispatchTotals = response.totals;
				xh.dispatch_pagging(page, parseInt($scope.dispatchTotals),$scope,pageSize);
			});
		};
		$scope.msc_search = function(page) {
			var pageSize = $("#page-limit-msc").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mscinfo?start=0&limit="+limit).
			success(function(response){
				xh.maskHide();
				$scope.mscData = response.items;
				$scope.mscTotals = response.totals;
				xh.msc_pagging(page, parseInt($scope.mscTotals),$scope,pageSize);
			});
		};
		//分页点击
		$scope.mbs_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mbsinfo?start=0&limit="+limit).
			success(function(response){
				$scope.mbs_start = (page - 1) * pageSize + 1;
				$scope.mbs_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.mbs_lastIndex = totals;
					} else {
						$scope.mbs_start = 0;
						$scope.mbs_lastIndex = 0;
					}
				}
				$scope.mbsData = response.items;
				$scope.mbsTotals = response.totals;
			});
			
		};
		$scope.sbs_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-sbs").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/sbsinfo?start=0&limit="+limit).
			success(function(response){
				$scope.sbs_start = (page - 1) * pageSize + 1;
				$scope.sbs_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.sbs_lastIndex = totals;
					} else {
						$scope.sbs_start = 0;
						$scope.sbs_lastIndex = 0;
					}
				}
				$scope.sbsData = response.items;
				$scope.sbsTotals = response.totals;
			});
			
		};
		$scope.net_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-net").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/netinfo?start=0&limit="+limit).
			success(function(response){
				$scope.net_start = (page - 1) * pageSize + 1;
				$scope.net_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.net_lastIndex = totals;
					} else {
						$scope.net_start = 0;
						$scope.net_lastIndex = 0;
					}
				}
				$scope.netData = response.items;
				$scope.netTotals = response.totals;
			});
			
		};
		$scope.dispatch_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-dispatch").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/dispatchinfo?start=0&limit="+limit).
			success(function(response){
				$scope.dispatch_start = (page - 1) * pageSize + 1;
				$scope.dispatch_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.dispatch_lastIndex = totals;
					} else {
						$scope.dispatch_start = 0;
						$scope.dispatch_lastIndex = 0;
					}
				}
				$scope.dispatchData = response.items;
				$scope.dispatchTotals = response.totals;
			});
			
		};
		$scope.msc_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-msc").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../app/mscinfo?start=0&limit="+limit).
			success(function(response){
				$scope.msc_start = (page - 1) * pageSize + 1;
				$scope.msc_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.msc_lastIndex = totals;
					} else {
						$scope.msc_start = 0;
						$scope.msc_lastIndex = 0;
					}
				}
				$scope.mscData = response.items;
				$scope.mscTotals = response.totals;
			});
			
		};
		$scope.msc_add_win_text();
	});
};
$(document).ready(function(){ 
	var $scope = angular.element(appElement).scope();
	$scope.mbs();
	}); 

// 刷新数据
/*xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};*/
/* 数据分页 */
xh.mbs_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.mbs_start = start;
	$scope.mbs_lastIndex = end;
	$scope.mbsTotals = totals;
	if (totals > 0) {
		$(".mbs-page-paging").html('<ul class="pagination mbs-pagination"></ul>');
		$('.mbs-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.mbs_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
xh.sbs_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.sbs_start = start;
	$scope.sbs_lastIndex = end;
	$scope.sbsTotals = totals;
	if (totals > 0) {
		$(".sbs-page-paging").html('<ul class="pagination sbs-pagination"></ul>');
		$('.sbs-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.sbs_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
xh.net_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.net_start = start;
	$scope.net_lastIndex = end;
	$scope.netTotals = totals;
	if (totals > 0) {
		$(".net-page-paging").html('<ul class="pagination net-pagination"></ul>');
		$('.net-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.net_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
//添加交换中心巡检记录
xh.add_msc=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/msc_add',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#addMscForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.msc_refresh();
				$("#mscAddWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
//添加交换中心巡检记录
xh.edit_msc=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/msc_edit',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#editMscForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.msc_refresh();
				$("#mscEditWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
xh.add_mbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/mbs_add',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#addMbsForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.mbs_refresh();
				$("#addMbsWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
xh.edit_mbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/mbs_edit',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#editMbsForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.mbs_refresh();
				$("#editMbsWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
xh.add_sbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/sbs_add',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#addSbsForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.sbs_refresh();
				$("#addSbsWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
xh.edit_sbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/sbs_edit',
		type : 'post',
		dataType : "json",
		data : {
			data:xh.serializeJson($("#editSbsForm").serializeArray())
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				toastr.success(data.message, '提示');
				$scope.mbs_refresh();
				$("#editSbsWin").modal('hide');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("系统错误", '提示');
		}
	});
};
//移动基站巡检表
xh.excelToMbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/excel_mbs',
		type : 'post',
		dataType : "json",
		data : {
			excelData:JSON.stringify($scope.mbsOneData)
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			//$("#btn-mbs").button('reset');
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
//导出自建基站巡检表
xh.excelToSbs=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/excel_sbs',
		type : 'post',
		dataType : "json",
		data : {
			excelData:JSON.stringify($scope.sbsOneData)
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
//导出网管巡检
xh.excelToNet=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/excel_net',
		type : 'post',
		dataType : "json",
		data : {
			excelData:JSON.stringify($scope.netOneData)
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
//导出调度台巡检
xh.excelToDispatch=function(){
	var $scope = angular.element(appElement).scope();
	xh.maskShow();
	//$("#btn-mbs").button('loading')
	$.ajax({
		url : '../../app/excel_dispatch',
		type : 'post',
		dataType : "json",
		data : {
			excelData:JSON.stringify($scope.dispatchOneData)
		},
		
		async : false,
		success : function(data) {
			xh.maskHide();
			//$("#btn-mbs").button('reset');
			if (data.success) {
				
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
xh.dispatch_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.dispatch_start = start;
	$scope.dispatch_lastIndex = end;
	$scope.dispatchTotals = totals;
	if (totals > 0) {
		$(".dispatch-page-paging").html('<ul class="pagination dispatch-pagination"></ul>');
		$('.dispatch-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.dispatch_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
xh.msc_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.msc_start = start;
	$scope.msc_lastIndex = end;
	$scope.mscTotals = totals;
	if (totals > 0) {
		$(".msc-page-paging").html('<ul class="pagination msc-pagination"></ul>');
		$('.msc-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.msc_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
var WebPrinter; //声明为全局变量 
xh.printExists=function() {
	
	try{ 
	    var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
		if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION); 
	 }catch(err){ 
		//alert("Error:本机未安装或需要升级!"); 
	 } 
	
    
};
xh.print_mbs=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("移动基站巡检");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print_mbs").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_sbs=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("自建基站巡检");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print_sbs").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_net=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("网管巡检");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print_net").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_dispatch=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("网管巡检");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("1%", "2%", "96%", "96%", document.getElementById("print_dispatch").innerHTML);
	 LODOP.PREVIEW();  	
};

