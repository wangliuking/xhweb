package xh.springmvc.handlers;

import java.io.File;
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
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.ContactsBean;
import xh.mybatis.bean.OfficeAddressBean;
import xh.mybatis.bean.OperationsBusBean;
import xh.mybatis.bean.OperationsInstrumentBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.ChartService;
import xh.mybatis.service.ContactsService;
import xh.mybatis.service.OfficeAddressService;

@Controller
@RequestMapping(value="/report/month")
public class ReportMonthController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ReportMonthController.class);
	private FlexJSON json=new FlexJSON();
	
	
	@RequestMapping(value = "/excel_month_bs", method = RequestMethod.GET)
	public void excel_report(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/");
			String pathname = saveDir + "/定期维护报告-基站月维护-"+time+".xls";
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

			WritableSheet sheet0 = book.createSheet("日常维护-基站", 0);
			
			excel_bsStatus(map,sheet0,fontFormat,fontFormat_h,fontFormat_Content);
			
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
			sheet.addCell(new Label(2, 0, "设备", fontFormat_h));
			sheet.addCell(new Label(3, 0, "ICP状态", fontFormat_h));
			sheet.addCell(new Label(4, 0, "BSR状态", fontFormat_h));
			sheet.addCell(new Label(5, 0, "基站时钟运行状态", fontFormat_h));
			sheet.addCell(new Label(6, 0, "双工器回波损耗", fontFormat_h));
			sheet.addCell(new Label(7, 0, "主控信道底噪查询", fontFormat_h));
			sheet.addCell(new Label(8, 0, "备注", fontFormat_h));
			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 50);
			sheet.setColumnView(7, 50);
			sheet.setColumnView(8, 20);

			
			List<BsStatusBean> list = BsStatusService.excelToBsStatus();
			
			for (int i = 0; i < list.size(); i++) {
			
				BsStatusBean bean = (BsStatusBean) list.get(i);
				
			/*	System.out.println("bsr1="+bean.getBsr_state1()+";bsr2="+bean.getBsr_state2()
						+";bsr3="+bean.getBsr_state3()+";bsr4="+bean.getBsr_state4());*/
				
				String bsrstatus=bsr_status(bean.getBsr_state1(),bean.getBsr_state2(),bean.getBsr_state3(),bean.getBsr_state4());
				sheet.addCell(new jxl.write.Number(0, i + 1,Double.parseDouble(String.valueOf(bean.getPeriod())),wcfN));	
				sheet.addCell(new jxl.write.Number(1, i + 1,Double.parseDouble(String.valueOf(bean.getBsId())),wcfN));				
				sheet.addCell(new Label(2, i + 1, String.valueOf(bean.getName()), fontFormat_Content));
				sheet.addCell(new Label(3, i + 1, bs_icp(bean.getIcp_status()), fontFormat_Content));
				sheet.addCell(new Label(4, i + 1, bsrstatus, fontFormat_Content));
				sheet.addCell(new Label(5, i + 1, String.valueOf(bean.getClock_status()), fontFormat_Content));
				sheet.addCell(new Label(6, i + 1, dpx_format(bean.getDpx_retLoss1(),bean.getDpx_retLoss2(),
						bean.getDpx_retLoss3(), bean.getDpx_retLoss4()), fontFormat_Content));
				sheet.addCell(new Label(7, i + 1, master_ch(bean.getCarrierLowNoiseRXRssi1(),
						bean.getCarrierLowNoiseRXRssi2(),bean.getCarrierLowNoiseRXRssi3(),
						bean.getCarrierLowNoiseRXRssi4()), fontFormat_Content));
				
				sheet.addCell(new Label(8, i + 1, bsr_status(bean.getBsr_state1()), fontFormat_Content));
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		
		
	}
	@RequestMapping(value = "/excel_month_inspection", method = RequestMethod.GET)
	public void excel_month_inspection(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/");
			String pathname = saveDir + "/成都市应急指挥调度无线通信网巡检记录汇总表-"+time+".xls";
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

			WritableSheet sheet0 = book.createSheet("巡检记录汇总表", 0);
			
			excel_inspection(time,sheet0,fontFormat,fontFormat_h,fontFormat_Content);
			
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
	public  void excel_inspection(String time,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0, 0, "成都市应急指挥调度无线通信网巡检记录汇总表", fontFormat_h));
			sheet.mergeCells(0, 0, 7, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "期数", fontFormat_h));
			sheet.addCell(new Label(3, 1, "应巡检次数", fontFormat_h));
			sheet.addCell(new Label(4, 1, "巡检时间", fontFormat_h));
			sheet.addCell(new Label(5, 1, "巡检人员", fontFormat_h));
			sheet.addCell(new Label(6, 1, "备注", fontFormat_h));
			sheet.addCell(new Label(7, 1, "是否完成", fontFormat_h));
			sheet.setRowView(0, 600);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 50);
			sheet.setColumnView(7, 20);

			
			List<Map<String,Object>> list=ChartService.excel_month_inspection(time);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map=list.get(i);
				
				sheet.addCell(new jxl.write.Number(0, i + 2,Integer.parseInt(map.get("bsid").toString()),fontFormat_Content));				
				sheet.addCell(new Label(1, i + 2, map.get("bsname").toString(), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(2, i + 2,Integer.parseInt(map.get("period").toString()),fontFormat_Content));
				sheet.addCell(new jxl.write.Number(3, i + 2,Integer.parseInt(map.get("checkTimes").toString()),fontFormat_Content));
				sheet.addCell(new Label(4, i + 2, map.get("time").toString(), fontFormat_Content));
				sheet.addCell(new Label(5, i + 2, map.get("checkman").toString(), fontFormat_Content));
				sheet.addCell(new Label(6, i + 2, map.get("remainwork").toString(), fontFormat_Content));
				sheet.addCell(new Label(7, i + 2, map.get("complate").toString(), fontFormat_Content));
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		
		
	}
	
	@RequestMapping(value = "/config_excel", method = RequestMethod.POST)
	public void config_excel(HttpServletRequest request, HttpServletResponse response) {

		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/upload");
			String pathname = saveDir + "/运维资源配置"+FunUtil.nowDateNotTime()+".xls";
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
			fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 14,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			//fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(true);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					13, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体



			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);

			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			WritableSheet sheet = book.createSheet("办公场所&运维车辆", 0);

			// 第1行
			
			
			sheet.addCell(new Label(0, 0, "运维资源配置表", fontFormat));
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(new Label(0, 1, "办公地点", fontFormat));
			sheet.mergeCells(0, 1, 4, 1);
			sheet.setRowView(0, 600, false); // 设置行高

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 50);
			sheet.setColumnView(3, 40);
			sheet.setColumnView(4, 40);
			
		

			//办公场所
			sheet.addCell(new Label(0, 2, "序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "办公场所", fontFormat_h));
			sheet.addCell(new Label(2, 2, "地址", fontFormat_h));
			sheet.addCell(new Label(3, 2, "面积( m2 )", fontFormat_h));
			sheet.addCell(new Label(4, 2, "备注", fontFormat_h));
			List<OfficeAddressBean> list=OfficeAddressService.office_list();
			for(int i=0;i<list.size();i++){
				OfficeAddressBean bean=list.get(i);
				sheet.addCell(new jxl.write.Number(0, i+3, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i+3, bean.getOffice(), fontFormat_Content));
				sheet.addCell(new Label(2, i+3, bean.getAddress(), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(3, i+3, bean.getArea(), fontFormat_Content));
				
				sheet.addCell(new Label(4, i+3, bean.getRemark(), fontFormat_Content));
			}
			//车辆
			int pos=list.size()+4;
			sheet.addCell(new Label(0, pos, "运维车辆", fontFormat));
			sheet.mergeCells(0, pos, 3, pos);
			sheet.addCell(new Label(0, pos+1, "序号", fontFormat_h));
			sheet.addCell(new Label(1, pos+1, "车型", fontFormat_h));
			sheet.addCell(new Label(2, pos+1, "车牌号", fontFormat_h));
			sheet.addCell(new Label(3, pos+1, "备注", fontFormat_h));
			List<OperationsBusBean> list2=OfficeAddressService.bus_list();
			for(int i=0;i<list2.size();i++){
				OperationsBusBean bean=list2.get(i);
				sheet.addCell(new jxl.write.Number(0, pos+i+2, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, pos+i+2, bean.getType(), fontFormat_Content));
				sheet.addCell(new Label(2, pos+i+2, bean.getNumber(), fontFormat_Content));				
				sheet.addCell(new Label(3, pos+i+2, bean.getRemark(), fontFormat_Content));
			}
			
			//仪器仪表
			pos=list.size()+5+list2.size();
			sheet.addCell(new Label(0, pos, "仪器仪表", fontFormat));
			sheet.mergeCells(0, pos, 6, pos);
			sheet.addCell(new Label(0, pos+1, "序号", fontFormat_h));
			sheet.addCell(new Label(1, pos+1, "名称", fontFormat_h));
			sheet.addCell(new Label(2, pos+1, "型号", fontFormat_h));
			sheet.addCell(new Label(3, pos+1, "S/N", fontFormat_h));
			sheet.addCell(new Label(4, pos+1, "数量", fontFormat_h));
			sheet.addCell(new Label(5, pos+1, "单位", fontFormat_h));
			sheet.addCell(new Label(6, pos+1, "备注", fontFormat_h));
			List<OperationsInstrumentBean> list3=OfficeAddressService.instrument_list();
			for(int i=0;i<list3.size();i++){
				OperationsInstrumentBean bean=list3.get(i);
				sheet.addCell(new jxl.write.Number(0, pos+i+2, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, pos+i+2, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, pos+i+2, bean.getModel(), fontFormat_Content));				
				sheet.addCell(new Label(3, pos+i+2, bean.getSn(), fontFormat_Content));
				sheet.addCell(new jxl.write.Number(4, pos+i+2, bean.getNumber(), fontFormat_Content));
				sheet.addCell(new Label(5, pos+i+2, "台", fontFormat_Content));
				sheet.addCell(new Label(6, pos+i+2, bean.getRemark(), fontFormat_Content));
			}
			/*for(int i=1;i<pos+list3.size();i++){
				sheet.setRowView(i, 400, false); // 设置行高
			}*/

			
			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			this.success = true;
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
 	public String bs_icp(String str){
		if(str==null || str==""){
			return "";
		}else if(str.equals("0") || str.equals("12")){
			return "OK";
		}else{
			return "NO";
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
	public String bsr_status(String str1 ,String str2,String str3,String str4){
		boolean a=true;
		int b=0;
		if(str1!=null){
			a=a && str1.equals("0");
			b=1;
		}
		if(str2!=null){
			a=a && str2.equals("0");
			b=1;
		}
		if(str3!=null){
			a=a && str3.equals("0");
			b=1;
		}
		if(str4!=null){
			a=a && str4.equals("0");
			b=1;
		}
			
		if(b==1){
			if(a){
				return "OK";
			}else{
				return "NO";
			}
		}else{
			return "";
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
			str.append("RX4=");
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
