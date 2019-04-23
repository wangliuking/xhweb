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
        //初始化控件
        var uiStepbar = bui.stepbar({
            id: "#step",
            click:false,
            data: [{
                title: "管理方提交抽查计划",
                subtitle: params.requestTime,
                content: "管理方提交抽查计划,请服务提供方做好相应的工作准备"
            }, {
                title: "准备抽查",
                subtitle: params.time1,
                content: params.checked==0?"耐心等待服务提供方完成抽查准备工作":"服务提供方已完成抽查准备工作"
            }, {
                title: "上传抽检记录",
                subtitle: params.time2,
                content: params.checked<=1?"耐心等待管理方上传抽检记录":"管理方已上传抽检记录"
            }]
        });

        //激活第2步
        uiStepbar.value(params.checked);

        uiStepbar.on("change", function(i) {
            console.log(i)
        })
        var index = uiStepbar.next();

        
        
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
