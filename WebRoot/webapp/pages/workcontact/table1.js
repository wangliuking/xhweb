bui.ready(function(){
	
   var params = router.getPageParams();
    if(params.userName!=undefined){
    	if(gl_para.userL==null){
    		login();
    	}
    }

    var bs = bui.store({
        scope: "page", // 用于区分公共数据及当前数据的唯一值
        field:{
        	
        },
        data: {
            lists: [] // 默认是数组时,只能通过 bui.array 之类的操作触发, 如果是Null,则可以通过 等于赋值的操作
        },        
        log: true,
        templates: {
            tplList: function(data) {
                return template(data);
            }
        },
        mounted: function(argument) {
            var _self = this;

            this.list = bui.list({
                id: "#listStore",
                url: "../../../../WorkContact/list2",
                page: 1,
                pageSize: 10,
                //如果分页的字段名不一样,通过field重新定义
                field: {
                    page: "start",
                    size: "limit",
                    data: "data"
                },
                onRefresh: function(scroll, datas) {
                    // 合并新的数据
                    bui.array.replace(_self.lists, datas.data);
                },
                onLoad: function(scroll, datas) {
                    bui.array.merge(_self.lists, datas.data)
                }
            });
        }
    })

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