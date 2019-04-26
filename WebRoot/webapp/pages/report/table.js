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
loader.define(function(require,exports,module){
	var pageview = {}, uiList="",bs="";
	pageview.init = function () {
		 var params = router.getPageParams();
		    var className="show";
	        
	        if(gl_para.userL.roleType==3 || gl_para.userL.roleType==0){
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
		        url: xh.getUrl()+"eventReport/list2",
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
		        	status:2,
		        	fileType:"所有报告"
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
        	switch (el.status) {
        	case -1:
                str = '文件被拒绝，请重新提交';
                textClass='text-danger';
                subClass = 'bui-sub danger';
                subText="拒绝";
                break;
            case 0:
                str= '待审核';
                textClass='text-primary';
                subClass = 'bui-sub primary';
                subText="待审核";
                break;
             case 1:
                 str = '已通过审核';
                 textClass='text-success';
                 subClass = 'bui-sub success';
                 subText="已通过";
                 break;
             default:
                 sub = '';
                 subClass = '';
                 break;
             }
        	
        	var json=JSON.stringify(el);
        	html +=`<li data-sub="${subText}"  class="bui-btn bui-box ${subClass}" href="pages/report/detail.html" param='${json}'>
            <div class="span4">
            <p class="item-text">提交时间：${el.createtime}</p>
            <p class="item-text"><span class="bui-label">提交者：</span><span class="bui-value">${el.contact}</span></p>
            
            <p class="item-text"><span class="bui-label">类型：</span><span class="bui-value">${el.fileType}</span></p>
            <p class="item-text"><span class="bui-label">文件名称：</span><span class="bui-value">${el.fileName}</span></p>
            <p class="item-text">状态：<span class="${textClass}">${str}</span></p>
            </div>
            <i class="icon-listright" style="color:#000;"></i>
            </li>`
        })
    }
    return html; 
};
