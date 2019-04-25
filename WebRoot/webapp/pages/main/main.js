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
var gl_para={};
var str="";
loader.define(function(require,exports,module) {

    var pageview = {};
    var params = router.getPageParams();
    if(params.userName!=undefined){
    	login(params);
    }; 

    // 模块初始化定义
    pageview.init = function () {
    	userPower();
    	userInfo();
    }
    // 初始化
    pageview.init();
    // 输出模块
    return pageview;
})
function login(params){
	$.ajax({
		url : xh.getUrl()+'web/login',
		type : 'POST',
		dataType : "json",
		data : {
			username : params.userName,
			password : params.password,
			ToSign :"",
			Signature :""
		},
		/* data : $("#loginForm").serializeArray(), */
		async : false,
		success : function(data) {
			if (data.success) {
				//toastr.success("success", '提示');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("登录超时", '提示');

		}
	});
}

function userPower(){
	bui.ajax({
        url: xh.getUrl()+"web/loginUserPower",
        data: {}
    }).then(function(res){
    	gl_para.up = res;
    },function(res,status){
        console.log(status);
     // status = "timeout" || "error" || "abort", "parsererror"
    })
}

function userInfo(){
	bui.ajax({
        url: xh.getUrl()+"web/loginUserInfo",
        method:'GET',
        dataType : "json",
        data: {},
        async : false
    }).then(function(res){
    	gl_para.userL=res;
		if(parseInt(res.roleType)==2){
        	str="成都市软件产业发展中心";
        	gl_para.sendUnit=str;
        }else if(parseInt(res.roleType)==3 || parseInt(res.roleType)==0){
        	str="成都亚光电子股份有限公司";
        	gl_para.sendUnit=str;
		}
    },function(res,status){
        console.log(status);
    })
}
