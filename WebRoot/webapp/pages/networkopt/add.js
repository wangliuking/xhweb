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
	if($("#addForm").find("input[name='result']").val()!=1){
		toastr.error("你还没有上传文件", '提示');
		return;
	}
	$.ajax({
		url : xh.getUrl()+'optimizenet/insertOptimizeNet',
		type : 'POST',
		dataType : "json",
		async : false,
		data :$("#addForm").serializeArray(),
		success : function(data) {
			if (data.success) {
				bui.alert(data.message)
				$("#addForm").find("input[name='result']").val("");
				$("#addForm").find("input[name='fileName']").val("");
				$("#addForm").find("input[name='filePath']").val("");
				$("#addForm").find("input[name='pathName']").val('');
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
	  console.log("->"+$("#addForm").find('input[name="filePath"]').val())
       if ($("#addForm").find('input[name="filePath"]').val() =="") {
        	 toastr.error("请选择需要上传的文件！","提示");
            return false;
        }
      var uiLoading = bui.loading({
          text:'文件中传中，请耐心等待'
      });
      

      uiLoading.show();
      uiMask.show();
      
	  $("#addForm").find("input[name='result']").val(2);
        $.ajaxFileUpload({
            url : xh.getUrl()+'optimizenet/upload', // 用于文件上传的服务器端请求地址
            secureuri : false, // 是否需要安全协议，一般设置为false
            fileElementId : 'filePath1', // 文件上传域的ID
            dataType : 'json', // 返回值类型 一般设置为json
            type : 'POST',
            success : function(data, status) // 服务器成功响应处理函数
            {
            	uiLoading.hide();
                uiMask.hide();
                if (data.success) {
                	$("input[name='result']").val(1);
    				$("input[name='fileName']").val(data.fileName);
    				$("input[name='path']").val(data.filePath);
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
            	toastr.error(e,'提示');
                uiLoading.hide();
                uiMask.hide();
            }
        });
    }