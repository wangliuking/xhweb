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
                title: "管理方发起网络优化任务",
                subtitle: params.requestTime,
                content: "管理方发起网络优化任务,请服务提供方尽快确认通知"
            }, {
                title: "服务提供方确认通知",
                subtitle: params.time2,
                content: params.checked==0?"耐心等待服务提供方确认通知":
                	"服务提供方已确认通知"+(params.note2!=null?"<span class='text-danger'>["+params.note2+"]</span>":"")
            }, {
                title: "上传网络优化方案",
                subtitle: params.time3,
                content: params.checked<=1?"等待服务提供方上传网络优化方案":"服务提供方已上传网络优化方案"
            }, {
                title: "审核优化方案",
                subtitle: params.time3,
                content: params.checked!=-2?(params.checked<=2?"等待管理方审核网络优化方案":
                	"优化方案已审核"+(params.note3!=null?"<span class='text-danger'>["+params.note3+"]</span>":"")):
                		"<span>任务中断</span>"+(params.note3!=null?"<span class='text-danger'>["+params.note3+"]</span>":""),
            },{
                title: "上传总结报告",
                subtitle: params.time4,
                content: params.checked<=3?"等待服务提供方上传总结报告":"服务提供方已上传总结报告"
            },{
                title: "审核总结报告",
                subtitle: params.time5,
                content: params.checked<=4?"等待管理方审核总结报告":
                	"总结报告已审核"+(params.note4!=null?"<span class='text-danger'>["+params.note4+"]</span>":""),
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
