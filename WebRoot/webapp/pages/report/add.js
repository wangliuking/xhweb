// 默认已经定义了main模块
loader.define(function(require,exports,module) {
    var pageview = {};
    // 主要业务初始化
    pageview.init = function() {
        var bs=bui.store({
            scope:'page',
            data:{
            	type:"周报"
            },
            methods:{
            	add:add,
            	upload:function(){
                	upload();
                }
            },
            beforeMount: function(){
                // 数据解析前执行
              },
           mounted: function(){
                // 数据解析后执行
              }
            
        });
        // 静态自定义绑定
        var uiSelect2 = bui.select({
            id: "#select-dialog",
            trigger: "#select2",
            type: "radio",
            effect: "fadeInRight",
            position: "right",
            fullscreen: true,
            mask: false,
            buttons: []
        });
     // 自定义确定按钮事件
        $("#makeSure").click(function() {
        	bs.type=uiSelect2.value();
            uiSelect2.hide();
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
	$.ajax({
		url : xh.getUrl()+'eventReport/addEventReport',
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
            url : xh.getUrl()+'uploadFile/upload', // 用于文件上传的服务器端请求地址
            secureuri : false, // 是否需要安全协议，一般设置为false
            fileElementId : 'pathName', // 文件上传域的ID
            dataType : 'json', // 返回值类型 一般设置为json
            type : 'POST',
            success : function(data, status) // 服务器成功响应处理函数
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
            error : function(data, status, e)// 服务器响应失败处理函数
            {
                alert(e);
                $("#uploadfile").attr("disabled", false);
                xh.maskHide();
            }
        });
    }

