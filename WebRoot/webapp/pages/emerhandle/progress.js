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
            hasNumber:true,
            data: [{
                title: "管理方发起应急处置演练任务",
                subtitle: params.requestTime,
                content: "管理方发起应急处置演练任务,请服务提供方尽快提交演练计划"
            }, {
                title: "提交演练计划",
                subtitle: params.time1,
                content: params.checked<=0?"等待服务提供方提交演练计划":"服务提供方已提交演练计划"
            }, {
                title: "审核演练计划",
                subtitle: params.time2,
                content: params.checked<=1?"等待管理方审核演练计划":
                	"管理方已审核演练计划"+(params.note3!=null?"<span class='text-danger'>["+params.note3+"]</span>":"")
            }, {
                title: "提交应急演练报告",
                subtitle: params.time3,
                content: params.checked<=2?"等待服务提供方上传应急演练报告":"服务提供方已上传应急演练报告"
            },{
                title: "确认应急演练报告",
                subtitle: params.time4,
                content: params.checked<=2?"等待管理方确认应急演练报告":
                	"管理方已确认应急演练报告"+(params.note4!=null?"<span class='text-danger'>["+params.note4+"]</span>":"")
            }]
        });

        //激活第2步
        if(params.checked==-2){
        	uiStepbar.value(2);
        }if(params.checked==-3){
        	uiStepbar.value(3);
        }else{
        	uiStepbar.value(params.checked);
        }

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
