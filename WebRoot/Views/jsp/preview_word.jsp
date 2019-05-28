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
</script>

</head>
<body>
	<script type="text/javascript">
		
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

