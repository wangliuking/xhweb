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
	var pageview = {}, uiList="",notReadUiList="",bs="";
	var mainHeight = $(window).height() - $("#tab-home-header").height()- $("#tabDynamicNav").height();
	//初始化顶部TAB
    uiSlideTabMessage = bui.tab({
        id       : "#tabMessage",
        menu     : "#tabMessageNav",
        height: mainHeight,
        swipe    : false,   //不允许通过滑动触发
        animate  : false    //点击跳转时不要动画
    });
	pageview.init = function () {
		 var params = router.getPageParams();
		 bs=bui.store({
	            scope:'page',
	            data:{
	                isadd:(gl_para.userL.roleType==2 && gl_para.up.o_check_work!='on')|| gl_para.userL.roleType==0
	            }
		 })
		 uiList=bui.list({
		        id: "#readedStore",
		        url: xh.getUrl()+"center/email/list2",
		        page: 1,
		        pageSize: 10,
		        method:'GET',
		        timeout:5000,
		        refresh:true,
		        height:0,
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
		        	status:1
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
		 notReadUiList=bui.list({
		        id: "#notReadStore",
		        url: xh.getUrl()+"center/email/list2",
		        page: 1,
		        pageSize: 10,
		        method:'GET',
		        timeout:5000,
		        refresh:true,
		        height:0,
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
		        	status:0
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
		 notReadUiList.empty();
		 pageview.init();
	 }
	 
	 pageview.bind=function(){
		 $(".bui-list").on('click','li',function(){
			 var url="";
				switch($(this).attr("htmtext")){				
				case "工作记录":
					url="pages/workrecord/table.html";
				    break;
				case "应急处置演练":
					url="pages/emerhandle/table.html";
				    break;
				case "网络优化":
					url="pages/networkopt/table.html";
				    break;
				case "服务抽检":
					url="pages/service/table.html";
				    break;
				case "工作联系单":
					url="pages/workcontact/table.html";
				    break;
				case "提交报告":
					url="pages/report/table.html";
				    break;
				default:
					url="pages/email/table.html";
					
				};
				setReaded($(this).attr("htmlid"));
				router.load({ url: url,param:{}});
		 })
	 }
	 function setReaded(id) {
			$.ajax({
				url : xh.getUrl()+'center/email/update',
				type : 'post',
				dataType : "json",
				data : {
					id : id
				},
				async : false,
				success : function(data) {
					pageview.refreshData();
				},
				error : function() {
				}
			});
		};
	//生成模板
	 function template(data) {
	 	var html = "";
	     if (data && data.length) {
	         data.forEach(function(el, index) {
	         	var str="",textClass="",subClass="";
	         	switch (el.status) {
	              case 1:
	                  str = '已读';
	                  textClass='text-success';
	                  subClass = 'success';
	                  break;
	              case 0:
	                  str= '未读';
	                  textClass='text-danger';
	                  subClass = 'primary';
	                  break;
	              default:
	                  sub = '';
	                  subClass = '';
	                  break;
	              }
	         	
	         	var json=JSON.stringify(el);
	         	/*html +=`<li data-sub="${str}"  class="bui-btn bui-box ${subClass}" href="" htmtext="${el.title}" param='${json}'>
	             <div class="span4">
	             <p class="item-text">时间：${el.time}</p>
	             <p class="item-text">标题：<span>${el.title}</span></p>
	             <p class="item-text">发件人：<span>${el.userName}</span></p>
	             <p class="item-text">内容：<span>${el.content}</span></p>
	             <p class="item-text">状态：<span class="${textClass}">${str}</span></p>
	             </div>
	             <i class="icon-listright" style="color:#000;"></i>
	             </li>`*/
	         	//var p=el.userName.substring(0,1);
	             html +=`<li   class="bui-btn bui-box" href="" htmlid="${el.id}" htmtext="${el.title}" param='${json}'>
	             <div class="ring ring-group">
					<div class="bui-icon ${subClass}">
					${el.userName}
				    </div>
				</div>
	             <div class="span4">
	             <p class="item-text bui-right">${el.time}</p>
	             <p class="item-title"><span>${el.title}</span></p>
	             <p class="item-text"><span>${el.content}</span></p>
	             </div>
	             <i class="icon-listright" style="color:#000;"></i>
	             </li>`
	             
	            /* html=`
	             <li  class="bui-btn bui-box" href="pages/chat/chat.html" param='${json}'>
					<div class="ring ring-group">
					<div class="bui-icon success">
					${p}
				    </div>
				</div>
				<div class="span1">
					<h3 class="item-title">
					<span class="bui-badges primary"></span>${el.title}<span class="item-time bui-right">${el.time}<i class="icon-listright" style="color:#000;"></i></span>
					</h3>
					<p class="item-text">${el.content}</p>
					
				</div>
			</li>`*/
	             
	             
	             
	         })
	     }
	     return html; 
	 };
	// 初始化
	pageview.init();
	pageview.bind();
	 // 输出模块
    return pageview;
});



