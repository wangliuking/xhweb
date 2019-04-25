// 默认已经定义了main模块
loader.define(function(require,exports,module) {
    var pageview = {};
    // 主要业务初始化
    pageview.init = function() {
        var bs=bui.store({
            scope:'page',
            data:{
            	No:No(),
            	sendUnit:gl_para.sendUnit
            },
            methods:{
            	add:add,
            	upload:function(){
                	upload();
                }
            }
            
        });

    };
    // 事件绑定
    pageview.bind = function() {
    	/*$("input[name='pathName").on("change",function(e){
    		var ss = e.currentTarget.files;
    		$("#filelist").append("343434");
    		alert($("#filelist").html());
    		e.currentTarget.files[0].name
    		console.log("文件->"+ss.length)
    		for (var m = 0; m < ss.length; m++) {
    			var efileName = ss[m].name;
    			if (ss[m].size > 1024 * 1024) {
    				sfileSize = (Math.round(ss[m].size / (1024 * 1024))).toString()
    						+ 'MB';
    			} else {
    				sfileSize = (Math.round(ss[m].size / 1024)).toString() + 'KB';
    			}
    			$("#filelist").append('<li>'+efileName+'</li>')
    			
    		}
        });*/
    	
       /* $("#file").on("click",function(e){
            alert(1)
        });*/
       /* $("#add").click(function(){
        	alert(111)
        	bui.alert("提醒对话框");
        	bui.ajax({
                url: "../../../WorkContact/add",
                method : 'POST',
        		dataType : "json",
        		async : true,
                data: {
                	formData : xh.serializeJson($("#addForm").serializeArray())
                }
            }).then(function(res){
              alert(res.success)
                
            },function(res,status){
                console.log(status);
                // status = "timeout" || "error" || "abort", "parsererror"
            })
        })*/
    }
    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();
    return pageview;
})
function add(){
	$.ajax({
		url : xh.getUrl()+'WorkContact/add',
		type : 'POST',
		dataType : "json",
		async : false,
		data : {
			formData : xh.serializeJson($("#addForm").serializeArray())
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
		},
		error : function() {
			toastr.error("参数错误", '提示');
		}
	});
}
function upload() {
	  console.log("->"+$("#addForm").find('input[name="pathName"]').val())
       if ($('#pathName').val() =="") {
        	 toastr.error("请选择需要上传的文件！","提示");
            return false;
        }
      
        $("input[name='result']").val(2);
        $("#uploadfile").attr("disabled", true);
        xh.maskShow("正在上传文件，请耐心等待");
        $.ajaxFileUpload({
            url : xh.getUrl()+'uploadFile/upload', //用于文件上传的服务器端请求地址
            secureuri : false, //是否需要安全协议，一般设置为false
            fileElementId : 'pathName', //文件上传域的ID
            dataType : 'json', // 返回值类型 一般设置为json
            type : 'POST',
            success : function(data, status) //服务器成功响应处理函数
            {
                $("#uploadfile").attr("disabled", false);
                xh.maskHide();
                if (data.success) {
                    $("input[name='result']").val(1);
                    $("input[name='fileName']").val(data.fileName);
                    $("input[name='filePath']").val(data.filePath);
                    $("#uploadResult").html("文件上传成功");
                    toastr.success("文件上传成功", '提示');
                } else {
                    $("input[name='result']").val(0);
                    toastr.error("文件上传失败", '提示');
                    $("#uploadResult").html("文件上传失败");
                }

            },
            error : function(data, status, e)//服务器响应失败处理函数
            {
                alert(e);
                $("#uploadfile").attr("disabled", false);
                xh.maskHide();
            }
        });
    }
function No() {
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
}
function sss(){
	bui.ajax({
        url: xh.getUrl()+"web/loginUserInfo",
        method:'GET',
        dataType : "json",
        data: {},
        async : false
    }).then(function(data){
    	if(parseInt(res.roleType)==2){
        	str="成都市软件产业发展中心";
        
        }else if(parseInt(res.roleType)==3 || parseInt(res.roleType)==0){
        	str="成都亚光电子股份有限公司";
		}
		return str;
    },function(res,status){
        console.log(status);
    })
}