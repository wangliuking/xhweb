package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.ChartReportDispatch;
import xh.mybatis.bean.ChartReportImpBsBean;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.ReportDayService;

@Controller
@RequestMapping(value="/report")
public class ReportDayController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ReportDayController.class);
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
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					12, WritableFont.NO_BOLD, false,
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
					13, WritableFont.BOLD, false,
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
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

			WritableSheet sheet0 = book.createSheet("首页", 0);
			WritableSheet sheet = book.createSheet("日常统计维护", 1);
			WritableSheet sheet1 = book.createSheet("日常调度台统计", 2);
			WritableSheet sheet2 = book.createSheet("日常维护-告警统计", 3);
			WritableSheet sheet3 = book.createSheet("日常统计测量", 4);
			WritableSheet sheet4 = book.createSheet("基站检查", 5);
			WritableSheet sheet5 = book.createSheet("故障记录表"+time.substring(0, time.lastIndexOf("-")), 5);
			
			excel_frist(map,sheet0,fontFormat,fontFormat_h,fontFormat_Content);
			excel_server(map,sheet,fontFormat,fontFormat_h,fontFormat_Content);
			excel_dispatch(map,sheet1,fontFormat,fontFormat_h,fontFormat_Content);
			excel_alarm(map,sheet2,fontFormat,fontFormat_h,fontFormat_Content);
			excel_msc(map,sheet3,fontFormat,fontFormat_h,fontFormat_Content);
			excel_bsStatus(map,sheet4,fontFormat,fontFormat_h,fontFormat_Content);
			excel_bs_fault(map,sheet5,fontFormat,fontFormat_h,fontFormat_Content);
			
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
	public  void  excel_frist(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		String str1=time+" 0时    至 "+time+" 24时";
		WritableFont font_header = new WritableFont(WritableFont.TIMES, 28,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		// 应用字体
		WritableCellFormat font = new WritableCellFormat(font_header);
		// 设置其他样式
		try {
			font.setAlignment(Alignment.CENTRE);
			font.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			font.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			
			font.setWrap(true);// 不自动换行
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 水平对齐
		
		try {
			
			/*a 单元格的列号
			b 单元格的行号
			c 从单元格[a,b]起，向下合并的列数
			d 从单元格[a,b]起，向下合并的行数*/
		sheet.addCell(new Label(1, 2, "成都市应急指挥调度 无线通信网 系统日维护报告", font));
		
		sheet.setRowView(2, 600);
		sheet.setRowView(3, 600);
		sheet.setRowView(4, 600);
		sheet.mergeCells(1,2,2,4);
		
		sheet.addCell(new Label(1, 5, "项目名称", fontFormat_Content));
		sheet.addCell(new Label(2, 5, "成都市应急指挥调度无线通信网工程", fontFormat_Content));
		
		sheet.addCell(new Label(1, 6, "系统版本", fontFormat_Content));
		sheet.addCell(new Label(2, 6, "6.2", fontFormat_Content));
		
		sheet.addCell(new Label(1, 7, "维护周期", fontFormat_Content));
		sheet.addCell(new Label(2, 7, str1, fontFormat_Content));
		
		sheet.addCell(new Label(1, 8, "报告人", fontFormat_Content));
		sheet.addCell(new Label(2, 8, "", fontFormat_Content));
		
		sheet.addCell(new Label(1, 9, "报告时间", fontFormat_Content));
		sheet.addCell(new Label(2, 9, FunUtil.nowDateNoTime(), fontFormat_Content));
		
		sheet.addCell(new Label(1, 10, "成都亚光电子股份有限公司", fontFormat_Content));
		sheet.mergeCells(1,10,2,10);
		sheet.addCell(new Label(1, 11, "成都市应急指挥调度无线通信网工程项目部", fontFormat_Content));
		sheet.mergeCells(1,11,2,11);
		sheet.addCell(new Label(1, 12, "中国•成都", fontFormat_Content));
		sheet.mergeCells(1,12,2,12);

		
		
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 50);
		sheet.setRowView(5, 600);
		sheet.setRowView(6, 600);
		sheet.setRowView(7, 600);
		sheet.setRowView(8, 600);
		sheet.setRowView(9, 600);
		sheet.setRowView(10, 600);
		sheet.setRowView(11, 600);
		sheet.setRowView(12, 600);
		

		
	
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_server(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		WritableFont font_header = new WritableFont(WritableFont.TIMES, 28,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		// 应用字体
		WritableCellFormat font = new WritableCellFormat(font_header);
		
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#0.00"); // 设置数字格式
		jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式
		try {
			wcfN.setAlignment(Alignment.CENTRE);
			wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfN.setBackground(Colour.WHITE);// 背景色
			wcfN.setWrap(true);// 自动换行
		} catch (WriteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}// 水平对齐
		
		
        jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd"); 
		
		DisplayFormat DisplayFormat = NumberFormats.PERCENT_FLOAT;
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);

	     
        WritableCellFormat wcfp = new WritableCellFormat(wf,DisplayFormat);
		// 设置其他样式
		try {
			font.setAlignment(Alignment.CENTRE);
			font.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			font.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			
			font.setWrap(true);// 不自动换行
		

			
			
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 水平对齐
		
		try {
		sheet.addCell(new Label(0, 0, "系统日常维护表", fontFormat));
		sheet.addCell(new Label(0, 1, "主服务器当前运行状态", fontFormat));
		sheet.addCell(new Label(5, 2, "统计时间"+time, fontFormat));
		sheet.mergeCells(0,0,7,0);
		sheet.mergeCells(0,1,7,1);
		sheet.mergeCells(5,2,7,2);
		
		sheet.setRowView(0, 600);
		sheet.setRowView(1, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		
		sheet.addCell(new Label(0, 3, "设备", fontFormat_h));
		sheet.addCell(new Label(1, 3, "IP", fontFormat_h));
		sheet.addCell(new Label(2, 3, "cpu占用", fontFormat_h));
		sheet.addCell(new Label(3, 3, "内存使用率", fontFormat_h));
		sheet.addCell(new Label(4, 3, "硬盘使用", fontFormat_h));
		sheet.addCell(new Label(5, 3, "可用量", fontFormat_h));
		sheet.addCell(new Label(6, 3, "运行时间", fontFormat_h));
		sheet.mergeCells(6,3,7,3);
		
		
		
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
			sheet.addCell(new Label(2, i + 4,bean.get("cpuLoad")+"%",fontFormat_Content));
			sheet.addCell(new Label(3, i + 4,bean.get("memPercent")+"%",fontFormat_Content));
			sheet.addCell(new jxl.write.Number(4, i + 4, Double.parseDouble(bean.get("diskUsed").toString()),wcfN));
			sheet.addCell(new jxl.write.Number(5, i + 4, Double.parseDouble(bean.get("diskSize").toString()), wcfN));
			sheet.addCell(new Label(6, i + 4, "", fontFormat_Content));
			sheet.mergeCells(6,i + 4,7,i + 4);
		}
		
		//容灾
		int start=list_one.size()+5;
		sheet.addCell(new Label(0, start+1, "容灾服务器当前运行状态", fontFormat));
		sheet.mergeCells(0,start+1,7,start+1);
		sheet.setRowView(start+1, 600);
		
		sheet.addCell(new Label(0, start+2, "设备", fontFormat_h));
		sheet.addCell(new Label(1, start+2, "IP", fontFormat_h));
		sheet.addCell(new Label(2, start+2, "cpu占用", fontFormat_h));
		sheet.addCell(new Label(3, start+2, "内存使用率", fontFormat_h));
		sheet.addCell(new Label(4, start+2, "硬盘使用", fontFormat_h));
		sheet.addCell(new Label(5, start+2, "可用量", fontFormat_h));
		sheet.addCell(new Label(6, start+2, "运行时间", fontFormat_h));
		sheet.mergeCells(6,start+2,7,start+2);
		for (int i = 0; i < list_two.size(); i++) {
			Map bean =list_two.get(i);
			sheet.setRowView(start+i + 3, 400);
			sheet.addCell(new Label(0, start+i + 3, String.valueOf(bean.get("name")), fontFormat_Content));
			sheet.addCell(new Label(1, start+i + 3, String.valueOf(bean.get("ip")), fontFormat_Content));
			sheet.addCell(new Label(2, start+i + 3,bean.get("cpuLoad")+"%",fontFormat_Content));
			sheet.addCell(new Label(3, start+i + 3,bean.get("memPercent")+"%",fontFormat_Content));
			
			sheet.addCell(new jxl.write.Number(4, start+i + 3, Double.parseDouble(bean.get("diskUsed").toString()),wcfN));
			sheet.addCell(new jxl.write.Number(5, start+i + 3, Double.parseDouble(bean.get("diskSize").toString()), wcfN));
			
			
			sheet.addCell(new Label(6, start+i + 3, "", fontFormat_Content));
			sheet.mergeCells(6,start+i + 3,7,start+i + 3);
		}
		
		//GPS统计
		
		start=list_one.size()+5+list_two.size()+3;
		List<Map<String,Object>> gps=ReportDayService.now_week_gpsnumber(time);
		sheet.addCell(new Label(0, start+1, "", fontFormat_h));
		sheet.addCell(new Label(1, start+1, "周一", fontFormat_h));
		sheet.addCell(new Label(2, start+1, "周二", fontFormat_h));
		sheet.addCell(new Label(3, start+1, "周三", fontFormat_h));
		sheet.addCell(new Label(4, start+1, "周四", fontFormat_h));
		sheet.addCell(new Label(5, start+1, "周五", fontFormat_h));
		sheet.addCell(new Label(6, start+1, "周六", fontFormat_h));
		sheet.addCell(new Label(7, start+1, "周日", fontFormat_h));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        List<Date> lDate=FunUtil.findNowWeekDays();
        
        sheet.addCell(new Label(0, start+2, "区域/时间", fontFormat_h));
        for(int i=0;i<lDate.size();i++){
        	Date dd=lDate.get(i);
        	sheet.addCell(new Label((i+1), start+2, sdf.format(dd), fontFormat_Content));
        }
        sheet.addCell(new Label(0, start+3, "总数", fontFormat_Content));
        sheet.addCell(new Label(0, start+4, "交管8009850", fontFormat_Content));
        sheet.addCell(new Label(0, start+5, "公安8000695", fontFormat_Content));
        sheet.addCell(new Label(0, start+6, "双流7088899", fontFormat_Content));
        for(int i=0;i<gps.size();i++){   	
        	/*int total=Integer.parseInt(gps.get(i).get("v1").toString())+
        			Integer.parseInt(gps.get(i).get("v2").toString())+
        			Integer.parseInt(gps.get(i).get("v3").toString());*/
        	int total=Integer.parseInt(gps.get(i).get("total").toString());
        	sheet.addCell(new jxl.write.Number(i+1, start+3, total, fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+4, Integer.parseInt(gps.get(i).get("v1").toString()), fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+5, Integer.parseInt(gps.get(i).get("v2").toString()), fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+6, Integer.parseInt(gps.get(i).get("v3").toString()), fontFormat_Content));
        }
        if(gps.size()<7){
        	int x=gps.size();
        	for(int i=x;i<7;i++){
        		sheet.addCell(new Label(i+1, start+3, "", fontFormat_Content));
            	sheet.addCell(new Label(i+1, start+4, "", fontFormat_Content));
            	sheet.addCell(new Label(i+1, start+5, "", fontFormat_Content));
            	sheet.addCell(new Label(i+1, start+6, "", fontFormat_Content));
        	}
        }
        
        Map<String,Object>  mapGps=ReportDayService.now_gpsunit_status();
        
        sheet.addCell(new Label(0, start+7, "交管局GPS数据库", fontFormat_Content));
        sheet.addCell(new Label(1, start+7, mapGps.get("jg").toString().equals("1")?"OK":"NO", fontFormat_Content));
        sheet.addCell(new Label(2, start+7, "", fontFormat_Content));
        sheet.mergeCells(1,start+7,7,start+7);
        
        sheet.addCell(new Label(0, start+8, "公安局GPS数据库", fontFormat_Content));
        sheet.addCell(new Label(1, start+8, mapGps.get("cd").toString().equals("1")?"OK":"NO", fontFormat_Content));
        sheet.addCell(new Label(2, start+8, "", fontFormat_Content));
        sheet.mergeCells(1,start+8,7,start+8);
        
        
        sheet.addCell(new Label(0, start+9, "桥接基站、网管当前运行状态", fontFormat_Content));
        sheet.mergeCells(0,start+9,7,start+9);
        
        sheet.addCell(new Label(0, start+10, "设备", fontFormat_Content));
        sheet.addCell(new Label(1, start+10, "IP", fontFormat_Content));
        sheet.mergeCells(1,start+10,2,start+10);
        sheet.addCell(new Label(3, start+10, "运行状态", fontFormat_Content));
        sheet.addCell(new Label(4, start+10, "", fontFormat_Content));
        sheet.addCell(new Label(5, start+10, "备注", fontFormat_Content));
        sheet.mergeCells(5,start+10,7,start+10);
        
        
        sheet.addCell(new Label(0, start+11, "桥接中心工作状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+12, "东信桥接1站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+13, "东信桥接2站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+14, "东信桥接3站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+15, "MOTO桥接1站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+16, "MOTO桥接2站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+17, "MOTO桥接3站状态", fontFormat_Content));
        sheet.addCell(new Label(0, start+18, "网管客户端登录", fontFormat_Content));
        sheet.addCell(new Label(0, start+19, "信道资源状态监控", fontFormat_Content));
        sheet.addCell(new Label(0, start+20, "故障告警监控功能", fontFormat_Content));
        
        List<Map<String,Object>> dev=ReportDayService.other_device_status();
        for(int i=0;i<dev.size();i++){
        	Map<String,Object> devMap=dev.get(i);
        	sheet.addCell(new Label(1, start+11+i, devMap.get("ip").toString(), fontFormat_Content));
        	sheet.addCell(new Label(3, start+11+i, devMap.get("status").toString().equals("1")?"OK":"NO", fontFormat_Content));
        	if(i==dev.size()-1){
        		 sheet.mergeCells(1,start+11+i,2,start+13+i);
        	}else{
        		sheet.mergeCells(1,start+11+i,2,start+11+i);
        	}
        }
        
        
		
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_dispatch(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		jxl.write.DateFormat dfn = new jxl.write.DateFormat("yyyy-MM-dd"); 
		jxl.write.WritableCellFormat df = new jxl.write.WritableCellFormat(dfn); // 设置表单格式
		try {
			df.setAlignment(Alignment.CENTRE);
			df.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			df.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			df.setBackground(Colour.WHITE);// 背景色
			df.setWrap(true);// 自动换行
		} catch (WriteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}// 水平对齐
		
		
        
		try {
		sheet.addCell(new Label(0, 0, "调度台运行状态检查", fontFormat));
		sheet.addCell(new Label(4, 1, "统计时间  "+time, fontFormat));
		sheet.mergeCells(0,0,5,0);
		sheet.mergeCells(4,1,5,1);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 50);
		
		sheet.addCell(new Label(0, 2, "调度台ID", fontFormat_h));
		sheet.addCell(new Label(1, 2, "调度台名称", fontFormat_h));
		sheet.addCell(new Label(2, 2, "DBOX IP", fontFormat_h));
		sheet.addCell(new Label(3, 2, "主机IP", fontFormat_h));
		sheet.addCell(new Label(4, 2, "状态", fontFormat_h));
		sheet.addCell(new Label(5, 2, "DBOX运行时长", fontFormat_h));
		
		
		
		List<ChartReportDispatch> list=ReportDayService.chart_dispatch();
		
		for (int i = 0; i < list.size(); i++) {
			ChartReportDispatch bean =list.get(i);
		
			sheet.addCell(new Label(0, i + 3, String.valueOf(bean.getDstId()), fontFormat_Content));
			sheet.addCell(new Label(1, i + 3, String.valueOf(bean.getDstName()), fontFormat_Content));
			sheet.addCell(new Label(2, i + 3, String.valueOf(bean.getDxbox_ip()), fontFormat_Content));
			sheet.addCell(new Label(3, i + 3, String.valueOf(bean.getIp()), fontFormat_Content));
			sheet.addCell(new Label(4, i + 3, bean.getFlag()==0?"NO":"OK", fontFormat_Content));
			sheet.addCell(new Label(5, i + 3, bean.getDxbox_runtime()!=null && bean.getDxbox_runtime()!=""?(bean.getDxbox_runtime()).trim():"", fontFormat_Content));
		}
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_msc(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
			
			sheet.addCell(new Label(0, 0, "日常统计测量", fontFormat_h));
			
			sheet.mergeCells(0,0,5,0);
			
			sheet.setRowView(0, 600);
			sheet.setColumnView(0, 10);
			sheet.addCell(new Label(0, 1, "序号", fontFormat_Content));
			sheet.addCell(new Label(1, 1, "维护项目", fontFormat_Content));
			sheet.addCell(new Label(2, 1, "注册用户数量", fontFormat_Content));
			sheet.addCell(new Label(3, 1, "总呼叫次数  （统计前一天）", fontFormat_Content));
			sheet.addCell(new Label(4, 1, " 呼叫总持续时间（统计前一天）", fontFormat_Content));
			sheet.addCell(new Label(5, 1, "备注", fontFormat_Content));
			
			sheet.addCell(new Label(0, 2, "1", fontFormat_Content));
			sheet.addCell(new Label(1, 2, "重点基站呼叫测量统计（查询时间:"+time+"）", fontFormat_Content));
			sheet.mergeCells(1, 2, 5, 2);
			
			
			List<ChartReportImpBsBean> list=ReportDayService.chart_bs_imp_call(map.get("time").toString());
			
			
			for (int i = 0; i < list.size(); i++) {
				ChartReportImpBsBean bean =list.get(i);
				sheet.setRowView(i + 2, 400);
				sheet.addCell(new Label(0, i + 3, "1."+(i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i + 3, bean.getBsid()+"号基站", fontFormat_Content));
				sheet.addCell(new jxl.write.Number(2, i + 3, bean.getRegUsers(), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(3, i + 3,bean.getTotalActiveCalls(), fontFormat_Content));
				sheet.addCell(new Label(4, i + 3, funUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
				sheet.addCell(new Label(5, i + 3, "", fontFormat_Content));
			}
			
			int nowLine=3+list.size()-1;
			
			nowLine+=3;
			
			
		
	    
			sheet.addCell(new Label(0, nowLine, "2", fontFormat_Content));
		    sheet.addCell(new Label(1, nowLine, "交换中心话务量统计", fontFormat));
		    sheet.mergeCells(1, nowLine, 6, nowLine);
		    sheet.addCell(new Label(0, nowLine+1, "2.1", fontFormat_Content));
		    sheet.addCell(new Label(1, nowLine+1, "统计时段"+time, fontFormat));
		    sheet.mergeCells(1, nowLine+1, 6, nowLine+1);

		sheet.setRowView(nowLine, 600);
		sheet.setRowView(1, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		
		EastMscDayBean bean=ReportDayService.chart_msc_call(map);
		sheet.addCell(new Label(0, nowLine+2, "2.2", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+2, "活动呼叫总数", fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+2, "活动呼叫总持续时间", fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+2, "平均呼叫持续时间", fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+2, "呼叫总数", fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+2, "呼损率", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+2, "未成功呼叫总数", fontFormat_Content));
		
		sheet.setRowView(nowLine+3, 400);
		sheet.addCell(new Label(0, nowLine+3, "2.3", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+3, String.valueOf(bean.getTotalActiveCall()), fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+3, funUtil.second_time((int) bean.getTotalActiveCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+3, funUtil.second_time((int) bean.getAverageCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+3, String.valueOf(bean.getTotalCalls()), fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+3, String.valueOf(bean.getFailedPercentage())+"%", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+3, String.valueOf(bean.getNoEffectCalls()), fontFormat_Content));
		
		
		sheet.addCell(new Label(0, nowLine+4, "2.4", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+4, "组呼个数", fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+4, "组呼时长", fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+4, "个呼次数", fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+4, "个呼时长", fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+4, "", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+4, "", fontFormat_Content));
		
		sheet.setRowView(nowLine+5, 400);
		sheet.addCell(new Label(0, nowLine+5, "2.5", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+5, String.valueOf(bean.getGroupCalls()), fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+5, funUtil.second_time((int) bean.getGroupCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+5, String.valueOf(bean.getPrivateCalls()), fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+5, funUtil.second_time((int) bean.getPrivateCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+5, "", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+5, "", fontFormat_Content));
		
		sheet.addCell(new Label(0, nowLine+6, "2.6", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+6, "电话呼叫次数", fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+6, "电话呼叫时长", fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+6, "全双工单呼次数", fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+6, "半双工单呼次数", fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+6, "", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+6, "", fontFormat_Content));
		
		sheet.setRowView(nowLine+7, 400);
		sheet.addCell(new Label(0, nowLine+7, "2.7", fontFormat_Content));
		sheet.addCell(new Label(1, nowLine+7, String.valueOf(bean.getPhoneCalls()), fontFormat_Content));
		sheet.addCell(new Label(2, nowLine+7, funUtil.second_time((int) bean.getPhoneCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(3, nowLine+7, String.valueOf(bean.getPrivateDuplexCalls()), fontFormat_Content));
		sheet.addCell(new Label(4, nowLine+7, String.valueOf(bean.getPrivateSimplexCalls()), fontFormat_Content));
		sheet.addCell(new Label(5, nowLine+7, "", fontFormat_Content));
		sheet.addCell(new Label(6, nowLine+7, "", fontFormat_Content));
		
		
	
	
		
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
		
		Map<String, Object> map1=ReportDayService.operations_question(time);
		StringBuilder sb=new StringBuilder();
		sb.append("遗留问题：");
		if(map1!=null){
			sb.append(map1.get("question").toString());
		}
		
		sheet.addCell(new Label(0, 4, sb.toString(), fontFormat_Content));
		
		sheet.mergeCells(0,4,4,9);
		
		
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	public void excel_bs_fault(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
			
		
		// 设置数字格式
					jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
					jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

					/*Label label_h1 = new Label(0, 0, "基站信息", fontFormat_h);// 创建单元格
					Label label_h2 = new Label(5, 0, "故障情况", fontFormat_h);// 创建单元格
*/					

					Label label_1 = new Label(0, 1, "建设期", fontFormat_h);// 创建单元格
					Label label_2 = new Label(1, 1, "故障归属", fontFormat_h);// 创建单元格
					Label label_3 = new Label(2, 1, "基站编号", fontFormat_h);// 创建单元格
					Label label_4= new Label(3, 1, "基站名称", fontFormat_h);
					Label label_5 = new Label(4, 1, "基站分级", fontFormat_h);
					Label label_6 = new Label(5, 1, "使用状态", fontFormat_h);
					Label label_7 = new Label(6, 1, "星期", fontFormat_h);
					Label label_8 = new Label(7, 1, "故障发生时间", fontFormat_h);
					Label label_9 = new Label(8, 1, "报障来源", fontFormat_h);
					Label label_10 = new Label(9, 1, "故障等级", fontFormat_h);
					Label label_11= new Label(10, 1, "故障类别", fontFormat_h);
					Label label_12 = new Label(11, 1, "故障原因",fontFormat_h);
					Label label_13 = new Label(12, 1, "目前处理情况",fontFormat_h);
					Label label_14 = new Label(13, 1, "是否影响业务",fontFormat_h);
					Label label_15 = new Label(14, 1, "故障处理结果",fontFormat_h);
					Label label_16 = new Label(15, 1, "故障恢复时间",fontFormat_h);
					Label label_17= new Label(16, 1, "故障历时",fontFormat_h);
					Label label_18 = new Label(17, 1, "备注",fontFormat_h);
					Label label_19 = new Label(18, 1, "故障处理人员",fontFormat_h);
					Label label_20 = new Label(19, 1, "故障记录人员",fontFormat_h);
					Label label_21 = new Label(20, 1, "基站归属",fontFormat_h);
					Label label_22 = new Label(21, 1, "传输情况（单双链路）",fontFormat_h);
					Label label_23 = new Label(22, 1, "停电时间",fontFormat_h);
					Label label_24 = new Label(23, 1, "是否允许临时性发电",fontFormat_h);
					Label label_25 = new Label(24, 1, "发电时间",fontFormat_h);
					
					
					CellView cellView = new CellView();  
					cellView.setAutosize(true); //设置自动大小    
					 

					sheet.setRowView(0, 300);
					sheet.setColumnView(0, 10); //建设期
					sheet.setColumnView(1, 10); //故障归属
					sheet.setColumnView(2, 10); //基站编号
					sheet.setColumnView(3, 25); //基站名称
					sheet.setColumnView(4, 10); //基站分级
					sheet.setColumnView(5, 10); //使用状态
					sheet.setColumnView(6, 10); //星期
					sheet.setColumnView(7, 40); //故障发生时间
					sheet.setColumnView(8, 10); //报障来源
					sheet.setColumnView(9, 20); //故障等级
					sheet.setColumnView(10, 20); //故障类别
					sheet.setColumnView(11, 30); //故障原因
					sheet.setColumnView(12, 40);//目前处理情况
					sheet.setColumnView(13, 10);//是否影响业务
					sheet.setColumnView(14, 50);     //故障处理结果
					sheet.setColumnView(15, 30);      //故障恢复时间
					sheet.setColumnView(16, 20);      //故障历时
					sheet.setColumnView(17, 50);     //备注
					sheet.setColumnView(18, 15);      //故障处理人员
					sheet.setColumnView(19, 15);      //故障记录人员
					sheet.setColumnView(20, 10);      //基站归属
					sheet.setColumnView(21, 10);  
					sheet.setColumnView(22, 30);  
					sheet.setColumnView(23, 20);  
					sheet.setColumnView(24, 20);  

					sheet.addCell(label_1);
					sheet.addCell(label_2);
					sheet.addCell(label_3);
					sheet.addCell(label_4);
					sheet.addCell(label_5);
					sheet.addCell(label_6);
					sheet.addCell(label_7);
					sheet.addCell(label_8);
					sheet.addCell(label_9);
					sheet.addCell(label_10);
					sheet.addCell(label_11);
					sheet.addCell(label_12);
					sheet.addCell(label_13);
					sheet.addCell(label_14);
					sheet.addCell(label_15);
					sheet.addCell(label_16);
					sheet.addCell(label_17);
					sheet.addCell(label_18);
					sheet.addCell(label_19);
					sheet.addCell(label_20);
					sheet.addCell(label_21);
					sheet.addCell(label_22);
					sheet.addCell(label_23);
					sheet.addCell(label_24);
					sheet.addCell(label_25);
					/*sheet.addCell(label_h1);
					sheet.addCell(label_h2);*/
					
					
					// ws.mergeCells(0, 0, 0, 1);//合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
			            //合： 第1列第1行  到 第13列第1行  
					sheet.mergeCells(0, 0, 3, 0); 
					sheet.mergeCells(5, 0, 14, 0);  
					
					Map<String,Object> mapstr=new HashMap<String, Object>();
					
					mapstr.put("startTime",time.substring(0, time.lastIndexOf("-"))+"-01 00:00:00" );
					mapstr.put("endTime",time+" 23:59:59" );
					
					List<BsAlarmExcelBean> list = BsStatusService.bsAlarmExcel(mapstr);
					for (int i = 0; i < list.size(); i++) {
						BsAlarmExcelBean bean =list.get(i);
						Label value_1 = new Label(0, i + 2, bean.getPeriod()==3?"三期":"四期", fontFormat_Content);
						Label value_2 = new Label(1, i + 2, bean.getFaultType(), fontFormat_Content);
						Label value_3 = new Label(2, i + 2, String.valueOf(bean.getBsId()), fontFormat_Content);
						Label value_4 = new Label(3, i + 2, bean.getName(),fontFormat_Content);
						Label value_5 = new Label(4, i + 2, bean.getLevel(),fontFormat_Content);
						Label value_6 = new Label(5, i + 2, bean.getTag()==1?"在用":"未使用",fontFormat_Content);		
						Label value_7 = new Label(6, i + 2,FunUtil.formateWeekly(bean.getTime()) ,fontFormat_Content);
						Label value_8 = new Label(7, i + 2,bean.getTime() ,fontFormat_Content);
						Label value_9 = new Label(8, i + 2,bean.getFrom(),fontFormat_Content);
						Label value_10 = new Label(9, i + 2, bean.getSeverity(),fontFormat_Content);
						Label value_11 = new Label(10, i + 2, bean.getType(),fontFormat_Content);
						Label value_12 = new Label(11, i + 2, bean.getReason(),fontFormat_Content);
						Label value_13 = new Label(12, i + 2, bean.getNowDeal(),fontFormat_Content);
						Label value_14 = new Label(13, i + 2, bean.getImbusiness(),fontFormat_Content);
						Label value_15 = new Label(14, i + 2, bean.getDealResult(),fontFormat_Content);
						Label value_16= new Label(15, i + 2, bean.getFaultRecoveryTime(),fontFormat_Content);
						Label value_17 = new Label(16, i + 2, bean.getFaultTimeTotal(),fontFormat_Content);
						Label value_18 = new Label(17, i + 2, bean.getContent(),fontFormat_Content);
						Label value_19 = new Label(18, i + 2,bean.getFaultHandlePerson() ,fontFormat_Content);
						Label value_20 = new Label(19, i + 2, bean.getFaultRecordPerson(),fontFormat_Content);
						Label value_21 = new Label(20, i + 2, bean.getHometype(),fontFormat_Content);
						Label value_22 = new Label(21, i + 2, "",fontFormat_Content);
						Label value_23 = new Label(22, i + 2, bean.getElc_time(),fontFormat_Content);
						Label value_24 = new Label(23, i + 2, bean.getIs_allow_generation(),fontFormat_Content);
						Label value_25 = new Label(24, i + 2, bean.getGeneration_date(),fontFormat_Content);
						//sheet.setRowView(i + 2, 300);
						sheet.addCell(value_1);
						sheet.addCell(value_2);
						sheet.addCell(value_3);
						sheet.addCell(value_4);
						sheet.addCell(value_5);
						sheet.addCell(value_6);
						sheet.addCell(value_7);
						sheet.addCell(value_8);
						sheet.addCell(value_9);
						sheet.addCell(value_10);
						sheet.addCell(value_11);
						sheet.addCell(value_12);
						sheet.addCell(value_13);
						sheet.addCell(value_14);
						sheet.addCell(value_15);
						sheet.addCell(value_16);
						sheet.addCell(value_17);
						sheet.addCell(value_18);
						sheet.addCell(value_19);
						sheet.addCell(value_20);
						sheet.addCell(value_21);
						sheet.addCell(value_22);
						sheet.addCell(value_23);
						sheet.addCell(value_24);
						sheet.addCell(value_25);
						
					}
		} catch (Exception e) {
			// TODO: handle exception
		}
				

	}
	public  void excel_bsStatus(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
		
			

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
					nf); // 设置表单格式
			wcfN.setAlignment(Alignment.CENTRE);// 水平对齐
			wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfN.setBackground(Colour.WHITE);// 背景色
			wcfN.setWrap(true);// 自动换行
			sheet.addCell(new Label(0, 0, "基站期数", fontFormat_h));
			sheet.addCell(new Label(1, 0, "基站ID", fontFormat_h));
			sheet.addCell(new Label(2, 0, "基站名称", fontFormat_h));
			sheet.addCell(new Label(3, 0, "BSC时钟", fontFormat_h));
			sheet.addCell(new Label(4, 0, "BSR1", fontFormat_h));
			sheet.addCell(new Label(5, 0, "BSR2", fontFormat_h));
			sheet.addCell(new Label(6, 0, "BSR3", fontFormat_h));
			sheet.addCell(new Label(7, 0, "BSR4", fontFormat_h));
			sheet.addCell(new Label(8, 0, "载波数", fontFormat_h));
			sheet.addCell(new Label(9, 0, "主控信道底噪查询", fontFormat_h));
			sheet.addCell(new Label(10, 0, "BSC运行时间", fontFormat_h));
			sheet.addCell(new Label(11, 0, "回拨损耗", fontFormat_h));
			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 7);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 10);
			sheet.setColumnView(5, 10);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 10);
			sheet.setColumnView(8, 7);
			sheet.setColumnView(9, 50);
			sheet.setColumnView(10, 50);
			sheet.setColumnView(11, 50);

			
			List<BsStatusBean> list = BsStatusService.excelToBsStatus();
			for (int i = 0; i < list.size(); i++) {
				BsStatusBean bean = (BsStatusBean) list.get(i);
				sheet.addCell(new jxl.write.Number(0, i + 1,Double.parseDouble(String.valueOf(bean.getPeriod())),wcfN));
				sheet.addCell(new jxl.write.Number(1, i + 1,Double.parseDouble(String.valueOf(bean.getBsId())),wcfN));
				
				sheet.addCell(new Label(2, i + 1, String.valueOf(bean.getName()), fontFormat_Content));
				sheet.addCell(new Label(3, i + 1, String.valueOf(bean.getClock_status()), fontFormat_Content));
				sheet.addCell(new Label(4, i + 1, bsr_status(bean.getBsr_state1()), fontFormat_Content));
				sheet.addCell(new Label(5, i + 1, bsr_status(bean.getBsr_state2()), fontFormat_Content));
				sheet.addCell(new Label(6, i + 1, bsr_status(bean.getBsr_state3()), fontFormat_Content));
				sheet.addCell(new Label(7, i + 1, bsr_status(bean.getBsr_state4()), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(8, i + 1,Double.parseDouble(String.valueOf(bean.getChnumber())),wcfN));
				sheet.addCell(new Label(9, i + 1, master_ch(bean.getCarrierLowNoiseRXRssi1(),
						bean.getCarrierLowNoiseRXRssi2(),bean.getCarrierLowNoiseRXRssi3(),
						bean.getCarrierLowNoiseRXRssi4()), fontFormat_Content));
				sheet.addCell(new Label(10, i + 1, bsc_runtime(bean.getBscRuntime()), fontFormat_Content));
				sheet.addCell(new Label(11, i + 1, dpx_format(bean.getDpx_retLoss1(),bean.getDpx_retLoss2(),
						bean.getDpx_retLoss3(), bean.getDpx_retLoss4()), fontFormat_Content));
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		
		
	}
 	public String bsr_status(String str){
		if(str==null || str==""){
			return "";
		}else if(str.equals("0")){
			return "Normal";
		}else{
			return "ERROR";
		}
	}
	public String bsc_runtime(String str){
		if(str==null || str==""){
			return "";
		}else{
			if(str.indexOf(":")>-1){
				return str.substring(str.indexOf(":")+1);
			}
			return str;
		}
	}
	public String master_ch(int a,int b,int c,int d){
		StringBuilder str=new StringBuilder();
		if(Math.abs(a)>1){
			str.append("RX1=");
			str.append(Double.parseDouble(String.valueOf(a))/10);
			str.append("dBm");
			str.append(",");
		}
		if(Math.abs(b)>1){
			str.append("RX2=");
			str.append(Double.parseDouble(String.valueOf(b))/10);
			str.append("dBm");
			str.append(",");
		}
		if(Math.abs(c)>1){
			str.append("RX3=");
			str.append(Double.parseDouble(String.valueOf(c))/10);
			str.append("dBm");
			str.append(",");
		}
		if(Math.abs(d)>1){
			str.append("RX5=");
			str.append(Double.parseDouble(String.valueOf(d))/10);
			str.append("dBm");
			str.append(",");
		}
		if(str.length()>0){
			str.deleteCharAt(str.lastIndexOf(","));
		}
		
		return str.toString();
	
	}
	public String dpx_format(String a,String b,String c,String d){
		StringBuilder str=new StringBuilder();
		if(a!=null && a!=""){
			if(Integer.parseInt(a)>0){
				str.append("DPX1=");
				str.append(Double.parseDouble(a)/10);
				str.append("dB");
				str.append(",");
			}
		}
		if(b!=null && b!=""){
			if(Integer.parseInt(b)>0){
				str.append("DPX2=");
				str.append(Double.parseDouble(b)/10);
				str.append("dB");
				str.append(",");
			}
		}
		if(c!=null && c!=""){
			if(Integer.parseInt(c)>0){
				str.append("DPX3=");
				str.append(Double.parseDouble(c)/10);
				str.append("dB");
				str.append(",");
			}
		}
		if(d!=null && d!=""){
			if(Integer.parseInt(d)>0){
				str.append("DPX4=");
				str.append(Double.parseDouble(d)/10);
				str.append("dB");
				str.append(",");
			}
		}
		if(str.length()>0){
			str.deleteCharAt(str.lastIndexOf(","));
		}
		return str.toString();
	
	}
}

