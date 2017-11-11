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
	
	app.controller("userstatus", function($scope, $http) {
		xh.maskShow();
		$scope.count = "10";//每页数据显示默认值
		var type=$("select[name='type']").val();
		var zone=$("select[name='zone']").val();
		var link=$("select[name='link']").val();
		var status=$("select[name='status']").val();
		$http.get("../../bs/map/area").success(
				function(response) {
					$scope.zoneData = response.items;
				});
		/*获取信息*/
		$http.get("../../bs/allBsInfo?type="+type+"&zone="+zone+"&link="+link+"&status="+status).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = $scope.data.length;
		});
		
		
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
		};
		/* 查询数据 */
		$scope.search = function(page) {
			var $scope = angular.element(appElement).scope();
			var type=$("select[name='type']").val();
			var zone=$("select[name='zone']").val();
			var link=$("select[name='link']").val();
			var status=$("select[name='status']").val();
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
			$http.get("../../bs/allBsInfo?type="+type+"&zone="+zone+"&link="+link+"&status="+status).
			success(function(response){
				xh.maskHide();
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
		
		
	});
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};
xh.excelToBsstatus=function(){
	window.location.href="../../bsstatus/ExcelToStationStatus";
};

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