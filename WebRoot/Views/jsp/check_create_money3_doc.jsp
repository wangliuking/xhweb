<%@ page language="java" 
    import="java.util.*,
    com.zhuozhengsoft.pageoffice.*,
    com.zhuozhengsoft.pageoffice.wordwriter.*,
    xh.func.plugin.*,
    java.net.*,
    xh.mybatis.bean.MoneyBean" 
    pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

MoneyBean bean=(MoneyBean)request.getSession().getAttribute(request.getSession().getId()+"money3");
%>
<%
    
        WordDocument doc = new WordDocument();
        doc.setEnableAllDataRegionsEditing(true); // 此属性可以设置在提交模式（docSubmitForm）下，所有的数据区域可以编辑

        doc.openDataRegion("PO_m_a1").setValue(bean.getM_a1());
        doc.openDataRegion("PO_m_l1").setValue(bean.getM_l1());
        doc.openDataRegion("PO_m_a2").setValue(bean.getM_a2());
        doc.openDataRegion("PO_m_a3").setValue(bean.getM_a3());
        doc.openDataRegion("PO_m_a4").setValue(bean.getM_a4());
        
        doc.openDataRegion("PO_m_b1").setValue(bean.getM_b1());
        doc.openDataRegion("PO_m_b2").setValue(bean.getM_b2());
        
       
        
        doc.openDataRegion("PO_m_d1").setValue(bean.getM_d1());
        doc.openDataRegion("PO_m_f1").setValue(bean.getM_f1());
        doc.openDataRegion("PO_m_g1").setValue(bean.getM_g1());
        
        
        
        doc.openDataRegion("PO_n_a1").setValue(bean.getN_a1());
        doc.openDataRegion("PO_n_l1").setValue(bean.getN_l1());
        doc.openDataRegion("PO_n_a2").setValue(bean.getN_a2());
        doc.openDataRegion("PO_n_a3").setValue(bean.getN_a3());
        doc.openDataRegion("PO_n_a4").setValue(bean.getN_a4());
        
        doc.openDataRegion("PO_n_b1").setValue(bean.getN_b1());
        doc.openDataRegion("PO_n_b2").setValue(bean.getN_b2());
        
        
        doc.openDataRegion("PO_n_d1").setValue(bean.getN_d1());
        doc.openDataRegion("PO_n_f1").setValue(bean.getN_f1());
        doc.openDataRegion("PO_n_g1").setValue(bean.getN_g1());
        
        
      doc.openDataRegion("PO_m_total").setValue(String.valueOf(bean.getMoney_total()));
       
       String name=URLEncoder.encode(bean.getFilePath(), "utf-8");
      

        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        fmCtrl.setServerPage(basePath+"poserver.zz");
        fmCtrl.setWriter(doc);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
        fmCtrl.setSaveFilePage(basePath+"office/save_page?path="+name);
        fmCtrl.fillDocumentAs(request.getSession().getServletContext().getRealPath("/")+"/doc/template/扣款3.doc", DocumentOpenType.Word, bean.getFileName());
      
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnProgressComplete() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             window.external.close(); 
             window.external.CallParentFunc("xh.writeMoneySussess()");
        }
    </script>

</head>
<body>
<p>正在生成扣款文件。请耐心等待</p>
    <div>
        <%=fmCtrl.getHtmlCode("FileMakerCtrl1")%>
    </div>
</body>
</html>