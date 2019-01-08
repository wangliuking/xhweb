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
		$scope.type="3";
		$scope.time=xh.getPreMonth();
		$scope.chart_msc_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_msc_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.msc_data = response.items;
				$scope.msc_totals = response.totals;
				
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
		$scope.chart_bs_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.bs_call_data = response.items;
				$scope.bs_totals = response.totals;
				$scope.a=0;
				$scope.b=0;
				$scope.c=0;
				$scope.d=0;
				$scope.e=0;
				$scope.f=0;
				$scope.g=0;
				$scope.h=0;
				
				for(i=0,len=$scope.bs_call_data.length;i<len;i++){
					$scope.a+=parseInt($scope.bs_call_data[i].totalActiveCalls);
					$scope.b+=parseInt($scope.bs_call_data[i].totalActiveCallDuration);
					$scope.c+=parseInt($scope.bs_call_data[i].averageCallDuration);
					$scope.d+=parseInt($scope.bs_call_data[i].PTTCount);
					$scope.e+=parseInt($scope.bs_call_data[i].queueCount);
					$scope.f+=parseInt($scope.bs_call_data[i].queueDuration);
					
					if(parseInt(response.items[i].maxUserRegCount)>$scope.g){
						$scope.g=parseInt(response.items[i].maxUserRegCount);
					}
					if(parseInt(response.items[i].maxGroupRegCount)>$scope.h){
						$scope.h=parseInt(response.items[i].maxGroupRegCount);
					}
					
					
					
				}
				
			});
			
		}
		$scope.chart_vpn_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_vpn_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.vpn_call_data = response.items;
				$scope.vpn_totals = response.totals;
				$scope.a=0;
				$scope.b=0;
				$scope.c=0;
				$scope.d=0;
				$scope.e=0;
				$scope.f=0;
				$scope.g=0;
				$scope.h=0;
				for(i=0,len=response.items.length;i<len;i++){
					$scope.a+=parseInt(response.items[i].totalActiveCalls);
					$scope.b+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.items[i].averageCallDuration);
					$scope.d+=parseInt(response.items[i].dexTotalCalls);
					$scope.e+=parseInt(response.items[i].totalFailedCalls);
					$scope.f+=parseInt(response.items[i].failedPercentage);
					$scope.g+=parseInt(response.items[i].noEffectCalls);
				}
			});
		}
		$scope.chart_bs_level_area_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call//chart_bs_level_area_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.area_call_data = response.areaitems;
				$scope.level_call_data = response.levelitems;
				$scope.area_totals = response.totals;
				
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
				
				$scope.a1=0;
				$scope.b1=0;
				$scope.c1=0;
				$scope.d1=0;
				$scope.e1=0;
				$scope.f1=0;
				$scope.g1=0;
				$scope.h1=0;
				$scope.x1=0;
				$scope.y1=0;			
				for(i=0,len=response.areaitems.length;i<len;i++){
					$scope.x+=parseInt(response.areaitems[i].bsTotals);
					$scope.a+=parseInt(response.areaitems[i].totalActiveCalls);
					$scope.b+=parseInt(response.areaitems[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.areaitems[i].averageCallDuration);
					$scope.d+=parseInt(response.areaitems[i].PTTCount);
					$scope.e+=parseInt(response.areaitems[i].queueCount);
					$scope.f+=parseInt(response.areaitems[i].queueDuration);					
					if(parseInt(response.areaitems[i].maxUserRegCount)>$scope.g){
						$scope.g=parseInt(response.areaitems[i].maxUserRegCount);
					}
					if(parseInt(response.areaitems[i].maxGroupRegCount)>$scope.h){
						$scope.h=parseInt(response.areaitems[i].maxGroupRegCount);
					}										
					$scope.y+=parseInt(response.areaitems[i].percent);
				}
				for(i=0,len=response.levelitems.length;i<len;i++){
					$scope.x1+=parseInt(response.levelitems[i].bsTotals);
					$scope.a1+=parseInt(response.levelitems[i].totalActiveCalls);
					$scope.b1+=parseInt(response.levelitems[i].totalActiveCallDuration);
					$scope.c1+=parseInt(response.levelitems[i].averageCallDuration);
					$scope.d1+=parseInt(response.levelitems[i].PTTCount);
					$scope.e1+=parseInt(response.levelitems[i].queueCount);
					$scope.f1+=parseInt(response.levelitems[i].queueDuration);					
					if(parseInt(response.levelitems[i].maxUserRegCount)>$scope.g1){
						$scope.g1=parseInt(response.levelitems[i].maxUserRegCount);
					}
					if(parseInt(response.levelitems[i].maxGroupRegCount)>$scope.h1){
						$scope.h1=parseInt(response.levelitems[i].maxGroupRegCount);
					}										
					$scope.y1+=parseInt(response.levelitems[i].percent);
				}
				
				
				
			});
		}
		$scope.chart_bs_zone_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_zone_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.zone_call_data = response.items;
				$scope.zone_totals = response.totals;
				$scope.a=0;
				$scope.b=0;
				$scope.c=0;
				$scope.d=0;
				$scope.e=0;
				$scope.f=0;
				$scope.g=0;
				$scope.h=0;
				$scope.x=0;
				for(i=0,len=response.items.length;i<len;i++){
					$scope.x+=parseInt(response.items[i].bsTotals);
					$scope.a+=parseInt(response.items[i].totalActiveCalls);
					$scope.b+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.items[i].averageCallDuration);
					$scope.d+=parseInt(response.items[i].PTTCount);
					$scope.e+=parseInt(response.items[i].queueCount);
					$scope.f+=parseInt(response.items[i].queueDuration);
					
					if(parseInt(response.items[i].maxUserRegCount)>$scope.g){
						$scope.g=parseInt(response.items[i].maxUserRegCount);
					}
					if(parseInt(response.items[i].maxGroupRegCount)>$scope.h){
						$scope.h=parseInt(response.items[i].maxGroupRegCount);
					}
				}
			});
		}
		$scope.chart_bs_zone_top10_call=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_zone_top10_call?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.zone_top10_call_data = response.items;
				$scope.zone_top10_totals = response.totals;
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
				for(i=0,len=response.items.length;i<len;i++){
					$scope.x+=parseInt(response.items[i].bsTotals);
					$scope.a+=parseInt(response.items[i].totalActiveCalls);
					$scope.b+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.items[i].averageCallDuration);
					$scope.d+=parseInt(response.items[i].PTTCount);
					$scope.e+=parseInt(response.items[i].queueCount);
					$scope.f+=parseInt(response.items[i].queueDuration);
					
					if(parseInt(response.items[i].maxUserRegCount)>$scope.g){
						$scope.g=parseInt(response.items[i].maxUserRegCount);
					}
					if(parseInt(response.items[i].maxGroupRegCount)>$scope.h){
						$scope.h=parseInt(response.items[i].maxGroupRegCount);
					}
					
					
					$scope.y+=parseInt(response.items[i].percent);
				}
			});
		}
		
		
		$scope.chart_bs_call_top10=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_call_top10?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.bs_top10_call_data = response.items;
				$scope.bs_top10_totals = response.totals;
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
			
				for(i=0,len=response.items.length;i<len;i++){
					$scope.x+=parseInt(response.items[i].bsTotals);
					$scope.a+=parseInt(response.items[i].totalActiveCalls);
					$scope.b+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.items[i].averageCallDuration);
					$scope.d+=parseInt(response.items[i].PTTCount);
					$scope.e+=parseInt(response.items[i].queueCount);
					$scope.f+=parseInt(response.items[i].queueDuration);
					
					if(parseInt(response.items[i].maxUserRegCount)>$scope.g){
						$scope.g=parseInt(response.items[i].maxUserRegCount);
					}
					if(parseInt(response.items[i].maxGroupRegCount)>$scope.h){
						$scope.h=parseInt(response.items[i].maxGroupRegCount);
					}
					
					
					$scope.y+=parseInt(response.items[i].percent);
				}
			});
		}
		$scope.chart_bs_userreg_top10=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_userreg_top10?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.bs_userreg_top10_data = response.items;
				$scope.bs_userreg_top10_totals = response.totals;
			});
		}
		$scope.chart_bs_queue_top10=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_bs_queue_top10?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.bs_queue_top10_data = response.items;
				$scope.bs_queue_top10_totals = response.totals;
				$scope.a1=0;
				$scope.b1=0;
				$scope.c1=0;
				$scope.d1=0;
				$scope.e1=0;
				$scope.f1=0;
				$scope.g1=0;
				$scope.h1=0;
				$scope.x1=0;
				$scope.y1=0;
				for(i=0,len=response.items.length;i<len;i++){
					$scope.a1+=parseInt(response.items[i].totalActiveCalls);
					$scope.b1+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c1+=parseInt(response.items[i].averageCallDuration);
					$scope.d1+=parseInt(response.items[i].PTTCount);
					$scope.e1+=parseInt(response.items[i].queueCount);
					$scope.f1+=parseInt(response.items[i].queueDuration);
					
					if(parseInt(response.items[i].maxUserRegCount)>$scope.g1){
						$scope.g1=parseInt(response.items[i].maxUserRegCount);
					}
					if(parseInt(response.items[i].maxGroupRegCount)>$scope.h1){
						$scope.h1=parseInt(response.items[i].maxGroupRegCount);
					}				
					$scope.y1+=parseInt(response.items[i].percent);
				}
			});
		}
        $scope.call_top10=function(){
        	$scope.chart_bs_call_top10();
        	$scope.chart_bs_userreg_top10();
        	$scope.chart_bs_queue_top10();
		}
    	$scope.chart_vpn_call_top10=function(){
			xh.maskShow();
			var time=$("#starttime").val()==""?xh.getPreMonth():$("#starttime").val();
			var endtime=$("#endtime").val();
			var type=$scope.type;
			$http.get("../../call/chart_vpn_call_top10?time="+time+"&type="+type+"&endtime="+endtime).
			success(function(response){
				xh.maskHide();
				$scope.vpn_call_top10_data = response.items;
				$scope.vpn_call_top10_totals = response.totals;
				$scope.a=0;
				$scope.b=0;
				$scope.c=0;
				$scope.d=0;
				$scope.e=0;
				$scope.f=0;
				$scope.g=0;
				$scope.h=0;
				for(i=0,len=response.items.length;i<len;i++){
					$scope.a+=parseInt(response.items[i].totalActiveCalls);
					$scope.b+=parseInt(response.items[i].totalActiveCallDuration);
					$scope.c+=parseInt(response.items[i].averageCallDuration);
					$scope.d+=parseInt(response.items[i].dexTotalCalls);
					$scope.e+=parseInt(response.items[i].totalFailedCalls);
					$scope.f+=parseInt(response.items[i].failedPercentage);
					$scope.g+=parseInt(response.items[i].noEffectCalls);
					$scope.h+=parseInt(response.items[i].percent);
				}
			});
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
		url : '../../call/excel_call',
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

