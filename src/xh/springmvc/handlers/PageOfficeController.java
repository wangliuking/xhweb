package xh.springmvc.handlers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import com.zhuozhengsoft.pageoffice.wordreader.*;


@Controller
@RequestMapping("/office")
public class PageOfficeController {

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
		
		poCtrl.setServerPage("../poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("打印","PrintFile()",6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setSaveFilePage("../order/save_page");//
		poCtrl.setSaveDataPage("../order/save_data");
		poCtrl.webOpen("../doc/meet.doc", OpenModeType.docSubmitForm, "");
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
		poCtrl.webOpen("../../doc/meet.doc", OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}

	
	
	

}
