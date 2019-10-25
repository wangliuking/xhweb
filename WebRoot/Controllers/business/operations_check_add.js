/**
 * 资产管理
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

/**
 * ,'基站月度巡检表（含调度台及直放站）'
 */
var fileNames=["本月计划维护作业完成情况",
				"下月计划维护作业",
				"系统运行维护服务月报",
				"基站信息表",
				"运维资源配置表",
				"运维人员通讯录",
				"运维故障统计",
				"故障处理报告",
				"备品备件表",
				"定期维护报告-交换中心月维护",
				"定期维护报告-基站月维护",
				"巡检汇总表",
				"基站月度巡检表"]
xh.load = function() {
	var app = angular.module("app", []);
	
	//replaceFilePath
	app.filter('replaceFilePath', function() { // 可以注入依赖
		return function(text) {
			var x=text.replace(/checksource/g,"check");
			return x;
		};
	});
	
	var pageSize = $("#page-limit").val();
	app.controller("xhcontroller", function($scope,$http) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		$scope.time="";
		$scope.type=3;
	
		
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.loginUserRoleId = response.roleId;
			$scope.loginUserRoleType = response.roleType;
		});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		
		$scope.showFileWin=function(){
			$("input[name='pathName']").click();
		}
		$scope.checkcut=function(){
			var month=$("input[name='month']").val();
			var type=$scope.type;
			/*$scope.getFileList();
			$scope.getBsCheckFileList();
			$scope.getEnsureFileList();*/
			console.log("m->"+month);
			console.log("y->"+type)
			if(month==null || month==''){
				
			}else{
				$scope.showAllFile(month);
			}
			
			/*$http.get("../../check/search_checkcut_count?period="+type+"&month="+month).success(
					function(response) {
						$scope.checkcut_total = response.count;
			});*/
		}
		$scope.getBsCheckFileList=function(){
			var month=$("input[name='month']").val();
			var type=$("select[name='type']").val();
			$http.get("../../check/bscheckfile?period="+type+"&month="+month).success(
					function(response) {
						$scope.bscheck_files = response.files;
						$scope.bscheck_fileTotal = response.totals;
						$("#num-check").text($scope.bscheck_fileTotal)
						
						
						
						
			});
		}
		$scope.getEnsureFileList=function(){
			var month=$("input[name='month']").val();
			var type=$("select[name='type']").val();
			
			$http.get("../../check/bs_ensure_file?period="+type+"&month="+month).success(
					function(response) {
						$scope.ensure_files = response.files;
						$scope.ensure_fileTotal = response.totals;
						$("#num-ensure").text($scope.ensure_fileTotal)
			});
		}
		/*巡检记录文件*/
		$scope.getAppFile=function(){
			var month=$("input[name='month']").val();
			var type=$("select[name='type']").val();
			//$("#show_up_floder").toggle();
			//$("#file_title").text("考核文件>>通信保障报告");
			$.get("../../check/bs_app_file?period="+type+"&month="+month).success(
					function(response) {
						$scope.app_fileTotal=response.totals
						$("#num-app").text($scope.app_fileTotal)
						
						
			});
			
		}
		$scope.getFileList=function(tt){
			var month=tt;
			$scope.getBsCheckFileList();
			$scope.getEnsureFileList();
			$scope.getAppFile();
			/*var check_num=$scope.bscheck_fileTotal;
			var ensure_num=$scope.ensure_fileTotal;
			var app_num=$scope.app_fileTotal;*/
			$("#check_files").find("li").remove();
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_check()">故障核减申请书(<span id="num-check"></span>)</span></li>');
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_ensure()">通信保障报告(<span id="num-ensure"></span>)</span></li>');
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_app()">基站月巡检表(<span id="num-app"></span>)</span></li>');
			var type=$("select[name='type']").val();
			$http.get("../../check/allcheckfile?period="+type+"&month="+month).success(
					function(response) {
						$scope.files = response.files;
						$scope.fileTotal = response.totals;
                        var a=JSON.stringify(response.files);
						
						var com=new Array();
						var index=0;
						for(var i=0;i<fileNames.length;i++){
							if(a.indexOf(fileNames[i])==-1){
								com[index]=fileNames[i];
								index++;
							}
						}
						$scope.com=com.join(",");
						$scope.com_size=com.length;
						
						for(var i=0;i<$scope.fileTotal;i++){
							var x=$scope.files[i];
							var str='<li style="margin-top:10px;">';
							if(x.doc=='doc' || x.doc=='docx'){
								str+='<img src="../../Resources/images/icon/16/doc.png">';
							}else if(x.doc=='xls' || x.doc=='xlsx'){
								str+='<img src="../../Resources/images/icon/16/xls.png">';
							}else if(x.doc=='pdf'){
								str+='<img src="../../Resources/images/icon/16/pdf.png">';
							}else if(x.doc=='jpeg'){
								str+='<img src="../../Resources/images/icon/16/jpeg.png">';
							}
							str+='<span style="cursor: pointer;" title="点击预览"  onclick="xh.editDoc(\''+x.filePath+'\')">'+x.fileName+'</span>';
							str+='</li>';
							$("#check_files").append(str);
							
						}	
						
			});
		}
		
		$scope.look_check=function(){
			/*var month=$("input[name='month']").val();
			var type=$("select[name='type']").val();
			$scope.getFileList();*/
			//$scope.getBsCheckFileList();
			//$scope.getEnsureFileList();
			alert(1)
			
			
		}
		$scope.showAllFile=function(tt){
			$("#file_title").text("考核文件");
			$("#check_files").find('li').remove();
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_check()">故障核减申请书</span></li>');
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_ensure()">通信保障报告</span></li>');
			$("#check_files").append('<li style="cursor: pointer;"><img src="../../Resources/images/icon/16/floder.png"><span title="点击查看文件" onclick="xh.look_app()">基站月巡检表</span></li>');
			if(tt==null || tt==''){
				console.log("null")
			}else{
				$scope.getFileList(tt);
			}
			
			//$("#show_up_floder").hide();
		}
		$scope.previewDoc=function(path){
			console.log(path);
			
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
			}/*else if(path.toLowerCase().indexOf("jpeg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("jpg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("png")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}*/else{
				alert("该文件类型不支持在线预览")
			}
			
		}
		$scope.editDoc=function(path){
			console.log(path);
			
			if(path.toLowerCase().indexOf("doc")!=-1){
				console.log("doc")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/editWord?path='+
						path,'width=1200px;height=800px;');
			}else if(path.toLowerCase().indexOf("xls")!=-1){
				console.log("xls")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/editExcel?path='+
						path,'width=1200px;height=800px;');
			}else if(path.toLowerCase().indexOf("pdf")!=-1){
				console.log("pdf")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/previewPDF?path='+
						path,'width=1200px;height=800px;');
			}/*else if(path.toLowerCase().indexOf("jpeg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("jpg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("png")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}*/else{
				alert("该文件类型不支持在线预览")
			}
			
		}
		$scope.searchMoney=function(){
			var time=$("input[name='month']").val();
			$scope.time=time;
			console.log($scope.time);
			$http.get("../../check/show_money_detail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.money_data = response.items;
				$scope.money_sum=response.sum;
				
				
			});
		}
		$scope.searchScore=function(){
			var time=$("input[name='month']").val();
			console.log($scope.time);
			$http.get("../../check/show_score_detail?time="+time).
			success(function(response){
				xh.maskHide();
				$scope.score_data= response.items;
				$scope.score_sum=response.sum;					
			});
		}
	  
		
		//$scope.checkcut();
		
	});
	
};
xh.searchScore=function(time){
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.searchScore();
	$scope.searchMoney();
}
xh.refreshFile=function(month){
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.getFileList(month);
}
xh.checkcut=function(tt){
	
	var $scope = angular.element(appElement).scope();
	$scope.showAllFile(tt);
}
xh.look_check=function(){
	$("#check_files2").find('li').remove();
	var month=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	//$("#show_up_floder").toggle();
	//$("#file_title").text("考核文件>>故障核减申请书");
	$.get("../../check/bscheckfile?period="+type+"&month="+month).success(
			function(response) {
				if(response.totals<1){
					$("#check_files2").append("<li>没有文件!</li>");
					return;
				}
				for(var i=0;i<response.totals;i++){
					var x=response.files[i];
					var str='<li style="margin-top:10px;">';
					str+='<img src="../../Resources/images/icon/16/doc.png">';
					str+='<span style="cursor: pointer;" title="点击预览"  onclick="xh.editDoc(\''+x.filePath+'\')">'+x.fileName+'</span>';
					str+='</li>';
					$("#check_files2").append(str);
					
				}	
				
				
	});
	
}
xh.look_ensure=function(){
	$("#check_files2").find('li').remove();
	var month=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	//$("#show_up_floder").toggle();
	//$("#file_title").text("考核文件>>通信保障报告");
	$.get("../../check/bs_ensure_file?period="+type+"&month="+month).success(
			function(response) {
				if(response.totals<1){
					$("#check_files2").append("<li>没有文件</li>");
					return;
				}
				for(var i=0;i<response.totals;i++){
					var x=response.files[i];
					var str='<li style="margin-top:10px;">';
					str+='<img src="../../Resources/images/icon/16/doc.png">';
					str+='<span style="cursor: pointer;" title="点击预览"  onclick="xh.editDoc(\''+x.filePath+'\')">'+x.fileName+'</span>';
					str+='</li>';
					$("#check_files2").append(str);
					
				}	
				
				
	});
	
}
xh.look_app=function(){
	$("#check_files2").find('li').remove();
	var month=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	//$("#show_up_floder").toggle();
	//$("#file_title").text("考核文件>>通信保障报告");
	$.get("../../check/bs_app_file?period="+type+"&month="+month).success(
			function(response) {
				if(response.totals<1){
					$("#check_files2").append("<li>没有文件</li>");
					return;
				}
				for(var i=0;i<response.totals;i++){
					var x=response.files[i];
					var str='<li style="margin-top:10px;">';
					str+='<img src="../../Resources/images/icon/16/doc.png">';
					str+='<span style="cursor: pointer;" title="点击预览"  onclick="xh.editDoc(\''+x.filePath+'\')">'+x.fileName+'</span>';
					str+='</li>';
					$("#check_files2").append(str);
					
				}	
				
				
	});
	
}
xh.editDoc=function(path){
	console.log(path);
	
	if(path.toLowerCase().indexOf("doc")!=-1){
		console.log("doc")
		POBrowser.openWindowModeless(xh.getUrl()+'/office/editWord?path='+
				path,'width=1200px;height=800px;');
	}else if(path.toLowerCase().indexOf("xls")!=-1){
		console.log("xls")
		POBrowser.openWindowModeless(xh.getUrl()+'/office/editExcel?path='+
				path,'width=1200px;height=800px;');
	}else if(path.toLowerCase().indexOf("pdf")!=-1){
		console.log("pdf")
		POBrowser.openWindowModeless(xh.getUrl()+'/office/previewPDF?path='+
				path,'width=1200px;height=800px;');
	}/*else if(path.toLowerCase().indexOf("jpeg")!=-1){
		$("#showPicWin").modal('show');
		$scope.img_src=xh.getUrl()+"/"+path;
	}else if(path.toLowerCase().indexOf("jpg")!=-1){
		$("#showPicWin").modal('show');
		$scope.img_src=xh.getUrl()+"/"+path;
	}else if(path.toLowerCase().indexOf("png")!=-1){
		$("#showPicWin").modal('show');
		$scope.img_src=xh.getUrl()+"/"+path;
	}*/else{
		alert("该文件类型不支持在线预览")
	}
	
}
xh.period=function(){
	var tt=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	type=parseInt(type);
	//2019-10 属于第六期，
	var a=parseInt(tt.split("-")[0]);
	var b=parseInt(tt.split("-")[1]);
	var rs=0;
	if(type==3){
		if(b>=8){
			rs= (2+a-2015);
		}else{
			rs= (1+a-2015);
		}
	}else if(type==4){
		if(b>=5){
			rs= (2+a-2018);
		}else{
			rs= (1+a-2018);
		}
	}
	
	return xh.toChinesNum(rs);
	
}
//年度
xh.year=function(){
	//2019-10 属于第六期，
	var tt=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	type=parseInt(type);
	var a=parseInt(tt.split("-")[0]);
	var b=parseInt(tt.split("-")[1]);
	var rs=0;
	if(type==3){
		if(b>=8){
			rs= a;
		}else{
			rs= a-1;
		}
	}else if(type==4){
		if(b>=5){
			rs= a;
		}else{
			rs= a-1;
		}
	}
	return rs
	
}
xh.jd=function(){
	var tt=$("input[name='month']").val();
	var a=parseInt(tt.split("-")[1]);
	if(a==5){
		return "一";
	}else if(a>=6 && a<=10){
		return "二";
	}else if(a>=11 || a<=4){
		return "三";
	}else{
		return "";
	}
}
xh.getWordName=function(){
	var tt=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	var y=xh.year();
	var x=xh.period();
	type=parseInt(type);
	var doc_name="";
	if(type==3){
       doc_name="成都市应急指挥调度无线通信网三期工程采购项目\r\n";
	   doc_name+="第"+x+"期（"+y+"运维年度）\r\n";
	   doc_name+=tt.split("-")[0]+tt.split("-")[1]+"月例会会议纪要";
	}else if(type==4){
		doc_name="成都市应急指挥调度无线通信网四期工程服务项目\r\n";
		doc_name+="第"+x+"期（"+y+"运维年度）";
		doc_name+="第"+xh.jd()+"阶段 \r\n";
		doc_name+=tt.split("-")[0]+tt.split("-")[1]+"月例会会议纪要";
	}
	console.log("name:"+doc_name)
	return doc_name;
}
xh.add = function() {
    var files=[];	
    var addfileNames=new Array();
    var com=new Array();
	$("#fileArea ul li").each(function(index){
	    var name = $(this).children().first().text();
	    var path = $(this).children(".path").text();
	    if(name!="" && path!=""){
	    	var a={
	    			fileName:name,
	    			filePath:path
	    	}
	    	files.push(a);
	    	addfileNames[index]=name.substring(0,name.indexOf("."));
	    }
	   
	});
	var a=addfileNames.toString();
	var index=0;
	for(var i=0;i<fileNames.length;i++){
		if(a.indexOf(fileNames[i])==-1){
			com[index]=fileNames[i];
			index++;
		}
	}
	var month=$("input[name='month']").val();
	var type=$("select[name='type']").val();
	/*if(files.length<1){
		toastr.error("还没有上传文件", '提示');
		return ;
	}*/
	if(month==""){
		toastr.error("考核月份不能为空", '提示');
		return ;
	}
	if(com.length>0){
		//swal("提示","你还有文件没有上传完，禁止提交，待传文件:\r\n"+com.join("\r\n"),"info");
		swal({
			title : "提示",
			text : "你还有文件没有上传完，待传文件:\r\n"+com.join("\r\n"),
			type : "info",
			showCancelButton : true,
			confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			confirmButtonText : "继续提交",
			cancelButtonText : "取消",
			confirmButtonClass: 'btn btn-success',
			  cancelButtonClass: 'btn btn-danger',
		      closeOnConfirm : true, 
		      closeOnCancel : true
		 
		}, function(isConfirm1) {
			if (isConfirm1===true) {
				$.ajax({
					url : '../../check/add',
					type : 'POST',
					dataType : "json",
					async : true,
					data:{
						time:month,
						type:type,
						name:xh.getWordName(),
						files: JSON.stringify(files)
					},
					success : function(data) {
						
						
						if (data.success) {
							POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/check_create_meet_doc.jsp','width=300px;height=200px;');
							
							//swal("提示","你还有文件没有上传完，禁止提交，待传文件:\r\n"+com.join("\r\n"),"info");
							/*swal({
								title : "提示",
								text : "提交申请成功",
								type : "success",
								showCancelButton : true,
								confirmButtonColor : "#DD6B55",
								confirmButtonText : "提交申请成功，返回列表页面",
								cancelButtonText : "取消",
								 closeOnConfirm : false, 
							     closeOnCancel : true
							
							}, function(isConfirm) {
								if (isConfirm===true) {
									window.location.href="operations_check.html"
								}
							});*/
							
						} else {
							toastr.error(data.message, '提示');
						}
					},
					error : function() {
						toastr.error("系统错误", '提示');
					}
				});
			}
		});
	}else{
		
		$.ajax({
			url : '../../check/add',
			type : 'POST',
			dataType : "json",
			async : true,
			data:{
				time:month,
				type:type,
				name:xh.getWordName(),
				files: JSON.stringify(files)
			},
			success : function(data) {
				if (data.success) {
					POBrowser.openWindowModeless(xh.getUrl()+'/Views/jsp/check_create_meet_doc.jsp','width=300px;height=200px;');
					/*swal({
						title : "提示",
						text : "提交申请成功",
						type : "success",
						showCancelButton : true,
						confirmButtonColor : "#DD6B55",
						confirmButtonText : "提交申请成功，返回列表页面",
						cancelButtonText : "取消",
					    closeOnCancel : true,
					    closeOnConfirm : false
					
					}, function(isConfirm) {
						if (isConfirm) {
							window.location.href="operations_check.html"
						}
					});*/
					
				} else {
					toastr.error(data.message, '提示');
				}
			},
			error : function() {
				toastr.error("系统错误", '提示');
			}
		});
	}
	
};
xh.createMeetDocSussess=function(){
	
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
xh.isAdded = function(name) {
	var success=0;
    var files=[];	
    var addfileNames=new Array();
    var com=new Array();
    name=name.split(".")[0];
	$("#fileArea ul li").each(function(index){
	    var name = $(this).children().first().text();
	    var path = $(this).children(".path").text();
	    if(name!="" && path!=""){
	    	var a={
	    			fileName:name,
	    			filePath:path
	    	}
	    	files.push(a);
	    	addfileNames[index]=name.substring(0,name.indexOf("."));
	    }
	   
	});
	var a=addfileNames.toString();
	var b=fileNames.toString();
	
	if(b.indexOf(name)==-1){
		success=2;//文件名称非必须提交的文件名称
	}
	if(a.indexOf(name)>-1){
		success=1;//数据存在
	}
	return success;
	
};
xh.toChinesNum=function(num){
    let changeNum = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']; //changeNum[0] = "零"
    let unit = ["", "十", "百", "千", "万"];
    num = parseInt(num);
    let getWan = (temp) => {
    let strArr = temp.toString().split("").reverse();
    let newNum = "";
    for (var i = 0; i < strArr.length; i++) {
      newNum = (i == 0 && strArr[i] == 0 ? "" : (i > 0 && strArr[i] == 0 && strArr[i - 1] == 0 ? "" : changeNum[strArr[i]] + (strArr[i] == 0 ? unit[0] : unit[i]))) + newNum;
    }
     return newNum;
   }
   let overWan = Math.floor(num / 10000);
   let noWan = num % 10000;
   if (noWan.toString().length < 4) noWan = "0" + noWan;
   return overWan ? getWan(overWan) + "万" + getWan(noWan) : getWan(num);
}




