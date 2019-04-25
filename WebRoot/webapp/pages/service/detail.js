// 默认已经定义了main模块

var params="";
loader.define(function(require,exports,module){
    var pageview = {};

    // 主要业务初始化
    pageview.init = function() {
        // 这里写main模块的业务
       params = router.getPageParams();
        var styles="",styles2="";
        
        if(gl_para.userL.roleType==3 && params.checked==0){
        	styles="";
        }else{
        	styles="none";
        }
        if(gl_para.userL.roleType==2 && params.checked==1){
        	styles2="";
        }else{
        	styles2="none";
        }

        var bs=bui.store({
            scope:'page',
            data:{
                list:params,
                styles: {
                    display: styles
                },
                styles2: {
                    display: styles2
                }
            },
            methods:{
            	download:function(type){
            		var fileName="",filePath="";
            		if(type == 1){
            			fileName = params.fileName1;
            			filePath = params.filePath1;
            		}
            		else if(type == 2){
            			fileName = params.fileName2;
            			filePath = params.filePath2;
            		}
            		else if(type == 3){
            			fileName = params.fileName3;
            			filePath = params.filePath3;
            		}
            		var filepath = "/Resources/upload/servicecheck/" + fileName;
            		if(fileName!=null && fileName!=""){
            			var index=filepath.lastIndexOf("/");
            			var name=filepath.substring(index+1,filepath.length);	
            			var downUrl = h.getUrl()+"uploadFile/downfile?filePath="+filepath+"&fileName=" + name;
            			if(xh.isfileapp(filepath)){
            				window.open(downUrl, '_self',
            				'width=1,height=1,toolbar=no,menubar=no,location=no');
            			}else{
            				toastr.error("文件不存在", '提示');
            			}
            		}
            	}
            }
        });
       
     // 静态自定义绑定
        var checkDialog = bui.dialog({
            id: "#check-dg",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        var checkDialog2 = bui.dialog({
            id: "#check-dg2",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        $("#check").click(function() {
        	checkDialog.open();
        });
        $("#check2").click(function() {
        	checkDialog2.open();
        });
        $("#progress").click(function() {
        	router.load({ url: "pages/service/progress.html", 
        		param:params});
        });
     // 自定义确定按钮事件
        $("#makeSure").click(function() {
        	check_dg.hide();
        });
        
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
function sureBtn(){
	$.ajax({
		url : h.getUrl()+'qualitycheck/checkedOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			userName:params.userName,
			note1:$("#check-dg").find("textarea[name='note1']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["table"],function(res){
							res.refresh();
							res.init();
	                    })
					}
				});
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.success("系统错误", '提示');
		}
	});
}
function uploadFile() {
	  console.log("->"+$("#check-dg2").find('input[name="filePath"]').val())
     if ($("#check-dg2").find('input[name="filePath"]').val() =="") {
      	 toastr.error("请选择需要上传的文件！","提示");
          return false;
      }
    
	  $("#check-dg2").find("input[name='result']").val(2);
      xh.maskShow("正在上传文件，请耐心等待");
      $.ajaxFileUpload({
          url : h.getUrl()+'qualitycheck/upload', // 用于文件上传的服务器端请求地址
          secureuri : false, // 是否需要安全协议，一般设置为false
          fileElementId : 'filePath2', // 文件上传域的ID
          dataType : 'json', // 返回值类型 一般设置为json
          type : 'POST',
          success : function(data, status) // 服务器成功响应处理函数
          {
              if (data.success) {
            	  $("#check-dg2").find("input[name='result']").val(1);
            	  $("#check-dg2").find("input[name='fileName']").val(data.fileName);
            	  $("#check-dg2").find("input[name='path']").val(data.filePath);
                  //toastr.success("文件上传成功", '提示');
                  uploadRecoreBtn();
              } else {
            	  $("#check-dg2").find("input[name='result']").val(0);
                  toastr.error("文件上传失败", '提示');
              }

          },
          error : function(data, status, e)// 服务器响应失败处理函数
          {
              alert(e);
          }
      });
  }
function uploadRecoreBtn(){
	$.ajax({
		url : h.getUrl()+'qualitycheck/checkedTwo',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			user1:params.user1,
			fileName: $("#check-dg2").find("input[name='fileName']").val(),
			path:$("#check-dg2").find("input[name='path']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["table"],function(res){
							res.refresh();
							res.init();
	                    })
					}
				});
				
			} else {
				toastr.error(data.message, '提示');
			}
		},
		error : function() {
			toastr.success("系统错误", '提示');
		}
	});
}