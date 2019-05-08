<%@ page language="java"
    import="java.util.*,com.zhuozhengsoft.pageoffice.*"
    pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
poCtrl.addCustomToolButton("保存", "Save()", 1);
poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
//设置保存页面
poCtrl.setSaveFilePage("SaveFile.jsp");
//打开Word文档
poCtrl.webOpen("doc/test.doc",OpenModeType.docNormalEdit,"张佚名");
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title> 选项卡</title>
    <style type="text/css">
     /* CSS样式制作 */  
     *{padding:0px; margin:0px;}
      a{ text-decoration:none; color:black;}
      a:hover{text-decoration:none; color:#336699;}
       #tab{width:auto; padding:5px;height:150px;margin:20px;}
       #tab ul{list-style:none; height:30px;line-height:30px; border-bottom:2px #C88 solid;}
       #tab ul li{background:#FFF;cursor:pointer;float:left;list-style:none height:29px; line-height:29px;padding:0px 10px; margin:0px 3px; border:1px solid #BBB; border-bottom:2px solid #C88;}
       #tab ul li.on{border-top:2px solid Saddlebrown; border-bottom:2px solid #FFF;}
       #tab div{height:700px;width:auto; line-height:24px;border-top:none;  padding:1px; border:1px solid #336699;padding:10px;}
       .hide{display:none;}
       .show{ display:block;}
       .page{}
    </style>
    <script type="text/javascript" src="../jquery.min.js"></script>
    <script type="text/javascript">
        // JS实现选项卡切换
       
    </script>

</head>
<body>

    <script type="text/javascript">
        function Save() {
             document.getElementById("PageOfficeCtrl1").WebSave();
             
           }
        function PrintFile(){
             document.getElementById("PageOfficeCtrl1").ShowDialog(4); 
             
           }
        function IsFullScreen(){
             document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
             
           }
        function CloseFile(){
             window.external.close(); 
             
           }
    </script>
    <div id = "tab">
       
    <div id="firstPage">
        <%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
    </div>
   

</div>

</body>
</html>

