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
	
	app.filter('weekly', function() {
		return function(text) {
			return text + "  " + xh.weekly(text);
		}
	})
	app.filter('textArea', function() {
		return function(text) {
			
			var encodedStr = "" ;
		    if (text=="") {
		    	return encodedStr ;
		    }
		    else {
		    	var str = text.replace(/<br>/g,"<br />");
		        for (var i = 0 ; i < str.length ; i ++){
		            encodedStr += "&#" + str.substring(i, i + 1).charCodeAt().toString(10) + ";" ;
		        }
		    }
		    return encodedStr ;
		}
	})
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);

	app.controller("xhcontroller", function($scope, $http,$location) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值;
		$scope.NO="";
		$scope.sendUnit="";
		$scope.page=1;
		$scope.time = $location.search().month;
		$scope.type = $location.search().type;
		
		
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(function(response) {
			$scope.up = response;
			
		});
		$scope.nowDate = xh.today();
		
		$http.get(
				"../../WorkContact/list?status=&time="+$scope.time+"&type="+$scope.type+"&start=0&limit="
						+ pageSize).success(
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
		/*获取编号*/
		$scope.CodeNum = function() {
			$http.get("../../WorkContact/codeNum").success(function(response) {
				$scope.NO = response.code;
				
			});
		}
		
		$scope.select=function(){
			$http.get("../../select/workcontact").success(function(response) {
				$scope.select_data = response.items;
				
			});
		}
		/* 显示详细信息 */
		$scope.detail = function(id) {
			$("#detail").modal('show');
			$scope.detailData = $scope.data[id];
			//text.replace(/<br>/g,"<br />");
			var str=$scope.detailData.content;
			str=str.replace(/<br>/g,"<br />");
			str=str.replace(/" "/g,"&nbsp;")
			$("#df").html(str)
		};
		$scope.showEdit= function(id) {
			$("#edit").modal('show');
			$scope.editData = $scope.data[id];
			var str=$scope.editData.content;
			str=str.replace(/<br>/g,"\r\n");
			str=str.replace(/" "/g," ")
			$("#content").html(str)
		};
		$scope.showCheck = function(id) {
			$("#checkWin").modal('show');
			$scope.detailData = $scope.data[id];
		};
		$scope.download = function(path) {
			var index=path.lastIndexOf("/");
			var name=path.substring(index+1,path.length);	
			var downUrl = "../../uploadFile/downfile?filePath="+path+"&fileName=" + name;
			if(xh.isfile(path)){
				window.open(downUrl, '_self',
				'width=1,height=1,toolbar=no,menubar=no,location=no');
			}else{
				toastr.error("文件不存在", '提示');
			}
			
		};
		$scope.showFileWin=function(){
			$("input[name='pathName']").click();
		}
		$scope.showUpdateFileWin=function(){
			$("input[name='pathName2']").click();
		}
		$("input[name='pathName']").change(function(){
			console.log($(this).val());
		});
		$scope.openWord = function(tag,id) {
			$scope.detailData = $scope.data[id];
			if(tag==1){
				POBrowser.openWindowModeless('../../office/openWord?path='+$scope.detailData.file_path,'width=1200px;height=800px;');
			}
			
		};
		$scope.previewDoc=function(path){
			console.log(path)
			if(path.toLowerCase().indexOf("doc")!=-1){
				console.log("doc")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/previewWord?path='+
						path,'width=1200px;height=800px;');
			}else if(path.toLowerCase().indexOf("xls")!=-1){
				console.log("xls")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/previewExcel?path='+
						path,'width=1200px;height=800px;');
			}else if(path.toLowerCase().indexOf("pdf")!=-1){
				console.log("pdf")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/previewPDF?path='+
						path,'width=1200px;height=800px;');
			}else{
				alert("该文件类型不支持在线预览")
			}
			
		}
	


		

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var time = $("#time").val();
			var type = $("#type").val();
			var status = $("#status").val();
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
					"../../WorkContact/list?status=&time="+$scope.time+"&type="+$scope.type+"&start="+start+"&limit="
							+ pageSize).success(function(response) {
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.page=page;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		// 分页点击
		$scope.pageClick = function(page, totals, totalPages) {
			var pageSize = $("#page-limit").val();
			var time = $("#time").val();
			var type = $("#type").val();
			var status = $("#status").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../WorkContact/list?status=&time="+$scope.time+"&type="+$scope.type+"&start="+start+"&limit="
							+ pageSize).success(function(response) {
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
				$scope.page=page;
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
xh.weekly = function(dateStr) {

	var weekDay = [ "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];

	if (dateStr != null && dateStr != "") {
		var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/")));
		return weekDay[myDate.getDay()];
	} else {
		return "";
	}

}
xh.today = function() {
	var today = new Date();
	var h = today.getFullYear();
	var m = today.getMonth() + 1;
	var d = today.getDate();
	var week = today.getDay();
	
	var str = '';
	if (week == 0) {
		str = "星期日";
	} else if (week == 1) {
		str = "星期一";
	} else if (week == 2) {
		str = "星期二";
	} else if (week == 3) {
		str = "星期三";
	} else if (week == 4) {
		str = "星期四";
	} else if (week == 5) {
		str = "星期五";
	} else if (week == 6) {
		str = "星期六";
	}
	m = m < 10 ? "0" + m : m; // 这里判断月份是否<10,如果是在月份前面加'0'
	d = d < 10 ? "0" + d : d; // 这里判断日期是否<10,如果是在日期前面加'0'
	return h + "-" + m + "-" + d +" "+str;
}
xh.No = function() {
	var today = new Date();
	var y = today.getFullYear();
	var m = today.getMonth() + 1;
	var d = today.getDate();
	var h=today.getHours();
	var m2=today.getMinutes();
	var s=today.getSeconds();
	var str='CDYJW-';
	m = m < 10 ? "0" + m : m; // 这里判断月份是否<10,如果是在月份前面加'0'
	d = d < 10 ? "0" + d : d; // 这里判断日期是否<10,如果是在日期前面加'0'
	h = h < 10 ? "0" + h : h; // 这里判断日期是否<10,如果是在日期前面加'0'
	m2 = m2 < 10 ? "0" + m2 : m2; // 这里判断日期是否<10,如果是在日期前面加'0'
	s = s < 10 ? "0" + s : s; // 这里判断日期是否<10,如果是在日期前面加'0'

	return str+y+m+d+h+m2+s;
}
