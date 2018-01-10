if (!("xh" in window)) {
	window.xh = {};
};
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
		"extendedTimeOut" : "2000",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut",
		"progressBar" : true,
};
/* 登录系统 */
xh.login = function() {
	
	$("#login-btn").toggleClass("disabled");
	$.ajax({
		url : '../web/login',
		type : 'GET',
		dataType : "json",
		data : $("#loginForm").serializeArray(),
		async : false,
		success : function(data) {
			$("#login-btn").button("reset");
			if (data.success) {
				window.location.href = "../main.html";
			} else {
				toastr.error(data.message, '提示');
				/*swal({
					title : "提示",
					text : "用户名或者密码错误!",
					type : "error"
				});*/
				/*$("#login-btn").val("登录");
				$("#login-btn").toggleClass("disabled");*/
			}
		},
		error : function() {
			toastr.error("登录超时", '提示');
			$("#login-btn").button("reset");
			/*$("#login-btn").val("登录");
			$("#login-btn").toggleClass("disabled");*/

		}
	});
}