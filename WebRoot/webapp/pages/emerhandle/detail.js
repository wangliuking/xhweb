// 默认已经定义了main模块

var params="";
loader.define(function(require,exports,module){
    var pageview = {};
    console.log(xh.getUrl());
    // 主要业务初始化
    pageview.init = function() {
        // 这里写main模块的业务
        params = router.getPageParams();
        var bs=bui.store({
            scope:'page',
            data:{
                list:params,
                show1:(gl_para.userL.roleType==3 && gl_para.up.o_check_emergency=='on' && params.checked==0)?true:false,
                show2:(gl_para.userL.roleType==2 && gl_para.up.o_check_emergency=='on' && (params.checked==1 || params.checked==-1))?true:false,
                show3:(gl_para.userL.roleType==3 && gl_para.up.o_check_emergency=='on' && params.checked==2)?true:false,
                show4:(gl_para.userL.roleType==2 && gl_para.up.o_check_emergency=='on' && params.checked==3)?true:false
                
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
            		var filepath = "/Resources/upload/emergency/" + fileName;
            		if(fileName!=null && fileName!=""){
            			var index=filepath.lastIndexOf("/");
            			var name=filepath.substring(index+1,filepath.length);	
            			var downUrl = xh.getUrl()+"uploadFile/downfile?filePath="+filepath+"&fileName=" + name;
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
        var checkDialog3 = bui.dialog({
            id: "#check-dg3",
            fullscreen: true,
            mask: false,
            effect: "fadeInRight"
        });
        var checkDialog4 = bui.dialog({
            id: "#check-dg4",
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
        $("#check3").click(function() {
        	checkDialog3.open();
        });
        $("#check4").click(function() {
        	checkDialog4.open();
        });
        $("#progress").click(function() {
        	router.load({ url: "pages/emerhandle/progress.html", 
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
		url : xh.getUrl()+'emergency/checkedOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			userName:params.userName,
			note2:$("#check-dg").find("textarea[name='note2']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["pages/emerhandle/table"],function(res){
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
function uploadFile(tag) {
	var path="",uiDialog="";
	 if(tag==1){
		 path = 'filePath1';uiDialog="check-dg";
	 }else if(tag==2){
		 path = 'filePath2';uiDialog="check-dg3";
	 }else{
		 return;
	 }
	 if ($("#"+uiDialog).find('input[name="filePath"]').val() =="") {
      	 toastr.error("请选择需要上传的文件！","提示");
          return false;
      }
	  $("#"+uiDialog).find("input[name='result']").val(2);
	 var uiLoading = bui.loading({
         text:'文件中传中，请耐心等待'
     });
     uiLoading.show();
     uiMask.show();
      $.ajaxFileUpload({
          url : xh.getUrl()+'emergency/upload', // 用于文件上传的服务器端请求地址
          secureuri : false, // 是否需要安全协议，一般设置为false
          fileElementId : path, // 文件上传域的ID
          dataType : 'json', // 返回值类型 一般设置为json
          type : 'POST',
          success : function(data, status) // 服务器成功响应处理函数
          {
        	  uiLoading.hide();
        	  uiMask.hide();
              if (data.success) {
            	  if(tag==1){
            		  checkedOne(data);
            	  }else if(tag==2){
            		  checkedThree(data);
            	  }
              } else {
            	  $("#"+uiDialog).find("input[name='result']").val(0);
                  toastr.error("文件上传失败", '提示');
              }

          },
          error : function(data, status, e)// 服务器响应失败处理函数
          {
        	  uiLoading.hide();
        	  uiMask.hide();
        	  toastr.error(e, '提示');
          }
      });
  }
function checkedOne(data){
	$("#check-dg").find("input[name='result']").val(1);
	$("#check-dg").find("input[name='fileName']").val(data.fileName);
	$("#check-dg").find("input[name='path']").val(data.filePath);
	$.ajax({
		url : xh.getUrl()+'emergency/checkedOne',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			userName:params.userName,
			fileName: $("#check-dg").find("input[name='fileName']").val(),
			path:$("#check-dg").find("input[name='path']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["pages/emerhandle/table"],function(res){
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
function checkedTwo(tag){
	$.ajax({
		url : xh.getUrl()+'emergency/checkedTwo',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			user1:params.user1,
			checked: tag
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["pages/emerhandle/table"],function(res){
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
function checkedThree(data){
	$("#check-dg3").find("input[name='result']").val(1);
	$("#check-dg3").find("input[name='fileName']").val(data.fileName);
	$("#check-dg3").find("input[name='path']").val(data.filePath);
	$.ajax({
		url : xh.getUrl()+'emergency/checkedThree',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			userName:params.userName,
			note3:$("#check-dg3").find("textarea[name='note3']").val(),
			fileName: $("#check-dg3").find("input[name='fileName']").val(),
			path:$("#check-dg3").find("input[name='path']").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["pages/emerhandle/table"],function(res){
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
function checkedFour(){
	$.ajax({
		url : xh.getUrl()+'emergency/checkedFour',
		type : 'POST',
		dataType : "json",
		async : true,
		data : {
			id:params.id,
			user1:params.user1,
			note4: $("#check-dg4").find("textarea[name='note4']").val(),
			level: $("#check-dg4").find("input[name='level']:checked").val()
		},
		success : function(data) {

			if (data.success) {
				toastr.success(data.message, '提示');
				bui.back({
					callback:function(){
						loader.require(["pages/emerhandle/table"],function(res){
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