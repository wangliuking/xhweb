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

	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值;
		$scope.NO=xh.No();
		$scope.sendUnit="";
		$scope.page=1;
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(function(response) {
			$scope.up = response;
			
		});
		$scope.nowDate = xh.today();
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response;
			if($scope.loginUser.roleType==2){
				$scope.sendUnit="成都市软件产业发展中心"
			}else if($scope.loginUser.roleType==3 || $scope.loginUser.roleType==0){
				$scope.sendUnit="成都亚光电子股份有限公司"
			}
		});
		$http.get("../../WorkContact/list?&start=0&limit=" + pageSize).success(
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
		$("input[name='pathName']").change(function(){
			console.log($(this).val());
		})

		/* 签收 */
		$scope.sign = function(index) {
			var id = $scope.data[index].taskId;
			$.ajax({
				url : '../../WorkContact/sign',
				type : 'POST',
				dataType : "json",
				async : true,
				data : {
					taskId : id,
					addUser : $scope.data[index].addUser
				},
				success : function(data) {

					if (data.success) {
						xh.refresh();
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.success("系统错误", '提示');
				}
			});

		};
		$scope.del=function(id){
			$scope.oneData = $scope.data[id];
			swal({
				title : "提示",
				text : "确定要删除记录吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消",
			    closeOnConfirm : true, 
			    closeOnCancel : true,
			    }, function(isConfirm) {
				    if (isConfirm) {
				    	$.ajax({
							url : '../../WorkContact/del',
							type : 'post',
							dataType : "json",
							data : {
								id:$scope.oneData.id
							},				
							async : false,
							success : function(data) {
								xh.maskHide();
								//$("#btn-mbs").button('reset');
								if (data.success) {
									toastr.success(data.message, '提示');
									$scope.refresh();
								} else {
									toastr.error(data.message, '提示');
								}
							},
							error : function() {
								xh.maskHide();
								toastr.error("系统错误", '提示');
							}
						});
				    }
			});
			
			
		}

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var time = $("#time").val();
			var type = $("#type").val();
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
					"../../WorkContact/list?time="+time+"&type="+type+"&start=" + start + "&limit="
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
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../WorkContact/list?time="+time+"&type="+type+"&start=" + start + "&limit="
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
/* 添加 */
xh.add = function() {
	var $scope = angular.element(appElement).scope();
	var files=[];
	
	$("#fileArea ul li").each(function(){
	    var name = $(this).children().first().text();
	    var path = $(this).children(".path").text();
	    if(name!="" && path!=""){
	    	var a={
	    			fileName:name,
	    			filePath:path
	    	}
	    	files.push(a);
	    }
	   
	});
	$.ajax({
		url : '../../WorkContact/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			formData : xh.serializeJson($("#addForm").serializeArray()),
			files: JSON.stringify(files)
		// 将表单序列化为JSON对象
		},
		success : function(data) {
			$("#add_btn").button('reset');
			if (data.success) {
				$('#add').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("参数错误", '提示');
			$("#add_btn").button('reset');
		}
	});
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
