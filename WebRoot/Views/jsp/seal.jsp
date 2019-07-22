<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>会议纪要</title>
<script type="text/javascript" src="../jquery.min.js"></script>

</head>
<body>

	<script type="text/javascript">
	    var strSealName = "${sealName}";
	    var userName="${userName}";
	    var type = "${type}";
	    var fileId="${fileId}";
	    fileId=parseInt(fileId);
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			if(getSeal()){
				if(fileId>0){
                    
                    window.external.CallParentFunc("xh.sealDoc("+fileId+")");
                }
			}
			//cc();

		}
		
		 //文档关闭前先提示用户是否保存
        function BeforeBrowserClosed(){
         if (document.getElementById("PageOfficeCtrl1").IsDirty){
                if(confirm("提示：文档修改过，需要保存才能生效，确认已经保存过，并关闭文档？"))
                {
                    return  true;
                    
                }else{
                
                    return  false;
                }
                
            }
             
        }
		function IsFullScreen() {
			document.getElementById("PageOfficeCtrl1").FullScreen = !document
					.getElementById("PageOfficeCtrl1").FullScreen;

		}
		function CloseFile() {
			window.external.close();

		}
		function Seal() {
			try {
				var bRet=document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSeal(userName);
				if (bRet) {
                    if(fileId>0){
                        
                        window.external.CallParentFunc("xh.sealDoc("+fileId+")");
                    }
                    alert(msg+"成功！");
                } else {
                    alert(msg+"失败！");
                }
				
				/* var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
						.AddSealByName(strSealName, null, null);
				var msg="盖章";
				if(type=="1"){
					 msg="盖章";
				}else{
					msg="签字";
				}
				if (bRet) {
					if(fileId>0){
						
						window.external.CallParentFunc("xh.sealDoc("+fileId+")");
					}
					alert(msg+"成功！");
				} else {
					alert(msg+"失败！");
				} */
			} catch (e) {
			}
		}
		
		function DeleteSeal() {
			var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
			var strTempSealName = "";
			if (iCount > 0) {
				for (var i = 0; i < iCount; i++) {
					strTempSealName = document
							.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
					if (strTempSealName == strSealName) {
						document.getElementById("PageOfficeCtrl1").ZoomSeal
								.Item(i).DeleteSeal();//删除印章
						alert("成功删除了“" + strSealName + "”");
						break;
					}
				}
			} else {
				alert("请先在文档中加盖印章后，再执行当前操作。");
			}
		}
		function getSeal() {
            var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            var strTempSealName = "";
            if (iCount > 0) {
                /* for (var i = 0; i < iCount; i++) {
                    strTempSealName = document.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
                    if (strTempSealName == searName2) {
                        return true;
                        break;
                    }
                } */
                return true;
            } else {
                
            }
            return false;
        }
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

