window.router = bui.router();

var gl_para={};
var str="";
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
bui.ready(function(){
    // 初始化路由
    router.init({
        id: "#bui-router"
    });
    var params = router.getPageParams();
   // console.log("dddd->"+JSON.stringify(params))
    if(params.userName!=undefined){
    	login(params);
    }; 
    //绑定事件
    bind();
    bui.load({ url: "pages/frist/index.html", param: {} });
    //获取用户信息
    getUserInfo();
    //获取用户权限
    getUserPower();
})

// 事件类定义
function bind() {
    // 绑定页面的所有按钮有href跳转
    bui.btn({id:"#bui-router",handle:".bui-btn"}).load();

    // 统一绑定页面所有的后退按钮
     $("#bui-router").on("click",".btn-back",function (e) {
        bui.back();
    })
    $("#bui-router").on("click",".btn-back-home",function (e) {
    	bui.back({
    		callback:function(){
    		}
    	});
        //router.load({ url: xh.getUrl()+"webapp/index.html",param:{}});
    	/*loader.require(["pages/main/main"],function(res){
			console.log("d->"+JSON.stringify(res))
        })*/
    })
}
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
		

		}
	});
}
