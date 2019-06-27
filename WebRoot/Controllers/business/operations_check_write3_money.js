
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
		$scope.doc_name="成都市应急指挥调度无线通信网三期工程服务项目\r\n服务扣款表";
		var files=$location.search().files;
		
		$scope.sum_money3=function(){
			var a=0;
			a+=parseFloat($("#money3Form").find("input[name='m_a1']").val()==""?0:$("#money3Form").find("input[name='m_a1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a2']").val()==""?0:$("#money3Form").find("input[name='m_a2']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a3']").val()==""?0:$("#money3Form").find("input[name='m_a3']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_a4']").val()==""?0:$("#money3Form").find("input[name='m_a4']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_b1']").val()==""?0:$("#money3Form").find("input[name='m_b1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_b2']").val()==""?0:$("#money3Form").find("input[name='m_b2']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_d1']").val()==""?0:$("#money3Form").find("input[name='m_d1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_f1']").val()==""?0:$("#money3Form").find("input[name='m_f1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_g1']").val()==""?0:$("#money3Form").find("input[name='m_g1']").val());
			a+=parseFloat($("#money3Form").find("input[name='m_l1']").val()==""?0:$("#money3Form").find("input[name='m_l1']").val());
			$scope.money_sum3=a;
		}
		$scope.searchMoney=function(time){
			$http.get("../../check/searchDetail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.money_data3 = response.items3;
				$scope.money_sum3=response.sum3;
				$scope.money_data4 = response.items4;
				$scope.money_sum4=response.sum4;
				
				
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
		}
		$scope.searchMoney($scope.time);
	   
	});
	
	
};
xh.add = function() {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../check/record_money_3',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			money3Data:xh.serializeJson($("#money3Form").serializeArray()),
			time: $scope.time,
			applyId: $scope.applyId,
			money_total:$scope.money_sum3,
			doc_name:$("textarea[name='doc_name']").val()
		},
		success : function(data) {
			if (data.success) {
				money=JSON.stringify(data.money)
				POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/check_create_money3_doc.jsp','width=300px;height=200px;');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};

xh.writeMoneySussess=function(){
	
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