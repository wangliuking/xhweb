package xh.springmvc.handlers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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

import com.microsoft.sqlserver.jdbc.SQLServerException;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsRunStatusBean;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.EmhBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.ServerStatusService;
import xh.mybatis.service.SqlServerService;

@Controller
@RequestMapping(value = "/bsstatus")
public class BsStatusController {
	protected final Log log = LogFactory.getLog(BsStatusController.class);
	private FlexJSON json = new FlexJSON();
	private boolean success;

	@RequestMapping(value = "/index/ajax_table2", method = RequestMethod.GET)
	@ResponseBody
	public void selectAllBsStatus(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, List<BsStatusBean>> map = new HashMap<String, List<BsStatusBean>>();
		BsStatusService bsStatusService = new BsStatusService();
		try {
			List<BsStatusBean> list = bsStatusService.selectAllBsStatus();
			map.put("data", list);
			String data = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/ExcelToStationStatus", method = RequestMethod.GET)
	public void ExcelToStationStatus(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/upload");
			String pathname = saveDir + "/基站运行记录.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.PINK);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.LIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(false);// 不自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
					nf); // 设置表单格式

			Label title = new Label(0, 0, "设备信息", fontFormat);

			WritableSheet sheet = book.createSheet("基站运行记录", 0);
			// sheet.mergeCells(0,0,3,0);

			Label label_1 = new Label(0, 0, "基站ID", fontFormat_h);// 创建单元格
			Label label_2 = new Label(1, 0, "设备", fontFormat_h);
			Label label_3 = new Label(2, 0, "基站icp状态", fontFormat_h);
			Label label_4 = new Label(3, 0, "BSC运行状态", fontFormat_h);
			Label label_5 = new Label(4, 0, "基站时钟运行状态", fontFormat_h);
			Label label_6 = new Label(5, 0, "双工器回波损耗", fontFormat_h);
			Label label_7 = new Label(6, 0, "BSC运行时间", fontFormat_h);
			Label label_8 = new Label(7, 0, "BSR运行状态", fontFormat_h);
			Label label_9 = new Label(8, 0, "主控信道底噪查询", fontFormat_h);
			Label label_10 = new Label(9, 0, "ENB运行时间（telnet 10.1.基站ID.1)",
					fontFormat_h);
			Label label_11 = new Label(10, 0, "psm1运行时长", fontFormat_h);
			Label label_12 = new Label(11, 0, "psm2运行时长", fontFormat_h);

			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 30);
			sheet.setColumnView(6, 50);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 50);
			sheet.setColumnView(10, 50);
			sheet.setColumnView(11, 50);

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
			List<BsStatusBean> list = BsStatusService.excelToBsStatus();
			for (int i = 0; i < list.size(); i++) {
				BsStatusBean bean = (BsStatusBean) list.get(i);
				int icpS = bean.getStatus();
				int bscS = bean.getStatus();
				String stationClock = bean.getClock_status();
				String dpx1 = bean.getReturnloss1();
				String dpx2 = bean.getReturnloss2();
				String bscRunTime = bean.getBscRuntime();
				String enbRunTime = bean.getEnbRunTime();
				Label value_1 = new Label(0, i + 1, String.valueOf(bean
						.getBsId()), fontFormat_Content);
				Label value_2 = new Label(1, i + 1, bean.getName(),
						fontFormat_Content);
				Label value_3 = new Label(2, i + 1, icpS == 1 ? "NO" : "OK",
						fontFormat_Content);
				Label value_4 = new Label(3, i + 1, bscS == 1 ? "NO" : "OK",
						fontFormat_Content); // 格式化数值
				Label value_5 = new Label(4, i + 1, stationClock == ""
						|| stationClock == null ? "NA" : stationClock,
						fontFormat_Content);
				Label value_6 = new Label(5, i + 1, dpx1 == "" || dpx1 == null
						&& dpx2 == "" || dpx2 == null ? "NA" : "DPX1="
						+ bean.getReturnloss1() + "dB" + " DPX2="
						+ bean.getReturnloss2() + "dB", fontFormat_Content);
				Label value_7 = new Label(6, i + 1, bscRunTime == ""
						|| bscRunTime == null ? "NA" : bscRunTime,
						fontFormat_Content);
				Label value_8 = new Label(7, i + 1, "NA", fontFormat_Content);
				Label value_9 = new Label(8, i + 1, "NA", fontFormat_Content);
				Label value_10 = new Label(9, i + 1, enbRunTime == ""
						|| enbRunTime == null ? "NA" : enbRunTime,
						fontFormat_Content);
				Label value_11 = new Label(10, i + 1, bean.getPsm1runtime(),fontFormat_Content);
				Label value_12 = new Label(11, i + 1, bean.getPsm2runtime(),fontFormat_Content);
				sheet.setRowView(i + 1, 400);
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
				// System.out.println(assetbean.getNumber());
			}

			book.write();
			book.close();
			log.info("导出基站运行记录表");
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
	//基站故障记录登记表
	@RequestMapping(value = "/ExcelToBsAlarm", method = RequestMethod.GET)
	public void ExcelToBsAlarm(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/upload");
			String pathname = saveDir + "/基站故障记录登记表.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.PINK);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
		
 
            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,  
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色 
            
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义 
            
        	WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);
            
            
			// 设置其他样式
        	
        	wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
        	wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
        	wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
        	wcf_title.setBackground(Colour.WHITE);// 背景色
        	wcf_title.setWrap(false);// 不自动换行
        	
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(false);// 不自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

			Label title = new Label(0, 0, "设备信息", fontFormat);

			WritableSheet sheet = book.createSheet("基站故障记录", 0);
			// sheet.mergeCells(0,0,3,0);
			
			Label label_h1 = new Label(0, 0, "基站信息", wcf_title);// 创建单元格
			Label label_h2 = new Label(5, 0, "故障情况", wcf_title);// 创建单元格
			

			Label label_1 = new Label(0, 1, "基站编号", fontFormat_h);// 创建单元格
			Label label_2 = new Label(1, 1, "基站名称", fontFormat_h);
			Label label_3 = new Label(2, 1, "基站分级", fontFormat_h);
			Label label_4 = new Label(3, 1, "使用状态", fontFormat_h);
			Label label_5 = new Label(4, 1, "星期", fontFormat_h);
			Label label_6 = new Label(5, 1, "故障发生时间", fontFormat_h);
			Label label_7 = new Label(6, 1, "报障来源", fontFormat_h);
			Label label_8 = new Label(7, 1, "故障等级", fontFormat_h);
			Label label_9 = new Label(8, 1, "故障类别", fontFormat_h);
			Label label_10 = new Label(9, 1, "故障原因",fontFormat_h);
			Label label_11 = new Label(10, 1, "目前处理情况",fontFormat_h);
			Label label_12 = new Label(11, 1, "是否影响业务",fontFormat_h);
			Label label_13 = new Label(12, 1, "故障处理结果",fontFormat_h);
			Label label_14 = new Label(13, 1, "故障恢复时间",fontFormat_h);
			Label label_15= new Label(14, 1, "故障历时",fontFormat_h);
			Label label_16 = new Label(15, 1, "备注",fontFormat_h);
			Label label_17 = new Label(16, 1, "故障处理人员",fontFormat_h);
			Label label_18 = new Label(17, 1, "故障记录人员",fontFormat_h);
			Label label_19 = new Label(18, 1, "基站归属",fontFormat_h);
			
			
			CellView cellView = new CellView();  
			cellView.setAutosize(true); //设置自动大小    
			 

			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10); //基站编号
			sheet.setColumnView(1, 25); //基站名称
			sheet.setColumnView(2, 10); //基站分级
			sheet.setColumnView(3, 10); //使用状态
			sheet.setColumnView(4, 10); //星期
			sheet.setColumnView(5, 20); //故障发生时间
			sheet.setColumnView(6, 10); //报障来源
			sheet.setColumnView(7, 20); //故障等级
			sheet.setColumnView(8, 20); //故障类别
			sheet.setColumnView(9, 30); //故障原因
			sheet.setColumnView(10, 40);//目前处理情况
			sheet.setColumnView(11, 10);//是否影响业务
			sheet.setColumnView(12, 50);     //故障处理结果
			sheet.setColumnView(13, 20);      //故障恢复时间
			sheet.setColumnView(14, 20);      //故障历时
			sheet.setColumnView(15, 50);     //备注
			sheet.setColumnView(16, 15);      //故障处理人员
			sheet.setColumnView(17, 15);      //故障记录人员
			sheet.setColumnView(18, 10);      //基站归属

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
			sheet.addCell(label_h1);
			sheet.addCell(label_h2);
			
			
			// ws.mergeCells(0, 0, 0, 1);//合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
	            //合： 第1列第1行  到 第13列第1行  
			sheet.mergeCells(0, 0, 3, 0); 
			sheet.mergeCells(5, 0, 14, 0);  
			
			Map<String,Object> map=new HashMap<String, Object>();
			
			List<BsAlarmExcelBean> list = BsStatusService.bsAlarmExcel(map);
			for (int i = 0; i < list.size(); i++) {
				BsAlarmExcelBean bean =list.get(i);
				Label value_1 = new Label(0, i + 2, String.valueOf(bean.getBsId()), fontFormat_Content);
				Label value_2 = new Label(1, i + 2, bean.getName(),fontFormat_Content);
				Label value_3 = new Label(2, i + 2, bean.getLevel(),fontFormat_Content);
				Label value_4 = new Label(3, i + 2, bean.getStatus()==1?"在用":"未使用",fontFormat_Content);		
				Label value_5 = new Label(4, i + 2,FunUtil.formateWeekly(bean.getTime()) ,fontFormat_Content);
				Label value_6 = new Label(5, i + 2,bean.getTime() ,fontFormat_Content);
				Label value_7 = new Label(6, i + 2,bean.getFrom(),fontFormat_Content);
				Label value_8 = new Label(7, i + 2, "",fontFormat_Content);
				Label value_9 = new Label(8, i + 2, bean.getType(),fontFormat_Content);
				Label value_10 = new Label(9, i + 2, bean.getReason(),fontFormat_Content);
				Label value_11 = new Label(10, i + 2, bean.getNowDeal(),fontFormat_Content);
				Label value_12 = new Label(11, i + 2, bean.getImbusiness(),fontFormat_Content);
				Label value_13 = new Label(12, i + 2, bean.getDealResult(),fontFormat_Content);
				Label value_14= new Label(13, i + 2, bean.getFaultRecoveryTime(),fontFormat_Content);
				Label value_15 = new Label(14, i + 2, bean.getFaultTimeTotal(),fontFormat_Content);
				Label value_16 = new Label(15, i + 2, bean.getContent(),fontFormat_Content);
				Label value_17 = new Label(16, i + 2,bean.getFaultHandlePerson() ,fontFormat_Content);
				Label value_18 = new Label(17, i + 2, bean.getFaultRecordPerson(),fontFormat_Content);
				Label value_19 = new Label(18, i + 2, bean.getHometype(),fontFormat_Content);
				sheet.setRowView(i + 2, 300);
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
			}

			book.write();
			book.close();
			log.info("基站故障记录登记表");
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
	//基站状态检查表
		@RequestMapping(value = "/ExcelToBsStatus", method = RequestMethod.GET)
		public void ExcelToBsStatus(HttpServletRequest request,
				HttpServletResponse response) {
			try {
				String saveDir = request.getSession().getServletContext()
						.getRealPath("/upload");
				String pathname = saveDir + "/基站状态检查表.xls";
				File Path = new File(saveDir);
				if (!Path.exists()) {
					Path.mkdirs();
				}
				File file = new File(pathname);
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableFont font = new WritableFont(
						WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD,
						false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
				WritableCellFormat fontFormat = new WritableCellFormat(font);
				fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
				fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
				fontFormat.setWrap(true); // 自动换行
				fontFormat.setBackground(Colour.PINK);// 背景颜色
				fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
						Colour.DARK_GREEN);
				fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

				// 设置头部字体格式
				WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
						WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
						Colour.BLACK);
				// 应用字体
				WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
				
				WritableCellFormat fontFormat_alarm = new WritableCellFormat(font_header);
				// 设置其他样式
				fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
				fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
				fontFormat_h.setWrap(false);// 不自动换行
				
				fontFormat_alarm.setAlignment(Alignment.CENTRE);// 水平对齐
				fontFormat_alarm.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				fontFormat_alarm.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				fontFormat_alarm.setBackground(Colour.YELLOW2);// 背景色
				fontFormat_alarm.setWrap(false);// 不自动换行

				// 设置主题内容字体格式
				WritableFont font_Content = new WritableFont(WritableFont.TIMES,
						10, WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
				// 应用字体
			
	 
	            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,  
	                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色 
	            
	            WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义 
	            
	        	WritableCellFormat fontFormat_Content = new WritableCellFormat(
						font_Content);
	            
	            
				// 设置其他样式
	        	
	        	wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
	        	wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
	        	wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
	        	wcf_title.setBackground(Colour.WHITE);// 背景色
	        	wcf_title.setWrap(false);// 不自动换行
	        	
				fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
				fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				fontFormat_Content.setBackground(Colour.WHITE);// 背景色
				fontFormat_Content.setWrap(false);// 不自动换行

				// 设置数字格式
				jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式

				WritableSheet sheet = book.createSheet("基站状态检查表", 0);
				Label label_1 = new Label(0, 0, "基站编号", fontFormat_h);// 创建单元格
				Label label_2 = new Label(1, 0, "基站名称", fontFormat_h);
				Label label_3 = new Label(2, 0, "基站归属", fontFormat_h);
				Label label_4 = new Label(3, 0, "基站状态", fontFormat_h);
				Label label_5 = new Label(4, 0, "备注", fontFormat_h);

				CellView cellView = new CellView();  
				cellView.setAutosize(true); //设置自动大小    
				 

				sheet.setRowView(0, 300);
				sheet.setColumnView(0, 10); //基站编号
				sheet.setColumnView(1, 30); //基站名称
				sheet.setColumnView(2, 10); //基站归属
				sheet.setColumnView(3, 10); //基站状态
				sheet.setColumnView(4, 50); //备注

				sheet.addCell(label_1);
				sheet.addCell(label_2);
				sheet.addCell(label_3);
				sheet.addCell(label_4);
				sheet.addCell(label_5);
				
				List<BsRunStatusBean> list = BsStatusService.excelToBsRunStatus();
				for (int i = 0; i < list.size(); i++) {
					BsRunStatusBean bean = (BsRunStatusBean) list.get(i);
					
					if(bean.getLink()==0){
						Label value_1 = new Label(0, i + 1, String.valueOf(bean.getBsId()), fontFormat_Content);
						Label value_2 = new Label(1, i + 1, bean.getName(),fontFormat_Content);
						Label value_3 = new Label(2, i + 1, bean.getHometype(),fontFormat_Content);
						Label value_4 = new Label(3, i + 1, bean.getLink()==0?"OK":"NO",fontFormat_Content);
						Label value_5 = new Label(4, i + 1, bean.getLink()==0?"OK":"基站断站",fontFormat_Content);
						sheet.setRowView(i + 1, 300);
						sheet.addCell(value_1);
						sheet.addCell(value_2);
						sheet.addCell(value_3);
						sheet.addCell(value_4);
						sheet.addCell(value_5);
					}else{
						Label value_1 = new Label(0, i + 1, String.valueOf(bean.getBsId()), fontFormat_alarm);
						Label value_2 = new Label(1, i + 1, bean.getName(),fontFormat_alarm);
						Label value_3 = new Label(2, i + 1, bean.getHometype(),fontFormat_alarm);
						Label value_4 = new Label(3, i + 1, bean.getLink()==0?"OK":"NO",fontFormat_alarm);
						Label value_5 = new Label(4, i + 1, bean.getLink()==0?"":"基站断站",fontFormat_alarm);
						sheet.setRowView(i + 1, 300);
						sheet.addCell(value_1);
						sheet.addCell(value_2);
						sheet.addCell(value_3);
						sheet.addCell(value_4);
						sheet.addCell(value_5);
					}
					
					
				}

				book.write();
				book.close();
				log.info("基站状态检查表");
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
		@RequestMapping(value = "/downExcel", method = RequestMethod.GET)
	     public void DownExcelFile(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
			String filePath=request.getParameter("filePath");
		    File file = new File(filePath);
		    String fileName = "";
		    response.setContentType("text/plain;charset=utf-8");
		    if (file.exists()) {
			try {
				// 要用servlet 来打开一个 EXCEL 文档，需要将 response 对象中 header 的
				// contentType 设置成"application/x-msexcel"。
				response.setContentType("application/x-msexcel");
				// 保存文件名称
				fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
				fileName = new String(fileName.getBytes("GB2312"), "iso8859_1");
				// servlet中，要在 header中设置下载方式
				response.setHeader("Content-Disposition",
						"attachment; filename=" + fileName);
				// 缓冲流(BufferedStream)可以一次读写一批数据，,缓冲流(Buffered
				// Stream)大大提高了I/O的性能。
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				// OutputStream输出流
				OutputStream bos = response.getOutputStream();
				byte[] buff = new byte[1024];
				int readCount = 0;
				// 每次从文件流中读1024个字节到缓冲里。
				readCount = bis.read(buff);
				while (readCount != -1) {
					// 把缓冲里的数据写入浏览器
					bos.write(buff, 0, readCount);
					readCount = bis.read(buff);
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				response.setStatus(HttpServletResponse.SC_OK);
				response.flushBuffer();
				response.getWriter().close();
			} catch (Exception e) {

			}
		}
	}

	@RequestMapping(value = "/bsEmh", method = RequestMethod.GET)
	@ResponseBody
	public void bsEmh(HttpServletRequest request, HttpServletResponse response) {
		int siteId = Integer.parseInt(request.getParameter("siteId"));
		int period = Integer.parseInt(request.getParameter("period"));

		HashMap<String, Object> result = new HashMap<String, Object>();
		try {

			if (period == 3) {
				result=SqlServerService.bsmonitorList(siteId);
				result.put("alarmItems",SqlServerService.bsmonitorAlarmList(siteId));
				result.put("alarmTotals",SqlServerService.bsmonitorAlarmList(siteId).size());	
				
			}else{
				result = BsStatusService.bsEmh(siteId);
				result.put("alarmItems",BsStatusService.bsEmhAlarm(siteId));
				result.put("alarmTotals",BsStatusService.bsEmhAlarm(siteId).size());
			}
			
			result.put("totals",result.size());
			result.put("period",period);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 response.setContentType("application/json;charset=utf-8"); 
		 String jsonstr = json.Encode(result); 
		 try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/bsVoiceAlarm", method = RequestMethod.GET)
	@ResponseBody
	public void bsVoiceAlarm(HttpServletRequest request, HttpServletResponse response) throws SQLServerException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list1 = null;
		try {
			list1 = BsStatusService.bsOffList();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		List<Map<String,Object>> list2=SqlServerService.bsJiAlarm();
		//List<Map<String, Object>> list3=DispatchStatusService.dispatchOffAlarm();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>  msc=ServerStatusService.unusualStatus(1);
		
		for (Map<String, Object> map : list1) {
			list.add(map);
		}
		for (Map<String, Object> map2 : list2) {
			list.add(map2);
		}
		for (Map<String, Object> map3 : msc) {
			list.add(map3);
		}		
		result.put("items", list);
		result.put("totals", list.size());
		
		 response.setContentType("application/json;charset=utf-8"); 
		 String jsonstr = json.Encode(result); 
		 try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 基站断站声音告警数目 
	 * @param request
	 * @param response
	 * @throws SQLServerException
	 */
	@RequestMapping(value = "/bsOffVoiceCount", method = RequestMethod.GET)
	@ResponseBody
	public void bsOffVoiceCount(HttpServletRequest request, HttpServletResponse response) throws SQLServerException {
		
		int bsCount=BsStatusService.bsOffVoiceCount();
		//int dispatchCount=DispatchStatusService.dispatchOffAlarmCount();
		int jiCount=SqlServerService.bsJiAlarmCount();
		int msc=ServerStatusService.alarmNum();
		
		
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("bsbreakTotals", bsCount+msc);
		result.put("jiTotals", jiCount);
		
		 response.setContentType("application/json;charset=utf-8"); 
		 String jsonstr = json.Encode(result); 
		 try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**\
	 * 
	 * @param request
	 * @param response
	 * @throws SQLServerException
	 */
	@RequestMapping(value = "/bsZoneAlarm", method = RequestMethod.GET)
	@ResponseBody
	public void bsZoneAlarm(HttpServletRequest request, HttpServletResponse response) throws SQLServerException {
	
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("items", BsStatusService.bsZoneAlarm());
		
		 response.setContentType("application/json;charset=utf-8"); 
		 String jsonstr = json.Encode(result); 
		 try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 基站断站告警更新
	 * @param request
	 * @param response
	 * @throws SQLServerException
	 */
	@RequestMapping(value = "/bsOffVoiceChange", method = RequestMethod.POST)
	@ResponseBody
	public void bsOffVoiceChange(HttpServletRequest request, HttpServletResponse response) throws SQLServerException {
		
		BsStatusService.bsOffVoiceChange();
		ServerStatusService.updateAlarmStatus();

	}
	
	@RequestMapping(value = "/updateAlarm", method = RequestMethod.POST)
	@ResponseBody
	public void updateAlarm(HttpServletRequest request, HttpServletResponse response) throws SQLServerException {
		
		/*DispatchStatusService.updateDispatchAlarmStatus();*/
		BsStatusService.updateAlarmStatus();
		SqlServerService.updateAlarmStatus();
		ServerStatusService.offAlarmStatus();

	}

	/**
	 * 基站下的bsc状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsc", method = RequestMethod.GET)
	@ResponseBody
	public void bsc(HttpServletRequest request, HttpServletResponse response) {
		int bsId = Integer.parseInt(request.getParameter("bsId"));
		String fsuId = null;
		try {
			List<Map<String, Object>> list = BsStatusService.bsc(bsId);

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("totals", list.size());
			result.put("items", list);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 基站下的bsr状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsr", method = RequestMethod.GET)
	@ResponseBody
	public void bsr(HttpServletRequest request, HttpServletResponse response) {
		int bsId = Integer.parseInt(request.getParameter("bsId"));
		String fsuId = null;
		try {
			List<Map<String, Object>> list = BsStatusService.bsr(bsId);

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("totals", list.size());
			result.put("items", list);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 基站下的dpx状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/dpx", method = RequestMethod.GET)
	@ResponseBody
	public void dpx(HttpServletRequest request, HttpServletResponse response) {
		int bsId = Integer.parseInt(request.getParameter("bsId"));
		try {
			List<Map<String, Object>> list = BsStatusService.dpx(bsId);

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("totals", list.size());
			result.put("items", list);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 基站下的psm状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/psm", method = RequestMethod.GET)
	@ResponseBody
	public void psm(HttpServletRequest request, HttpServletResponse response) {
		int bsId = Integer.parseInt(request.getParameter("bsId"));
		try {
			List<Map<String, Object>> list = BsStatusService.psm(bsId);

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("totals", list.size());
			result.put("items", list);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	/**
	 * 基站map实时监测
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsMapCount", method = RequestMethod.GET)
	@ResponseBody
	public void bsMapCount(HttpServletRequest request, HttpServletResponse response) {
		int count1=BsStatusService.MapBsOfflineCount();
		/*int count2=BsStatusService.MapDispatchAlarmCount();*/
		int count3=ServerStatusService.unusualStatus(0).size();
		
		int count4=SqlServerService.MapEmhAlarmCount()+BsStatusService.fourEmhAlarmListCount();
		try {

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("bsOffline", count1);
			/*result.put("dispatchOffline", count2);*/
			result.put("mscOffline", count3);
			result.put("emhAlarm", count4);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
