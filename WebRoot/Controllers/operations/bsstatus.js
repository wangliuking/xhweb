/**
 * 终端状态
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
var background="#fff";
var frist = 0;
var appElement = document.querySelector('[ng-controller=userstatus]');
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
	/*app.filter('upp', function() { //可以注入依赖
	    return function(text) {
	        return text+"$$";
	    };
	});*/
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);

	
	app.controller("userstatus", function($scope, $http, $location) {
		xh.maskShow();
		$scope.count = "10";//每页数据显示默认值
		$scope.zone = $location.search().zone;
		$scope.nowDate=xh.getOneDay();
		if($scope.zone==null){
			$scope.zone="全部"
		}
		$scope.bsIds="";
		var type=$("select[name='type']").val();
		var zone=$scope.zone==null?"全部":$scope.zone;
		var link=$("select[name='link']").val();
		var status=$("select[name='status']").val();
		var usergroup=$("input[name='usergroup']").val();
		$http.get("../../bs/map/area").success(
				function(response) {
					$scope.zoneData = response.items;
				});
		/*获取信息*/
	
		
		$http.get("../../bs/allBsInfo?type="+type+"&zone="+zone+"&link="+link+"&status="+status+"&usergroup="+usergroup+"&bsIds="+$scope.bsIds).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = $scope.data.length;
			$scope.bs_search_data = response.items;
			$scope.bs_search_totals = $scope.data.length;
		});
		
		
		
		$scope.bsView=function(bsId,bsName,period){
			
			window.location.href = "bsstatus-view.html?bsId=" + bsId+"&period="+period+"&bsName="+bsName;
		};
		$scope.bsHover=function(link,status,bsId,name,groupSum,userSum){
			//$("#aside-right-bottom").fadeToggle("fast");
			
			
			if(status==1 &&link==0){
				$(".bs-name").html(bsId+"-"+name);
				$(".bs-group").html(groupSum);
				$(".bs-user").html(userSum);
			}else{
				$(".bs-name").html(bsId+"-"+name);
				$(".bs-group").html("--");
				$(".bs-user").html("--");
			}
		}
		$scope.showGroupUser=function(index){
			var dd=$scope.data[index];
			
			var html={
					  type: 2,
					  title:dd.bsId+"-"+dd.name+":注册组/注册终端",
					  area: ['500px', '400px'],
					  shade: 0,
					  maxmin:true,
					  
					  /*skin: 'layui-layer-rim', //加上边框*/					  
					  content: ["../../Views/operations/bsstatus-group-user-box.html?bsId="+dd.bsId, 'no']
					};
			layer.open(html);
		}
		
		$scope.showBsModal=function(){
			$("#aside-right").fadeToggle("fast");
		}
	
		
		$scope.bssearch=function(){
			var checkbox=$("#aside-right").find("[type='checkbox']");
			var bsIds=[];
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					bsIds.push(checkbox[i].value)
				}
			}
			if(bsIds.length>0){
				console.log("bsIds===>"+bsIds.join(","));
				$scope.bsIds=bsIds.join(",");
				$scope.search(1);
			}else{
				$scope.bsIds="";
				$scope.search(1);
			}
			
		}
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		/* 查询数据 */
		$scope.search = function(page) {
			var type=$("select[name='type']").val();
			var zone=$("select[name='zone']").val();
			var zone=$scope.zone==null?$("select[name='zone']").val():$scope.zone;
			var link=$("select[name='link']").val();
			var status=$("select[name='status']").val();
			var pageSize = $("#page-limit").val();
			var usergroup=$("input[name='usergroup']").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			/*xh.maskShow();*/
			$http.get("../../bs/allBsInfo?type="+type+"&zone="+zone+"&link="+link+"&status="+status+"&usergroup="+usergroup+"&bsIds="+$scope.bsIds).
			success(function(response){
				/*xh.maskHide();*/
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
		};
		//分页点击
		$scope.pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../radio/status/oneBsRadio?bsId="+$scope.bsId+"&start="+start+"&limit="+pageSize ).
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
				$scope.radioData = response.items;
				$scope.radioTotals = response.totals;
			});
			
		};
		/*xh.tree();*/
		$("");
		
		
		setInterval(function(){
			$scope.search(1);
			}, 10000);
		
		
	});
};


// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
//基站运行记录
xh.excelToBsRun=function(){
	xh.maskShow();
	$("#btn-run").button('loading');
	$.ajax({
		url : '../../bsstatus/ExcelToStationStatus',
		type : 'get',
		dataType : "json",
		data : {
		},
		async : false,
		success : function(data) {
		
			$("#btn-run").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-run").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});
	
};
//基站故障记录
xh.excelToBsAlarm=function(){
	xh.maskShow();
	$("#btn-write").button('loading');
	var startTime=$("input[name='startTime']").val();
	var endTime=$("input[name='endTime']").val();
	if(startTime=="" || endTime==""){
		toastr.error("时间范围不能为空", '提示');
		$("#btn-write").button('reset');
		xh.maskHide();
		return;
	}
	
	$.ajax({
		url : '../../bsstatus/ExcelToBsAlarm',
		type : 'get',
		dataType : "json",
		data : {
			startTime:startTime,
			endTime:endTime
		},
		async : false,
		success : function(data) {
			xh.maskHide();
			$("#btn-write").button('reset');
			if (data.success) {
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
				
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-write").button('reset');
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};

//基站状态检查表
xh.excelToBsstatus=function(){
	xh.maskShow();
	$("#btn-status").button('loading')
	$.ajax({
		url : '../../bsstatus/ExcelToBsStatus',
		type : 'get',
		dataType : "json",
		data : {
		},
		async : false,
		success : function(data) {
			xh.maskHide();
			$("#btn-status").button('reset');
			if (data.success) {
				
				window.location.href="../../bsstatus/downExcel?filePath="+data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-status").button('reset');
			xh.maskHide();
			toastr.error("导出失败", '提示');
		}
	});
};
xh.getOneDay=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+"23:59:59";   
    return  strYesterday;
}

/* 数据分页 */
xh.pagging = function(currentPage,totals, $scope) {
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
			visiblePages : 3,
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