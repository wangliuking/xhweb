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
xh.load = function() {
	var app = angular.module("app", []);
	
	var pageSize = $("#page-limit").val();
	app.controller("xhcontroller", function($scope,$http) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		$scope.time=xh.getLastMonth();
		$scope.id=0;
		$scope.page=1;
		
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			$scope.userL = response;
			$scope.loginUserRoleId = response.roleId;
			$scope.loginUserRoleType = response.roleType;
		});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
		});
		/*跳转到申请进度页面*/
	
		

		
		/*获取记录表*/
		$http.get("../../check/data?time="+$scope.time+"&start=0&limit=" + $scope.count).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			
			
			
			xh.pagging(1, parseInt($scope.totals), $scope);
		});
		
		    
	    $scope.toCheck2 = function (index) {
	    $scope.check_data=$scope.data[index];
	   
	    $("#checkWin2").modal('show');
	    };
		
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search($scope.page);
			$("#table-checkbox").prop("checked", false);
		};
		/*$scope.refreshFiles = function() {
			
			$scope.detailData = $scope.data[$scope.id];
		};*/
		/*跳转到处理页面*/
		$scope.toDeal = function (id) {
			$scope.editData = $scope.data[id];
			window.location.href="operations_check_create.html?user=" +$scope.editData.user+
					"&id="+$scope.editData.id+"" +
					"&checkMonth="+$scope.editData.checkMonth+"&applyId="+$scope.editData.applyId;
	    };
	    $scope.addFile=function(id){
	    	var x = $scope.data[id];
	    	window.location.href="operations_check_add_file.html?" +
	    			"period="+x.type+"&month="+x.checkMonth+"&applyId="+x.applyId
	    }
	    //提交扣分扣款
	    
	    $scope.upScoreAndMoney=function(id){
	    	$scope.checkData = $scope.data[id];
	    	swal({
				title : "提示",
				//text : "确认已经填写了扣分，扣款记录吗？",
				text:"确认整个考核工作都结束了，并关闭流程？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../check/up_score_money',
						type : 'post',
						dataType : "json",
						data : {
							applyId : $scope.checkData.applyId
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

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
	    }
	    //确认扣分扣款
	    $scope.sureScoreAndMoney=function(id){
	    	$scope.checkData = $scope.data[id];
	    	swal({
				title : "提示",
				text : "确认扣分扣款信息正确吗",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../check/sure_score_money',
						type : 'post',
						dataType : "json",
						data : {
							applyId : $scope.checkData.applyId,
							applyId : $scope.checkData.applyId,
							checkUser : $scope.checkData.checkUser
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

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
	    }
		/*跳转到申请进度页面*/
		$scope.toProgress = function (id) {
			$scope.progressData = $scope.data[id];
			//$scope.searchDetail($scope.progressData.checkMonth)
			//$scope.asset_apply_list(id);
			/* $scope.searchMoney($scope.progressData.checkMonth);
			 $scope.searchScore($scope.progressData.checkMonth);*/
			 $scope.time=$scope.progressData.checkMonth;
			$("#progress").modal('show');
	    };
	    $scope.recordScore = function (id) {
			var data = $scope.data[id];
			var url="";
			var a=[];
			for(var i=0;i<data.files.length;i++){
				a.push(data.files[i].fileName);
			}
			
			if(data.type==4){
				url+="operations_check_write4_score.html?applyId="+data.applyId;
				url+="&checkMonth="+data.checkMonth;
				url+="&files="+a.join(",");
				url+="&docName="+data.score_header
			}else if(data.type==3){
				url+="operations_check_write3_score.html?applyId="+data.applyId;
				url+="&checkMonth="+data.checkMonth;
				url+="&files="+a.join(",");
				url+="&docName="+data.score_header;
			}
			//console.log(data.score_header.replace(/\r\n/g,"|"));
			window.localStorage.setItem("docName", data.score_header);
			window.location.href=url;
	    };
	    $scope.recordMoney = function (id) {
			var data = $scope.data[id];
			var url="";
			var a=[];
			for(var i=0;i<data.files.length;i++){
				a.push(data.files[i].fileName);
			}
			if(data.type==4){
				url+="operations_check_write4_money.html?applyId="+data.applyId;
				url+="&checkMonth="+data.checkMonth;
				url+="&files="+a.join(",");
				url+="&docName="+data.money_header;
			}else if(data.type==3){
				url+="operations_check_write3_money.html?applyId="+data.applyId;
				url+="&checkMonth="+data.checkMonth;
				url+="&files="+a.join(",");
				url+="&docName="+data.money_header;
			}
			window.localStorage.setItem("docName", data.money_header);
			window.location.href=url;
	    };
		$scope.showFileWin = function (id) {
			$scope.detailData = $scope.data[id];
			$scope.getEnsureFileList($scope.detailData);
			$scope.getAppFileList($scope.detailData);
			$scope.getBsCheckFileList($scope.detailData);
			$("#filesWin").modal('show');
	    };
	    $scope.showSignWin = function (id) {
			$scope.detailData = $scope.data[id];
			$scope.id=id;
			$("#signWin").modal('show');
	    };
	    $scope.showCheckWin = function (id) {
	    	$scope.checkData=$scope.data[id];	
			$("#checkWin").modal('show');
	    };
	    $scope.showSureFileWin = function (id) {
	    	$scope.checkData=$scope.data[id];	
			$("#sureFileWin").modal('show');
	    };
	    $scope.showScoreMoneySignWin = function (id) {
			var data = $scope.data[id];
			$scope.checkData=$scope.data[id];
			
			$scope.fileName_score="";
			$scope.fileName_money="";
			if(data.type==3){
				$scope.fileName_score=data.score3_fileName;
				$scope.fileName_money=data.money3_fileName;
			}else if(data.type==4){
				$scope.fileName_score=data.score4_fileName;
				$scope.fileName_money=data.money4_fileName;
			}
			console.log("00-->"+$scope.up.o_check_operations_check)
			$("#ScoreMoneySignWin").modal('show');
	    };
	    

		/*下载工作记录*/
		$scope.download = function(path) {
			var index=path.lastIndexOf("/");
			var name=path.substring(index+1,path.length);
			var filename=name
			var downUrl = "../../uploadFile/download?fileName=" + filename + "&filePath=" + path;
			if(xh.isfile(path)){
				window.open(downUrl, '_self','width=1,height=1,toolbar=no,menubar=no,location=no');
			}else{
				toastr.error("文件不存在", '提示');
			}
		};
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
			}else if(path.toLowerCase().indexOf("jpeg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("jpg")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else if(path.toLowerCase().indexOf("png")!=-1){
				$("#showPicWin").modal('show');
				$scope.img_src=xh.getUrl()+"/"+path;
			}else{
				alert("该文件类型不支持在线预览")
			}
			
		}
		$scope.showFile=function(index,tag){
			var data=$scope.data[index];
			var path="";
			if(data.type==3){
				if(tag==1){
					path=data.score3_filePath
				}else if(tag==2){
					path=data.money3_filePath
				}
				else if(tag==3){
					path=data.filePath
				}
			}else if(data.type==4){
				if(tag==1){
					path=data.score4_filePath
				}else if(tag==2){
					path=data.money4_filePath
				}else if(tag==3){
					path=data.filePath
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
		$scope.sealDoc=function(id,path){
			console.log(path);
			var doc=path.substring(path.lastIndexOf(".")+1);
			if(path.toLowerCase().indexOf("doc")>0 
					||path.toLowerCase().indexOf("docx")>0
					||path.toLowerCase().indexOf("xls")>0
					||path.toLowerCase().indexOf("xlsx")>0){
				console.log(doc)
				POBrowser.openWindowModeless(xh.getUrl()+'/office/seal?fileId='+id+'&doc='+doc+'&type=1&path='+
						path,'width=1200px;height=800px;');
			}else{
				alert("该文件类型不支持在线签章")
			}
			
		}
		$scope.getFileList=function(){
			var month=$("input[name='month']").val();
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
						
			});
		}
	
		$scope.getBsCheckFileList=function(data){
			var month=data.checkMonth;
			var type=data.type;
			$http.get("../../check/bscheckfile?period="+type+"&month="+month).success(
					function(response) {
						$scope.bscheck_files = response.files;
						$scope.bscheck_fileTotal = response.totals;
						
						
						
						
			});
		}
		$scope.getEnsureFileList=function(data){
			var month=data.checkMonth;
			var type=data.type;
			$http.get("../../check/bs_ensure_file?period="+type+"&month="+month).success(
					function(response) {
						$scope.ensure_files = response.files;
						$scope.ensure_fileTotal = response.totals;
			});
		}
		//获取月巡检文件
		$scope.getAppFileList=function(data){
			var month=data.checkMonth;
			var type=data.type;
			$http.get("../../check/bs_app_file?period="+type+"&month="+month).success(
					function(response) {
						$scope.app_files = response.files;
						$scope.app_fileTotal = response.totals;
			});
		}
		$scope.sealScoreMoneyDoc=function(tag){
			var data=$scope.checkData;
			var path="";
			if(data.type==3){
				if(tag==1){
					path=data.score3_filePath
				}else if(tag==2){
					path=data.money3_filePath
				}
			}else if(data.type==4){
				if(tag==1){
					path=data.score4_filePath
				}else if(tag==2){
					path=data.money4_filePath
				}
			}
			var doc=path.substring(path.lastIndexOf(".")+1);
			if(path.toLowerCase().indexOf("pdf")==-1){
				console.log("doc")
				POBrowser.openWindowModeless(xh.getUrl()+'/office/signAndSeal?doc='+doc+'&type=1&path='+
						path,'width=1200px;height=800px;');
			}else{
				alert("该文件类型不支持在线预览")
			}
			
		}
		$scope.sealComplete=function(index){
			$scope.checkData=$scope.data[index];
			
			swal({
				title : "提示",
				text : "确认通知管理方查看相关文件？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../check/sealComplete',
						type : 'post',
						dataType : "json",
						data : {
							id : $scope.checkData.id,
							user:$scope.checkData.user
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

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
			
		}
		
		$scope.sealScoreMoneyComplete=function(){
			//$scope.checkData=$scope.data[index];
			
			swal({
				title : "提示",
				text : "确认扣分文件与扣款文件都已经签章了吗？",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消",
				closeOnConfirm: false
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if(isConfirm){
					$.ajax({
						url : '../../check/sealScoreMoneyComplete',
						type : 'post',
						dataType : "json",
						data : {
							id : $scope.checkData.id,
							user:$scope.checkData.user,
							checkUser:$scope.checkData.checkUser,
							checkUser2:$scope.checkData.checkUser2
								
						},
						async : false,
						success : function(data) {
							if (data.success) {
								$("#ScoreMoneySignWin").modal('hide');
								if($scope.userL.roleType==2){
									
									swal("提示", "请填写会议纪要", "success");
									
								}else{
									swal("提示", data.message, "success");
								}
								$scope.refresh();

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
			
		}
		$scope.write_meet=function(index,type){
			var path=$scope.data[index].filePath;
			$scope.checkData=$scope.data[index];
			POBrowser.openWindowModeless(xh.getUrl()+'/check/editMeetWord?type='+type+'&path='+
					path,'width=1200px;height=800px;');
			
		}
		$scope.sureFile=function(){
			var data=$scope.checkData;
			if($("#sureFileWin").find("textarea[name='note']").val()==""){
				toastr.error("会议安排处必须填写内容", '提示');
				return;
			}
			$.ajax({
				url : '../../check/sureFile',
				type : 'post',
				dataType : "json",
				data : {
					id : $scope.checkData.id,
					user:$scope.checkData.user,
					checkUser:$scope.checkData.checkUser,
					note:$("#sureFileWin").find("textarea[name='note']").val()
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();
						$("#sureFileWin").modal('hide')

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("系统错误", '提示');
				}
			});
			
		}
		$scope.signMeetVertical=function(){
			var data=$scope.checkData;
			$.ajax({
				url : '../../check/signMeet',
				type : 'post',
				dataType : "json",
				data : {
					id : data.id,
					user:data.user,
					checkUser3:data.checkUser3,
					status:9,
					checkUser:data.checkUser,
					checkUser2:data.checkUser2,
					writeMeetUser:data.writeMeetUser
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();
						$("#sureFileWin").modal('hide')

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("系统错误", '提示');
				}
			});
			
		}
		$scope.signMeet=function(index,tag){
			var data=$scope.data[index];
			var tag=9;
			var msg="";
			if(data.status==8){
				msg="确认，已经填写了会议纪要，并签字盖章了吗？";
				tag=9
			}else if(data.status==9){
				msg="确认已经签字了";
				tag=10;
			}
			
			swal({
				title : "提示",
				text :msg,
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../check/signMeet',
						type : 'post',
						dataType : "json",
						data : {
							id : data.id,
							user:data.user,
							checkUser3:data.checkUser3,
							status:tag,
							checkUser:data.checkUser,
							checkUser2:data.checkUser2,
							writeMeetUser:data.writeMeetUser
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();
								$("#sureFileWin").modal('hide')

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
			
		}
		$scope.signMeet2=function(){
			var data=$scope.checkData
			$.ajax({
				url : '../../check/signMeet',
				type : 'post',
				dataType : "json",
				data : {
					id : data.id,
					user:data.user,
					status:10,
					checkUser:data.checkUser,
					checkUser2:data.checkUser2
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();
						$("#sureFileWin").modal('hide')

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("系统错误", '提示');
				}
			});
			
		}
		$scope.sureMeet=function(index){
			var data=$scope.data[index];
			$.ajax({
				url : '../../check/sureMeet',
				type : 'post',
				dataType : "json",
				data : {
					id : data.id,
					checkUser2:data.checkUser2
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();
						$("#sureFileWin").modal('hide')

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("系统错误", '提示');
				}
			});
			
		}
		$scope.editDoc=function(index,tag){
			var data=$scope.data[index];
			var path="";
			if(data.type==3){
				if(tag==1){
					path=data.score3_filePath
				}else if(tag==2){
					path=data.money3_filePath
				}
				else if(tag==3){
					path=data.filePath
				}
			}else if(data.type==4){
				if(tag==1){
					path=data.score4_filePath
				}else if(tag==2){
					path=data.money4_filePath
				}else if(tag==3){
					path=data.filePath
				}
			}
			
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
		/*显示审核窗口*/
		$scope.checkWin = function (id) {
			$scope.checkData=$scope.data[id];			
				$("#checkWin1").modal('show');	
	    };
	    $scope.checkWin2 = function (id) {
			$scope.checkData=$scope.data[id];	
			 $scope.searchMoney($scope.checkData.checkMonth);
			 $scope.searchScore($scope.checkData.checkMonth);
			 $scope.time=$scope.checkData.checkMonth;
			$("#checkWin2").modal('show');	
	    };
	    $scope.check =function(tag){
	    	var title="";
			if(tag==-1){
				title="确定要拒绝考核文件吗？";
			}else{
				title="确认同意该考核文件吗？";
			}
			swal({
				title : "提示",
				text : title,
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../check/check',
						type : 'post',
						dataType : "json",
						data : {
							id : $scope.checkData.id,
							check:tag,
							user:$scope.checkData.user,
							note1:$("textarea[name='note']").val()
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();
								$("#checkWin").modal('hide')

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
		
	    };
		$scope.check4=function(index){
			var data=$scope.data[index];
			$.ajax({
				url : '../../check/check4',
				type : 'post',
				dataType : "json",
				data : {
					id : data.id,
					user:data.checkUser
				},
				async : false,
				success : function(data) {
					if (data.success) {
						toastr.success(data.message, '提示');
						$scope.refresh();

					} else {
						toastr.error(data.message, '提示');
					}
				},
				error : function() {
					toastr.error("系统错误", '提示');
				}
			});
		}
	   
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
			//xh.maskShow();
			$http.get("../../check/data?time="+time+"&start="+start+"&limit=" + pageSize).
			success(function(response){
				//xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				$scope.detailData=$scope.data[$scope.id];
				$scope.page=page;
				xh.pagging(1, parseInt($scope.totals), $scope);
			});
			/*$http.get("../../business/lend/list?start="+start+"&limit=" + pageSize).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});*/
		};
		//分页点击
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
			$http.get("../../check/data?time="+time+"&start="+start+"&limit=" + pageSize).
			success(function(response){
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
			xh.refresh();
		}, 5000)
	});
	
};
xh.add = function() {
	var fileName=$("#addWin").find("input[name='fileName']").val();
	var month=$("#addWin").find("input[name='month']").val();
	if(fileName==""){
		toastr.error("还没有上传文件", '提示');
		return ;
	}
	if(month==""){
		toastr.error("考核月份不能为空", '提示');
		return ;
	}
	
	$.ajax({
		url : '../../check/add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			//data:xh.serializeJson($("#addForm").serializeArray()) //将表单序列化为JSON对象
			month:$("#addWin").find("input[name='month']").val(),
			comment:$("#addWin").find("textarea[name='comment']").val(),
			fileName:$("#addWin").find("input[name='fileName']").val(),
			filePath:$("#addWin").find("input[name='path']").val()
		},
		success : function(data) {
			if (data.success) {
				toastr.success(data.message, '提示');
				$("#addWin").modal('hide');
				xh.refresh();
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};
xh.sealDoc= function(id) {	
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../check/sealFile',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			id:id
		},
		success : function(data) {
			if (data.success) {
				toastr.success(data.message, '提示');
				xh.refresh();
				
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("系统错误", '提示');
		}
	});
};
xh.signMeetVertical=function(){
	var $scope = angular.element(appElement).scope();
	$scope.signMeetVertical();
}


/*上传文件*/
xh.upload = function() {
	if($("input[type='file']").val()==""){
		toastr.error("你还没选择文件", '提示');
		return;
	}
	xh.maskShow();
	$.ajaxFileUpload({
		url : '../../asset/upload', //用于文件上传的服务器端请求地址
		secureuri : false, //是否需要安全协议，一般设置为false
		fileElementId : 'filePath', //文件上传域的ID
		dataType : 'json', //返回值类型 一般设置为json
		type:'POST',
		success : function(data, status) //服务器成功响应处理函数
		{
			xh.maskHide();
			if(data.success){
				$("#uploadResult").html(data.message);
				$("input[name='result']").val(1);
				$("input[name='fileName']").val(data.fileName);
				$("input[name='path']").val(data.filePath);
			}else{
				$("#uploadResult").html(data.message);
			}
			
		},
		error : function(data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
};
xh.download=function(){
	var $scope = angular.element(appElement).scope();
	var filename=$scope.checkData.fileName;
	var filepath = "/Resources/upload/assetCheck/" + filename;
	var downUrl = "../../uploadFile/download?fileName=" + filename + "&filePath=" + filepath;
	if(xh.isfile(filepath)){
		window.open(downUrl, '_self','width=1,height=1,toolbar=no,menubar=no,location=no');
	}else{
		toastr.error("文件不存在", '提示');
	}
	
};

// 刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
	

};
xh.signMeet2=function(){
	var $scope = angular.element(appElement).scope();
	$scope.signMeet2();
}
/* 数据分页 */
xh.pagging = function(currentPage, totals, $scope) {
	var pageSize = $("#page-limit").val();
	if(pageSize==undefined){
		pageSize=15;
	}
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
xh.getLastMonth = function() {
	var today = new Date();
	var yesterday_milliseconds = today.getTime(); // -1000*60*60*24

	var yesterday = new Date();
	yesterday.setTime(yesterday_milliseconds);

	var strYear = yesterday.getFullYear();

	var strDay = yesterday.getDate();
	var strMonth = yesterday.getMonth()+1;

	if((strMonth-1)==0){
		strYear-=1;
	}
	strMonth=strMonth-1;
	if (strMonth < 10) {
		strMonth = "0" + strMonth;
	}
	if (strDay < 10) {
		strDay = "0" + strDay;
	}
	
	var strYesterday = strYear + "-" + strMonth ;
	return strYesterday;
}
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
xh.print_score3=function() {
	var LODOP = getLodop();
	LODOP.PRINT_INIT("wwwww");
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("10%", "3%", "95%", "100%", document.getElementById("print_score3").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_score4=function() {
	var LODOP = getLodop();
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("10%", "3%", "95%", "100%", document.getElementById("print_score4").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_money3=function() {
	var LODOP = getLodop();
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("10%", "3%", "95%", "100%", document.getElementById("print_money3").innerHTML);
	 LODOP.PREVIEW();  	
};
xh.print_money4=function() {
	var LODOP = getLodop();
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
	LODOP.ADD_PRINT_TABLE("10%", "3%", "95%", "100%", document.getElementById("print_money4").innerHTML);
	 LODOP.PREVIEW();  	
};