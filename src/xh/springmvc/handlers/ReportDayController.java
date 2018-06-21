package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import xh.mybatis.bean.ChartReportDispatch;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.ReportDayService;

@Controller
@RequestMapping(value="/report")
public class ReportDayController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(CallController.class);
	private FlexJSON json=new FlexJSON();
	
	@RequestMapping(value = "/chart_server", method = RequestMethod.GET)
	public void chart_server(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap result = new HashMap();
		List<Map<String,Object>> list=ReportDayService.chart_server();
		List<Map<String,Object>> list_one=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list_two=new ArrayList<Map<String,Object>>();
		for(int i=0,j=list.size();i<j;i++){
			Map map=list.get(i);
			if(map.get("ID").equals(1)){
				list_one.add(map);
			}else{
				list_two.add(map);
			}
		}

		result.put("one", list_one);
		result.put("one_size", list_one.size());
		result.put("two", list_two);
		result.put("two_size", list_two.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_msc_call", method = RequestMethod.GET)
	public void chart_msc_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		HashMap result = new HashMap();
		result.put("items", ReportDayService.chart_msc_call(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_eastcom_alarm", method = RequestMethod.GET)
	public void chart_eastcom_alarm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		
		HashMap result = new HashMap();
		result.put("now", ReportDayService.chart_alarm_now());
		result.put("his", ReportDayService.chart_alarm_his(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	
	@RequestMapping(value = "/chart_dispatch", method = RequestMethod.GET)
	public void chart_dispatc(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		HashMap result = new HashMap();
		result.put("totals", ReportDayService.chart_dispatch().size());
		result.put("items", ReportDayService.chart_dispatch());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/excel_report", method = RequestMethod.GET)
	public void excel_report(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/dayreport");
			String pathname = saveDir + "/成都应急网日维护报告"+time+"日报.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);			
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
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
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行
		
			
			//总计内容字体格式
			WritableFont total_font = new WritableFont(WritableFont.COURIER,
					10, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.RED);
			// 应用字体
			WritableCellFormat total_fontFormat = new WritableCellFormat(total_font);
			total_fontFormat.setAlignment(Alignment.CENTRE);// 水平对齐
			total_fontFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			total_fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			total_fontFormat.setBackground(Colour.WHITE);// 背景色
			total_fontFormat.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
					nf); // 设置表单格式

			

			WritableSheet sheet = book.createSheet("日常统计维护", 0);
			WritableSheet sheet1 = book.createSheet("日常调度台统计", 1);
			WritableSheet sheet2 = book.createSheet("日常维护-告警统计", 2);
			WritableSheet sheet3 = book.createSheet("日常统计测量", 3);
			excel_server(map,sheet,fontFormat,fontFormat_h,fontFormat_Content);
			excel_dispatch(map,sheet1,fontFormat,fontFormat_h,fontFormat_Content);
			excel_alarm(map,sheet2,fontFormat,fontFormat_h,fontFormat_Content);
			excel_msc(map,sheet3,fontFormat,fontFormat_h,fontFormat_Content);
			
			book.write();
			book.close();
			/*DownExcelFile(response, pathname);*/
			 this.success=true;
			 HashMap<String, Object> result = new HashMap<String, Object>();
			 result.put("success", success);
			 result.put("pathName", pathname);
			 response.setContentType("application/json;charset=utf-8"); 
			 String jsonstr = json.Encode(result); 
			 response.getWriter().write(jsonstr);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_server(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, "系统日常维护表", fontFormat));
		sheet.addCell(new Label(0, 1, "主服务器当前运行状态", fontFormat));
		sheet.addCell(new Label(0, 2, "统计时间"+time, fontFormat));
		sheet.mergeCells(0,0,6,0);
		sheet.mergeCells(0,1,6,0);
		sheet.mergeCells(0,2,6,0);
		sheet.setRowView(0, 600);
		sheet.setRowView(1, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 40);
		
		sheet.addCell(new Label(0, 3, "设备", fontFormat_h));
		sheet.addCell(new Label(1, 3, "IP", fontFormat_h));
		sheet.addCell(new Label(2, 3, "cpu占用", fontFormat_h));
		sheet.addCell(new Label(3, 3, "内存使用率", fontFormat_h));
		sheet.addCell(new Label(4, 3, "硬盘使用", fontFormat_h));
		sheet.addCell(new Label(5, 3, "可用量", fontFormat_h));
		sheet.addCell(new Label(6, 3, "运行时间", fontFormat_h));
		
		
		
		List<Map<String,Object>> list=ReportDayService.chart_server();
		List<Map<String,Object>> list_one=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list_two=new ArrayList<Map<String,Object>>();
		for(int i=0,j=list.size();i<j;i++){
			Map<String,Object> mapa=list.get(i);
			if(mapa.get("ID").equals(1)){
				list_one.add(mapa);
			}else{
				list_two.add(mapa);
			}
		}
		for (int i = 0; i < list_one.size(); i++) {
			Map bean =list_one.get(i);
			sheet.setRowView(i + 4, 400);
			sheet.addCell(new Label(0, i + 4, String.valueOf(bean.get("name")), fontFormat_Content));
			sheet.addCell(new Label(1, i + 4, String.valueOf(bean.get("ip")), fontFormat_Content));
			sheet.addCell(new Label(2, i + 4, String.valueOf(bean.get("cpuLoad"))+"%", fontFormat_Content));
			sheet.addCell(new Label(3, i + 4, String.valueOf(bean.get("memPercent"))+"%", fontFormat_Content));
			sheet.addCell(new Label(4, i + 4, String.valueOf(bean.get("diskUsed")), fontFormat_Content));
			sheet.addCell(new Label(5, i + 4, String.valueOf(bean.get("diskSize")), fontFormat_Content));
			sheet.addCell(new Label(6, i + 4, "", fontFormat_Content));
		}
		
		//容灾
		int start=list_one.size()+5;
		sheet.addCell(new Label(0, start+1, "容灾服务器当前运行状态", fontFormat));
		sheet.mergeCells(0,start+1,6,0);
		sheet.setRowView(start+1, 600);
		
		sheet.addCell(new Label(0, start+2, "设备", fontFormat_h));
		sheet.addCell(new Label(1, start+2, "IP", fontFormat_h));
		sheet.addCell(new Label(2, start+2, "cpu占用", fontFormat_h));
		sheet.addCell(new Label(3, start+2, "内存使用率", fontFormat_h));
		sheet.addCell(new Label(4, start+2, "硬盘使用", fontFormat_h));
		sheet.addCell(new Label(5, start+2, "可用量", fontFormat_h));
		sheet.addCell(new Label(6, start+2, "运行时间", fontFormat_h));
		for (int i = 0; i < list_two.size(); i++) {
			Map bean =list_two.get(i);
			sheet.setRowView(start+i + 3, 400);
			sheet.addCell(new Label(0, start+i + 3, String.valueOf(bean.get("name")), fontFormat_Content));
			sheet.addCell(new Label(1, start+i + 3, String.valueOf(bean.get("ip")), fontFormat_Content));
			sheet.addCell(new Label(2, start+i + 3, String.valueOf(bean.get("cpuLoad"))+"%", fontFormat_Content));
			sheet.addCell(new Label(3, start+i + 3, String.valueOf(bean.get("memPercent"))+"%", fontFormat_Content));
			sheet.addCell(new Label(4, start+i + 3, String.valueOf(bean.get("diskUsed")), fontFormat_Content));
			sheet.addCell(new Label(5, start+i + 3, String.valueOf(bean.get("diskSize")), fontFormat_Content));
			sheet.addCell(new Label(6, start+i + 3, "", fontFormat_Content));
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_dispatch(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, "调度台运行状态检查", fontFormat));
		sheet.mergeCells(0,0,5,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		
		sheet.addCell(new Label(0, 1, "调度台ID", fontFormat_h));
		sheet.addCell(new Label(1, 1, "调度台名称", fontFormat_h));
		sheet.addCell(new Label(2, 1, "DBOX IP", fontFormat_h));
		sheet.addCell(new Label(3, 1, "主机IP", fontFormat_h));
		sheet.addCell(new Label(4, 1, "状态", fontFormat_h));
		sheet.addCell(new Label(5, 1, "DBOX运行时长", fontFormat_h));
		
		
		
		List<ChartReportDispatch> list=ReportDayService.chart_dispatch();
		
		for (int i = 0; i < list.size(); i++) {
			ChartReportDispatch bean =list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new Label(0, i + 2, String.valueOf(bean.getDstId()), fontFormat_Content));
			sheet.addCell(new Label(1, i + 2, String.valueOf(bean.getDstName()), fontFormat_Content));
			sheet.addCell(new Label(2, i + 2, String.valueOf(bean.getDxbox_ip()), fontFormat_Content));
			sheet.addCell(new Label(3, i + 2, String.valueOf(bean.getIp()), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, bean.getFlag()==0?"NO":"OK", fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, String.valueOf(bean.getDxbox_runtime()), fontFormat_Content));
		}
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_msc(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, "交换中心话务量统计", fontFormat));
		sheet.addCell(new Label(0, 1, "统计时段"+time, fontFormat));
		sheet.mergeCells(0,0,5,0);
		sheet.mergeCells(0,1,5,0);
		sheet.setRowView(0, 600);
		sheet.setRowView(1, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		
		EastMscDayBean bean=ReportDayService.chart_msc_call(map);
		sheet.addCell(new Label(0, 2, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(1, 2, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(2, 2, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(3, 2, "呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, 2, "呼损率", fontFormat_h));
		sheet.addCell(new Label(5, 2, "未成功呼叫总数", fontFormat_h));
		
		sheet.setRowView(3, 400);
		sheet.addCell(new Label(0, 3, String.valueOf(bean.getTotalActiveCall()), fontFormat_Content));
		sheet.addCell(new Label(1, 3, funUtil.second_time((int) bean.getTotalActiveCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(2, 3, funUtil.second_time((int) bean.getAverageCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(3, 3, String.valueOf(bean.getTotalCalls()), fontFormat_Content));
		sheet.addCell(new Label(4, 3, String.valueOf(bean.getFailedPercentage())+"%", fontFormat_Content));
		sheet.addCell(new Label(5, 3, String.valueOf(bean.getNoEffectCalls()), fontFormat_Content));
		
		
		
		sheet.addCell(new Label(0, 4, "组呼个数", fontFormat_h));
		sheet.addCell(new Label(1, 4, "组呼时长", fontFormat_h));
		sheet.addCell(new Label(2, 4, "个呼次数", fontFormat_h));
		sheet.addCell(new Label(3, 4, "个呼时长", fontFormat_h));
		
		sheet.setRowView(5, 400);
		sheet.addCell(new Label(0, 5, String.valueOf(bean.getGroupCalls()), fontFormat_Content));
		sheet.addCell(new Label(1, 5, funUtil.second_time((int) bean.getGroupCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(2, 5, String.valueOf(bean.getPrivateCalls()), fontFormat_Content));
		sheet.addCell(new Label(3, 5, funUtil.second_time((int) bean.getPrivateCallDuration()), fontFormat_Content));
		
		sheet.addCell(new Label(0, 6, "电话呼叫次数", fontFormat_h));
		sheet.addCell(new Label(1, 6, "电话呼叫时长", fontFormat_h));
		sheet.addCell(new Label(2, 6, "全双工单呼次数", fontFormat_h));
		sheet.addCell(new Label(3, 6, "半双工单呼次数", fontFormat_h));
		
		sheet.setRowView(7, 400);
		sheet.addCell(new Label(0, 7, String.valueOf(bean.getPhoneCalls()), fontFormat_Content));
		sheet.addCell(new Label(1, 7, funUtil.second_time((int) bean.getPhoneCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(2, 7, String.valueOf(bean.getPrivateDuplexCalls()), fontFormat_Content));
		sheet.addCell(new Label(3, 7, String.valueOf(bean.getPrivateSimplexCalls()), fontFormat_Content));
		
		
	
	
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_alarm(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, "告警信息统计表", fontFormat));
		sheet.mergeCells(0,0,4,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		
		sheet.addCell(new Label(1, 1, "紧急告警", fontFormat_h));
		sheet.addCell(new Label(2, 1, "主要告警", fontFormat_h));
		sheet.addCell(new Label(3, 1, "次要告警", fontFormat_h));
		sheet.addCell(new Label(4, 1, "一般通知", fontFormat_h));
		
		
		
		Map<String, Object> list_one=ReportDayService.chart_alarm_now();
		Map<String, Object> list_two=ReportDayService.chart_alarm_his(map);

		sheet.addCell(new Label(0, 2, "当前告警数量统计", fontFormat_Content));
		sheet.addCell(new Label(1, 2, String.valueOf(list_one.get("a_1")), fontFormat_Content));
		sheet.addCell(new Label(2, 2, String.valueOf(list_one.get("a_2")), fontFormat_Content));
		sheet.addCell(new Label(3, 2, String.valueOf(list_one.get("a_3")), fontFormat_Content));
		sheet.addCell(new Label(4, 2, String.valueOf(list_one.get("a_4")), fontFormat_Content));
		sheet.addCell(new Label(0, 3, "历时告警数量统计", fontFormat_Content));
		sheet.addCell(new Label(1, 3, String.valueOf(list_two.get("a_1")), fontFormat_Content));
		sheet.addCell(new Label(2, 3, String.valueOf(list_two.get("a_2")), fontFormat_Content));
		sheet.addCell(new Label(3, 3, String.valueOf(list_two.get("a_3")), fontFormat_Content));
		sheet.addCell(new Label(4, 3, String.valueOf(list_two.get("a_4")), fontFormat_Content));
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
}

