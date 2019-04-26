// 默认已经定义了main模块
loader.define(function(require,exports,module) {
    var pageview = {};
    // 主要业务初始化
    pageview.init = function() {
        var bs=bui.store({
            scope:'page',
            data:{
            	tel:gl_para.userL.tel,
            	applicant:gl_para.userL.userName
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
    	
    }
    // 初始化
    pageview.init();
    // 绑定事件
    pageview.bind();
    return pageview;
})
function add(){
	if ($("#addForm").find('input[name="result"]').val()!=1) {
   	 toastr.error("你还没上传文件！","提示");
       return false;
   }
	$.ajax({
		url : xh.getUrl()+'work/addwork',
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
            	bui.back({
            		callback:function(){
            			loader.require(['pages/workrecord/table'],function(res){
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
       if ($("#addForm").find('input[name="pathName"]').val() =="") {
        	 toastr.error("请选择需要上传的文件！","提示");
            return false;
        }
      
        $("input[name='result']").val(2);
        var uiLoading = bui.loading({
            text:'文件中传中，请耐心等待'
        });
        

        uiLoading.show();
        uiMask.show();
        $.ajaxFileUpload({
            url : xh.getUrl()+'uploadFile/upload', //用于文件上传的服务器端请求地址
            secureuri : false, //是否需要安全协议，一般设置为false
            fileElementId : 'pathName', //文件上传域的ID
            dataType : 'json', // 返回值类型 一般设置为json
            type : 'POST',
            success : function(data, status) //服务器成功响应处理函数
            {
            	uiLoading.hide();
                uiMask.hide();
                if (data.success) {
                	$("#addForm").find("input[name='result']").val(1);
                	$("#addForm").find("input[name='fileName']").val(data.fileName);
                	$("#addForm").find("input[name='filePath']").val(data.filePath);
                    toastr.success("文件上传成功", '提示');
                } else {
                    $("input[name='result']").val(0);
                    toastr.error("文件上传失败", '提示');
                }

            },
            error : function(data, status, e)//服务器响应失败处理函数
            {
            	uiLoading.hide();
                uiMask.hide();
            }
        });
    }
