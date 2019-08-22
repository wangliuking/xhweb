// 默认已经定义了main模块

var params="";
var userL=JSON.parse(getSessionData("user"));
var up=JSON.parse(getSessionData("up"));
loader.define(function(require,exports,module){


    var pageview = {};

    // 主要业务初始化
    pageview.init = function() {
        // 这里写main模块的业务
        params = router.getPageParams();
        console.log("~~~~~~~~~~~~");
        console.log(params);
        var fileName1Arr = "";
        var fileName2Arr = "";
        var fileName3Arr = "";
        if(params.fileName1 != null){
            var tempArr = params.fileName1.split(";");
            fileName1Arr = tempArr.splice(0,tempArr.length-1);
        }
        if(params.fileName2 != null){
            var tempArr = params.fileName2.split(";");
            fileName2Arr = tempArr.splice(0,tempArr.length-1);
            fileName1Arr = fileName1Arr.concat(fileName2Arr);
        }
        if(params.fileName3 != null){
            var tempArr = params.fileName3.split(";");
            fileName3Arr = tempArr.splice(0,tempArr.length-1);
            fileName1Arr = fileName1Arr.concat(fileName3Arr);
        }

        var html="";
        for(var i=0;i<fileName1Arr.length;i++){
        	html+='<p><img width="100%" height="100%" src="'+"../Resources/upload/CheckCut/"+fileName1Arr[i]+'" /></p>'
        	//$("#file-div").append(html)
        }
        $("#file-div").html(html)

        var bs=bui.store({
            scope:'page',
            data:{
                list:params,
                showCheck1Btn:(userL.roleId==10003 && params.checked == 0)?true:false,
                showCheck2Btn:(userL.roleId==10002 && params.checked == 1)?true:false
            },
            methods:{

            }
        });

        $("#bsId").html(params.bsId+"-"+params.name);
        $("#hometype").html(params.hometype.replace("该基站建设于",""));
        $("#transferOne").html(params.transferOne.replace("第一条传输链路运营商为",""));
        $("#transferTwo").html(params.transferTwo.replace("第二条传输链路运营商为",""));
        $("#powerOne").html(params.powerOne.replace("基站主设备为","")+"，续航时间为"+params.powerTimeOne);
        $("#powerTwo").html(params.powerTwo.replace("传输、环控等网络设备为","")+"，续航时间为"+params.powerTimeTwo);
        $("#content").html(params.desc+" 该故障断站为不可抗力因素导致，根据运维服务质量验收标准"+params.rules+"，故向市软件中心申请故障核减"+params.checkCutTime+"分钟");
        
        var checkDialog1 = bui.dialog({
            id: "#check1-dg",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        var checkDialog2 = bui.dialog({
            id: "#check2-dg",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        $("#check1").click(function() {
            checkDialog1.open();
        });
        $("#check2").click(function() {
            checkDialog2.open();
        });

        // 接收页面参数
        var getParams = bui.getPageParams();
        getParams.done(function(result){
        })
    }

    // 事件绑定
    pageview.bind = function() {

    }

    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();

    return pageview;
})
function check(tag){
	//router.load({ url: "pages/workcontact/check.html", param: {} });
    var dataParams;
    if(tag == 1 || tag == -2){
        dataParams = {
            id: params.id,
            checked: tag,
            note2: $("#check1-dg").find("textarea[name='note2']").val()
		}
    }else if(tag == 2 || tag == -1){
    	var noteData = $("#check2-dg").find("textarea[name='note3']").val();
    	if(noteData == null || noteData == ""){
            noteData = "同意核减。";
		}
        dataParams = {
            id: params.id,
            checked: tag,
            note3: noteData
        }
	}
	 bui.ajax({
	        url: xh.getUrl()+"checkCut/appCheck",
	        method:'POST',
	        dataType:'JSON',
	        data: dataParams
	    }).then(function(res){
	    	console.log(res)
	    	toastr.success("处理完成", '提示');
	    	bui.back({
				callback:function(){
					loader.require(["pages/checkcut/table"],function(res){
						res.refresh();
						res.init();
                    })
				}
			});
	    },function(res,status){
	        console.log(status);
	    })
}