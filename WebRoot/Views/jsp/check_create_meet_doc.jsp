<%@ page language="java" 
    import="java.util.*,
    com.zhuozhengsoft.pageoffice.*,
    com.zhuozhengsoft.pageoffice.wordwriter.*,
    xh.func.plugin.*,
    java.net.*,
    xh.mybatis.bean.ScoreBean" 
    pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<%
    
        WordDocument doc = new WordDocument();

        Map<String,Object> map=(Map<String,Object>)request.getSession().getAttribute(request.getSession().getId()+"meet_doc");
        String doc_name=map.get("doc_name").toString();
        String meet_name=map.get("meet_name").toString();
        String meet_path=map.get("meet_path").toString();
        System.out.println("name->"+map);
        //String str=month.split("-")[0]+"年"+month.split("-")[0]+"月";
        doc.setEnableAllDataRegionsEditing(true); // 此属性可以设置在提交模式（docSubmitForm）下，所有的数据区域可以编辑

        /* if(type==3){
        	doc.openDataRegion("PO_Name").setValue("成都市应急指挥调度无线通信网三期工程服务项目\r\n"+bean.getTime()+"项目服务扣分表");
        	
        }else{
        	doc.openDataRegion("PO_Name").setValue("成都市应急指挥调度无线通信网四期工程服务项目\r\n"+bean.getTime()+"项目服务扣分表");
        } */
        doc.openDataRegion("PO_Name").setValue(doc_name);
       
        String name=URLEncoder.encode(meet_path, "utf-8");
      

        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        fmCtrl.setServerPage(basePath+"poserver.zz");
        fmCtrl.setWriter(doc);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
        fmCtrl.setSaveFilePage(basePath+"office/save_page?path="+name);
        fmCtrl.fillDocumentAs(request.getSession().getServletContext().getRealPath("/")+meet_path, DocumentOpenType.Word, meet_name);
      
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnProgressComplete() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             window.external.close(); 
             window.external.CallParentFunc("xh.createMeetDocSussess()");
        }
    </script>

</head>
<body>
<p>正在创建会议纪要文件。请耐心等待</p>
    <div>
        <%=fmCtrl.getHtmlCode("FileMakerCtrl1")%>
    </div>
</body>
</html>