// 默认已经定义了main模块

var params="";
loader.define(function(require,exports,module){
    var pageview = {};

    // 主要业务初始化
    pageview.init = function() {
        // 这里写main模块的业务
       params = router.getPageParams();
        var className="show";
        
        if(gl_para.userL.roleType==2 && gl_para.up.o_check_report=='on' && params.status==0){
        	className="show";
        }else{
        	className="hide";
        }

        var bs=bui.store({
            scope:'page',
            data:{
                list:params,
                activeClass:className
            },
            methods:{
            	check:function(tag){
            		$.ajax({
        				url : xh.getUrl()+'eventReport/signEventReport',
        				type : 'POST',
        				dataType : "json",
        				async : true,
        				data : {
        					id:params.id,
        					recvUser:params.uploadUser,
        					status:tag,
        					note:$("#check-dg").find("textarea[name='note']").val()
        				},
        				success : function(data) {

        					if (data.success) {
        						toastr.success(data.message, '提示');
        						bui.back({
        							callback:function(){
        								loader.require(["table"],function(res){
        									res.refresh();
        									res.init();
        			                    })
        							}
        						});
        						
        					} else {
        						toastr.error(data.message, '提示');
        					}
        				},
        				error : function() {
        					toastr.success("系统错误", '提示');
        				}
        			});
            	},
            	download:function(){
            		var path=params.filePath;
            		if(path!=null && path!=""){
            			var index=path.lastIndexOf("/");
            			var name=path.substring(index+1,path.length);	
            			var downUrl = xh.getUrl()+"uploadFile/downfile?filePath="+path+"&fileName=" + name;
            			if(xh.isfileapp(path)){
            				window.open(downUrl, '_self',
            				'width=1,height=1,toolbar=no,menubar=no,location=no');
            			}else{
            				toastr.error("文件不存在", '提示');
            			}
            		}
            	}
            }
        });
       
     // 静态自定义绑定
        var checkDialog = bui.dialog({
            id: "#check-dg",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        $("#check").click(function() {
        	checkDialog.open();
        });
     // 自定义确定按钮事件
        $("#makeSure").click(function() {
        	check_dg.hide();
        });
        
    }

    // 事件绑定
    pageview.bind = function() {
       

    }

    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();

    return pageview;
});
function checkBtn(tag){
	$.ajax({
		url : xh.getUrl()+'eventReport/signEventReport',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			recvUser:params.uploadUser,
			status:tag,
			note:$("#check-dg").find("textarea[name='note']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["table"],function(res){
							res.refresh();
							res.init();
	                    })
					}
				});
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.success("系统错误", '提示');
		}
	});
}