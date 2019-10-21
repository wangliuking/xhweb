if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
var cx=0;
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
	var app = angular.module("app", [])
	

	app.filter('qualitystatus', function() { // 可以注入依赖
		return function(text) {
			if (text == 0) {
				return "未签收";
			} else if (text == 1) {
				return "签收";
			}
		};
	});

	app.controller("xhcontroller", function($scope, $http) {
		$scope.count = "20";// 每页数据显示默认值
		var pageSize = $("#page-limit").val();
		$scope.data=null;
		$scope.page=1;
		$scope.time=xh.dateNowFormat("month");
		$scope.time_day=xh.dateNowFormat("day");
		$scope.gps_total=0;
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
		$scope.showBsModal=function(){
			$http.get("../../bs/bsInfolimit").
			success(function(response){
				xh.maskHide();
				//$scope.bs_search_data = response.items;
				var data=response.items;
				$scope.bs_search_data=[];
				for(var i=0;i<data.length;i++){
					var record=data[i];
					var bsId=record.bsId;
					var a=xh.hasValue(bsId);
					if(a){
						record.isgreen=true;
					}else{
						record.isgreen=false;
					}
					$scope.bs_search_data.push(record);
				}
			});
			
			
			/*$("#aside-right").fadeToggle("fast");*/
			$("#bs-win").modal('show');
		}
		
		$scope.change_bs=function(bsId){
			$("#id-"+bsId).toggleClass("select-div");
			xh.writeBs(bsId);
		}
		
	
		

		/* 查询数据 */
		$scope.search = function(page,self) {
			var pageSize = $("#page-limit").val();
			var starttime=$("#start_time").val();
			var endtime=$("#end_time").val();
			var bsId=getLocalData("bs_search");
			if(starttime=="" || endtime==""){
				alert("时间区间不能为空");
				return;
			}
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			if(self){
				xh.maskShow();
			}
			$http.get("../../gps/gps_count?bsId="+bsId+"&start="+start+"&starttime="+starttime+"" +
					"&endtime="+endtime+"&limit=" + pageSize).success(function(response) {
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page;
				$scope.gps_total=0;
				for(var i=0;i<$scope.data.length;i++){
					$scope.gps_total+=$scope.data[i].total;
				}
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var starttime=$("#start_time").val();
			var endtime=$("#end_time").val();
			var bsId=getLocalData("bs_search");
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../gps/gps_count?bsId="+bsId+"&start="+start+"&starttime="+starttime+"" +
					"&endtime="+endtime+"&limit=" + pageSize).success(function(response) {
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
				$scope.gps_total=0;
				for(var i=0;i<$scope.data.length;i++){
					$scope.gps_total+=$scope.data[i].total;
				}
				$scope.page=page
			});

		};
		
		setInterval(function(){
			var starttime=$("#start_time").val();
			var endtime=$("#end_time").val();
			if(starttime!="" && endtime!=""){
				$scope.search(1,false);
			}
		}, 20000)
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
xh.writeBs=function(v){
	var tt=getLocalData("bs_search");
	var rs=[];

	if(tt!=null && tt!=''){
		var a=tt.split(",");
		var tag=false;
		for(var i=0;i<a.length;i++){
			if(v==a[i]){
				tag=true;
			}else{
				rs.push(a[i]);
			}
		}
		if(!tag){rs.push(v)}
		
	}else{
		rs.push(v)
	}
	setLocalData("bs_search",rs.join(","))
	console.log("后:"+getLocalData("bs_search"));
	
}
xh.hasValue=function(v){
	var tt=getLocalData("bs_search");
	var rs=[];
	var tag=false;
	if(tt!=null && tt!=''){
		var a=tt.split(",");		
		for(var i=0;i<a.length;i++){
			if(v==a[i]){
				tag=true;
			}
		}
		
	}
	return tag;
}
