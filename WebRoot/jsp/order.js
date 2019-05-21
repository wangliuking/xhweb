/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
var userName="";
var appElement = document.querySelector('[ng-controller=xhcontroller]');
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
$.get("web/loginUserInfo").success(function(response) {

	userName=response.userName;
	$("input[name='dispatchman']").val(userName);
	if(response.roleType==null || response.roleType!=3){
		window.location.href="jsp/no.html"
	}
	console.log(response.userName)
	
});
/*xh.open=function(){
	window.open("jsp/order.jsp",'_blank', 'dd','width=900,height=600,top=100px,left=300px','menubar=false')
}*/
xh.order=function(){

    var userid=$("input[name='userid']").val();
    var errtype=$("span[name='errtype']").text();
    var errlevel=$("span[name='errlevel']").text();
    
    if(userid==""){
        toastr.error("接单人不能为空", '提示');
        return;
    }
    if(errtype=="" || errlevel==""){
        toastr.error("故障类型,等级不能为空", '提示');
        return;
    }
    if($("#order-form").find("input[name='dispatchman']")==""){
        toastr.error("派单人不能为空", '提示');
        return;
    }
    var formData={
        id:$("#order").find("input[name='id']").val()==""?0:$("#order").find("input[name='id']").val(),
        from:$("#order").find("span[name='from']").text(),
        zbdldm:$("#order").find("span[name='zbdldm']").text(),
        bsid:$("#order").find("input[name='bsId']").val(),
        bsname:$("#order").find("input[name='name']").val(),
        userid:userid,
        dispatchtime:$("#order").find("input[name='dispatchtime']").val(),
        dispatchman:$("#order").find("input[name='dispatchman']").val(),
        errtype:errtype,
        errlevel:errlevel,
        errfoundtime:$("#order").find("input[name='errfoundtime']").val(),
        errslovetime:$("#order").find("input[name='errslovetime']").val(),
        progress:$("#order").find("textarea[name='progress']").val(),
        proresult:$("#order").find("textarea[name='proresult']").val(),
        workman:$("#order").find("input[name='workman']").val(),
        auditor:$("#order").find("input[name='auditor']").val(),
    };
    $.ajax({
        url : 'order/writeOrder',
        data : {
            formData:JSON.stringify(formData)
            
        },
        type : 'post',
        dataType : "json",
        async : false,
        success : function(response) {
            var data = response;
            if(data.success){
                toastr.success("派单成功", '提示');
                //xh.closeWindow();
                xh.closeHtml();
            }else{
                toastr.error("派单失败", '提示');
            }
            

        },
        failure : function(response) {
            toastr.error("派单失败", '提示');
        }
    });
}
xh.closeWindow=function(){
	   window.parent.opener=null;
	   window.parent.open('', '_self', ''); 
	   window.parent.close();
	}
xh.nowDate=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 
    
    var h=yesterday.getHours();
    var m=yesterday.getMinutes();
    var s=yesterday.getSeconds();

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    
    if(h<10){
    	h="0"+h;
    }
    if(m<10){
    	m="0"+m;
    }
    if(s<10){
    	s="0"+s;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+h+":"+m+":"+s;   
    return  strYesterday;
}
xh.getOneDay=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+"23:59:59";   
    return  strYesterday;
}

/*运维组发起请求审核*/
xh.add = function() {
    $.ajax({
        url : '../../checkCut/updateCheckTag',
        type : 'POST',
        dataType : "json",
        async : false,
        data : $("#addForm").serializeArray(),
        success : function(data) {
            $.ajax({
                url : '../../checkCut/createCheckCut',
                type : 'POST',
                dataType : "json",
                async : true,
                data : $("#addForm").serializeArray(),
                success : function(data) {
                    $("#add_btn").button('reset');
                    if (data.result ==1) {
                        $('#add').modal('hide');
                        $("input[name='result']").val(1);
                        xh.refresh();
                        toastr.success(data.message, '提示');
                    } else {
                        toastr.error(data.message, '提示');
                    }
                },
                error : function() {
                    $("#add_btn").button('reset');
                }
            });
        }
    });
};

/* 上传文件 */
xh.upload = function(index) {
    if (index == -1) {
        path = 'filePath-1';
        note = 'uploadResult-1';
    }
    if (index == 1) {
        path = 'filePath1';
        note = 'uploadResult1';
    }
    if (index == 2) {
        path = 'filePath2';
        note = 'uploadResult2';
    }
    if (index == 3) {
        path = 'filePath3';
        note = 'uploadResult3';
    }
    if (index == 4) {
        path = 'filePath4';
        note = 'uploadResult4';
    }
    if ($("#" + path).val() == "") {
        toastr.error("你还没选择文件", '提示');
        return;
    }
    xh.maskShow();
    $.ajaxFileUpload({
        url : '../../checkCut/upload', // 用于文件上传的服务器端请求地址
        secureuri : false, // 是否需要安全协议，一般设置为false
        fileElementId : path, // 文件上传域的ID
        dataType : 'json', // 返回值类型 一般设置为json
        type : 'POST',
        success : function(data, status) // 服务器成功响应处理函数
        {
            // var result=jQuery.parseJSON(data);
            console.log(data.filePath)
            xh.maskHide();
            if (data.success) {
                $("#"+note).html(data.message);
                $("input[name='result']").val(1);
                $("input[name='fileName']").val(data.fileName);
                $("input[name='path']").val(data.filePath);
            } else {
                $("#"+note).html(data.message);
            }

        },
        error : function(data, status, e)// 服务器响应失败处理函数
        {
            alert(e);
        }
    });
};
xh.closeHtml=function(){  
	var a={success:true}
	window.parent.postMessage(a,'http://183.221.117.37:7800/analystrunner/project/f439e8be-6cde-497c-88c6-3663b5b7a71e/#/ee16452e-8915-4227-941c-f40e67efca70');
  }  

/*修改核减申请表*/
xh.sheetChange = function() {
    var bean={
        id:$("div[name='id']").text(),
        bsId:$("input[name='bsId']").val(),
        name:$("input[name='name']").val(),
        hometype:$("input[name='hometype']").val(),
        transfer:$("input[name='transfer']").val(),
        transferCompare:$("input[name='transferCompare']").val(),
        transferOne:$("input[name='transferOne']").val(),
        transferTwo:$("input[name='transferTwo']").val(),
        powerOne:$("input[name='powerOne']").val(),
        powerTimeOne:$("input[name='powerTimeOne']").val(),
        powerTwo:$("input[name='powerTwo']").val(),
        powerTimeTwo:$("input[name='powerTimeTwo']").val(),
        maintainTime:$("input[name='maintainTime']").val(),
        isPower:$("input[name='isPower']").val(),
        firstDesc:$("input[name='firstDesc']").val(),
        desc:$("div[name='desc']").text(),
        breakTime:$("input[name='breakTime']").val(),
        restoreTime:$("input[name='restoreTime']").val(),
        checkCutTime:$("input[name='checkCutTime']").val(),
        alarmTime:$("input[name='alarmTime']").val(),
        situation:$("div[name='situation']").text(),
        rules:$("input[name='rules']").val(),
        period:$("div[name='period']").text(),
        applyTime:$("div[name='applyTime']").text(),
        suggest:$("div[name='suggest']").text(),
        persion3:$("div[name='persion3']").text(),
        persion1:$("div[name='persion1']").text(),
        persion2:$("div[name='persion2']").text()
    }
    $.ajax({
        url : '../../checkCut/sheetChange',
        type : 'POST',
        dataType : "json",
        data : {"bean" : JSON.stringify(bean)},
        success : function(data) {
            $("#checkCut-btn").button('reset');
            $('#sheet').modal('hide');
            xh.refresh();
            toastr.success(data.message, '提示');
        },
        error : function() {
            $("#checkCut-btn").button('reset');
        }
    });
};

function calTime(date1,date2){
    var date3 = new Date(date2).getTime() - new Date(date1).getTime();   //时间差的毫秒数
    //------------------------------
    //计算出相差天数
    var days=Math.floor(date3/(24*3600*1000))
    //计算出小时数
    var leave1=date3%(24*3600*1000)    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000))
    //计算相差分钟数
    var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000))
    //计算相差秒数
    var leave3=leave2%(60*1000)      //计算分钟数后剩余的毫秒数
    var seconds=Math.round(leave3/1000)
    //alert(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
    return Math.round(date3/(1000*60));
}
/*window.addEventListener('message',function(e){
    //if(e.source!=window.parent) return;
    
   console.log(e.origin,e.data)
},false);*/
