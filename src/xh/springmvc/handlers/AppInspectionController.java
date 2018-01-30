package xh.springmvc.handlers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AppInspectionServer;
import xh.mybatis.service.BsStatusService;

@Controller
@RequestMapping("/app")
public class AppInspectionController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(AppInspectionController.class);
	private WebLogBean webLogBean=new WebLogBean();
	private FlexJSON json = new FlexJSON();
	
	/*<!--查询800M移动基站巡检表-->*/
	@RequestMapping(value="/mbsinfo",method = RequestMethod.GET)
	public void mbsinfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=FunUtil.StringToInt(request.getParameter("start"));
		int limit=FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",AppInspectionServer.mbsinfoCount());
		result.put("items", AppInspectionServer.mbsinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*<!--自建基站巡检表-->*/
	@RequestMapping(value="/sbsinfo",method = RequestMethod.GET)
	public void sbsinfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=FunUtil.StringToInt(request.getParameter("start"));
		int limit=FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",AppInspectionServer.sbsinfoCount());
		result.put("items", AppInspectionServer.sbsinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*<!--网管巡检表-->*/
	@RequestMapping(value="/netinfo",method = RequestMethod.GET)
	public void netinfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=FunUtil.StringToInt(request.getParameter("start"));
		int limit=FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",AppInspectionServer.netinfoCount());
		result.put("items", AppInspectionServer.netinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*<!--调度台巡检表-->*/
	@RequestMapping(value="/dispatchinfo",method = RequestMethod.GET)
	public void dispatchinfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=FunUtil.StringToInt(request.getParameter("start"));
		int limit=FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",AppInspectionServer.dispatchinfoCount());
		result.put("items", AppInspectionServer.dispatchinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//基站故障记录登记表
	@RequestMapping(value = "/excel_mbs", method = RequestMethod.POST)
	public void app_mbs(HttpServletRequest request,
			HttpServletResponse response) {
		String excelData=request.getParameter("excelData");
		
		Map<String,String> map=GsonUtil.json2Object(excelData, Map.class);
		
		/*log.info("map-->"+map);*/
		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/移动基站巡检表.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 16, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
					Colour.BLACK);
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
					11, WritableFont.NO_BOLD, false,
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
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("移动基站巡检表", 0);
			CellView cellView = new CellView();  
			cellView.setAutosize(true); //设置自动大小 
			
			//第1行
			Label title = new Label(0, 0, "800M移动基站巡检作业表", fontFormat);
			sheet.mergeCells(0,0,7,0);
			sheet.addCell(title);
			 sheet.setRowView(0, 600, false); //设置行高
			
			//第2行
			Label label_1_0 = new Label(0, 1, "基站名称", fontFormat_Content);// 创建单元格
			Label label_1_1 = new Label(1, 1, map.get("bsname"), fontFormat_Content);// 创建单元格
			sheet.mergeCells(1,1,3,1);
			Label label_1_2 = new Label(4, 1, "基站ID", fontFormat_Content);// 创建单元格
			Label label_1_3 = new Label(5, 1, map.get("bsid"), fontFormat_Content);// 创建单元格
			Label label_1_4 = new Label(6, 1, "基站级别", fontFormat_Content);// 创建单元格
			Label label_1_5 = new Label(7, 1, map.get("bslevel"), fontFormat_Content);// 创建单元格
			
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 10);
			sheet.setColumnView(5, 40);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 30);
			
			
			sheet.addCell(label_1_0);
			sheet.addCell(label_1_1);
			sheet.addCell(label_1_2);
			sheet.addCell(label_1_3);
			sheet.addCell(label_1_4);	
			sheet.addCell(label_1_5);
			
			
			
			//第3行
			Label label_2_0 = new Label(0, 2, "机房类型", fontFormat_Content);// 创建单元格
			Label label_2_1 = new Label(1, 2, map.get("bstype"), fontFormat_Content);// 创建单元格
			Label label_2_2 = new Label(2, 2, "巡检时间", fontFormat_Content);// 创建单元格
			Label label_2_3 = new Label(3, 2, map.get("commitdate"), fontFormat_Content);// 创建单元格
			sheet.mergeCells(3,2,5,2);
			Label label_2_4 = new Label(6, 2, "巡检人", fontFormat_Content);// 创建单元格
			Label label_2_5 = new Label(7, 2, map.get("checkman"), fontFormat_Content);// 创建单元格
			sheet.addCell(label_2_0);
			sheet.addCell(label_2_1);
			sheet.addCell(label_2_2);
			sheet.addCell(label_2_3);
			sheet.addCell(label_2_4);
			sheet.addCell(label_2_5);
			
			//第4行
			Label label_3_0 = new Label(0, 3, "巡检项目", fontFormat_Content);// 创建单元格
			Label label_3_1 = new Label(1, 3, "具体项目", fontFormat_Content);// 创建单元格
			Label label_3_2 = new Label(2, 3, "具体操作", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,3,5,3);
			Label label_3_3 = new Label(6, 3, "执行情况", fontFormat_Content);// 创建单元格
			Label label_3_4 = new Label(7, 3, "备注", fontFormat_Content);// 创建单元格
			sheet.addCell(label_3_0);
			sheet.addCell(label_3_1);
			sheet.addCell(label_3_2);
			sheet.addCell(label_3_3);
			sheet.addCell(label_3_4);
			
			//第4-5行
			Label label_4_0 = new Label(0, 4, "天面", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,4,0,5);
			Label label_4_1 = new Label(1, 4, "防倾斜", fontFormat_Content);// 创建单元格
			Label label_4_2 = new Label(2, 4, "观测铁塔、抱杆、天线是否有明显倾斜", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,4,5,4);
			
			Label label_4_3 = new Label(6, 4, map.get("d1"), fontFormat_Content);// 创建单元格
			Label label_4_4 = new Label(7, 4, map.get("c1"), fontFormat_Content);// 创建单元格
			sheet.addCell(label_4_0);
			sheet.addCell(label_4_1);
			sheet.addCell(label_4_2);
			sheet.addCell(label_4_3);
			sheet.addCell(label_4_4);
			

			Label label_5_1 = new Label(1, 5, "防雷情况", fontFormat_Content);// 创建单元格
			Label label_5_2 = new Label(2, 5, "避雷针是否正常，接地线焊点是否锈蚀，天线支架、走线架是否接地，馈线是否三点接地", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,5,5,5);
			
			Label label_5_3 = new Label(6, 5, map.get("d2"), fontFormat_Content);// 创建单元格
			Label label_5_4 = new Label(7, 5, map.get("c2"), fontFormat_Content);// 创建单元格
			
			sheet.addCell(label_5_1);
			sheet.addCell(label_5_2);
			sheet.addCell(label_5_3);
			sheet.addCell(label_5_4);
			
			//第6行
			Label label_6_0 = new Label(0, 6, "防雷情况", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,6,0,12);
			Label label_6_1 = new Label(1, 6, "机房安全", fontFormat_Content);// 创建单元格
			sheet.mergeCells(1,6,1,9);
			Label label_6_2 = new Label(2, 6, "灭火设备是否完好", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,6,5,6);
			
			Label label_6_3 = new Label(6, 6, map.get("d3"), fontFormat_Content);// 创建单元格
			Label label_6_4 = new Label(7, 6, map.get("c3"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_6_0);
			sheet.addCell(label_6_1);
			sheet.addCell(label_6_2);
			sheet.addCell(label_6_3);
			sheet.addCell(label_6_4);	
			//第7行
			Label label_7_2 = new Label(2, 7, "门、窗、锁是否完好", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,7,5,7);			
			Label label_7_3 = new Label(6, 7, map.get("d4"), fontFormat_Content);// 创建单元格
			Label label_7_4 = new Label(7, 7, map.get("c4"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_7_2);
			sheet.addCell(label_7_3);
			sheet.addCell(label_7_4);
			//第8行
			Label label_8_2 = new Label(2, 8, "门窗、馈线孔等缝隙密封是否严实", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,8,5,8);			
			Label label_8_3 = new Label(6, 8, map.get("d5"), fontFormat_Content);// 创建单元格
			Label label_8_4 = new Label(7, 8, map.get("c5"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_8_2);
			sheet.addCell(label_8_3);
			sheet.addCell(label_8_4);
			//第9行
			Label label_9_2 = new Label(2, 9, "机房走线架及室内、室外铜牌安装是否规范", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,9,5,9);			
			Label label_9_3 = new Label(6, 9, map.get("d6"), fontFormat_Content);// 创建单元格
			Label label_9_4 = new Label(7, 9, map.get("c6"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_9_2);
			sheet.addCell(label_9_3);
			sheet.addCell(label_9_4);			
			//第10行
			
			Label label_10_1 = new Label(1, 10, "机房温度", fontFormat_Content);// 创建单元格
			Label label_10_2 = new Label(2, 10, "空调制冷效果、温度设置及运行状态是否正常", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,10,5,10);
			
			Label label_10_3 = new Label(6, 10, map.get("d7"), fontFormat_Content);// 创建单元格
			Label label_10_4 = new Label(7, 10, map.get("c7"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_10_1);
			sheet.addCell(label_10_2);
			sheet.addCell(label_10_3);
			sheet.addCell(label_10_4);			
			//第11行
			Label label_11_1 = new Label(1, 11, "积水情况", fontFormat_Content);// 创建单元格
			sheet.mergeCells(1,11,1,12);
			Label label_11_2 = new Label(2, 11, "灭火设备是否完好", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,11,5,11);
			
			Label label_11_3 = new Label(6, 11, map.get("d8"), fontFormat_Content);// 创建单元格
			Label label_11_4 = new Label(7, 11, map.get("c8"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_11_1);
			sheet.addCell(label_11_2);
			sheet.addCell(label_11_3);
			sheet.addCell(label_11_4);	
			//第12行
			Label label_12_2 = new Label(2, 12, "机房有无裂缝、渗水、漏水等情况", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,12,5,12);
			
			Label label_12_3 = new Label(6, 12, map.get("d9"), fontFormat_Content);// 创建单元格
			Label label_12_4 = new Label(7, 12, map.get("c9"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_12_2);
			sheet.addCell(label_12_3);
			sheet.addCell(label_12_4);	
			//第13行
			Label label_13_0 = new Label(0, 13, "电源配套", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,13,0,18);
			Label label_13_1 = new Label(1, 13, "逆变器", fontFormat_Content);// 创建单元格
			/*sheet.mergeCells(1,13,1,9);*/
			Label label_13_2 = new Label(2, 13, "逆变器运行是否正常，有无挂死隐患", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,13,5,13);
			
			Label label_13_3 = new Label(6, 13, map.get("d10"), fontFormat_Content);// 创建单元格
			Label label_13_4 = new Label(7, 13, map.get("c10"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_13_0);
			sheet.addCell(label_13_1);
			sheet.addCell(label_13_2);
			sheet.addCell(label_13_3);
			sheet.addCell(label_13_4);			
			//第14行
			Label label_14_1 = new Label(1, 14, "电源线", fontFormat_Content);// 创建单元格
			/*sheet.mergeCells(1,14,1,9);*/
			Label label_14_2 = new Label(2, 14, "电源线是否老化，连接是否正常，有无短路隐患", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,14,5,14);
			
			Label label_14_3 = new Label(6, 14, map.get("d11"), fontFormat_Content);// 创建单元格
			Label label_14_4 = new Label(7, 14, map.get("c11"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_14_1);
			sheet.addCell(label_14_2);
			sheet.addCell(label_14_3);
			sheet.addCell(label_14_4);			
			//第15行
			Label label_15_1 = new Label(1, 15, "配电箱", fontFormat_Content);// 创建单元格
			sheet.mergeCells(1,15,1,16);
			Label label_15_2 = new Label(2, 15, "直流配电柜运行是否正常，空开有无跳闸隐患，电压是否稳定", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,15,5,15);
			
			Label label_15_3 = new Label(6, 15, map.get("d12"), fontFormat_Content);// 创建单元格
			Label label_15_4 = new Label(7, 15, map.get("c12"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_15_1);
			sheet.addCell(label_15_2);
			sheet.addCell(label_15_3);
			sheet.addCell(label_15_4);			
			//第16行

			Label label_16_2 = new Label(2, 16, "交流配电柜运行是否正常，空开有无跳闸隐患，电压是否稳定", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,16,5,16);
			
			Label label_16_3 = new Label(6, 16, map.get("d13"), fontFormat_Content);// 创建单元格
			Label label_16_4 = new Label(7, 16, map.get("c13"), fontFormat_Content);// 创建单元格		

			sheet.addCell(label_16_2);
			sheet.addCell(label_16_3);
			sheet.addCell(label_16_4);			
			//第17行
			Label label_17_1 = new Label(1, 17, "照明", fontFormat_Content);// 创建单元格
			Label label_17_2 = new Label(2, 17, "机房照明设施是否完好", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,17,5,17);
			
			Label label_17_3 = new Label(6, 17, map.get("d14"), fontFormat_Content);// 创建单元格
			Label label_17_4 = new Label(7, 17, map.get("c14"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_17_1);
			sheet.addCell(label_17_2);
			sheet.addCell(label_17_3);
			sheet.addCell(label_17_4);							
			//第18行
			Label label_18_1 = new Label(1, 18, "插座", fontFormat_Content);// 创建单元格
			Label label_18_2 = new Label(2, 18, "机房插座是否有电", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,18,5,18);
			
			Label label_18_3 = new Label(6, 18, map.get("d15"), fontFormat_Content);// 创建单元格
			Label label_18_4 = new Label(7, 18, map.get("c15"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_18_1);
			sheet.addCell(label_18_2);
			sheet.addCell(label_18_3);
			sheet.addCell(label_18_4);						
			//第19行
			Label label_19_0 = new Label(0, 19, "主体设备", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,19,0,23);
			Label label_19_1 = new Label(1, 19, "单板状态", fontFormat_Content);// 创建单元格
			/*sheet.mergeCells(1,19,1,9);*/
			Label label_19_2 = new Label(2, 19, "检查各单板运行状态、供电情况是否正常，设备是否有明显告警", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,19,5,19);
			
			Label label_19_3 = new Label(6, 19, map.get("d16"), fontFormat_Content);// 创建单元格
			Label label_19_4 = new Label(7, 19, map.get("c16"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_19_0);
			sheet.addCell(label_19_1);
			sheet.addCell(label_19_2);
			sheet.addCell(label_19_3);
			sheet.addCell(label_19_4);				 
			//第20行
			Label label_20_1 = new Label(1, 20, "告警确认", fontFormat_Content);// 创建单元格
			Label label_20_2 = new Label(2, 20, "通过与监控后台联系确认设备有无告警", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,20,5,20);
			
			Label label_20_3 = new Label(6, 20, map.get("d17"), fontFormat_Content);// 创建单元格
			Label label_20_4 = new Label(7, 20, map.get("c17"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_20_1);
			sheet.addCell(label_20_2);
			sheet.addCell(label_20_3);
			sheet.addCell(label_20_4);	
			//第21行
			Label label_21_1 = new Label(1, 21, "连线状态", fontFormat_Content);// 创建单元格
			Label label_21_2 = new Label(2, 21, "电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,21,5,21);
			
			Label label_21_3 = new Label(6, 21, map.get("d18"), fontFormat_Content);// 创建单元格
			Label label_21_4 = new Label(7, 21, map.get("c18"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_21_1);
			sheet.addCell(label_21_2);
			sheet.addCell(label_21_3);
			sheet.addCell(label_21_4);			
			//第22行
			Label label_22_1 = new Label(1, 22, "设备加固", fontFormat_Content);// 创建单元格
			Label label_22_2 = new Label(2, 22, "设备安装是否牢固", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,22,5,22);
			
			Label label_22_3 = new Label(6, 22, map.get("d19"), fontFormat_Content);// 创建单元格
			Label label_22_4 = new Label(7, 22, map.get("c19"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_22_1);
			sheet.addCell(label_22_2);
			sheet.addCell(label_22_3);
			sheet.addCell(label_22_4);	
			//第23行
			Label label_23_1 = new Label(1, 23, "设备温度", fontFormat_Content);// 创建单元格
			Label label_23_2 = new Label(2, 23, "设备是否有高温、异味", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,23,5,23);
			
			Label label_23_3 = new Label(6, 23, map.get("d20"), fontFormat_Content);// 创建单元格
			Label label_23_4 = new Label(7, 23, map.get("c20"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_23_1);
			sheet.addCell(label_23_2);
			sheet.addCell(label_23_3);
			sheet.addCell(label_23_4);
			//第24行
			Label label_24_0 = new Label(0, 24, "动环监控", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,24,0,26);
			Label label_24_1 = new Label(1, 24, "环境监测", fontFormat_Content);// 创建单元格
			Label label_24_2 = new Label(2, 24, "查看温湿度、漏水是否正常(需值班人员配合完成)", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,24,5,24);
			
			Label label_24_3 = new Label(6, 24, map.get("d21"), fontFormat_Content);// 创建单元格
			Label label_24_4 = new Label(7, 24, map.get("c21"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_24_0);
			sheet.addCell(label_24_1);
			sheet.addCell(label_24_2);
			sheet.addCell(label_24_3);
			sheet.addCell(label_24_4);	
			//第25行
			Label label_25_1 = new Label(1, 25, "安全监测", fontFormat_Content);// 创建单元格
			Label label_25_2 = new Label(2, 25, "查看门磁、烟感、摄像头是否正常(需值班人员配合完成)", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,25,5,25);
			
			Label label_25_3 = new Label(6, 25, map.get("d22"), fontFormat_Content);// 创建单元格
			Label label_25_4 = new Label(7, 25, map.get("c22"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_25_1);
			sheet.addCell(label_25_2);
			sheet.addCell(label_25_3);
			sheet.addCell(label_25_4);
			//第26行
			Label label_26_1 = new Label(1, 26, "交直流电流电压", fontFormat_Content);// 创建单元格
			Label label_26_2 = new Label(2, 26, "查看交直流是否正常(需值班人员配合完成)", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,26,5,26);
			
			Label label_26_3 = new Label(6, 26, map.get("d23"), fontFormat_Content);// 创建单元格
			Label label_26_4 = new Label(7, 26, map.get("c23"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_26_1);
			sheet.addCell(label_26_2);
			sheet.addCell(label_26_3);
			sheet.addCell(label_26_4);
			//第27行
			Label label_27_0 = new Label(0, 27, "清洁", fontFormat_Content);// 创建单元格
			sheet.mergeCells(0,27,0,28);
			Label label_27_1 = new Label(1, 27, "机房清洁", fontFormat_Content);// 创建单元格
			Label label_27_2 = new Label(2, 27, "清洁机房内门窗、地面、墙面卫生", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,27,5,27);
			
			Label label_27_3 = new Label(6, 27, map.get("d24"), fontFormat_Content);// 创建单元格
			Label label_27_4 = new Label(7, 27, map.get("c24"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_27_0);
			sheet.addCell(label_27_1);
			sheet.addCell(label_27_2);
			sheet.addCell(label_27_3);
			sheet.addCell(label_27_4);	
			//第28行
			Label label_28_1 = new Label(1, 28, "设备除尘", fontFormat_Content);// 创建单元格
			Label label_28_2 = new Label(2, 28, "清洁设备表面、内部、主体设备滤网", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,28,5,28);
			
			Label label_28_3 = new Label(6, 28, map.get("d25"), fontFormat_Content);// 创建单元格
			Label label_28_4 = new Label(7, 28, map.get("c25"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_28_1);
			sheet.addCell(label_28_2);
			sheet.addCell(label_28_3);
			sheet.addCell(label_28_4);
			//第29行
			Label label_29_0 = new Label(0, 29, "功能", fontFormat_Content);// 创建单元格
			Label label_29_1 = new Label(1, 29, "呼叫功能", fontFormat_Content);// 创建单元格
			Label label_29_2 = new Label(2, 29, "沿途呼叫测试", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2,29,5,29);
			
			Label label_29_3 = new Label(6, 29, map.get("d26"), fontFormat_Content);// 创建单元格
			Label label_29_4 = new Label(7, 29, map.get("c26"), fontFormat_Content);// 创建单元格		
			sheet.addCell(label_29_0);
			sheet.addCell(label_29_1);
			sheet.addCell(label_29_2);
			sheet.addCell(label_29_3);
			sheet.addCell(label_29_4);	
			//第30行
			Label label_30_0 = new Label(0, 30, "遗留问题", fontFormat_Content);// 创建单元格
			Label label_30_1 = new Label(1, 30, map.get("remainwork"), fontFormat_Content);// 创建单元格
			sheet.mergeCells(1,30,7,30);
			sheet.addCell(label_30_0);
			sheet.addCell(label_30_1);

			book.write();
			book.close();
			log.info("移动基站巡检表");
			//DownExcelFile(response, pathname);
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
	
	 public void DownExcelFile(HttpServletResponse response,String filePath)
				throws Exception {
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
}
