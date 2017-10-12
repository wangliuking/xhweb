if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
xh.load = function() {
	var app = angular.module("app", []);
	var caller = $("#caller").val();
	var called = $("#called").val();
	var pageSize = $("#page-limit").val();

	app.controller("audio", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";// 每页数据显示默认值
		$scope.operationMenu = true; // 菜单变色
		$http.get(
				"../../call/list?caller=" + caller + "&called=" + called + ""
						+ "&starttime=''&endtime=" + xh.getOneDay()
						+ "&start=0&limit=" + pageSize).success(
				function(response) {
					xh.maskHide();
					$scope.data = response.items;
					$scope.totals = response.totals;
					$scope.starttime = xh.getDay();
					$scope.endtime = xh.getOneDay();
					xh.pagging(1, parseInt($scope.totals));
				});
		// 刷新数据
		$scope.refresh = function() {
			xh.search(1);

		};
		// 查询数据
		$scope.search_1 = function(page) {
			xh.search(page);

		};
		//播放语音文件
		$scope.play=function(index){
			console.log("play="+$scope.data[index].call_Path);
			layer.closeAll();
			var html={
					  type: 2,
					  title:'语音播放器',
					  area: ['340px', '200px'],
					  shade: 0,
					  /*skin: 'layui-layer-rim', //加上边框*/					  
					  content: ["../../Views/operations/play.jsp?playerID="+$scope.data[index].call_Path, 'no']
					};
			layer.open(html);
			
		};
		/* 下载文件 */
		$scope.download = function(index) {
			var path=$scope.data[index].call_Path;
			var index=path.lastIndexOf("/");
			var name=path.substring(index+1,path.length);	
			console.log("下载音频文件名filename=>" +name);
			var downUrl = "../../call/download?fileName=" + name;
			window.open(downUrl, '_self',
					'width=1,height=1,toolbar=no,menubar=no,location=no');
		};
		/* 播放录音 */
		$scope.play_click = function(sef, audioName, index) {
			//这里因为本地数据录音路径名为ModelTest*.mp3 ，因此设置默认值为ModelTest1.mp3,
			//正常使用是audioName传入录音文件名。（即数据库中call_Path字段）
			//audioName = "ModelTest1.mp3"
			//测试默认值
			var url = "../../Resources/audio/" + audioName;
				
			$scope.audioName=audioName;	
			$scope.playUrl=url;
			$("#audioPlay").modal('show');	
			
			
			/*var div = document.getElementById('div' + index);
			div.innerHTML = '<embed src="' + url
					+ '" loop="0" autostart="true" height="60"></embed>';
			var emb = document.getElementsByTagName('EMBED')[0];
			if (emb) { 这里可以写成一个判断 wav 文件是否已加载完毕，以下采用setTimeout模拟一下 
				div = document.getElementById('div2');
				div.innerHTML = 'loading: ' + emb.src;
				sef.disabled = true;
				setTimeout(function() {
					div.innerHTML = '';
				}, 5000);
			}*/
		};

		// 分页点击
		$scope.pageClick = function(page) {
			var pageSize = $("#page-limit").val();
			var caller = $("#caller").val();
			var called = $("#called").val();
			var starttime = $("#starttime").val();
			var endtime = $("#endtime").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get(
					"../../call/list?caller=" + caller + "&called=" + called
							+ "" + "&starttime=" + starttime + "&endtime="
							+ endtime + "&start=" + start + "&limit="
							+ pageSize).success(function(response) {
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
			});

		};

	});
};

xh.download = function() {
	var $scope = angular.element(appElement).scope();
	var filename = $scope.checkData.fileName;
	console.log("filename=>" + filename);
	var downUrl = "../../call/download?fileName=" + filename;
	window.open(downUrl, '_self',
			'width=1,height=1,toolbar=no,menubar=no,location=no');
};

/* 查询数据 */
xh.search = function(page) {
	var appElement = document.querySelector('[ng-controller=audio]');
	var $scope = angular.element(appElement).scope();
	var pageSize = $("#page-limit").val();
	var caller = $("#caller").val();
	var called = $("#called").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var start = 1, limit = pageSize;
	page = parseInt(page);
	if (page <= 1) {
		start = 0;
		limit = pageSize;
	} else {
		start = (page - 1) * pageSize;
		limit = page * pageSize;
	}
	xh.maskShow();
	$.ajax({
		url : '../../call/list',
		data : {
			caller : caller,
			called : called,
			starttime : starttime,
			endtime : endtime,
			start : start,
			limit : limit
		},
		type : 'GET',
		dataType : "json",
		async : false,
		success : function(response) {
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			xh.pagging(page, parseInt($scope.totals));
		},
		failure : function(response) {
			xh.maskHide();
		}
	});
};
/* 数据分页 */
xh.pagging = function(currentPage, totals) {
	var appElement = document.querySelector('[ng-controller=audio]');
	var $scope = angular.element(appElement).scope();
	var pageSize = $("#page-limit").val();
	var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
			.ceil(parseInt(totals, 10) / pageSize);
	var start = 1;
	var end = pageSize;
	if (totalPages > 1) {
		start = currentPage + 1 * pageSize;
		end = (currentPage + 1) * pageSize;
	} else {
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
				// xh.search(page);
				if (frist == 1) {
					$scope.pageClick(page);
				}
				frist = 1;

			}
		});
	}

};