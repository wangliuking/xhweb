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
int type=Integer.parseInt(request.getParameter("type"));

/* String str=request.getParameter("bean"); */
ScoreBean bean=new ScoreBean();
if(type==3){
	bean=(ScoreBean)request.getSession().getAttribute(request.getSession().getId()+"score3");
}else{
	bean=(ScoreBean)request.getSession().getAttribute(request.getSession().getId()+"score4");
}
System.out.println("time->"+bean.toString());
/* ScoreBean bean=GsonUtil.json2Object(str, ScoreBean.class); */
%>
<%
    
        WordDocument doc = new WordDocument();
        String month=bean.getTime();
        System.out.println("time->"+month);
        System.out.println("time->type"+type);
        //String str=month.split("-")[0]+"年"+month.split("-")[0]+"月";
        doc.setEnableAllDataRegionsEditing(true); // 此属性可以设置在提交模式（docSubmitForm）下，所有的数据区域可以编辑

        if(type==3){
        	doc.openDataRegion("PO_Name").setValue("成都市应急指挥调度无线通信网三期工程服务项目\r\n"+bean.getTime()+"项目服务扣分表");
        	
        }else{
        	doc.openDataRegion("PO_Name").setValue("成都市应急指挥调度无线通信网四期工程服务项目\r\n"+bean.getTime()+"项目服务扣分表");
        }
        doc.openDataRegion("PO_s_a1").setValue(bean.getS_a1());
        
        doc.openDataRegion("PO_s_b1").setValue(bean.getS_b1());
        doc.openDataRegion("PO_s_b2").setValue(bean.getS_b2());
        doc.openDataRegion("PO_s_b3").setValue(bean.getS_b3());
        doc.openDataRegion("PO_s_b4").setValue(bean.getS_b4());
        
        doc.openDataRegion("PO_s_c1").setValue(bean.getS_c1());
        doc.openDataRegion("PO_s_c2").setValue(bean.getS_c2());
        
        doc.openDataRegion("PO_s_d1").setValue(bean.getS_d1());
        doc.openDataRegion("PO_s_d2").setValue(bean.getS_d2());
        
        doc.openDataRegion("PO_s_e1").setValue(bean.getS_e1());
        
        doc.openDataRegion("PO_s_f1").setValue(bean.getS_f1());
        doc.openDataRegion("PO_s_f2").setValue(bean.getS_f2());
        
        doc.openDataRegion("PO_s_g1").setValue(bean.getS_g1());
        doc.openDataRegion("PO_s_h1").setValue(bean.getS_h1());
        
        
        doc.openDataRegion("PO_n_a1").setValue(bean.getN_a1());
        
        doc.openDataRegion("PO_n_b1").setValue(bean.getN_b1());
        doc.openDataRegion("PO_n_b2").setValue(bean.getN_b2());
        doc.openDataRegion("PO_n_b3").setValue(bean.getN_b3());
        doc.openDataRegion("PO_n_b4").setValue(bean.getN_b4());
        
        doc.openDataRegion("PO_n_c1").setValue(bean.getN_c1());
        doc.openDataRegion("PO_n_c2").setValue(bean.getN_c2());
        
        doc.openDataRegion("PO_n_d1").setValue(bean.getN_d1());
        doc.openDataRegion("PO_n_d2").setValue(bean.getN_d2());
        
        doc.openDataRegion("PO_n_e1").setValue(bean.getN_e1());
        
        doc.openDataRegion("PO_n_f1").setValue(bean.getN_f1());
        doc.openDataRegion("PO_n_f2").setValue(bean.getN_f2());
        
        doc.openDataRegion("PO_n_g1").setValue(bean.getN_g1());
        doc.openDataRegion("PO_n_h1").setValue(bean.getN_h1());
        
        
        
      doc.openDataRegion("PO_s_total").setValue(String.valueOf(bean.getScore_total()));
       
       String name=URLEncoder.encode(bean.getFilePath(), "utf-8");
      

        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        fmCtrl.setServerPage(basePath+"poserver.zz");
        fmCtrl.setWriter(doc);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
        fmCtrl.setSaveFilePage(basePath+"office/save_page?path="+name);
        fmCtrl.fillDocumentAs(request.getSession().getServletContext().getRealPath("/")+"/doc/template/扣分.doc", DocumentOpenType.Word, bean.getFileName());
      
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script type="text/javascript">
        function OnProgressComplete() {
           // window.parent.hideGif();// 隐藏Default页中的loading图片
             window.external.close(); 
             window.external.CallParentFunc("xh.writeScoreSussess()");
        }
    </script>

</head>
<body>
<p>正在生成扣分文件。请耐心等待</p>
    <div>
        <%=fmCtrl.getHtmlCode("FileMakerCtrl1")%>
    </div>
</body>
</html>