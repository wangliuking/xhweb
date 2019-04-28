var params="";
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
		    params = router.getPageParams();
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
		        url: xh.getUrl()+"optimizenet/selectAll2",
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
	//生成模板
	 function template(data) {
	 	var html = "";
	     if (data && data.length) {
	         data.forEach(function(el, index) {
	         	var status=el.status;
	         	var str="",textClass="",subClass="",subText="";
	         	switch (el.checked) {
	        	     case -1:
	           	   if(gl_para.userL.roleType==3){
	           		str= '网络优化方案审核不通过，请重新上传';
	           	  }else{
	           		str='网络优化方案审核不通过,等待重新上传';
	              	}
	               
	               textClass='text-danger';
	               subClass = 'bui-sub danger';
	               subText="处理中";
	               break;
	         	 case -2:
	         		 str= '网络优化任务中断，结束';
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  subText="中断";
	                  break;
	         	 case -3:
	         		 if(gl_para.userL.roleType==3){
	         			 str= '总结报告审核未通过，请重新上传<br>'+el.note4;
	                	  }else{
	                		 str= '总结报告审核未通过，等待服务提供方重新上传<br>'+el.note4;
	                   	}
	         		 
	                  textClass='text-danger';
	                  subClass = 'bui-sub danger';
	                  subText="未通过";
	                  break;
	             case 0:
	             	if(gl_para.userL.roleType==3){
	             		str= '请确认通知';
	             	}else{
	             		str= '等待服务提供方确认信息';
	             	}
	                 
	                 textClass='text-primary';
	                 subClass = 'bui-sub primary';
	                 subText="待确认";
	                 break;
	             case 1:
	             	if(gl_para.userL.roleType==3){
	             		str= '请上传上传网络优化方案';
	             	}else{
	             		str='等待服务提供方上传网络优化方案';
	             	}
	                 
	                 textClass='text-warning';
	                 subClass = 'bui-sub warning';
	                 subText="处理中";
	                 break;
	             case 2:
	             	if(gl_para.userL.roleType==3){
	             		str= '等待管理方审核网络优化方案';
	             	}else{
	             		str='请审核网络优化方案';
	             	}
	                 
	                 textClass='text-warning';
	                 subClass = 'bui-sub warning';
	                 subText="处理中";
	                 break;
	             case 3:
	             	if(gl_para.userL.roleType==3){
	             		str= '请上传总结报告';
	             	}else{
	             		str='等待服务提供方上传总结报告';
	             	}
	                 
	                 textClass='text-warning';
	                 subClass = 'bui-sub warning';
	                 subText="处理中";
	                 break;
	             case 4:
	             	if(gl_para.userL.roleType==3){
	             		str= '等待管理方审核总结报告';
	             	}else{
	             		str='请审核总结报告';
	             	}
	                 
	                 textClass='text-warning';
	                 subClass = 'bui-sub warning';
	                 subText="处理中";
	                 break;
	              case 5:
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
	         	html +=`<li data-sub="${subText}"  class="bui-btn bui-box ${subClass}" href="pages/networkopt/detail.html" param='${json}'>
	             <div class="span4">
	             <p class="item-text">申请时间：${el.requestTime}</p>
	             <p class="item-text"><span class="bui-label">联系单位：</span><span class="bui-value">${el.unit1}</span></p>
	             <p class="item-text"><span class="bui-label">联系人：</span><span class="bui-value">${el.userUnit}</span></p>
	             
	             <p class="item-text"><span class="bui-label">联系电话：</span><span class="bui-value">${el.tel}</span></p>
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
