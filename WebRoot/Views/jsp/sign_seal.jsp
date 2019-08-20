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
	    var strSealName1 = "${sealName1}";
	    var strSealName2 = "${sealName2}";
	    var type = "${type}";
	   /*  fileId=parseInt(fileId); */
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
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
		function sign() {
            try {
                var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
                        .AddSealByName(strSealName2, null, null);
               
                if (bRet) {
                    /* if(fileId>0){
                        
                        window.external.CallParentFunc("xh.sealDoc("+fileId+")");
                    } */
                    //Save();
                   // alert("签字成功！");
                } else {
                    alert("签字失败！");
                }
            } catch (e) {
            }
        }
        
		function Seal() {
			try {
				var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
						.AddSealByName(strSealName1, null, null);
				if (bRet) {
					/* if(fileId>0){
						
						window.external.CallParentFunc("xh.sealDoc("+fileId+")");
					} */
					//Save();
					//alert("盖章成功！");
				} else {
					alert("盖章失败！");
				}
			} catch (e) {
			}
		}
		
		
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

