window.router = bui.router();
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
var uiMask = bui.mask();
bui.ready(function(){
	
	/*var params = router.getPageParams();
	
	console.log("jsdfsdbdb->"+params)*/

    // 加载页面到div容器里面, 更多参数请查阅API
    router.init({
        id: "#bui-router"
    });
  
    /* 获取用户权限 */
    getUserPower();
    getUserInfo();
    

    bui.load({ url: "table.html", param: {} });
    bui.btn({ id: "#bui-router", handle: ".bui-btn" }).load();
    $("#bui-router").on("click", ".btn-back", function(e) {
        // 支持后退多层,支持回调
        bui.back();
    })
})
// 事件类定义
function bind() {
    // 绑定页面的所有按钮有href跳转
    bui.btn({ id: "#bui-router", handle: ".bui-btn" }).load();

    // 统一绑定页面所有的后退按钮
    $("#bui-router").on("click", ".btn-back", function(e) {

        // 支持后退多层,支持回调
        bui.back();
    })
 
}
function getUserPower(){
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
function getUserInfo(){
	  bui.ajax({
	        url: xh.getUrl()+"web/loginUserInfo",
	        data: {}
	    }).then(function(res){
	    	gl_para.userL=res
	    },function(res,status){
	        console.log(status);
	     // status = "timeout" || "error" || "abort", "parsererror"
	    })
}