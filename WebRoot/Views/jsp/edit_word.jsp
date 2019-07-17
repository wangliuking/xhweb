<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>编辑doc</title>
<script type="text/javascript" src="../jquery.min.js"></script>

</head>
<body>

	<script type="text/javascript">
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
		
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

