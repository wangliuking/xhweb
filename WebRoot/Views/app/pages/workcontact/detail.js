// 默认已经定义了main模块

loader.define(function(require,exports,module){


    var pageview = {};

    // 主要业务初始化
    pageview.init = function() {
        // 这里写main模块的业务
        var params = router.getPageParams();
        var className="show";
        
        if(gl_para.userL.roleType!=params.user_type && gl_para.up.o_task=='on' && params.status==0){
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
            	sure:function(e){
            		$.ajax({
        				url : '../../../../WorkContact/sign',
        				type : 'POST',
        				dataType : "json",
        				async : true,
        				data : {
        					taskId : params.taskId,
        					addUser : params.addUser
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
            			var downUrl = "../../../../uploadFile/downfile?filePath="+path+"&fileName=" + name;
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
        var content=params.content.replace(/<br>/g,"<br />");
        content=content.replace(/" "/g,"&nbsp;")
        $("#content").html(content);

        // 接收页面参数
        var getParams = bui.getPageParams();
        getParams.done(function(result){
        })
    }

    // 事件绑定
    pageview.bind = function() {
        $("#file").on("click",function(e){
            alert($(this.prop("value")))
        });
        $("#backToMain").on("click",function (e) {
            // 后退到首页并刷新
            bui.back({
                index: -4,  // -4 已经超出历史记录最大值,所以会退到第一页
                callback: function () {
                    // 获取main模块
                    loader.require(["main"],function(main){
                        // 刷新首页
                        main.init();
                        bui.hint("刷新成功");
                    })
                }
            })
        })

    }

    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();

    return pageview;
})