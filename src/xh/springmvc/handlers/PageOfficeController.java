package xh.springmvc.handlers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

@Controller
@RequestMapping("/office")
public class PageOfficeController {

	@RequestMapping("/openword")
	public String openword(HttpServletRequest request,
			HttpServletResponse response,Map<String,Object> map) {
		PDFCtrl pdfCtrl1 = new PDFCtrl(request);
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save", 1); // 添加自定义按钮
		poCtrl.setSaveFilePage("/save");// 设置保存的action
		poCtrl.webOpen("doc/test.doc", OpenModeType.docAdmin, "张三");
		poCtrl.getHtmlCode("PageOfficeCtrl1");
		map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		// --- PageOffice的调用代码 结束 -----
		ModelAndView mv = new ModelAndView("word");
		return "ddd";
	}

}
