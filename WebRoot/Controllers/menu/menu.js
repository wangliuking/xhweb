/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var appElement = document.querySelector('[ng-controller=menu]');
xh.load = function() {
	var app = angular.module("app", []);
	app.controller("menu", function($scope, $http) {		
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			$scope.loginUser = response.user;
			$scope.loginUserVpnId = response.vpnId;
			$scope.roleId = response.roleId;	
			$scope.roleType = response.roleType;	
		});
		$http.get("../../web/webMenu").success(function(response) {
			$scope.menu=response.items;
		
		});
		

	});
};
xh.month_report=function(time){
	POBrowser.openWindowModeless(xh.getUrl()+'/word/showReport?time='+time,'width=1200px;height=800px;');
}
//基站运行记录
xh.excelToBsRun = function() {
	xh.maskShow();
	$("#btn-run").button('loading');
	$.ajax({
		url : '../../bsstatus/ExcelToStationStatus',
		type : 'get',
		dataType : "json",
		data : {},
		async : false,
		success : function(data) {

			$("#btn-run").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-run").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.server_excel = function(time) {
	xh.maskShow();
	$("#btn-run").button('loading');
	$.ajax({
		url : '../../server/excel_report',
		type : 'get',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#btn-run").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#btn-run").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.bs_month_excel = function(time) {
	xh.maskShow();
	$("#excel-month-bs-btn").button('loading');
	$.ajax({
		url : '../../report/month/excel_month_bs',
		type : 'get',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-bs-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				/*window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;*/
				swal({
					title : "提示",
					text : "文件生成成功",
					type : "info"
				});

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-bs-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.inspection_month_excel = function(time) {
	xh.maskShow();
	$("#excel-month-inspection-btn").button('loading');
	$.ajax({
		url : '../../report/month/excel_month_inspection',
		type : 'get',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-inspection-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-inspection-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};

xh.inspection_msc_month_excel = function(time) {
	xh.maskShow();
	$("#excel-month-inspection-msc-btn").button('loading');
	$.ajax({
		url : '../../app/excel_msc',
		type : 'POST',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-inspection-msc-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-inspection-msc-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
//文件下载
jQuery.download = function(url, method, zipName, filename){
    jQuery('<form action="'+url+'" method="post">' +  // action请求路径及推送方法
                '<input type="text" name="zipName" value="'+zipName+'"/>' + // 文件路径
                '<input type="text" name="fileName" value="'+filename+'"/>' + // 文件名称
            '</form>')
    .appendTo('body').submit().remove();
};
xh.month_excel = function(time) {
	xh.maskShow();
	$("#excel-month-report-info-btn").button('loading');
	$.ajax({
		url : '../../word/monthword',
		type : 'POST',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-report-info-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
					+ data.pathName;
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-report-info-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
xh.bs_month_inspection_excel = function(time) {
	xh.maskShow();
	$("#excel-month-bs-inspection-btn").button('loading');
	$.ajax({
		url : '../../app/excel_bs',
		type : 'POST',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			$("#excel-month-bs-inspection-btn").button('reset');
			xh.maskHide();
			if (data.success) {
				 $.download('../../app/download', 'post', data.zipName, data.fileName); // 下载文件
				/*window.location.href = "../../app/download?zipName="
						+ data.zipName+"&fileName="+data.fileName;*/
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			$("#excel-month-bs-inspection-btn").button('reset');
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
//导出运维通讯录
xh.address_excel = function(time) {
	xh.maskShow();
	$.ajax({
		url : '../../contacts/excel_contacts',
		type : 'POST',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			xh.maskHide();
			if (data.success) {
				window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};

//导出运维资源配置
xh.config_excel = function(time) {
	xh.maskShow();
	$.ajax({
		url : '../../report/month/config_excel',
		type : 'POST',
		dataType : "json",
		data : {
			time:time
		},
		async : true,
		success : function(data) {

			xh.maskHide();
			if (data.success) {
				//window.location.href = "../../bsstatus/downExcel?filePath="+ data.pathName;
				swal({
					title : "提示",
					text : "文件生成成功",
					type : "info"
				});
			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			toastr.error("导出失败", '提示');
			xh.maskHide();
		}
	});

};
//导出基站信息
xh.bs_excel = function(time,tag) {
	xh.maskShow();
	if(tag==0){
		$("#excel-month-bs-info-btn").button('loading');
	}else{
		$("#excel-month-bs-info-btn2").button('loading');
	}
	
	var period=$("#excel-month-msc-inspection").find("select[name='period']").val();
	
	$.ajax({
		url : '../../bs/excel_bs_info',
		type : 'GET',
		dataType : "json",
		data : {
			time:time,
			period:period
		},
		async : true,
		success : function(data) {
			$("#excel-month-bs-info-btn").button('reset');
			$("#excel-month-bs-info-btn2").button('reset');

			xh.maskHide();
			if (data.success) {
				$("#excel-month-msc-inspection").modal("hide");
				if(tag==0){
					window.location.href = "../../bsstatus/downExcel?filePath="
						+ data.pathName;
				}else{
					swal({
						title : "提示",
						text : "文件生成成功",
						type : "info"
					});
				}
				

			} else {
				toastr.error("导出失败", '提示');
			}
		},
		error : function() {
			toastr.error("导出失败", '提示');
			$("#excel-month-bs-info-btn").button('reset');
			$("#excel-month-bs-info-btn2").button('reset');
			xh.maskHide();
		}
	});

};
