/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
var appElement = document.querySelector('[ng-controller=user]');
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
	
	app.filter('timeFormat', function() { //可以注入依赖
	    return function(text) {
	    	
	    	var time=xh.getTime(text);
	        return time;
	    };
	});

	app.controller("user", function($scope, $http) {
		
		$scope.count = "15";//每页数据显示默认值
		$scope.page=1;
		$scope.securityMenu=true; //菜单变色
		$scope.starttime=xh.getPreMonth();
		$scope.a=0;
		$scope.b=0;
		$scope.c=0;
		$scope.d=0;
		$scope.e=0;
		$scope.f=0;
		$scope.g=0;
		$scope.h=0;
		$scope.x=0;
		$scope.y=0;
		var i=0,len=0;
		$scope.type="2";
		$scope.time=xh.getPreMonth();
		$scope.chart_msc_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_vpn_group_call?time="+time+"&type="+type+"&endtime="+endtime+"&start=0&limit=" + $scope.count).
			success(function(response){
				xh.maskHide();
				$scope.vpnGrupCalldata = response.items;
				$scope.vpnGrupCalltotals = response.totals;
				//xh.pagging(1, parseInt(	$scope.vpnGrupCalltotals), $scope);
				
			});
			
		}
		$scope.refresh = function() {
			$('#xh-tabs a:first').tab('show');
			$scope.search($scope.page);
		};
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var pageSize = $scope.count;
			var time=$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
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
			$http.get("../../call/chart_vpn_group_call?time="+time+"&type="+type+"&endtime="+endtime+"&start="+start+"&limit=" + pageSize)
					.success(function(response) {
						xh.maskHide();
						$scope.vpnGrupCalldata = response.items;
						$scope.vpnGrupCalltotals = response.totals;
						xh.pagging(page, parseInt($scope.vpnGrupCalltotals), $scope);
					});
		};
		$scope.chart_vpn_zone_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_vpn_call?vpnid=2&time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.vpnZoneCalldata = response.items;
				$scope.vpnZoneCalltotals = response.totals;
				
			});
			
		}
		$scope.showDate=function(){
			var type=$("select[name='type']").val();
			$("input[name='starttime']").val("");
			$("input[name='endtime']").val("");
			var a="",b="",c="";
			var x="",y="",z="";
			if(type==1){
				a="选择开始日期";
				b="WdatePicker({dateFmt:'yyyy-MM-dd'})";
				c="开始日期";
				x="选择结束日期";
				y="WdatePicker({dateFmt:'yyyy-MM-dd'})";
				z="结束日期"
					
				
			}else if(type==2){
				a="选择周";
				b="WdatePicker({isShowWeek:true,onpicked:funccc,errDealMode:3,firstDayOfWeek:1})";
				c="周"
				
			}else if(type==3){
				a="选择月份";
				b="WdatePicker({dateFmt:'yyyy-MM'})"
				c="月";
			}
			else if(type==3){
				a="选择年";
				b="WdatePicker({dateFmt:'yyyy'})";
				c="年"
				
			}else if(type==4){
				a="选择开始时间";
				b="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'})";
				c="开始时间";
				x="选择结束时间";
				y="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'})";
				z="结束时间"
			}else{
				a="选择年份";
				b="WdatePicker({dateFmt:'yyyy'})";
				c="年";
			}
			$("label[for='starttime']").html(a);
			$("input[name='starttime']").attr("onfocus",b);
			$("input[name='starttime']").attr("placeholder",c);
			if(type==1 || type==4){
				$("label[for='endtime']").html(x);
				$("input[name='endtime']").attr("onfocus",y);
				$("input[name='endtime']").attr("placeholder",z);
			}
		}
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $scope.count;
			var time=$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$scope.page=page;

			xh.maskShow();
			$http.get("../../call/chart_vpn_group_call?time="+time+"&type="+type+"&endtime="+endtime+"&start="+start+"&limit=" + pageSize).
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
		
				$scope.vpnGrupCalldata = response.items;
				$scope.vpnGrupCalltotals = response.totals;
				
				
			});

		};
		var tag=0;
		setInterval(function(){
			if(tag==0){
				$scope.refresh();
			}
			tag=1;
		
		}, 1000)
		
		
		
	});
};

xh.excel=function(){
	var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
	var type=$("select[name='type']").val();
	var endtime=$("#endtime").val();
	xh.maskShow("正在分析数据，请耐心等待");
	$("#btn-excel").button('loading');
	$.ajax({
		url : '../../call/excel_vpn_group',
		type : 'get',
		dataType : "json",
		data : {
			time:time,
			type:type,
			endtime:endtime
		},
		async : true,
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
/* 数据分页 */
xh.pagging = function(currentPage, totals, $scope) {
	var pageSize = $scope.count;
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
function funccc(){

	$dp.$('starttime').value=$dp.cal.getP('y')+"年第"+$dp.cal.getP('W','WW')+"周";
	}

