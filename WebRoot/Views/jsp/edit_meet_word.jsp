<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>会议纪要</title>
<script type="text/javascript" src="../jquery.min.js"></script>
<script type="text/javascript">
	//文档关闭前先提示用户是否保存
/* 	function BeforeBrowserClosed() {
		 if (document.getElementById("PageOfficeCtrl1").IsDirty) {
			if (confirm("提示：文档修改过，需要保存才能生效，确认已经保存过，并关闭文档？")) {
				return true;

			} else {

				return false;
			}

		} 

	} */
</script>

</head>
<body>

	<script type="text/javascript">
	var searName1="${sealName1}";
	var searName2="${sealName2}";
	var type="${type}";
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            if(type==2){
                if(!getSeal()){               	
                	//alert("你还没有签字");
                }else{
                	//window.external.CallParentFunc("xh.signMeet2()");
                }
            }else{
            	if(!getSeal()){                
                    //alert("你还没有签字");
                }else{
                	//window.external.CallParentFunc("xh.signMeetVertical()");
                }
            }
		}
		
		function PrintFile() {
			document.getElementById("PageOfficeCtrl1").ShowDialog(4);

		}
		function IsFullScreen() {
			document.getElementById("PageOfficeCtrl1").FullScreen = !document
					.getElementById("PageOfficeCtrl1").FullScreen;

		}
		function CloseFile() {
			
			
			if (confirm("提示：文档修改过，需要保存才能生效，确认已经保存过，并关闭文档？")) {
				window.external.close();
                return true;

            } else {

                return false;
            }
			//Save();
			

		}
		function sign() {
            try {
                var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
                        .AddSealByName(searName2, null, null);
                
                if (bRet) {
                    //alert("签字成功！");
                } else {
                    alert("签字失败！");
                }
            } catch (e) {
            }
        }
		function Seal() {
            try {
                var bRet = document.getElementById("PageOfficeCtrl1").ZoomSeal
                        .AddSealByName(searName1, null, null);
                
                if (bRet) {
                    //alert("盖章成功！");
                } else {
                    alert("盖章失败！");
                }
            } catch (e) {
            }
        }
		function getSeal() {
            var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            var strTempSealName = "";
            if (iCount > 0) {
                for (var i = 0; i < iCount; i++) {
                    strTempSealName = document
                            .getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
                    if (strTempSealName == searName2) {
                    	return true;
                        break;
                    }
                }
            } else {
                
            }
            return false;
        }
		
		function check(){
			window.external.close();
			window.external.CallParentFunc("xh.checkModel()");
		}
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

