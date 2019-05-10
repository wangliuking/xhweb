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
loader.define(function(require,exports,module) {

    var pageview = {},uiNavtab,store;
    var params = router.getPageParams();
    if(params.userName!=undefined){
    	login(params);
    }; 

    // 模块初始化定义
    pageview.init = function () {
    	userPower();
    	userInfo();
    	store=bui.store({
            scope:'page',
            data:{
                email:notReadEmail()
            }
    })
    	pageview.navTab();
    	 pageview.bind();
    	
    }
    // 底部导航
    pageview.navTab= function() {

        //按钮在tab外层,需要传id
        uiNavtab = bui.tab({
        	id:"#tabFoot",
            menu:"#tabFootNav",
            scroll: false,
            swipe: false,
            animate: true,
            // 1: 声明是动态加载的tab
            autoload: true,
        })

        // 2: 监听加载后的事件, load 只加载一次
        uiNavtab.on("to",function (index) {
            var index = index || 0;
            switch(index){
               /* case 0:
                loader.require(["pages/frist/index"])
                break;*/
                case 0:
                loader.require(["pages/main/menu"])
                break;
                case 1:
                loader.require(["pages/email/table"])
                break;
                case 2:
                loader.require(["pages/me/table"])
                break;
            }
        }).to(0)
    }
    pageview.bind=function(){
    	setInterval(function(){
    		var a=notReadEmail();
    		store.set("email",a);
    	}, 10000)
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
function notReadEmail(){
	var count=0;
	bui.ajax({
        url: xh.getUrl()+"center/email/noReadEmailCount",
        method:'GET',
        dataType : "json",
        data: {},
        async : false
    }).then(function(res){
    	count=res.totals		
    },function(res,status){
        console.log(status);
    })
    return count;
}


