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
    
        WordDocument doc = new WordDocument();
        doc.setEnableAllDataRegionsEditing(true); // 此属性可以设置在提交模式（docSubmitForm）下，所有的数据区域可以编辑

        doc.openDataRegion("PO_Reason").setValue(bean.getReason());
        doc.openDataRegion("PO_Type").setValue(bean.getType());
        doc.openDataRegion("PO_SendUnit").setValue(bean.getSendUnit());
        doc.openDataRegion("PO_RecvUnit").setValue(bean.getRecvUnit());
        doc.openDataRegion("PO_CopyUnit").setValue(bean.getCopyUnit());
        doc.openDataRegion("PO_Time").setValue(bean.getTime());
        doc.openDataRegion("PO_Code").setValue(bean.getCode());
        doc.openDataRegion("PO_Content").setValue(bean.getContent());
        doc.openDataRegion("PO_AddUser").setValue(bean.getAddUser());
      

        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        fmCtrl.setServerPage(basePath+"poserver.zz");
        fmCtrl.setWriter(doc);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
        fmCtrl.setSaveFilePage(basePath+"office/save_page?path="+bean.getFile_path());
        fmCtrl.fillDocumentAs(basePath+"doc/template/工作联系单.doc", DocumentOpenType.Word, bean.getFile_name());
      
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnProgressComplete() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             window.external.close(); 
        }
    </script>

</head>
<body>
<p>正在操作数据。请耐心等待</p>
    <div>
        <%=fmCtrl.getHtmlCode("FileMakerCtrl1")%>
    </div>
</body>
</html>