package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xh.func.plugin.FunUtil;
import xh.mybatis.service.WebUserServices;

import com.zhuozhengsoft.pageoffice.DocumentOpenType;
import com.zhuozhengsoft.pageoffice.FileMakerCtrl;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PDFOpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import com.zhuozhengsoft.pageoffice.wordreader.*;


@Controller
@RequestMapping("/office")
public class PageOfficeController {
	
	@RequestMapping("/save_page")
	public void savepage(HttpServletRequest request,
			HttpServletResponse response) {		
		String path=request.getParameter("path");
		try {
			path=URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("保存文件->解码后："+path);
		
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		
		

		FileSaver fs = new FileSaver(request, response);
		String p=path.substring(0, path.lastIndexOf("/")+1);
		String n=path.substring(path.lastIndexOf("/")+1);
		System.out.println("保存文件->解码后：p:"+p);
		System.out.println("保存文件->解码后：n:"+n);
		System.out.println("保存文件->解码后：n:"+request.getSession().getServletContext().getRealPath("/")+"/"+path);
		System.out.println("保存文件->解码后：pathh:"+request.getSession().getServletContext().getRealPath("/")+"/"+path);
		fs.saveToFile(request.getSession().getServletContext().getRealPath("/")+"/"+path);
		fs.close();
	}

	@RequestMapping("/meet")
	public ModelAndView meet(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		WordDocument wordDoc = new WordDocument();
		
		DataRegion dataRegion = wordDoc.openDataRegion("PO_name");
		dataRegion.setEditing(true);
		dataRegion.setValue("会议纪要");
		
		//打开数据区域，openDataRegion方法的参数代表Word文档中的书签名称
		DataRegion dataRegion1 = wordDoc.openDataRegion("PO_time");
		//设置DataRegion的可编辑性
		dataRegion1.setEditing(true);
		//为DataRegion赋值,此处的值可在页面中打开Word文档后自己进行修改
		dataRegion1.setValue("");	
		
		DataRegion dataRegion2 = wordDoc.openDataRegion("PO_address");
		dataRegion2.setEditing(true);
		dataRegion2.setValue("");
		
		DataRegion dataRegion3 = wordDoc.openDataRegion("PO_person");
		dataRegion3.setEditing(true);
		dataRegion3.setValue("");
		
		DataRegion dataRegion4 = wordDoc.openDataRegion("PO_content");
		dataRegion4.setEditing(true);
		dataRegion4.setValue("");
	
		poCtrl.setWriter(wordDoc);
		
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/meet.doc";
		String savePath=request.getSession().getServletContext().getRealPath("doc/meet/");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		savePath +=data[0];
		
		System.out.println("地址："+savePath);
		//path=data[0] + "/" + data[1] + "/"+ data[2];
		String filePath=savePath+"/"+FunUtil.MD5(String.valueOf(new Date().getTime()))+".doc";
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File source=new File(path);
		File dst=new File(filePath);
		try {
			FunUtil.copyFile(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("文件："+path);
		poCtrl.setServerPage("../poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setSaveFilePage("../meet/save_page?path="+filePath);
		poCtrl.setSaveDataPage("../meet/save_data");
	
		poCtrl.webOpen(filePath, OpenModeType.docSubmitForm, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/meet/edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		
		poCtrl.setServerPage("../../poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setSaveFilePage("../../order/save_page");
		poCtrl.webOpen("../../doc/template/meet.doc", OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/openWord")
	public ModelAndView openWord(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(false); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		//设置页面的显示标题
		poCtrl.setCaption("");
		poCtrl.webOpen(request.getContextPath() +"/"+path, OpenModeType.docReadOnly, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	
	@RequestMapping("/previewWord")
	public ModelAndView previewWord(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		if(path.indexOf("/")==0){
			path=path.substring(path.indexOf("/")+1);
		}
		
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		//设置页面的显示标题
		poCtrl.setCaption("");
		poCtrl.webOpen(request.getContextPath() +"/"+path, OpenModeType.docReadOnly, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/preview_word");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/previewExcel")
	public ModelAndView previewExcel(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		/*if(path.indexOf("/")==0){
			path=path.substring(path.indexOf("/")+1);
		}
		*/
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		//设置页面的显示标题
		poCtrl.setCaption("");
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath(path), OpenModeType.xlsReadOnly, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/preview_excel");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/previewPDF")
	public ModelAndView previewPDF(HttpServletRequest request,
			HttpServletResponse response) {
		String path=request.getParameter("path");
		if(path.indexOf("/")==0){
			path=path.substring(path.indexOf("/")+1);
		}

		PDFCtrl pdfCtrl1 = new PDFCtrl(request);
		pdfCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		// Create custom toolbar
		pdfCtrl1.addCustomToolButton("打印", "PrintFile()", 6);
		pdfCtrl1.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
		pdfCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
		pdfCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
		pdfCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
		pdfCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
		pdfCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("向左旋转90度", "SetRotateLeft()", 12);
		pdfCtrl1.addCustomToolButton("向右旋转90度", "SetRotateRight()", 13);
		pdfCtrl1.setTitlebar(false); //隐藏标题栏
		pdfCtrl1.setTagId("PDFCtrl1");
		pdfCtrl1.webOpen(request.getContextPath() +"/"+path, PDFOpenModeType.pdfViewOnly);
		ModelAndView mv = new ModelAndView("Views/jsp/preview_pdf");
		mv.addObject("pageoffice", pdfCtrl1.getHtmlCode("PDFCtrl1"));
		return mv;
	}
	
	@RequestMapping("/seal")
	public ModelAndView seal(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		String doc=request.getParameter("doc");
		String type=request.getParameter("type");
		String fileId=request.getParameter("fileId")==null?"0":request.getParameter("fileId");
		System.out.println("文件ID:"+fileId);
		
		System.out.println("签章文件->"+path);	
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		if(type.equals("1")){
			poCtrl.addCustomToolButton("加盖印章", "Seal()", 2);
			//poCtrl.addCustomToolButton("删除印章", "DeleteSeal()", 21);
		}else{
			poCtrl.addCustomToolButton("签字", "Seal()", 2);
			//poCtrl.addCustomToolButton("删除签字", "DeleteSeal()", 21);
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		//设置页面的显示标题
		poCtrl.setCaption("");
		if(doc.contains("doc")){
			poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit, "");
		}else{
			poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.xlsNormalEdit, "");
		}
		
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/seal");
		mv.addObject("type", type);
		mv.addObject("fileId", Integer.parseInt(fileId));
		mv.addObject("sealName",WebUserServices.sealName(FunUtil.loginUser(request),type));
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/signAndSeal")
	public ModelAndView signAndSeal(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		String doc=request.getParameter("doc");
		
		System.out.println("签章文件->"+path);	
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("签字", "sign()", 2);
		poCtrl.addCustomToolButton("加盖印章", "Seal()", 2);
	
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		//设置页面的显示标题
		poCtrl.setCaption("");
		if(doc.contains("doc")){
			poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit, "");
		}else{
			poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.xlsNormalEdit, "");
		}
		
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/sign_seal");
		mv.addObject("sealName1",WebUserServices.sealName(FunUtil.loginUser(request),"1"));
		mv.addObject("sealName2",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	
	@RequestMapping("/sealWord")
	public ModelAndView sealWord(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		System.out.println("签章文件->"+path);
		/*if(path.indexOf("/")==0){
			path=path.substring(path.indexOf("/")+1);
		}*/		
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("加盖印章", "Seal()", 2);
		//poCtrl.addCustomToolButton("删除印章", "DeleteSeal()", 21);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		//设置页面的显示标题
		poCtrl.setCaption("");
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/seal_word");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	@RequestMapping("/sealExcel")
	public ModelAndView sealExcel(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		/*if(path.indexOf("/")==0){
			path=path.substring(path.indexOf("/")+1);
		}*/
		
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("加盖印章", "Seal()", 2);
		//poCtrl.addCustomToolButton("删除印章", "DeleteSeal()", 21);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setMenubar(true); //隐藏菜单栏
		poCtrl.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		//设置页面的显示标题
		poCtrl.setCaption("");
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.xlsNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/seal_excel");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	
	

	
	
	

}
