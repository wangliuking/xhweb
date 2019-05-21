<%@ page language="java" import="java.util.*,java.net.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="utf-8"%>
<%
 String path =URLDecoder.decode(request.getParameter("path"),"utf-8");
/* String path = new String(request.getParameter("path").getBytes("iso-8859-1"),"utf-8"); */
/* String path=request.getParameter("path"); */
System.out.println("ppp->"+path);
 FileSaver fs=new FileSaver(request,response);
fs.saveToFile(request.getSession().getServletContext().getRealPath(path));

fs.close(); 
%>
