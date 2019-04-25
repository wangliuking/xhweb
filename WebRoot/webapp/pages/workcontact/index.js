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
bui.ready(function(){
	
	/*var params = router.getPageParams();
	
	console.log("jsdfsdbdb->"+params)*/

    // 加载页面到div容器里面, 更多参数请查阅API
    router.init({
        id: "#bui-router"
    });
    $.get("../../../web/loginUserInfo").success(function(res) {
    	gl_para.userL=res;
		if(parseInt(res.roleType)==2){
        	str="成都市软件产业发展中心";
        	gl_para.sendUnit=str;
        	
        
        }else if(parseInt(res.roleType)==3 || parseInt(res.roleType)==0){
        	str="成都亚光电子股份有限公司";
        	gl_para.sendUnit=str;
		}
		
	});
    /* 获取用户权限 */
	$.get("../../../web/loginUserPower").success(function(response) {
		gl_para.up = response;
		
	});
    bui.load({ url: "table.html", param: {} });
    bui.btn({ id: "#bui-router", handle: ".bui-btn" }).load();
    $("#bui-router").on("click", ".btn-back", function(e) {
        // 支持后退多层,支持回调
        bui.back();
    })
       // 监听页面所有后退事件
  /* router.on("back",function(e){
    router.refresh();
    bui.refresh();
  })*/
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


/*
bui.ready(function() {
    // 初始化路由
    router.init({
        id: "#bui-router",
        progress: true,
        hash: true,
    })

    // 绑定事件
    bind();

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
})*/
