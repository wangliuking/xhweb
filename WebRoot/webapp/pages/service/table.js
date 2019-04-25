var params="";
loader.define(function(require,exports,module){
	var pageview = {}, uiList="",bs="";
	pageview.init = function () {
		    params = router.getPageParams();
		    if(params.userName!=undefined){
		    	login(params);
		    }; 
		    var className="show";
	        
	        if(gl_para.userL.roleType==2 || gl_para.userL.roleType==0){
	        	className="show";
	        }else{
	        	className="hide";
	        }
	      bs=bui.store({
	            scope:'page',
	            data:{
	                activeClass:className
	            }
	      });
		 uiList=bui.list({
		        id: "#listStore",
		        url: "../../../qualitycheck/selectAll2",
		        page: 1,
		        pageSize: 10,
		        
		        method:'GET',
		        timeout:5000,
		        refresh:true,
		        height:document.documentElement.clientHeight-50,
		        refreshTips:{
		        	start:'努力加载中.',
		        	fail:'点击重新加载'
		        },
		        //如果分页的字段名不一样,通过field重新定义
		        field: {
		            page: "start",
		            size: "limit",
		            data: "items"
		        },
		        data:{
		        	
		        },
		        //refresh:true,
		        template: function(data) {
		        	return template(data)
		        },
		        onBeforeRefresh : function () {
		            console.log("brefore refresh")
		          },
		          onBeforeLoad : function () {
		            console.log("brefore load")
		          },
		          onRefresh: function() {
		              // 刷新以后执行
		              console.log("refreshed")
		          },
		          onLoad: function() {
		              // 刷新以后执行
		              console.log("loaded")
		          }
		    });
	 };
	 pageview.refresh=function(){
		 uiList.empty();
	 }
	 pageview.refreshData=function(){
		 uiList.empty();
		 pageview.init();
	 }
	 
	 
	// 初始化
	pageview.init();
	 // 输出模块
    return pageview;
});
//生成模板
function template(data) {
	var html = "";
    if (data && data.length) {
        data.forEach(function(el, index) {
        	var status=el.status;
        	var str="",textClass="",subClass="",subText="";
        	switch (el.checked) {
            case 0:
            	if(gl_para.userL.roleType==3){
            		str= '请确认是否可以抽检';
            	}else{
            		str= '等待服务提供方确认是否可以抽检';
            	}
                
                textClass='text-primary';
                subClass = 'bui-sub primary';
                subText="待确认";
                break;
            case 1:
            	if(gl_para.userL.roleType==3){
            		str= '等待管理方上传抽检记录';
            	}else{
            		str='请管理方上传抽检记录';
            	}
                
                textClass='text-warning';
                subClass = 'bui-sub warning';
                subText="处理中";
                break;
             case 2:
                 str = '结束';
                 textClass='text-success';
                 subClass = 'bui-sub success';
                 subText="结束";
                 break;
             default:
                 sub = '';
                 subClass = '';
                 break;
             }
        	
        	var json=JSON.stringify(el);
        	html +=`<li data-sub="${subText}"  class="bui-btn bui-box ${subClass}" href="detail.html" param='${json}'>
            <div class="span4">
            <p class="item-text">申请时间：${el.requestTime}</p>
            <p class="item-text"><span class="bui-label">联系人：</span><span class="bui-value">${el.applicant}</span></p>
            
            <p class="item-text"><span class="bui-label">联系电话：</span><span class="bui-value">${el.tel}</span></p>
            <p class="item-text">状态：<span class="${textClass}">${str}</span></p>
            </div>
            <i class="icon-listright" style="color:#000;"></i>
            </li>`
        })
    }
    return html; 
};
function login(params){
	bui.ajax({
        url: "../../../web/login",
        method:'post',
        dataType : "json",
        data: {
        	username : params.userName,
			password : params.password,
			ToSign :"",
			Signature :""
        },
        async : false
    }).then(function(data){
    	if (data.success) {
			//toastr.success("success", '提示');
		} else {
			toastr.error(data.message, '提示');
		}
    },function(res,status){
        console.log(status);
        toastr.error("登录超时", '提示');
     // status = "timeout" || "error" || "abort", "parsererror"
    })
	
	
	
	/*$.ajax({
		url : '../../../web/login',
		type : 'POST',
		dataType : "json",
		data : {
			username : params.userName,
			password : params.password,
			ToSign :"",
			Signature :""
		},
		 data : $("#loginForm").serializeArray(), 
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
	});*/
}
