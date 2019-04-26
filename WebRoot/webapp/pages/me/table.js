
loader.define(function(require,exports,module){
	var pageview = {}, uiList="",bs="";
	getUserInfo();
	pageview.init = function () {
		 var params = router.getPageParams();
		 bs=bui.store({
	            scope:'page',
	            data:{
	            	userL:gl_para.userL
	            }
		 })
		 
	 };
	 pageview.bind=function(){
		 
	 }
	// 初始化
	pageview.init();
	pageview.bind();
	 // 输出模块
    return pageview;
});
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
