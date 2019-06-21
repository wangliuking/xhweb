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
/*	var type = $("#type").val();
	var name = $("#name").val();
	var model = $("#model").val();
	var serialNumber = $("#serialNumber").val();
	var from = $("#from").val();
	var status = $("#status").val();
	var pageSize = $("#page-limit").val();*/

    app.filter('upp', function() { //可以注入依赖
        return function(text) {
            if(text=="" || text==null)
                return "";
            else
                return parseFloat(text);
        };
    });

    app.filter('changeValue', function() { //可以注入依赖
        return function(text) {
        	if(text == null || text == ""){
				return "";
			}else{
                //console.log(text);
                var arr1 = text.split("（");
                var arr2 = arr1[1].split("）");
                var arr3 = arr2[0].split(" ");
                return arr3[1];
			}
        };
    });

    app.filter('changePeriod', function() { //可以注入依赖
        return function(text) {
            if("成都市应急调度指挥无线通信网4期项目部" == text){
            	return "成都市应急调度指挥无线通信网四期项目部";
			}else if("成都市应急调度指挥无线通信网3期项目部" == text){
                return "成都市应急调度指挥无线通信网三期项目部";
            }else{
            	return text;
			}
        };
    });
	
	var pageSize = $("#page-limit").val();
    app.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }]);
	app.controller("xhcontroller", function($scope,$http,$location) {
		xh.maskShow();
		$scope.count = "15";//每页数据显示默认值
		$scope.businessMenu=true; //菜单变色

		//获取登录用户
		$http.get("../../web/loginUserInfo").
		success(function(response){
			xh.maskHide();
			$scope.userNameAdd = response.userName;
			$scope.telAdd = response.tel;
			$scope.loginUser = response.user;
			$scope.loginUserRoleType = response.roleType;
			$scope.roleId = response.roleId;
			//console.log(response);
		});
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
					//console.log(response);
		});

		/*获取申请记录表*/
		$http.get("../../checkCut/selectAll?start=0&limit=" + pageSize).
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;
			xh.pagging(1, parseInt($scope.totals), $scope);
		});
		
		/*获取管理方人员列表*/
		$http.get("../../web/user/getUserList?roleId=10002").
		success(function(response){
			$scope.userData_MainManager = response.items;
			$scope.userTotals_MainManager = response.totals;
			if($scope.userTotals_MainManager > 0){
				$scope.user_M=$scope.userData_MainManager[0].user;
			}
		});
		
		/* 刷新数据 */
		$scope.refresh = function(page) {
			$scope.search(page);
		};

		$scope.exportWord = function () {
            var bsId = $("#bsId").val();
            var bsName = $("#bsName").val();
            var status = $('#status option:selected') .val();
            if(status == 100){
                status = "";
            }
            var bsPeriod = $('#bsPeriod option:selected') .val();
            var bsRules = $('#bsRules option:selected') .val();
            var tempCheckCutType = $("#checkCutType").val();
            if(tempCheckCutType == 0){
                tempCheckCutType = "";
            }
			var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if(startTime == "" || endTime == ""){
            	alert("请选择需要导出的时间!");
            	return false;
			}
            window.location.href="../../checkCut/downLoadZipFile?bsId="+bsId+"&bsName="+bsName+"&checked="+status+"&startTime="+startTime+"&endTime="+endTime+"&bsPeriod="+bsPeriod+"&bsRules="+bsRules+"&checkCutType="+tempCheckCutType;
        };

        $scope.sign = function () {
            window.location.href="../../../JSsignature_pad/JSsignature_pad.html";
        }

        /**
		 * 发起签名
         */
        $scope.signNow = function () {
            $.ajax({
                url : '../../checkCut/signNow',
                type : 'get',
                dataType : "json",
                async : false,
                success : function(data) {
                    if (data.success) {
                        //修改person的值
						if($scope.roleId == "301"){
                            $scope.sheetData.persion1 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
						}else if($scope.roleId == "10003"){
                            $scope.sheetData.persion2 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
                        }else if($scope.roleId == "10002"){
                            $scope.sheetData.persion3 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
                        }
                    } else {
                        alert(data.message);
                    }
                }
            });
        }

        //打印
        $scope.print_order=function(persion1,persion2,persion3) {
        	console.log(persion1);
            var id = $scope.sheetData.id;

            var LODOP = getLodop();
            LODOP.PRINT_INIT("故障核减申请书");
            LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
            LODOP.ADD_PRINT_HTML("1%", "2%", "96%", "96%", document.getElementById("print_checkcut").innerHTML);

            LODOP.PREVIEW();

            //更新打印状态
            $.ajax({
                url : '../../checkCut/updatePrintStatusById?id='+id,
                type : 'GET',
                dataType : "json",
                async : true,
                success : function(data) {

                }
            });
        };

		/*重新计算*/
		$scope.calAgain = function () {
            var date1 = $("input[name='breakTime']").val();
            var date2 = $("input[name='restoreTime']").val();    //结束时间
            var date3 = new Date(date2).getTime() - new Date(date1).getTime();   //时间差的毫秒数
            $scope.calcData = Math.round(date3/(1000*60));
        }

		/*跳转到进度页面*/
		$scope.toProgress = function (id) {
			$scope.editData = $scope.data[id];
			$scope.checkData=$scope.editData;
			/*$http.get("../../net/applyProgress?id="+$scope.editData.id).
			success(function(response){
				$scope.progressData = response.items;
				
			});*/
			$scope.progressData=$scope.editData;
			$("#progress").modal('show');
	    };
	    /*是否需要整改*/
		// $scope.dropnetChange=function(){
		// 	var dropnet=$("#checkForm3").find("select[name='dropnet']").val();
		// 	$scope.dropnet=dropnet;
		// };

        $scope.sheetShow = function(id){
            var temp = $scope.data[id];
            $scope.nowIndex = id;
            $scope.nowChecked = temp.checked;
            if(temp.checked>1 && temp.checked!=7){
                //该核减已经审核
                var tempPersion3 = temp.persion3;
                var arr1 = tempPersion3.split("/");
                var arr2 = arr1[1].split(".");
                $scope.checkedMan = arr2[0];
            }else{
                $scope.checkedMan = "";
            }
            $scope.checkCutType = temp.checkCutType;
            var sheetId = temp.id;
            $http.get("../../checkCut/sheetShow?id="+sheetId).success(function (response) {
                $scope.sheetData = response.items;
				console.log($scope.sheetData);
                var data= response.items;  //开始时间
				var date1 = data.breakTime;
                var date2 = data.restoreTime;    //结束时间
                var date3 = new Date(date2).getTime() - new Date(date1).getTime();   //时间差的毫秒数
                var nowMonth = new Date().getMonth()+1;
                var sheetMonth = new Date(date1).getMonth()+1;
                console.log("nowMonth : "+nowMonth);
                console.log("sheetMonth : "+sheetMonth);
                if(sheetMonth<nowMonth){
                    //表单创建时间不在当月
                    $scope.sheetIsUpdate = false;
                }else{
                    $scope.sheetIsUpdate = true;
                }
                //------------------------------
                //计算出相差天数
                var days=Math.floor(date3/(24*3600*1000))
                //计算出小时数
                var leave1=date3%(24*3600*1000)    //计算天数后剩余的毫秒数
                var hours=Math.floor(leave1/(3600*1000))
                //计算相差分钟数
                var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
                var minutes=Math.floor(leave2/(60*1000))
                //计算相差秒数
                var leave3=leave2%(60*1000)      //计算分钟数后剩余的毫秒数
                var seconds=Math.round(leave3/1000)
                //alert(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
                if(date2 == null || date2 == "0000-00-00 00:00:00"){
                    $scope.calcData = 0;
				}else{
                    $scope.calcData = Math.round(date3/(1000*60));
				}

            });
            $("#sheet").modal('show');
        }

        /*显示依据*/
        $scope.showContent = function (id) {
            $scope.contentData = $scope.data[id];
            console.log($scope.contentData);
            $("input[name='result']").val(1);
            $("input[name='fileName']").val($scope.data[id].fileName1+$scope.data[id].fileName2+$scope.data[id].fileName3);
            $("input[name='path']").val($scope.data[id].filePath1);

            var fileName1Arr = "";
            var fileName2Arr = "";
            var fileName3Arr = "";
            if($scope.data[id].fileName1 != null){
                var tempArr = $scope.data[id].fileName1.split(";");
                fileName1Arr = tempArr.splice(0,tempArr.length-1);
            }
            if($scope.data[id].fileName2 != null){
                var tempArr = $scope.data[id].fileName2.split(";");
                fileName2Arr = tempArr.splice(0,tempArr.length-1);
            }
            if($scope.data[id].fileName3 != null){
                var tempArr = $scope.data[id].fileName3.split(";");
                fileName3Arr = tempArr.splice(0,tempArr.length-1);
            }
            $scope.imgListData = fileName1Arr.concat(fileName2Arr).concat(fileName3Arr);
            console.log($scope.imgListData)
            $("#showContent").modal('show');
        }

		/*显示审核窗口*/
		$scope.checkWin = function (id) {
			$scope.checkData = $scope.data[id];
			//$http.get("../../web/user/userlist10002").
			$http.get("../../web/user/getUserList?roleId=10003").
			success(function(response){
				$scope.userData = response.items;
				$scope.userTotals = response.totals;
				if($scope.userTotals>0){
					$scope.user=$scope.userData[0].user;
				}
			});
            if($scope.checkData.checked==-2){
                $("#checkWin-2").modal('show');
            }
            if($scope.checkData.checked==-1){
                $("#checkWin-2").modal('show');
            }
            if($scope.checkData.checked==0){
                $("#checkWin1").modal('show');
            }
			if($scope.checkData.checked==1){
				$("#checkWin2").modal('show');
			}
			if( $scope.checkData.checked==2){
				$("#checkWin3").modal('show');
			}
			if($scope.checkData.checked==3){
				$("#checkWin4").modal('show');
			}
            if($scope.checkData.checked==7){
                $("#add").modal('show');
            }

	    };
		/* 显示修改model */
		$scope.editModel = function(id) {
			$scope.editData = $scope.data[id];
			$scope.type = $scope.editData.type.toString();
			$scope.from = $scope.editData.from.toString();
		};
		/* 显示修改model */
		$scope.showEditModel = function() {
			var checkVal = [];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					checkVal.push($(this).attr("index"));
				}
			});
			if (checkVal.length != 1) {
				/*swal({
					title : "提示",
					text : "只能选择一条数据",
					type : "error"
				});*/
				toastr.error("只能选择一条数据", '提示');
				return;
			}
			$("#edit").modal('show');
			$scope.editData = $scope.data[parseInt(checkVal[0])];
			
			$scope.type = $scope.editData.type.toString();
			$scope.from = $scope.editData.from.toString();
		};
		/* 删除 */
		$scope.delBs = function(id) {
			swal({
				title : "提示",
				text : "确定要删除该记录吗？",
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
						url : '../../checkCut/deleteCheckCutById?id='+id,
						type : 'get',
						dataType : "json",
						async : false,
						success : function(data) {
                            if (data.success) {
                                toastr.success('删除成功', '提示');
                                $scope.refresh(1);
                            } else {
                                toastr.error('删除失败', '提示');
                            }
                            /*$.ajax({
                                url : '../../checkCut/updateCheckTag',
                                type : 'post',
                                dataType : "json",
                                data : {
                                    id : id
                                },
                                async : false,
                                success : function(data) {
                                    if (data.success) {
                                        toastr.success('删除成功', '提示');
                                        $scope.refresh();
                                    } else {
                                        toastr.error('删除失败', '提示');
                                    }
                                },
                                error : function() {
                                    $scope.refresh();
                                }
                            });*/
						}
					});
				}
			});
		};
		/* 查询数据 */
		$scope.search = function(page) {
			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			var bsId = $("#bsId").val();
			var bsName = $("#bsName").val();
            var status = $('#status option:selected') .val();
            var bsPeriod = $('#bsPeriod option:selected') .val();
            var bsRules = $('#bsRules option:selected') .val();
            var checkCutType = $('#checkCutType option:selected') .val();
            if(status == 100){
            	status = "";
			}
			if(checkCutType == 0){
                checkCutType = "";
			}
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
			frist = 0;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			console.log("================");
            console.log(start+"~~~"+page);
            console.log("================");
			$http.get("../../checkCut/selectAll?start="+start+"&limit=" + limit+"&bsId="+bsId+"&bsName="+bsName+"&checked="+status+"&startTime="+startTime+"&endTime="+endTime+"&bsPeriod="+bsPeriod+"&bsRules="+bsRules+"&checkCutType="+checkCutType).
			success(function(response){
				xh.maskHide();
				$scope.data = response.items;
				$scope.totals = response.totals;
				xh.pagging(page, parseInt($scope.totals), $scope);
			});
		};
		$scope.download = function() {
			xh.download();
		}
		//分页点击
		$scope.pageClick = function(page, totals, totalPages) {
            var bsId = $("#bsId").val();
            var bsName = $("#bsName").val();
            var status = $('#status option:selected') .val();
            var bsPeriod = $('#bsPeriod option:selected') .val();
            var bsRules = $('#bsRules option:selected') .val();
            var checkCutType = $('#checkCutType option:selected') .val();
            if(status == 100){
                status = "";
            }
            if(checkCutType == 0){
                checkCutType = "";
            }
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();

			var pageSize = $("#page-limit").val();
			var start = 1, limit = pageSize;
			page = parseInt(page);
			if (page <= 1) {
				start = 0;
			} else {
				start = (page - 1) * pageSize;
			}
			xh.maskShow();
			$http.get("../../checkCut/selectAll?start="+start+"&limit=" + limit+"&bsId="+bsId+"&bsName="+bsName+"&checked="+status+"&startTime="+startTime+"&endTime="+endTime+"&bsPeriod="+bsPeriod+"&bsRules="+bsRules+"&checkCutType="+checkCutType).
			success(function(response){
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
			});
		};
	});
};

/*修改核减申请表*/
xh.sheetChange = function() {
    var bean={
        id:$("input[name='sheetId']").val(),
        bsId:$("input[name='bsIdTemp']").val(),
        name:$("input[name='name']").val(),
        hometype:$("input[name='hometype']").val(),
        transfer:$("input[name='transfer']").val(),
        transferCompare:$("input[name='transferCompare']").val(),
        transferOne:$("input[name='transferOne']").val(),
        transferTwo:$("input[name='transferTwo']").val(),
        powerOne:$("input[name='powerOne']").val(),
        powerTimeOne:$("input[name='powerTimeOne']").val(),
        powerTwo:$("input[name='powerTwo']").val(),
        powerTimeTwo:$("input[name='powerTimeTwo']").val(),
        maintainTime:$("input[name='maintainTime']").val(),
        isPower:$("input[name='isPower']").val(),
        firstDesc:$("input[name='firstDesc']").val(),
        desc:$("div[name='desc']").text(),
        breakTime:$("input[name='breakTime']").val(),
        restoreTime:$("input[name='restoreTime']").val(),
        checkCutTime:$("input[name='checkCutTime']").val(),
        alarmTime:$("input[name='alarmTime']").val(),
        situation:$("div[name='situation']").text(),
        rules:$("select[name='rules'] option:selected").text(),
        period:$("div[name='period']").text(),
        applyTime:$("div[name='applyTime']").text(),
        suggest:$("div[name='suggest']").text(),
        persion3:$("input[name='persion3']").val(),
        persion1:$("input[name='persion1']").val(),
        persion2:$("input[name='persion2']").val()
    }
    $.ajax({
        url : '../../checkCut/sheetChange',
        type : 'POST',
        dataType : "json",
        data : {"bean" : JSON.stringify(bean)},
        success : function(data) {
            $("#checkCut-btn").button('reset');
            //$('#sheet').modal('hide');
            //var page = $(".page.active").find("a").text();
            //xh.refresh(page);
            toastr.success(data.message, '提示');
        },
        error : function() {
            $("#checkCut-btn").button('reset');
        }
    });
};

/*更新核减依据*/
xh.showContent = function() {
    $.ajax({
        url : '../../checkCut/updateCheckContent',
        type : 'POST',
        dataType : "json",
        async : true,
        data : $("#showContentForm").serializeArray(),
        success : function(data) {
            $("#showContent_btn").button('reset');
            if (data.result ==1) {
                $('#showContent').modal('hide');
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
                toastr.success(data.message, '提示');
            } else {
                toastr.error(data.message, '提示');
            }
        },
        error : function() {
            $("#add_btn").button('reset');
        }
    });
};

/*运维组发起请求审核*/
xh.add = function() {
    var data = $("#addForm").serializeArray();
    console.log(data);
	$.ajax({
		url : '../../checkCut/insertCheckCut',
		type : 'POST',
		dataType : "json",
		async : true,
		data : $("#addForm").serializeArray(),
		success : function(data) {
			$("#add_btn").button('reset');
			if (data.result ==1) {
				$('#add').modal('hide');
				$("input[name='result']").val(1);
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
				toastr.success(data.message, '提示');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			$("#add_btn").button('reset');
		}
	});
};

xh.checkneg2 = function() {
    $.ajax({
        url : '../../checkCut/checkedNegTwo',
        type : 'POST',
        dataType : "json",
        async : true,
        data:$("#checkForm-2").serializeArray(),
        success : function(data) {

            if (data.result ==1) {
                $('#checkWin-2').modal('hide');
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
                toastr.success(data.message, '提示');

            } else {
                toastr.error(data.message, '提示');
            }
        },
        error : function() {
        }
    });
};

xh.check1 = function() {
    var $scope = angular.element(appElement).scope();
    //复核前自动签名
    $scope.signNow();
    $scope.$apply();
    $.ajax({
        url : '../../checkCut/checkedOne',
        type : 'POST',
        dataType : "json",
        async : false,
        data:$("#checkForm1").serializeArray(),
        success : function(data) {

            if (data.result ==1) {
                //执行保存操作
                xh.sheetChange();
                $('#checkWin1').modal('hide');
                //$('#sheet').modal('hide');
                var page = $(".page.active").find("a").text();
                xh.refresh(page);

            } else {
                toastr.error("创建失败", '提示');
            }
        },
        error : function() {
        }
    });
};

xh.check2 = function() {
    var $scope = angular.element(appElement).scope();
    //审核前自动签名
    $scope.signNow();
    $scope.$apply();
	$.ajax({
		url : '../../checkCut/checkedTwo',
		type : 'POST',
		dataType : "json",
		async : false,
		data:$("#checkForm2").serializeArray(),
		success : function(data) {

			if (data.result ==1) {
                //执行保存操作
                xh.sheetChange();
				$('#checkWin2').modal('hide');
                //$('#sheet').modal('hide');
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
			} else {
				toastr.error("创建失败", '提示');
			}
		},
		error : function() {
		}
	});
};
/*运维负责人发起审批*/
xh.check3 = function() {
	$.ajax({
		url : '../../checkCut/checkedThree',
		type : 'POST',
		dataType : "json",
		async : true,
		data:$("#checkForm3").serializeArray(),
		success : function(data) {

			if (data.result ==1) {
				$('#checkWin3').modal('hide');
				$("input[name='result']").val(1);
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};
/*管理方审批*/
xh.check4 = function() {
	$.ajax({
		url : '../../checkCut/checkedFour',
		type : 'POST',
		dataType : "json",
		async : true,
		data:$("#checkForm4").serializeArray(),
		success : function(data) {
			if (data.result ==1) {
				$('#checkWin4').modal('hide');
                var page = $(".page.active").find("a").text();
                xh.refresh(page);
				toastr.success(data.message, '提示');

			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
		}
	});
};

/* 上传文件 */
xh.upload = function(index) {
    var $scope = angular.element(appElement).scope();
    if (index == 0) {
        path = 'filePath0';
        note = 'uploadResult0';
    }
    if (index == -2) {
        path = 'filePath-2';
        note = 'uploadResult-2';
    }
	if (index == 1) {
		path = 'filePath1';
		note = 'uploadResult1';
	}
	if (index == 3) {
		path = 'filePath2';
		note = 'uploadResult2';
	}
	if (index == 4) {
		path = 'filePath3';
		note = 'uploadResult3';
	}
    if (index == 5) {
        path = 'filePath4';
        note = 'uploadResult4';
    }
	if ($("#" + path).val() == "") {
		toastr.error("你还没选择文件", '提示');
		return;
	}
	xh.maskShow();
	$.ajaxFileUpload({
		url : '../../checkCut/upload', // 用于文件上传的服务器端请求地址
		secureuri : false, // 是否需要安全协议，一般设置为false
		fileElementId : path, // 文件上传域的ID
		dataType : 'json', // 返回值类型 一般设置为json
		type : 'POST',
		success : function(data, status) // 服务器成功响应处理函数
		{
			// var result=jQuery.parseJSON(data);
			console.log(data.fileName)
			xh.maskHide();
			if (data.success) {
				$("#"+note).html(data.message);
				$("input[name='result']").val(1);
				$("input[name='fileName']").val(data.fileName);
				$("input[name='path']").val(data.filePath);
				if(index == 0){
                    var tempArr = data.fileName.split(";");
                    $scope.imgListData = tempArr.splice(0,tempArr.length-1);
                    console.log($scope.imgListData)
                    $scope.$apply();
                }
			} else {
				$("#"+note).html(data.message);
			}

		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			alert(e);
		}
	});
};
xh.download=function(){
	var $scope = angular.element(appElement).scope();
	var checked = $scope.checkData.checked;
	var fileName = null;
	if(checked != -1){
		if(checked == 0 && $scope.checkData.fileName1!=null){
			fileName = $scope.checkData.fileName1;
		}
		else if(checked == 2 && $scope.checkData.fileName2!=null){
			fileName = $scope.checkData.fileName2;
		}
		else if(checked == 4 && $scope.checkData.fileName3!=null){
			fileName = $scope.checkData.fileName3;
		}
	}
	var filepath = "/Resources/upload/CheckCut/" + fileName;
	var downUrl = "../../uploadFile/download?fileName=" + fileName + "&filePath=" + filepath;
	if(xh.isfile(filepath)){
		window.open(downUrl, '_self','width=1,height=1,toolbar=no,menubar=no,location=no');
	}else{
		toastr.error("文件不存在", '提示');
	}
};

// 刷新数据
xh.refresh = function(page) {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh(page);
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


