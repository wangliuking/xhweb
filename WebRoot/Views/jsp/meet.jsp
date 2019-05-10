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
	function BeforeBrowserClosed() {
		if (document.getElementById("PageOfficeCtrl1").IsDirty) {
			if (confirm("提示：文档已被修改，是否继续关闭放弃保存 ？")) {
				return true;

			} else {

				return false;
			}

		}

	}
</script>

</head>
<body>

	<script type="text/javascript">
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();

		}
		function PrintFile() {
			document.getElementById("PageOfficeCtrl1").ShowDialog(4);

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

