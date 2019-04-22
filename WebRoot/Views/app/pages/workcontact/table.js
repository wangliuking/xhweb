loader.define(function(require,exports,module){
	var pageview = {}, uiList="";
	pageview.init = function () {
		 var params = router.getPageParams();
		 
		    if(params.userName!=undefined){
		    	if(gl_para.userL==null){
		    		login();
		    	}
		    }; 
		 uiList=bui.list({
		        id: "#listStore",
		        url: "../../../../WorkContact/list2",
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
		            data: "data"
		        },
		        data: {
		            lists: []
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
        	var str="",textClass="",subClass="";
        	switch (el.status) {
             case 1:
                 str = '已签收';
                 textClass='text-success';
                 subClass = 'bui-sub success';
                 break;
             case 0:
                 str= '待确认';
                 textClass='text-danger';
                 subClass = 'bui-sub danger';
                 break;
             default:
                 sub = '';
                 subClass = '';
                 break;
             }
        	
        	var json=JSON.stringify(el);
        	html +=`<li data-sub="${str}"  class="bui-btn bui-box ${subClass}" href="detail.html" param='${json}'>
            <div class="span4">
            <p class="item-text">发文时间：${el.time}</p>
            <p class="item-text"><span class="bui-label">发文人：</span><span class="bui-value">${el.userName}</span></p>
            
            <p class="item-text"><span class="bui-label">类型：</span>[<span class="bui-value">${el.type}</span>]<span>${el.reason}</span></p>
            <p class="item-text">状态：<span class="${textClass}">${str}</span></p>
            </div>
            <i class="icon-listright" style="color:#000;"></i>
            </li>`
        })
    }
    return html; 
};
function login(){
	$.ajax({
		url : '../../../../web/login',
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
				toastr.success("success", '提示');
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.error("登录超时", '提示');

		}
	});
}
