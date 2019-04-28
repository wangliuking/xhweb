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