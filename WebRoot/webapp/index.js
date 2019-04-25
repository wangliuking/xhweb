window.router = bui.router();

var gl_para={};
var str="";
bui.ready(function(){
    // 初始化路由
    router.init({
        id: "#bui-router"
    })

    // 绑定事件
    bind();
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
        // 支持后退多层,支持回调
    	console.log("后退：d")
        bui.back({
        	name:'table'
        });
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