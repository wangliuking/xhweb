
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
		$scope.doc_name="成都市应急指挥调度无线通信网三期工程服务项目\r\n"+$scope.time+"项目服务扣分表";
		var files=$location.search().files;
		var docName=$location.search().docName;
		if(docName!=null && docName!=""){
			$scope.doc_name=docName;
		}
		
		
		$scope.change3Input=function(){
			var a=0;
			a+=parseFloat($("#score3Form").find("input[name='s_a1']").val()==""?0:$("#score3Form").find("input[name='s_a1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b1']").val()==""?0:$("#score3Form").find("input[name='s_b1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b2']").val()==""?0:$("#score3Form").find("input[name='s_b2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b3']").val()==""?0:$("#score3Form").find("input[name='s_b3']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_b4']").val()==""?0:$("#score3Form").find("input[name='s_b4']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_c1']").val()==""?0:$("#score3Form").find("input[name='s_c1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_c2']").val()==""?0:$("#score3Form").find("input[name='s_c2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_d1']").val()==""?0:$("#score3Form").find("input[name='s_d1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_d2']").val()==""?0:$("#score3Form").find("input[name='s_d2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_e1']").val()==""?0:$("#score3Form").find("input[name='s_e1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_f1']").val()==""?0:$("#score3Form").find("input[name='s_f1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_f2']").val()==""?0:$("#score3Form").find("input[name='s_f2']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_g1']").val()==""?0:$("#score3Form").find("input[name='s_g1']").val());
			a+=parseFloat($("#score3Form").find("input[name='s_h1']").val()==""?0:$("#score3Form").find("input[name='s_h1']").val());
			$scope.score_sum3=a;
		}
		$scope.searchScore=function(time){
			$http.get("../../check/searchScore?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data3= response.items3;
				$scope.score_sum3=response.sum3;
				$scope.score_data4= response.items4;
				$scope.score_sum4=response.sum4;
				if($scope.score_sum3==0){
					$scope.autoScore(time);
				}
				
			});
		}
		$scope.autoScore=function(time){
			$http.get("../../check/show_score_detail?period=3&time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data3= response.items;
				$scope.score_sum3=response.sum;
			});
		}
		$scope.searchFile=function(fileName){
			var filesStr=files.split(",");
			var path="";
			if(files.indexOf(fileName)==-1){
				if(fileName=="成都市应急通信网应急通信保障预案"){
					path="/doc/成都市应急通信网应急通信保障预案.docx"
				}else if(fileName=="成都市应急通信网运维管理制度"){
					path="/doc/成都市应急通信网运维管理制度.doc"
				}else{
					alert("参考资料不存在");
					return;
				}
			}
			for(var i=0;i<filesStr.length;i++){
				if(filesStr[i].indexOf(fileName)!=-1){
					path="/upload/check/"+$scope.time.split("-")[0]
					+"/"+$scope.time.split("-")[1]+"/3/"+filesStr[i];
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
		$scope.showInfoWin=function(str){
			if(str=="用户响应"){
				layer.open({
					  type: 2, 
					  shade: 0.4,
					  anim: 1,
					  maxmin:true,
					  title:'用户需要求处理列表',
					  area: ['900px', '500px'],
					  content: xh.getUrl()+'/Views/business/user_need.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					}); 
			}
		  if(str=="应急通信保障"){
			layer.open({
				  type: 2, 
				  shade: 0.4,
				  anim: 1,
				  maxmin:true,
				  title:"通讯保障记录",
				  area: ['900px', '500px'],
				  content: xh.getUrl()+'/Views/business/record_ensure.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
				}); 
		}
		  if(str=="应急演练"){
				layer.open({
					  type: 2, 
					  shade: 0.4,
					  anim: 1,
					  title:'应急演练列表',
					  maxmin:true,
					  area: ['900px', '500px'],
					  content: xh.getUrl()+'/Views/business/record_emergency.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					}); 
			}
		  if(str=="网络优化"){
				layer.open({
					  type: 2, 
					  shade: 0.4,
					  anim: 1,
					  title:'网络优化列表',
					  maxmin:true,
					  area: ['900px', '500px'],
					  content: xh.getUrl()+'/Views/business/record_net.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					});
			}
		  if(str=="培训及技术支持服务"){
				layer.open({
					  type: 2, 
					  shade: 0.4,
					  anim: 1,
					  title:'培训及技术支持服务列表',
					  maxmin:true,
					  area: ['900px', '500px'],
					  content: xh.getUrl()+'/Views/business/record_train.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					});
			}
		  if(str=="运维响应机制"){
				var index=layer.open({
					  type: 2, 
					  shade: 0.4,
					  anim: 1,
					  title:'运维响应机制',
					  maxmin:true,
					  area: ['900px', '500px'],
					  content: xh.getUrl()+'/Views/operations/task_check_record.html?month='+$scope.time //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					});
				layer.full(index);
			}
		}
		$scope.searchScore($scope.time);
	});
	
	
};

xh.add = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../check/record_score_3',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			score3Data:xh.serializeJson($("#score3Form").serializeArray()),
			time: $scope.time,
			applyId: $scope.applyId,
			score_total:$scope.score_sum3,
			doc_name:$("textarea[name='doc_name']").val()
		},
		success : function(data) {
			if (data.success) {
				
				POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/check_create_score_doc.jsp?type=3','width=300px;height=200px;');
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
