
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
	app.config([ '$locationProvider', function($locationProvider) {
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : false
		});
	} ]);
	app.controller("xhcontroller", function($scope,$http, $location) {
		$scope.applyId = $location.search().applyId;
		$scope.time = $location.search().checkMonth;
		var files=$location.search().files;
		
		$scope.change4Input=function(){
			var a=0;
			a+=parseFloat($("#score4Form").find("input[name='s_a1']").val()==""?0:$("#score4Form").find("input[name='s_a1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b1']").val()==""?0:$("#score4Form").find("input[name='s_b1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b2']").val()==""?0:$("#score4Form").find("input[name='s_b2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b3']").val()==""?0:$("#score4Form").find("input[name='s_b3']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_b4']").val()==""?0:$("#score4Form").find("input[name='s_b4']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_c1']").val()==""?0:$("#score4Form").find("input[name='s_c1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_c2']").val()==""?0:$("#score4Form").find("input[name='s_c2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_d1']").val()==""?0:$("#score4Form").find("input[name='s_d1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_d2']").val()==""?0:$("#score4Form").find("input[name='s_d2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_e1']").val()==""?0:$("#score4Form").find("input[name='s_e1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_f1']").val()==""?0:$("#score4Form").find("input[name='s_f1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_f2']").val()==""?0:$("#score4Form").find("input[name='s_f2']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_g1']").val()==""?0:$("#score4Form").find("input[name='s_g1']").val());
			a+=parseFloat($("#score4Form").find("input[name='s_h1']").val()==""?0:$("#score4Form").find("input[name='s_h1']").val());
			$scope.score_sum4=a;
		} 
		$scope.searchScore=function(time){
			$http.get("../../check/searchScore?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data3= response.items3;
				$scope.score_sum3=response.sum3;
				$scope.score_data4= response.items4;
				$scope.score_sum4=response.sum4;
			});
		}
		$scope.searchFile=function(fileName){
			var filesStr=JSON.parse(files);
			var path="";
			if(files.indexOf(fileName)==-1){
				alert("参考资料不存在");
				return;
			}
			for(var i=0;i<filesStr.length;i++){
				if(filesStr[i].fileName.indexOf(fileName)!=-1){
					path=filesStr[i].filePath;
				}
			}
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
		$scope.searchScore($scope.time);
	});
	
	
};

xh.add = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../check/record_score_4',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			score4Data:xh.serializeJson($("#score4Form").serializeArray()),
			time: $scope.time,
			applyId: $scope.applyId,
			score_total:$scope.score_sum4
		},
		success : function(data) {
			if (data.success) {
				POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/check_create_score_doc.jsp?type=4&bean='+JSON.stringify(data.score),'width=300px;height=200px;');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};

xh.writeScoreSussess=function(){
	
	swal({
		title : "提示",
		text : "提交记录成功",
		type : "success",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "提交记录成功，返回申请列表",
		cancelButtonText : "取消",
	    closeOnCancel : true
	/*
	 * closeOnConfirm : false, closeOnCancel : false
	 */
	}, function(isConfirm) {
		if (isConfirm) {
			window.location.href="operations_check.html"
		}
	});
}
