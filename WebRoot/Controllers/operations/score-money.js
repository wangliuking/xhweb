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
		$scope.count = "10";//每页数据显示默认值
		$scope.starttime=xh.getNowMonth();
		
		/*获取扣分*/
		$scope.score=function(){	
			var pageSize = $("#page-limit-score").val();
			var time=$("input[name='time']").val();
			if(time==""){
				time=$scope.starttime
			}
			$http.get("../../check/search_score_detail?start=0&limit="+pageSize+"&time="+time).
			success(function(response){
				$scope.scoreData = response.items;
				$scope.scoreTotals = response.totals;
				xh.score_pagging(1, parseInt($scope.scoreTotals),$scope,pageSize);
			});
		}
		/*获取扣款*/
		$scope.money=function(){	
			var pageSize = $("#page-limit-money").val();
			var time=$("input[name='time']").val();
			if(time==""){
				time=$scope.starttime
			}
			$http.get("../../check/search_money_detail?start=0&limit="+pageSize+"&time="+time).
			success(function(response){
				$scope.moneyData = response.items;
				$scope.moneyTotals = response.totals;
				$scope.money=response.money;
				xh.money_pagging(1, parseInt($scope.moneyTotals),$scope,pageSize);
			});
		}
		
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.score_refresh();
			$scope.money_refresh();
		};
		$scope.score_refresh = function() {
			$scope.score_search(1);
		};
		$scope.money_refresh = function() {
			$scope.money_search(1);
		};
		
		
		/* 查询数据 */
		$scope.score_search = function(page) {
			var pageSize = $("#page-limit-score").val();
			var time=$("input[name='time']").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../check/search_score_detail?start=0&limit="+limit+"&time="+time).
			success(function(response){
				xh.maskHide();
				$scope.scoreData = response.items;
				$scope.scoreTotals = response.totals;
				xh.score_pagging(page, parseInt($scope.scoreTotals),$scope,pageSize);
			});
		};
		$scope.money_search = function(page) {
			var pageSize = $("#page-limit-money").val();
			var time=$("input[name='time']").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../check/search_money_detail?start=0&limit="+limit+"&time="+time).
			success(function(response){
				xh.maskHide();
				$scope.moneyData = response.items;
				$scope.moneyTotals = response.totals;
				$scope.money=response.money;
				xh.money_pagging(page, parseInt($scope.moneyTotals),$scope,pageSize);
			});
		};
		
		//分页点击
		$scope.score_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-score").val();
			var time=$("input[name='time']").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../check/search_score_detail?start="+start+"&limit="+limit+"&time="+time).
			success(function(response){
				$scope.score_start = (page - 1) * pageSize + 1;
				$scope.score_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.score_lastIndex = totals;
					} else {
						$scope.score_start = 0;
						$scope.score_lastIndex = 0;
					}
				}
				$scope.scoreData = response.items;
				$scope.scoreTotals = response.totals;
			});
			
		};
		$scope.money_pageClick = function(page,totals, totalPages) {
			var pageSize = $("#page-limit-money").val();
			var time=$("input[name='time']").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			$http.get("../../check/search_money_detail?start="+start+"&limit="+limit+"&time="+time).
			success(function(response){
				$scope.money_start = (page - 1) * pageSize + 1;
				$scope.money_lastIndex = page * pageSize;
				if (page == totalPages) {
					if (totals > 0) {
						$scope.money_lastIndex = totals;
					} else {
						$scope.money_start = 0;
						$scope.money_lastIndex = 0;
					}
				}
				$scope.moneyData = response.items;
				$scope.moneyTotals = response.totals;
			});
			
		};
		$scope.score();
	});
};
/* 数据分页 */
xh.score_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.score_start = start;
	$scope.score_lastIndex = end;
	$scope.scoreTotals = totals;
	if (totals > 0) {
		$(".score-page-paging").html('<ul class="pagination score-pagination"></ul>');
		$('.score-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.score_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
xh.money_pagging = function(currentPage,totals, $scope,pageSize) {
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
	$scope.money_start = start;
	$scope.money_lastIndex = end;
	$scope.moneyTotals = totals;
	if (totals > 0) {
		$(".money-page-paging").html('<ul class="pagination money-pagination"></ul>');
		$('.money-pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.money_pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};
xh.getNowMonth=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth(); 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strYesterday=strYear+"-"+strMonth;   
    return  strYesterday;
}
