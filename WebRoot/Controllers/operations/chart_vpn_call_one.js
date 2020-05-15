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
	
	app.filter('timeFormat', function() { //可以注入依赖
	    return function(text) {
	    	
	    	var time=xh.getTime(text);
	        return time;
	    };
	});

	app.controller("user", function($scope, $http) {
		
		$scope.count = "15";//每页数据显示默认值
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
			$http.get("../../call/chart_vpn_group_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.vpnGrupCalldata = response.items;
				$scope.vpnGrupCalltotals = response.totals;
				
			});
			
		}
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
		
		
		
		
       
		
		/* 刷新数据 */
		$scope.refresh = function() {
			
			$('#xh-tabs a:first').tab('show');
			$scope.chart_msc_call();
		
		};
		$scope.chart_msc_call();
		
		
		
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
function funccc(){

	$dp.$('starttime').value=$dp.cal.getP('y')+"年第"+$dp.cal.getP('W','WW')+"周";
	}

