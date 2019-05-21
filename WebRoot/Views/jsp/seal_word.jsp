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
		function Seal() {
			try {
				var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
						.AddSealByName("shangxu", null, null);
				if (bRet) {
					alert("盖章成功！");
				} else {
					alert("盖章失败！");
				}
			} catch (e) {
			}
		}
		var strSealName = "shangxu"
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
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

