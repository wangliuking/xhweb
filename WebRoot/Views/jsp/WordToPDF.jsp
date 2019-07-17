<%@ page language="java"
	import="java.util.*, com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@ page import="com.zhuozhengsoft.pageoffice.wordreader.WordDocument" %>
<%
	FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
	fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	fmCtrl.setSaveFilePage("/officeTest/savepdf"); //保存pdf的action或RequestMapping方法
	fmCtrl.fillDocumentAsPDF("doc/test.doc", DocumentOpenType.Word, "test.pdf");//填充word模板并转为pdf
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Word文件转换成PDF格式</title>
		<<input type="file" value="">
		<script type="text/javascript">
    </script>

	</head>
	<body>
		<form id="form1">
			<div id="div1"></div>
			<div style="width: auto; height: 700px;">

			</div>
		</form>
	</body>
</html>

