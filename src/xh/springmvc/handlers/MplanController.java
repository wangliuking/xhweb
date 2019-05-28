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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.ChartReportImpBsBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.MplanBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.MplanService;
import xh.mybatis.service.ReportDayService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/mplan")
public class MplanController {

	private boolean success;
	private String message;
	protected final Log log = LogFactory.getLog(MplanController.class);
	private WebLogBean webLogBean = new WebLogBean();
	private FlexJSON json = new FlexJSON();
	
	

	@SuppressWarnings("static-access")
	@RequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		MplanBean bean = GsonUtil.json2Object(data, MplanBean.class);
		int rs = MplanService.add(bean);
		if (rs > 0) {
			this.message = "添加计划成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(1);
			webLogBean.setContent("添加维护计划");
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "添加计划失败";
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
	@RequestMapping("/update")
	public void update(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String data = req.getParameter("data");
		MplanBean bean = GsonUtil.json2Object(data, MplanBean.class);
		int rs = MplanService.update(bean);
		if (rs > 0) {
			this.message = "修改计划成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改维护计划");
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "修改计划失败";
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
		int rs = MplanService.del(idlist);
		if (rs > 0) {
			this.message = "删除计划成功";
			this.success = true;
			webLogBean.setOperator(FunUtil.getCookie(req,
					FunUtil.readXml("web", "cookie_prefix") + "username"));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(req));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除维护计划:id=" + idstr);
			webLogBean.setCreateTime(FunUtil.nowDate());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "删除计划失败";
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
	@RequestMapping("/mplanList")
	public void mplanList(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		Map<String, Object> pMap = new HashMap<String, Object>();
		int start = FunUtil.StringToInt(req.getParameter("start"));
		int limit = FunUtil.StringToInt(req.getParameter("limit"));
		pMap.put("start", start);
		pMap.put("limit", limit);

		List<MplanBean> list = MplanService.mplanList(pMap);
		int count = MplanService.count(pMap);
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
				.getRealPath("/upload/mplan");
		String pathname = saveDir + "/上月度维护作业完成情况(成都应急网"+time+"计划维护作业完成情况).xls";
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

		WritableSheet sheet0 = book.createSheet("维护作业完成情况", 0);

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
			sheet.setColumnView(0, 10); 
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 40); 
			sheet.setColumnView(3, 25); 
			sheet.setColumnView(4, 20); 
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 30); 
			sheet.setColumnView(7, 30);
			
			sheet.addCell(new Label(0, 1, " 成都应急网"+time+"计划维护作业完成情况", fontFormat_h));
			sheet.mergeCells(0, 1, 7, 1);
			sheet.addCell(new Label(0, 2, " 序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "维护作业类型", fontFormat_h));
			sheet.addCell(new Label(2, 2, "主要工作内容", fontFormat_h));
			sheet.addCell(new Label(3, 2, "计划完成时间", fontFormat_h));
			sheet.addCell(new Label(4, 2, "完成情况", fontFormat_h));
			sheet.addCell(new Label(5, 2, "实际完成时间", fontFormat_h));
			sheet.addCell(new Label(6, 2, "未完成（滞后）原因", fontFormat_h));
			sheet.addCell(new Label(7, 2, "备注", fontFormat_h));

			List<MplanBean> list=MplanService.mplanList_month_one(time);
			for (int i = 0; i < list.size(); i++) {
				MplanBean bean = list.get(i);
				sheet.addCell(new jxl.write.Number(0, i + 3, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i + 3, bean.getMplan_type(), fontFormat_Content));
				sheet.addCell(new Label(2, i + 3, bean.getMplan_content(), fontFormat_Content));
				sheet.addCell(new Label(3, i + 3, bean.getPlan_time(), fontFormat_Content));
				sheet.addCell(new Label(4, i + 3, bean.getResult(), fontFormat_Content));
				sheet.addCell(new Label(5, i + 3, bean.getComplete_time(), fontFormat_Content));
				sheet.addCell(new Label(6, i + 3, bean.getReason(), fontFormat_Content));
				sheet.addCell(new Label(7, i + 3, bean.getNote(), fontFormat_Content));
				sheet.setRowView(i + 3, 300);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@SuppressWarnings("static-access")
	@RequestMapping(value="/excelTwo",method = RequestMethod.POST)
	public void excelTwo(HttpServletRequest req, HttpServletResponse rep)
			throws Exception {
		String time = req.getParameter("time");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		String saveDir = req.getSession().getServletContext()
				.getRealPath("/upload/mplan");
		String pathname = saveDir + "/本月度维护作业计划(成都应急网"+time+"计划维护作业).xls";
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

		WritableSheet sheet0 = book.createSheet("维护作业计划", 0);

		excel_two(map, sheet0, fontFormat, fontFormat_h, fontFormat_Content);

		book.write();
		book.close();
		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap.put("success", true);
		rmap.put("pathName", pathname);
		rep.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(rmap);
		rep.getWriter().write(jsonstr);

	}

	public void excel_two(Map<String, Object> map, WritableSheet sheet,
			WritableCellFormat fontFormat, WritableCellFormat fontFormat_h,
			WritableCellFormat fontFormat_Content) {
		String time = map.get("time").toString();
		try {
			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10); 
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 40); 
			sheet.setColumnView(3, 25); 
			sheet.setColumnView(4, 30);
			
			sheet.addCell(new Label(0, 1, " 成都应急网"+time+"计划维护作业", fontFormat_h));
			sheet.mergeCells(0, 1, 4, 1);
			sheet.addCell(new Label(0, 2, " 序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "维护作业类型", fontFormat_h));
			sheet.addCell(new Label(2, 2, "主要工作内容", fontFormat_h));
			sheet.addCell(new Label(3, 2, "计划完成时间", fontFormat_h));
			sheet.addCell(new Label(4, 2, "备注", fontFormat_h));

			List<MplanBean> list=MplanService.mplanList_month_two(time);
			for (int i = 0; i < list.size(); i++) {
				MplanBean bean = list.get(i);
				sheet.addCell(new jxl.write.Number(0, i + 3, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i + 3, bean.getMplan_type(), fontFormat_Content));
				sheet.addCell(new Label(2, i + 3, bean.getMplan_content(), fontFormat_Content));
				sheet.addCell(new Label(3, i + 3, bean.getPlan_time(), fontFormat_Content));
				sheet.addCell(new Label(4, i + 3, bean.getNote(), fontFormat_Content));
				sheet.setRowView(i + 3, 300);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
