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
	var pageview = {}, uiList="";
	pageview.init = function () {
		 var params = router.getPageParams();
		 uiList=bui.list({
		        id: "#listStore",
		        url: xh.getUrl()+"WorkContact/list2",
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
	//生成模板
	 function template(data) {
	 	var html = "";
	     if (data && data.length) {
	         data.forEach(function(el, index) {
	         	var status=el.status;
	         	var str="",textClass="",subClass="";
	         	switch (el.status) {
	              case 2:
	                  str = '已签收';
	                  textClass='text-success';
	                  subClass = 'bui-sub success';
	                  break;
	              case 1:
	                  str = '待签收';
	                  textClass='text-primary';
	                  subClass = 'bui-sub primary';
	                  break;
	              case 0:
	                  str= '待审核';
	                  textClass='text-primary';
	                  subClass = 'bui-sub primary';
	                  break;
	              case -1:
	                  str= '被拒绝';
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  break;
	              case -2:
	                  str= '已撤销';
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  break;
	              default:
	                  sub = '';
	                  subClass = '';
	                  break;
	              }
	         	
	         	var json=JSON.stringify(el);
	         	html +=`<li data-sub="${str}"  class="bui-btn bui-box ${subClass}" href="pages/workcontact/detail.html" param='${json}'>
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

