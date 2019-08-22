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
		        url: xh.getUrl()+"checkCut/selectAll2",
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
	 	console.log("====================")
		 console.log(data)
	     if (data && data.length) {
	         data.forEach(function(el, index) {
	         	var str="",textClass="",subClass="",st="";
	         	switch (el.checked) {
	              case -2:
	              	  st = '不通过';
	                  str = '运维负责人不通过';
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  break;
	              case -1:
                      st = '不通过';
	                  str = '管理方不通过';
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  break;
	              case 0:
                      st = '通过';
	                  str= '已发起';
	                  textClass='text-warning';
	                  subClass = 'bui-sub warning';
	                  break;
	              case 1:
                      st = '通过';
	                  str= '复核通过';
	                  textClass='text-info';
	                  subClass = 'bui-sub info';
	                  break;
	              case 2:
                      st = '通过';
	                  str= '审核通过';
	                  textClass='text-success';
	                  subClass = 'bui-sub success';
	                  break;
	              default:
                      st = '未发起';
                      str = '未发起';
                      textClass='text-danger';
                      subClass = 'bui-sub danger';
	                  break;
	              }
	         	var startPersion = "";
	         	if(el.userUnit){
                    startPersion = el.userUnit;
				}
	         	var json=JSON.stringify(el);
	         	html +=`<li data-sub="${st}"  class="bui-btn bui-box ${subClass}" href="pages/checkcut/detail.html" param='${json}'>
	             <div class="span4">
	             <p class="item-text">1</p>
	             <p class="item-text">故障时间：${el.breakTime}</p>
	             <p class="item-text"><span class="bui-label">发起人：</span><span class="bui-value">${startPersion}</span></p>
	             
	             <p class="item-text"><span class="bui-label">基站信息：</span><span class="bui-value">${el.bsId}-${el.name}，</span><span>${el.desc}</span></p>
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

