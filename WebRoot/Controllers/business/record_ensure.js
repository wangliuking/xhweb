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
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);

	app.controller("xhcontroller", function($scope, $http,$location) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		var pageSize = $("#page-limit").val();
		$scope.time = $location.search().month;
		$scope.page=1;
		$scope.start_time=$scope.time+"-01";
		$scope.end_time=xh.lastDay($scope.time.split("-")[0],$scope.time.split("-")[1]);
		console.log($scope.start_time)
		console.log($scope.end_time)

		//获取派单列表
		$http.get("../../userneed/communication_list?endtime="+$scope.end_time+"&starttime="+$scope.start_time+"&start=0&limit=" + $scope.count).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					xh.pagging(1, parseInt($scope.totals), $scope);
				});
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
		};
	
		/*显示详细信息*/
		$scope.show_detail = function(id) {
			$("#detailWin").modal('show');
			$scope.editData = $scope.data[id];
			
		};
		
		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var starttime=$scope.start_time;
			var endtime=$scope.end_time;
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../userneed/communication_list?start="+start+"&starttime="+starttime+"" +
					"&endtime="+endtime+"&limit=" + pageSize).success(function(response) {
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var starttime=$scope.start_time;
			var endtime=$scope.end_time;
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../userneed/communication_list?start="+start+"&starttime="+starttime+"" +
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
				$scope.page=page
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
xh.lastDay=function(year,month) {
	var lastDay= new Date(year,month,0);
	var year = lastDay.getFullYear();
	var month = lastDay.getMonth() + 1;
	month = month < 10 ? '0'+ month : month;
	var day = lastDay.getDate();
	day = day < 10 ? '0'+day : day;
	return year+"-"+month+"-"+day;
};

