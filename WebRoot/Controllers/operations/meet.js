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
	var status = $("#status").val();
	var pageSize = $("#page-limit").val();
	
	/*app.filter('timeFormat', function() { // 可以注入依赖
		return function(text) {
			if(text==null){
				return "";
			}else{
				var t1=text.start_time;
				var t2=text.end_time;
				var tt=t1.split(" ")[0]+" "+t1.split(" ")[1].split(":")[0]+":"+t1.split(" ")[1].split(":")[1];
				tt+="-"+t2.split(" ")[1].split(":")[0]+":"+t1.split(" ")[1].split(":")[1];
				return tt;
			}
		};
	});*/
	app.filter('singleTimeFormat', function() { // 可以注入依赖
		return function(text) {
			if(text==null){
				return "";
			}else{
				var t1=text
				return t1.split(" ")[0];
			}
		};
	});
	

	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		$scope.page=1;
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			console.log("loginuser="+$scope.loginUser);
			$scope.loginUserRoleId = response.roleId;
			$scope.roleType = response.roleType;
			$scope.userL=response;
		});
		$http.get(
				"../../meet/meetlist?&start=0&limit=" + pageSize).success(
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

		$scope.editModel = function(id) {
			$("#edit").modal('show');
			$scope.editData = $scope.data[id];
			var str=$scope.data[id].person;
			str=str.replace(/<br>/g,"\r\n");
			str=str.replace(/&nbsp;/g," ");
			var content=$scope.data[id].content;
			content=content.replace(/<br>/g,"\r\n");
			content=content.replace(/&nbsp;/g," ");
			$("#editForm").find("textarea[name='person']").val(content)
			$("#editForm").find("textarea[name='content']").val(content)
			/*
			var str=$scope.detailData.person;
			$("#df1").html(str.replace(/<br>/g,"<br />"))*/
		};
		$scope.detailModel = function(id) {
			$("#detail").modal('show');
			$scope.detailData = $scope.data[id];
			var str=$scope.detailData.person;
			str=str.replace(/<br>/g,"<br />");
			str=str.replace(/" "/g,"&nbsp;")
			$("#df2").html(str)
		};
		$scope.checkModel = function() {
			$("#checkWin").modal('show');
		};
		$scope.openWord = function(tag,id) {
			$scope.detailData = $scope.data[id];
			var path=$scope.detailData.file_path;
			if(tag==1){
				POBrowser.openWindowModeless('../../office/previewWord?path='+$scope.detailData.file_path,'width=1200px;height=800px;');
			}else if(tag==2){
				POBrowser.openWindowModeless('../../meet/seal?type=1&path='+$scope.detailData.file_path,'width=1200px;height=800px;');
			}else if(tag==3){
				POBrowser.openWindowModeless('../../meet/seal?type=2&path='+$scope.detailData.file_path,'width=1200px;height=800px;');
			}else if(tag==4){
				POBrowser.openWindowModeless('../../doc/createfile.jsp','width=1200px;height=800px;');
			}
			
		};
		$scope.check = function(tag) {
			var data={
					state:tag,
					id:$scope.detailData.id,
					send_user:$scope.detailData.send_user,
					note:$("#checkWin").find("textarea[name='note']").val()
			}
			$.ajax({
				url : '../../meet/check',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					formData:JSON.stringify(data)
				},
				success : function(data) {
					if (data.success) {
						$('#checkWin').modal('hide');
						xh.refresh();
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function(e) {
					toastr.error(e, '提示');
				}
			});
		};
		$scope.check2 = function(tag) {
			var data={
					state:tag,
					send_user:$scope.detailData.send_user,
					check_user1:$scope.detailData.check_user1,
					id:$scope.detailData.id
			}
			$.ajax({
				url : '../../meet/check2',
				type : 'POST',
				dataType : "json",
				async : true,
				data:{
					formData:JSON.stringify(data)
				},
				success : function(data) {
					if (data.success) {
						xh.refresh();
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function(e) {
					toastr.error(e, '提示');
				}
			});
		};

		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var time = $("#time").val();
			var start = 1, limit = pageSize;
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;

			} else {
				start = (page - 1) * pageSize;
			}
			/*console.log("limit=" + limit);
			xh.maskShow();*/
			$http.get(
					"../../meet/meetlist?time="+time+"&start=0&limit=" + pageSize).success(function(response) {
				//xh.maskHide();
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
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			//xh.maskShow();
			$http.get(
					"../../meet/meetlist?time="+time+"&start="+start+"&limit=" + pageSize).success(function(response) {
				//xh.maskHide();
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
				$scope.page=page;
				$scope.data = response.items;
				$scope.totals = response.totals;
			});

		};
		setInterval(function(){
			$scope.refresh();
		}, 5000);
	});
};
//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};
xh.checkModel = function() {
	var $scope = angular.element(appElement).scope();
	$scope.checkModel();
};
xh.check= function(tag) {
	var $scope = angular.element(appElement).scope();
	$scope.check(tag);
};
xh.check2= function(tag) {
	var $scope = angular.element(appElement).scope();
	$scope.check2(tag);
};
/* 添加 */
xh.add = function() {
	$.ajax({
		url : '../../meet/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
		},
		success : function(data) {
			if (data.success) {
				$('#add').modal('hide');
				xh.refresh();
				$("#addForm")[0].reset();
				$("#addForm").data('bootstrapValidator').resetForm();
				//toastr.success(data.message, '提示');
				POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/meet_doc.jsp?','width=300px;height=200px;');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function(e) {
			toastr.error(e, '提示');
		}
	});
};
xh.update= function() {
	var $scope = angular.element(appElement).scope();
	var data=$scope.editData;
	var afterData=xh.serializeJson($("#editForm").serializeArray())
	data.name=JSON.parse(afterData).name;
	data.meet_time=JSON.parse(afterData).meet_time;
	data.address=JSON.parse(afterData).address;
	data.person=JSON.parse(afterData).person;
	data.content=JSON.parse(afterData).content;
	$.ajax({
		url : '../../meet/update',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			formData:JSON.stringify(data)
		},
		success : function(data) {
			if (data.success) {
				$('#edit').modal('hide');
				xh.refresh();
				$("#editForm").data('bootstrapValidator').resetForm();
				POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/meet_doc.jsp?bean='+JSON.stringify(data.bean),'width=300px;height=200px;');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function(e) {
			toastr.error(e, '提示');
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
var WebPrinter; //声明为全局变量 
xh.printExists=function() {
	
	try{ 
	    var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM')); 
		if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION); 
	 }catch(err){ 
		//alert("Error:本机未安装或需要升级!"); 
	 }   
};
/*ADD_PRINT_TABLE(Top,Left,Width,Height,strHtml)*/
xh.print=function() {
	var LODOP = getLodop();
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("10%", "3%", "95%", "100%", document.getElementById("print").innerHTML);
	 LODOP.PREVIEW();  	
};
function cc(){
	alert(1);
}
xh.addSuccess=function(){
	swal("提示","操作成功","info");
}
