// 默认已经定义了main模块
var params="";
loader.define(function(require,exports,module) {
    var pageview = {};
    // 主要业务初始化
    pageview.init = function() {
    	params = router.getPageParams();
        var bs=bui.store({
            scope:'page',
            data:{
            	dropnet:-1,
            	show:true
            	
            },
            methods:{
            	check:function(){
            		  bui.ajax({
            		        url: xh.getUrl()+"optimizenet/checkedThree",
            		        method:'POST',
            		        dataType : "json",
            		        data: {
            		        	id:params.id,
            					user2:params.user2,
            					dropnet:$("input[name='dropnet']:checked").val(),
            					checked:$("input[name='checked']:checked").val(),
            					note3:$("textarea[name='note3']").val()
            		        }
            		    }).then(function(data){
            		    	if (data.success) {
            					bui.alert(data.message)
            	            	bui.back({
            	            		index:-2,
            	            		callback:function(){
            	            			loader.require(['table'],function(res){
            	            				res.refresh();
            	            				res.init({
            	            					page:1
            	            				});
            	            			});
            	            		}
            	            	});
            				} else {
            					bui.alert(data.message)
            				}
            		    },function(res,status){
            		        console.log(status);
            		    })
            	}
            },
            beforeMount: function(){
                // 数据解析前执行
            },
            computed:function(){
            	  
            },
           mounted: function(){
                // 数据解析后执行
              }
            
        });
      // 监听变量变更的时候,触发回调
        bs.watch("dropnet",function (newVal,oldVal) {
            if(newVal==-1){
            	bs.set('show',true);
            }else{
            	bs.set('show',false);
            }
        })
        // 静态自定义绑定
        var uiSelect2 = bui.select({
            id: "#select-dialog",
            trigger: "#select2",
            type: "radio",
            effect: "fadeInRight",
            position: "right",
            fullscreen: true,
            mask: false,
            buttons: []
        });
     // 自定义确定按钮事件
        $("#makeSure").click(function() {
        	bs.type=uiSelect2.value();
            uiSelect2.hide();
        });
     

    };
    // 事件绑定
    pageview.bind = function() {
    	
    }
    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();
    return pageview;
})
function check(){
	alert(1);return;
	$.ajax({
		url : xh.getUrl()+'optimizenet/checkedThree',
		type : 'POST',
		dataType : "json",
		async : false,
		data :{
			id:params.id,
			user2:params.user2,
			dropnet:$("input[name='dropnet']").val(""),
			checked:$("input[name='checked']").val(""),
			note3:$("textarea[name='note3']").val(""),
		},
		success : function(data) {
			if (data.success) {
				bui.alert(data.message)
            	bui.back({
            		callback:function(){
            			loader.require(['pages/networkopt/table'],function(res){
            				res.refresh();
            				res.init({
            					page:1
            				});
            			});
            		}
            	});
			} else {
				bui.alert(data.message)
			}
		},
		error : function() {
			toastr.error("参数错误", '提示');
		}
	});
}
