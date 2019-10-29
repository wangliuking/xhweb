// 默认已经定义了main模块
loader.define(function(require,exports,module) {
    var pageview = {};
    var params = router.getPageParams();
    var content="";
    if(params.content!=null){
    	content=params.content.replace(/<br>/g,"\r\n");
        content=content.replace(/" "/g," ")
    }
    //$("#content").html(content);
    // 主要业务初始化
    pageview.init = function() {
    	
        var bs=bui.store({
            scope:'page',
            data:{
            	No:code(),
            	content:content,
            	select:select_data(),
            	showEnsure:true,
            	sendUnit:gl_para.sendUnit
            },
            methods:{
            	add:add,
            	upload:function(){
                	// upload();
            	},
            	showFileWin:function(){
            		$("input[name='pathName']").click();
            	},
            	type_change:function(){
            		var a=$("select[name='type']").val();
            		if(a=="通信保障"){
            			this.showEnsure=true;
            		}else{
            			this.showEnsure=false;
            		}
            		console.log(this.showEnsure)
            	}
                
            }
            
        });
       /* if(params!=null){
        	 var content=params.content.replace(/<br>/g,"<br />");
             content=content.replace(/" "/g,"&nbsp;")
             $("#content").html(content);
        }*/
       
        
        setInterval(function(){
        	bs.No=code();
        }, 1000)
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
/* 获取编号 */
function code(){
	var codestr="";
	bui.ajax({
        url: xh.getUrl()+"WorkContact/codeNum",
        async:false,
        data: {}
    }).then(function(res){
    	codestr = res.code;
    },function(res,status){
        console.log(status);
    });
	return codestr;
}
function select_data(){
	var str="";
	bui.ajax({
        url: xh.getUrl()+"select/workcontact",
        async:false,
        data: {}
    }).then(function(res){
    	str = res.items;
    	if(str.length>0){
    		for(var i=0;i<str.length;i++){
    			var a="<option value='"+str[i].name+"'>"+str[i].name+"</option>";
    			//console.log(str[i].name);
    			$("#add-select").append(a);
    		}
    	}
    
    },function(res,status){
        console.log(status);
    });
	return str;
}
function add(){
	var files=[];
	var reason=$("input[name='reason']").val();
	var time=$("input[name='time']").val();
	var code=$("input[name='code']").val();
	
	if(reason==""){
		bui.alert("事由不能为空");return;
	}
	if(time==""){
		bui.alert("时间不能为空");return;
	}
	if(code==""){
		bui.alert("编号不能为空");return;
	}
	
	/*
	 * var name = $("input[name='fileName']").val(); var path =
	 * $("input[name='filePath']").val(); if(name!="" && path!=""){ var a={
	 * fileName:name, filePath:path } files.push(a); }
	 */
		$("#fileArea ul li").each(function(){
		    var name = $(this).children().first().text();
		    var path = $(this).children(".path").text();
		    if(name!="" && path!=""){
		    	var a={
		    			fileName:name,
		    			filePath:path
		    	}
		    	files.push(a);
		    }
		   
		});	
	   
	$.ajax({
		url : xh.getUrl()+'WorkContact/add',
		type : 'POST',
		dataType : "json",
		async : false,
		data : {
			formData : xh.serializeJson($("#addForm").serializeArray()),
			files: JSON.stringify(files)
		// 将表单序列化为JSON对象
		},
		success : function(data) {
			if (data.success) {
				bui.alert(data.message)
				$("input[name='result']").val("");
            	$("input[name='fileName']").val("");
            	$("input[name='filePath']").val("");
            	$("input[name='pathName']").val('');
            	$file =$("input[name='pathName");
            	$file.remove();
            	 $('.file').append('<input  class="form-control" type="file" id="pathName" name="pathName" />');
            	bui.back({
            		callback:function(){
            			loader.require(['pages/workcontact/table'],function(res){
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
/*
 * function upload() {
 * console.log("->"+$("#addForm").find('input[name="pathName"]').val()) if
 * ($('#pathName').val() =="") { toastr.error("请选择需要上传的文件！","提示"); return false; }
 * 
 * $("input[name='result']").val(2); var uiLoading = bui.loading({
 * text:'文件中传中，请耐心等待' });
 * 
 * 
 * uiLoading.show(); $.ajaxFileUpload({ url : xh.getUrl()+'uploadFile/upload',
 * //用于文件上传的服务器端请求地址 secureuri : false, //是否需要安全协议，一般设置为false fileElementId :
 * 'pathName', //文件上传域的ID dataType : 'json', // 返回值类型 一般设置为json type : 'POST',
 * success : function(data, status) //服务器成功响应处理函数 { uiLoading.hide(); if
 * (data.success) { $("input[name='result']").val(1);
 * $("input[name='fileName']").val(data.fileName);
 * $("input[name='filePath']").val(data.filePath);
 * $("#uploadResult").html("文件上传成功"); toastr.success("文件上传成功", '提示'); } else {
 * $("input[name='result']").val(0); toastr.error("文件上传失败", '提示');
 * $("#uploadResult").html("文件上传失败"); }
 *  }, error : function(data, status, e)//服务器响应失败处理函数 { uiLoading.hide(); } }); }
 */
/*function No() {
	var today = new Date();
	var y = today.getFullYear();
	var m = today.getMonth() + 1;
	var d = today.getDate();
	var h=today.getHours();
	var m2=today.getMinutes();
	var s=today.getSeconds();
	var str='CDYJW-';
	m = m < 10 ? "0" + m : m; // 这里判断月份是否<10,如果是在月份前面加'0'
	d = d < 10 ? "0" + d : d; // 这里判断日期是否<10,如果是在日期前面加'0'
	h = h < 10 ? "0" + h : h; // 这里判断日期是否<10,如果是在日期前面加'0'
	m2 = m2 < 10 ? "0" + m2 : m2; // 这里判断日期是否<10,如果是在日期前面加'0'
	s = s < 10 ? "0" + s : s; // 这里判断日期是否<10,如果是在日期前面加'0'

	return str+y+m+d+h+m2+s;
}*/
function sss(){
	bui.ajax({
        url: xh.getUrl()+"web/loginUserInfo",
        method:'GET',
        dataType : "json",
        data: {},
        async : false
    }).then(function(data){
    	if(parseInt(res.roleType)==2){
        	str="成都市软件产业发展中心（成都信息化技术应用发展中心）";
        
        }else if(parseInt(res.roleType)==3 || parseInt(res.roleType)==0){
        	str="成都亚光电子股份有限公司";
		}
		return str;
    },function(res,status){
        console.log(status);
    })
}