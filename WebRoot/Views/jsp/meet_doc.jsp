<%@ page language="java" 
    import="java.util.*,
    com.zhuozhengsoft.pageoffice.*,
    com.zhuozhengsoft.pageoffice.wordwriter.*,
    xh.func.plugin.*,
    xh.mybatis.bean.MeetBean" 
    pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String str=request.getParameter("bean");
MeetBean bean=GsonUtil.json2Object(str, MeetBean.class);
%>
<%
    
        WordDocument doc = new WordDocument();
        doc.setEnableAllDataRegionsEditing(true); // 此属性可以设置在提交模式（docSubmitForm）下，所有的数据区域可以编辑

        doc.openDataRegion("PO_Name").setValue(bean.getName());
        doc.openDataRegion("PO_Time").setValue(bean.getMeet_time());
        doc.openDataRegion("PO_Address").setValue(bean.getAddress());
        doc.openDataRegion("PO_Person").setValue(bean.getPerson());
        doc.openDataRegion("PO_Content").setValue(bean.getContent());
       

        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        fmCtrl.setServerPage(basePath+"poserver.zz");
        fmCtrl.setWriter(doc);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
        fmCtrl.setSaveFilePage(basePath+"office/save_page?path="+bean.getFile_path());
        fmCtrl.fillDocumentAs(request.getSession().getServletContext().getRealPath("doc/template/meet.doc"), DocumentOpenType.Word, bean.getFile_name());
      
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnProgressComplete() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             window.external.close(); 
             window.external.CallParentFunc("xh.addSuccess()");
        }
    </script>

</head>
<body>
<p>正在生成文件。请耐心等待</p>
    <div>
        <%=fmCtrl.getHtmlCode("FileMakerCtrl1")%>
    </div>
</body>
</html>