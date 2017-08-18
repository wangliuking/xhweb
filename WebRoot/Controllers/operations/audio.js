if (!("xh" in window)) {window.xh = {};};
var frist=0;
xh.load=function(){
	var app = angular.module("app", []);
	var caller=$("#caller").val();
	var called=$("#called").val();
	var pageSize=$("#page-limit").val();
	
	app.controller("audio", function($scope, $http) {
		xh.maskShow();
		$scope.count = "20";//每页数据显示默认值
		$scope.operationMenu=true; //菜单变色
		$http.get("../../call/list?caller="+caller+"&called="+called+"" +
				"&starttime=''&endtime="+xh.getOneDay()+"&start=0&limit="+pageSize).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals=response.totals;
			$scope.starttime=xh.getDay();
			$scope.endtime=xh.getOneDay();
			xh.pagging(1,parseInt($scope.totals));
		});
		//刷新数据
	    $scope.refresh=function(){
	    	xh.search(1);
	    	
	    };
	  //查询数据
	    $scope.search_1=function(page){
	    	xh.search(page);
	    	
	    };
	    /*下载文件*/
	  $scope.download=function(){
	  var filename=$("#download").val();
	  console.log("filename=>"+filename);
	  var downUrl="../../call/download?fileName="+filename;
	  window.open(downUrl,'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
	  }
	  /*播放录音*/
	  $scope.play_click=function(sef){
		  var playname=$("#play").val();
		  var div = document.getElementById('div1');
		  var url="http://localhost:8080/xhweb/WebRoot/Resources/data/"+playname;
	    div.innerHTML ='<embed src="'+url+'" loop="0" autostart="true" hidden="true"></embed>';var emb = document.getElementsByTagName('EMBED')[0];if(emb){/* 这里可以写成一个判断 wav 文件是否已加载完毕，以下采用setTimeout模拟一下 */
	        div = document.getElementById('div2');
	        div.innerHTML ='loading: '+emb.src;
	        sef.disabled =true;
	        setTimeout(function(){div.innerHTML='';},1000);}
	    }
	  
	   //分页点击
		$scope.pageClick = function(page) {
			var pageSize=$("#page-limit").val();
			var caller=$("#caller").val();
			var called=$("#called").val();
			var starttime=$("#starttime").val();
			var endtime=$("#endtime").val();
			var start=1,limit=pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../call/list?caller="+caller+"&called="+called+"" +
					"&starttime="+starttime+"&endtime="+endtime+"&start="+start+"&limit="+pageSize).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
			});
			
		};
		
  });
};

xh.download=function(){
	var $scope = angular.element(appElement).scope();
	var filename=$scope.checkData.fileName;
	console.log("filename=>"+filename);
	var downUrl="../../call/download?fileName="+filename;
	window.open(downUrl,'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
};

/*查询数据*/
xh.search=function(page){
	var appElement = document.querySelector('[ng-controller=audio]');
	var $scope = angular.element(appElement).scope();
	var pageSize=$("#page-limit").val();
	var caller=$("#caller").val();
	var called=$("#called").val();
	var starttime=$("#starttime").val();
	var endtime=$("#endtime").val();
	var start=1,limit=pageSize;
	page=parseInt(page);
	if(page<=1){
		start=0;limit=pageSize;
	}else{
		start=(page-1)*pageSize;
		limit=page*pageSize;
	}
	xh.maskShow();
	$.ajax({
		url : '../../call/list',
		data : {
			caller:caller,
			called:called,
			starttime:starttime,
			endtime:endtime,
			start:start,
			limit:limit
		},
		type : 'GET',
		dataType : "json",
		async : false,
		success : function(response) {
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals=response.totals;
			xh.pagging(page,parseInt($scope.totals));
		},
		failure : function(response) {
			xh.maskHide();
		}
	});
};
/*数据分页*/
xh.pagging=function(currentPage, totals){
	var appElement = document.querySelector('[ng-controller=audio]');
	var $scope = angular.element(appElement).scope();
	var pageSize=$("#page-limit").val();
	var totalPages = (parseInt(totals, 10)/pageSize)<1?1:Math.ceil(parseInt(totals, 10)/pageSize);
	  var start=1;
      var end=pageSize;
      if(totalPages>1){
      	start=currentPage+1*pageSize;
      	end=(currentPage+1)*pageSize;
      }else{
    	  if(totals>0){
    		  end=totals;
    	  }else{
    		  start=0;
    		  end=0;
    	  }
      }
      $scope.start=start;
      $scope.lastIndex=end;
      $scope.totals=totals;
	 if(totals>0){
		 $(".page-paging").html('<ul class="pagination"></ul>');
    	 $('.pagination').twbsPagination(
    				{
    					totalPages : totalPages,
    					visiblePages : 10,
    					version : '1.1',
    					startPage : currentPage,
    					onPageClick : function(event, page) {
    						//xh.search(page);
    						if(frist==1){
    							$scope.pageClick(page);
    						}
    						frist=1;
    						
    					}
    				});
	 }
	
};
/*xh.pagging132=function(currentPage,totals){
	var shouldShowPage=false;
	if(totals>0){shouldShowPage=true;}
	var options = {
	        bootstrapMajorVersion:3, //对应的bootstrap版本
	        currentPage:currentPage, //当前页数，获取从后台传过来的值
	        numberOfPages:5, //每页页数
	        pageDataCount:pageSize,//每页显示数据
	        totals:totals, //数据总数
	        totalPages:totals, //总页数，获取从后台传过来的值
	        shouldShowPage:shouldShowPage,//是否显示该按钮
	        alignment: "right",
	        itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
	            switch (type) {
	                case "first":
	                    return "首页";
	                case "prev":
	                    return "上一页";
	                case "next":
	                    return "下一页";
	                case "last":
	                    return "末页";
	                case "page":
	                    return page;
	            }
	        },
	        //点击事件
	        onPageClicked: function (event, originalEvent, type, page) {
	            xh.search(page);
	        }
	    };
	$("#padding").bootstrapPaginator(options);
}*/