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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.ReportDayService;
import xh.mybatis.service.ServerStatusService;
@Controller
@RequestMapping(value="/server")
public class ServerStatusController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ServerStatusController.class);
	private WebLogBean webLogBean=new WebLogBean();
	private FlexJSON json=new FlexJSON();
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",ServerStatusService.serverstatus().size());
		result.put("items", ServerStatusService.serverstatus());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/icpStatus",method = RequestMethod.GET)
	public void icpStatus(HttpServletRequest request, HttpServletResponse response){	
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("totals",ServerStatusService.icpStatus().size());
		result.put("items", ServerStatusService.icpStatus());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
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
			String saveDir = request.getSession().getServletContext().getRealPath("/upload");
			String pathname = saveDir + "/系统日常维护表"+time+".xls";
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			int days=FunUtil.getDaysOfMonth(sdf.parse(time));
			
			for(int i=0;i<days;i++){
				String title=time.split("-")[1]+"."+(i+1);
				String tt=time+"-"+((i+1)<10?"0"+(i+1):(i+1));
				
				WritableSheet sheetx = book.createSheet(title, i);
				excel_server(tt,sheetx,fontFormat,fontFormat_h,fontFormat_Content);
			}
			
			
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
	public  void  excel_server(String day,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
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
		sheet.addCell(new Label(5, 2, "统计时间"+day, fontFormat));
		sheet.mergeCells(0,0,6,0);
		sheet.mergeCells(0,1,6,1);
		sheet.mergeCells(5,2,6,2);
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
		
		
		
		List<Map<String,Object>> list=ServerStatusService.chart_server(day);
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
		}
		
		//容灾
		int start=list_one.size()+5;
		sheet.addCell(new Label(0, start+1, "容灾服务器当前运行状态", fontFormat));
		sheet.mergeCells(0,start+1,6,start+1);
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
			sheet.addCell(new Label(2, start+i + 3,bean.get("cpuLoad")+"%",fontFormat_Content));
			sheet.addCell(new Label(3, start+i + 3,bean.get("memPercent")+"%",fontFormat_Content));
			
			sheet.addCell(new jxl.write.Number(4, start+i + 3, Double.parseDouble(bean.get("diskUsed").toString()),wcfN));
			sheet.addCell(new jxl.write.Number(5, start+i + 3, Double.parseDouble(bean.get("diskSize").toString()), wcfN));
			
			
			sheet.addCell(new Label(6, start+i + 3, "", fontFormat_Content));
			sheet.mergeCells(6,start+i + 3,7,start+i + 3);
		}
		

		//GPS统计
		
		start=list_one.size()+5+list_two.size()+3;
		List<Map<String,Object>> gps=ReportDayService.now_week_gpsnumber(day);
		sheet.addCell(new Label(0, start+1, "", fontFormat_h));
		sheet.addCell(new Label(1, start+1, "周一", fontFormat_h));
		sheet.addCell(new Label(2, start+1, "周二", fontFormat_h));
		sheet.addCell(new Label(3, start+1, "周三", fontFormat_h));
		sheet.addCell(new Label(4, start+1, "周四", fontFormat_h));
		sheet.addCell(new Label(5, start+1, "周五", fontFormat_h));
		sheet.addCell(new Label(6, start+1, "周六", fontFormat_h));
		sheet.addCell(new Label(7, start+1, "周日", fontFormat_h));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        List<Date> lDate=FunUtil.findNowWeekDays(day);
        
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
        	
        	int total=Integer.parseInt(gps.get(i).get("v1").toString())+
        			Integer.parseInt(gps.get(i).get("v2").toString())+
        			Integer.parseInt(gps.get(i).get("v3").toString());
        	sheet.addCell(new jxl.write.Number(i+1, start+3, total, fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+4, Integer.parseInt(gps.get(i).get("v1").toString()), fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+5, Integer.parseInt(gps.get(i).get("v2").toString()), fontFormat_Content));
        	sheet.addCell(new jxl.write.Number(i+1, start+6, Integer.parseInt(gps.get(i).get("v3").toString()), fontFormat_Content));
        }
        
        Map<String,Object>  mapGps=ReportDayService.now_gpsunit_status();
        
        sheet.addCell(new Label(0, start+7, "交管局GPS数据库", fontFormat_Content));
        sheet.addCell(new Label(1, start+7, mapGps.get("jg").toString().equals("1")?"OK":"NO", fontFormat_Content));
        sheet.addCell(new Label(2, start+7, "", fontFormat_Content));
        sheet.mergeCells(2,start+7,7,start+7);
        
        sheet.addCell(new Label(0, start+8, "公安局GPS数据库", fontFormat_Content));
        sheet.addCell(new Label(1, start+8, mapGps.get("cd").toString().equals("1")?"OK":"NO", fontFormat_Content));
        sheet.addCell(new Label(2, start+8, "", fontFormat_Content));
        sheet.mergeCells(2,start+8,7,start+8);
        
        
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

}
