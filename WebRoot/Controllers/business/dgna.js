/**

 * 资产管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
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
		$scope.businessMenu=true; //菜单变色
		
		$scope.data=[];
		$scope.totals=0;
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
			$("#table-checkbox").prop("checked", false);
		};
		/*添加单个用户*/
		$scope.addone=function(){
			var groupId=$("#groupId").val();
			var userId=$("#userId").val();
			var store={
					groupId:'',
					userId:'',
					status:0					
			};
			var record=[];
			store.userId=userId;
			store.groupId=groupId;
			record.push(store);
			$dgnaData=record;
			
		};
		//添加一个用户
		$scope.addOneUser=function(){
			var userId=$("#userId").val();
			var groupId=$("#groupId").val();
			var record={userId:'',groupId:'',status:0};
			
			if(userId==""){
				toastr.error("用户ID不能为空", '提示');
				return;
			}
			if(groupId==""){
				toastr.error("组ID不能为空", '提示');
				return;
			}
			
			record.userId=userId;
			record.groupId=groupId;
			var flag=0;
			for(var i=0;i<$scope.data.length;i++){
				if($scope.data[0].userId==userId){
					flag=1;
				}
			}
			if(flag==0){
				$scope.data.push(record);
			}
			
			$scope.totals=$scope.data.length;
		};
		/* 添加多用户 */
		$scope.addMore = function() {
			var user1=parseInt($("#user1").val());
			var user2=parseInt($("#user2").val());
			var groupId=$("#groupId").val();
			
			
			if(groupId==""){
				toastr.error("组ID不能为空", '提示');
				return;
			}
			if(user2-user1<0){
				toastr.error("用户ID区间不正确", '提示');
				return;
			}
			for(var i=0;i<=user2-user1;i++){
				var record={userId:'',groupId:groupId,status:0};
				record.userId=user1+i;	
				var flag=0;
				for(var j=0;j<$scope.data.length;j++){
					if($scope.data[j].userId==record.userId){
						flag=1;
					}
				}
				if(flag==0){
					$scope.data.push(record);
					
				}
				
			}
			$scope.totals=$scope.data.length;
			
			
		};
		
		/* 删除用户 */
		$scope.delBs = function(id) {
			$scope.data.splice(id,1);
			$scope.totals=$scope.data.length;
			
		};
		/* 清空用户 */
		$scope.clear = function() {
			$scope.data.splice(0,$scope.data.length);
			$scope.totals=$scope.data.length;	
		};
		$scope.start=function(){
			var opreation=$("input[name='operation']:checked").val();
			var cou=$("input[name='cou']:checked").val();
			var attached=$("input[name='attached']:checked").val();
			var data=[];
			if($scope.data.length<1){
				toastr.error("还没有操作数据", '提示');
				return;
			}
			$.each($scope.data,function(i,record){
				data.push(record.userId);
			});
			$.ajax({
				url : '../../ucm/dgna',
				type : 'POST',
				dataType : "json",
				traditional :true,  //注意这个参数是必须的
				async : true,
				data:{
					operation:opreation,
					groupId:$("#groupId").val(),
					cou:cou,
					attached:attached,
					data:data.join(",")
				},
				success : function(data) {

					if (data.success) {
						toastr.success(data.message, '提示');
					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
				}
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

/*$http({
method : "POST",
url : "../../bs/list",
data : {
	bsId : bsId,
	name : name,
	start : start,
	limit : pageSize
},
headers : {
	'Content-Type' : 'application/x-www-form-urlencoded'
},
transformRequest : function(obj) {
	var str = [];
	for ( var p in obj) {
		str.push(encodeURIComponent(p) + "="
				+ encodeURIComponent(obj[p]));
	}
	return str.join("&");
}
}).success(function(response) {
xh.maskHide();
$scope.data = response.items;
$scope.totals = response.totals;
});*/
