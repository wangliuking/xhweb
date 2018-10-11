package xh.springmvc.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.ChartReportImpBsBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.AttachmentBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.AttachmentService;
import xh.mybatis.service.ReportDayService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/attachment")
public class AttachmentController {

	private boolean success;
	private String message;
	protected final Log log = LogFactory.getLog(AttachmentController.class);
	private WebLogBean webLogBean = new WebLogBean();
	private FlexJSON json = new FlexJSON();

	@SuppressWarnings("static-access")
	@RequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		AttachmentBean bean = GsonUtil.json2Object(data, AttachmentBean.class);
		bean.setTime(FunUtil.nowDateNotTime());
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("model", bean.getAttachment_model());
		paraMap.put("sn", bean.getAttachment_sn());
		int exists= AttachmentService.attachmentList_isexists(paraMap);
		if(exists>0){
			this.message="序列号已经存在";
			this.success=false;
		}else{
			int rs = AttachmentService.add(bean);
			
			if (rs > 0) {
				this.message = "添加设备成功";
				this.success = true;
				webLogBean.setOperator(FunUtil.getCookie(req,
						FunUtil.readXml("web", "cookie_prefix") + "username"));
				webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
				webLogBean.setStyle(1);
				webLogBean.setContent("添加设备");
				webLogBean.setCreateTime(FunUtil.nowDate());
				WebLogService.writeLog(webLogBean);

			} else {
				this.message = "添加设备失败";
				this.success = false;
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/update")
	public void update(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		AttachmentBean bean = GsonUtil.json2Object(data, AttachmentBean.class);
		int rs = AttachmentService.update(bean);
		if (rs > 0) {
			this.message = "修改设备成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改设备");
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "修改设备失败";
			this.success = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/del")
	public void del(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String idstr = req.getParameter("id");
		String[] ids = idstr.split(",");
		List<String> idlist = new ArrayList<String>();
		for (String str : ids) {
			idlist.add(str);
		}
		int rs = AttachmentService.del(idlist);
		if (rs > 0) {
			this.message = "删除备品备件成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除备品备件:id=" + idstr);
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "删除备品备件失败";
			this.success = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);

	}

	@SuppressWarnings("static-access")
	@RequestMapping("/attachmentList")
	public void attachmentList(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		Map<String, Object> pMap = new HashMap<String, Object>();
		int start = FunUtil.StringToInt(req.getParameter("start"));
		int limit = FunUtil.StringToInt(req.getParameter("limit"));
		pMap.put("start", start);
		pMap.put("limit", limit);

		List<AttachmentBean> list = AttachmentService.attachmentList(pMap);
		int count = AttachmentService.count(pMap);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", list);
		map.put("totals", count);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);

	}
	
	
	@RequestMapping("/add_config")
	public void add_config(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		AttachmentBean bean = GsonUtil.json2Object(data, AttachmentBean.class);
		bean.setTime(FunUtil.nowDateNotTime());
		
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("name", bean.getAttachment_name());
		paraMap.put("model", bean.getAttachment_model());
		
		int exists=AttachmentService.attachmentList_config_isexists(paraMap);
		
		
		if(exists>0){
			this.message="名称或者型号已经存在，请重新填写";
			this.success=false;
		}else{
			int rs = AttachmentService.add_config(bean);
			if (rs > 0) {
				this.message = "添加备品备件成功";
				this.success = true;
				webLogBean.setOperator(FunUtil.getCookie(req,
						FunUtil.readXml("web", "cookie_prefix") + "username"));
				webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
				webLogBean.setStyle(1);
				webLogBean.setContent("添加备品备件");
				webLogBean.setCreateTime(FunUtil.nowDate());
				WebLogService.writeLog(webLogBean);

			} else {
				this.message = "添加备品备件失败";
				this.success = false;
			}
		}
		
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/update_config")
	public void update_config(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		AttachmentBean bean = GsonUtil.json2Object(data, AttachmentBean.class);
		int rs = AttachmentService.update_config(bean);
		if (rs > 0) {
			this.message = "修改备品备件成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改备品备件");
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "修改备品备件失败";
			this.success = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);
	}

	@SuppressWarnings("static-access")
	@RequestMapping("/del_config")
	public void del_config(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String idstr = req.getParameter("id");
		String[] ids = idstr.split(",");
		List<String> idlist = new ArrayList<String>();
		for (String str : ids) {
			idlist.add(str);
		}
		int rs = AttachmentService.del_config(idlist);
		if (rs > 0) {
			this.message = "删除备品备件成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除备品备件:id=" + idstr);
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "删除备品备件失败";
			this.success = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);

	}

	@SuppressWarnings("static-access")
	@RequestMapping("/attachmentList_config")
	public void attachmentList_config(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		Map<String, Object> pMap = new HashMap<String, Object>();
		int start = FunUtil.StringToInt(req.getParameter("start"));
		int limit = FunUtil.StringToInt(req.getParameter("limit"));
		pMap.put("start", start);
		pMap.put("limit", limit);

		List<AttachmentBean> list = AttachmentService.attachmentList_config(pMap);
		int count = AttachmentService.count_config(pMap);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", list);
		map.put("totals", count);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		rep.getWriter().write(jsonstr);

	}

	
	
	

	@SuppressWarnings("static-access")
	@RequestMapping(value="/excelOne",method = RequestMethod.POST)
	public void excelOne(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String time = req.getParameter("time");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		String saveDir = req.getSession().getServletContext()
				.getRealPath("/upload/attachment");
		String pathname = saveDir + "/"+time+"备品备件表.xls";
		File Path = new File(saveDir);
		if (!Path.exists()) {
			Path.mkdirs();
		}
		File file = new File(pathname);
		WritableWorkbook book = Workbook.createWorkbook(file);
		WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"),
				15, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat fontFormat = new WritableCellFormat(font);
		fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
		fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
		fontFormat.setWrap(true); // 自动换行
		fontFormat.setBackground(Colour.WHITE);// 背景颜色
		fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
				Colour.DARK_GREEN);
		fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

		// 设置头部字体格式
		WritableFont font_header = new WritableFont(WritableFont.TIMES, 13,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		// 应用字体
		WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
		// 设置其他样式
		fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
		fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
		fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
		fontFormat_h.setBackground(Colour.LIGHT_GREEN);// 背景色
		fontFormat_h.setWrap(true);// 不自动换行

		// 设置主题内容字体格式
		WritableFont font_Content = new WritableFont(WritableFont.TIMES, 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.GRAY_80);
		// 应用字体
		WritableCellFormat fontFormat_Content = new WritableCellFormat(
				font_Content);
		// 设置其他样式
		fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
		fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
		fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
		fontFormat_Content.setBackground(Colour.WHITE);// 背景色
		fontFormat_Content.setWrap(true);// 自动换行

		WritableSheet sheet0 = book.createSheet("维护作业备品备件表", 0);

		excel_one(map, sheet0, fontFormat, fontFormat_h, fontFormat_Content);

		book.write();
		book.close();
		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap.put("success", true);
		rmap.put("pathName", pathname);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(rmap);
		rep.getWriter().write(jsonstr);

	}

	public void excel_one(Map<String, Object> map, WritableSheet sheet,
			WritableCellFormat fontFormat, WritableCellFormat fontFormat_h,
			WritableCellFormat fontFormat_Content) {
		String time = map.get("time").toString();
		try {
			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 7); 
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30); 
			sheet.setColumnView(3, 25); 
			sheet.setColumnView(4, 30); 
			sheet.setColumnView(5, 10);
			sheet.setColumnView(6, 15); 
			sheet.setColumnView(7, 15);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 30);
			sheet.setColumnView(10, 30);
			
			sheet.addCell(new Label(0, 0, "备品备件表", fontFormat_h));
			sheet.addCell(new Label(0, 1, time, fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.mergeCells(0, 1, 10, 1);
			sheet.addCell(new Label(0, 2, "序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "名称", fontFormat_h));
			sheet.addCell(new Label(2, 2, "设备描述", fontFormat_h));
			sheet.addCell(new Label(3, 2, "型号", fontFormat_h));
			sheet.addCell(new Label(4, 2, "序列号", fontFormat_h));
			sheet.addCell(new Label(5, 2, "单位", fontFormat_h));
			sheet.addCell(new Label(6, 2, "配置量", fontFormat_h));
			sheet.addCell(new Label(7, 2, "实际数量", fontFormat_h));
			sheet.addCell(new Label(8, 2, "完好率", fontFormat_h));
			sheet.addCell(new Label(9, 2, "存放位置", fontFormat_h));
			sheet.addCell(new Label(10, 2, "备注", fontFormat_h));

			List<AttachmentBean> list=AttachmentService.attachmentList_month_one(time);
			for (int i = 0; i < list.size(); i++) {
				AttachmentBean bean = list.get(i);
				sheet.addCell(new jxl.write.Number(0, i + 3, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i + 3, bean.getAttachment_name(), fontFormat_Content));
				sheet.addCell(new Label(2, i + 3, bean.getAttachment_desc(), fontFormat_Content));
				sheet.addCell(new Label(3, i + 3, bean.getAttachment_model(), fontFormat_Content));
				sheet.addCell(new Label(4, i + 3, bean.getAttachment_sn(), fontFormat_Content));
				sheet.addCell(new Label(5, i + 3, bean.getAttachment_unit(), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(6, i + 3, Integer.parseInt(bean.getAttachment_conf_number()), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(7, i + 3, Integer.parseInt(bean.getAttachment_reality_number()), fontFormat_Content));
				sheet.addCell(new Label(8, i + 3, bean.getAvai()+"%", fontFormat_Content));
				sheet.addCell(new Label(9, i + 3, bean.getAttachment_location(), fontFormat_Content));
				sheet.addCell(new Label(10, i + 3, bean.getAttachment_note(), fontFormat_Content));
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	
}
