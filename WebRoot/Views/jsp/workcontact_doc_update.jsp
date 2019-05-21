<%@ page language="java" 
    import="java.util.*,
    com.zhuozhengsoft.pageoffice.*,
    com.zhuozhengsoft.pageoffice.wordwriter.*,
    xh.func.plugin.*,
    xh.mybatis.bean.WorkContactBean" 
    pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String str=request.getParameter("bean");
WorkContactBean bean=GsonUtil.json2Object(str, WorkContactBean.class);
%>
<%
    
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        WordDocument doc = new WordDocument();
        
        DataRegion dataRegion = doc.openDataRegion("PO_CheckUser");
        dataRegion.setEditing(true);
        dataRegion.setValue(bean.getCheck_person());
        
        
        poCtrl.setServerPage(basePath+"poserver.zz");
        poCtrl.setWriter(doc);
        poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
        poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
        poCtrl.setJsFunction_AfterDocumentOpened("OnOpend");
       // poCtrl.setjs
        poCtrl.setSaveFilePage(basePath+"office/save_page?path="+bean.getFile_path());
        poCtrl.setTagId("PageOfficeCtrl1");
    
        poCtrl.webOpen(basePath+bean.getFile_path(), OpenModeType.docSubmitForm, ""); 
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnOpend() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             document.getElementById("PageOfficeCtrl1").WebSave();
             window.external.close();  
        }
        function Save() {
            // window.parent.hideGif();// 隐藏Default页中的loading图片
              document.getElementById("PageOfficeCtrl1").WebSave();
         }
    </script>

</head>
<body>
<p>正在操作数据。请耐心等待</p>
    <div style="width:400px;height:200px;">
        <%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
    </div>
</body>
</html>