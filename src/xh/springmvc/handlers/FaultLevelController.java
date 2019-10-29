package xh.springmvc.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.CaptchaUtil;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.FaultFourBean;
import xh.mybatis.bean.FaultOneBean;
import xh.mybatis.bean.FaultThreeBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.FaultLevelService;
import xh.mybatis.service.FaultService;
import xh.mybatis.service.UserNeedService;

@Controller
public class FaultLevelController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	protected final Log log=LogFactory.getLog(FaultLevelController.class);
	@RequestMapping(value = "/faultlevel/one_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap one_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		int type=Integer.parseInt(request.getParameter("type"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		map.put("type",type);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.one_list(map));
		result.put("totals",FaultLevelService.one_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/three_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap three_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		int type = funUtil.StringToInt(request.getParameter("type"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		map.put("type",type);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.three_list(map));
		result.put("totals",FaultLevelService.three_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap four_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.four_list(map));
		result.put("totals",FaultLevelService.four_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/one_add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap one_add(@RequestBody FaultOneBean bean){
		int rs=FaultLevelService.one_add(bean);	
		if(rs>0){
			this.success=true;
			this.message="添加成功";
		}else{
			this.success=false;
			this.message="添加失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap four_add(@RequestBody FaultFourBean bean){
		int rs=FaultLevelService.four_add(bean);
		if(rs>0){
			this.success=true;
			this.message="添加成功";
		}else{
			this.success=false;
			this.message="添加失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/one_update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap one_update(@RequestBody FaultOneBean bean){
		int rs=FaultLevelService.one_update(bean);
		if(rs>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/three_update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap three_update(@RequestBody FaultThreeBean bean){
		int rs=FaultLevelService.three_update(bean);
		if(rs>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap four_update(@RequestBody FaultFourBean bean){
		int rs=FaultLevelService.four_update(bean);
		if(rs>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value="/faultlevel/one_del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap one_del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=FaultLevelService.one_del(list);
		if(rs>0){
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/faultlevel/three_del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap three_del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=FaultLevelService.three_del(list);
		if(rs>0){
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/faultlevel/four_del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap four_del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=FaultLevelService.four_del(list);
		if(rs>0){
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value = "/faultlevel/excel_fault_three", method = RequestMethod.GET)
	public void excel_fault_three(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		int type = funUtil.StringToInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("type",type);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/dayreport");
			String pathname = saveDir + "/故障记录表"+time+".xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);			
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD,
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
			WritableFont font_header = new WritableFont(WritableFont.createFont("宋体"), 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(true);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.createFont("宋体"),
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
			WritableSheet sheet5 = book.createSheet("故障记录表"+time.substring(0, time.lastIndexOf("-")), 0);
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
	public void excel_bs_fault(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content){
		String time=map.get("time").toString();
		try {
			
		
		// 设置数字格式
					jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
					jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

					/*Label label_h1 = new Label(0, 0, "基站信息", fontFormat_h);// 创建单元格
					Label label_h2 = new Label(5, 0, "故障情况", fontFormat_h);// 创建单元格
*/					
					sheet.addCell( new Label(17, 0, "派单情况", fontFormat_h));// 创建单元格
					sheet.mergeCells(17, 0, 19, 0);
					/*sheet.mergeCells(0, 0, 16, 1);
					sheet.mergeCells(20, 0, 24, 1);*/
					Label label_1 = new Label(0, 0, "建设期", fontFormat_h);// 创建单元格
					sheet.mergeCells(0, 0, 0, 1);
					Label label_3 = new Label(1, 0, "基站编号", fontFormat_h);// 创建单元格
					sheet.mergeCells(1, 0, 1, 1);
					Label label_4= new Label(2, 0, "基站名称", fontFormat_h);
					sheet.mergeCells(2, 0, 2, 1);
					Label label_5 = new Label(3, 0, "基站分级", fontFormat_h);
					sheet.mergeCells(3, 0, 3, 1);
					Label label_6 = new Label(4, 0, "故障类别", fontFormat_h);
					sheet.mergeCells(4, 0, 4, 1);
					Label label_8 = new Label(5, 0, "故障发生时间", fontFormat_h);
					sheet.mergeCells(5, 0, 5, 1);
					Label label_9 = new Label(6, 0, "故障原因", fontFormat_h);
					sheet.mergeCells(6, 0, 6, 1);
					Label label_10 = new Label(7, 0, "故障分析", fontFormat_h);
					sheet.mergeCells(7, 0, 7, 1);
					Label label_11= new Label(8, 0, "故障恢复时间", fontFormat_h);
					sheet.mergeCells(8, 0, 8, 1);
					Label label_12 = new Label(9, 0, "故障历时",fontFormat_h);
					sheet.mergeCells(9, 0, 9, 1);
					Label label_13 = new Label(10, 0, "故障核减",fontFormat_h);
					sheet.mergeCells(10, 0, 10, 1);
					Label label_14 = new Label(11, 0, "登记人",fontFormat_h);
					sheet.mergeCells(11, 0, 11, 1);
					Label label_15 = new Label(12, 0, "是否自动恢复",fontFormat_h);
					sheet.mergeCells(12, 0, 12, 1);
					Label label_16 = new Label(13, 0, "处理人员",fontFormat_h);
					sheet.mergeCells(13, 0, 13, 1);
					Label label_17= new Label(14, 0, "操作详情",fontFormat_h);
					sheet.mergeCells(14, 0, 14, 1);
					Label label_18 = new Label(15, 0, "备注",fontFormat_h);
					sheet.mergeCells(15, 0, 15, 1);
					Label label_19 = new Label(16, 0, "责任方",fontFormat_h);
					sheet.mergeCells(16, 0, 16, 1);
					Label label_20 = new Label(17, 1, "维护",fontFormat_h);
					Label label_21 = new Label(18, 1, "移动",fontFormat_h);
					Label label_22 = new Label(19, 1, "电信",fontFormat_h);
					Label label_23 = new Label(20, 0, "派单时间",fontFormat_h);
					sheet.mergeCells(20, 0, 20, 1);
					Label label_24 = new Label(21, 0, "接单时间",fontFormat_h);
					sheet.mergeCells(21, 0, 21, 1);
					Label label_25 = new Label(22, 0, "回单时间",fontFormat_h);
					sheet.mergeCells(22, 0, 22, 1);
					Label label_26 = new Label(23, 0, "接单耗时",fontFormat_h);
					sheet.mergeCells(23, 0, 23, 1);
					Label label_27 = new Label(24, 0, "接单超时（分）",fontFormat_h);
					sheet.mergeCells(24, 0, 24, 1);
					Label label_28 = new Label(25, 0, "处理耗时",fontFormat_h);
					sheet.mergeCells(25, 0, 25, 1);
					Label label_29 = new Label(26, 0, "处理超时（分）",fontFormat_h);
					sheet.mergeCells(26, 0, 26, 1);
					
					
					CellView cellView = new CellView();  
					cellView.setAutosize(true); //设置自动大小    
					 

					sheet.setRowView(0, 300);
					sheet.setColumnView(0, 10); //建设期
					sheet.setColumnView(1, 10); //故障归属
					sheet.setColumnView(2, 20); //基站编号
					sheet.setColumnView(3, 10); //基站名称
					sheet.setColumnView(4, 10); //基站分级
					sheet.setColumnView(5, 24); //使用状态
					sheet.setColumnView(6, 24); //星期
					sheet.setColumnView(7, 40); //故障发生时间
					sheet.setColumnView(8, 24); //报障来源
					sheet.setColumnView(9, 10); //故障等级
					sheet.setColumnView(10, 10); //故障类别
					sheet.setColumnView(11, 10); //故障原因
					sheet.setColumnView(12, 10);//目前处理情况
					sheet.setColumnView(13, 10);//是否影响业务
					sheet.setColumnView(14, 30);     //故障处理结果
					sheet.setColumnView(15, 30);      //故障恢复时间
					sheet.setColumnView(16, 10);      //故障历时
					sheet.setColumnView(17, 10);     //备注
					sheet.setColumnView(18, 10);      //故障处理人员
					sheet.setColumnView(19, 10);      //故障记录人员
					sheet.setColumnView(20, 24);      //基站归属
					sheet.setColumnView(21, 24);  
					sheet.setColumnView(22, 24);  
					sheet.setColumnView(23, 10);  
					sheet.setColumnView(24, 10); 
					sheet.setColumnView(25, 10); 
					sheet.setColumnView(26, 10); 

					sheet.addCell(label_1);
					sheet.addCell(label_3);
					sheet.addCell(label_4);
					sheet.addCell(label_5);
					sheet.addCell(label_6);
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
					sheet.addCell(label_26);
					sheet.addCell(label_27);
					sheet.addCell(label_28);
					sheet.addCell(label_29);
					/*sheet.addCell(label_h1);
					sheet.addCell(label_h2);*/
					
					
					// ws.mergeCells(0, 0, 0, 1);//合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
			            //合： 第1列第1行  到 第13列第1行  
					/*sheet.mergeCells(0, 0, 3, 0); 
					sheet.mergeCells(5, 0, 14, 0);  */
					
					
					 List<FaultThreeBean> list = FaultLevelService.excel_three_list(map);
					for (int i = 0; i < list.size(); i++) {
						FaultThreeBean bean =list.get(i);
						Label value_1 = new Label(0, i + 2, bean.getPeriod().equals("3")?"三期":"四期", fontFormat_Content);
						Label value_2 = new Label(1, i + 2, bean.getBsId(), fontFormat_Content);
						Label value_3 = new Label(2, i + 2, bean.getName(), fontFormat_Content);
						Label value_4 = new Label(3, i + 2, bean.getLevel(),fontFormat_Content);
						Label value_5 = new Label(4, i + 2, bean.getFault_type(),fontFormat_Content);
						Label value_6 = new Label(5, i + 2, bean.getTime(),fontFormat_Content);		
						Label value_7 = new Label(6, i + 2, bean.getReason() ,fontFormat_Content);
						Label value_8 = new Label(7, i + 2,bean.getNowDeal() ,fontFormat_Content);
						Label value_9 = new Label(8, i + 2,bean.getFaultRecoveryTime(),fontFormat_Content);
						Label value_10 = new Label(9, i + 2, bean.getFaultTimeTotal(),fontFormat_Content);
						Label value_11 = new Label(10, i + 2, bean.getCheckTag().equals("0")?"否":"是",fontFormat_Content);
						Label value_12 = new Label(11, i + 2, bean.getFaultRecordPerson(),fontFormat_Content);
						Label value_13 = new Label(12, i + 2, bean.getSelf_recovery(),fontFormat_Content);
						Label value_14 = new Label(13, i + 2, bean.getFaultHandlePerson(),fontFormat_Content);
						Label value_15 = new Label(14, i + 2, bean.getHandle_detail(),fontFormat_Content);
						Label value_16= new Label(15, i + 2, bean.getContent(),fontFormat_Content);
						Label value_17 = new Label(16, i + 2, bean.getReponse_part(),fontFormat_Content);
						Label value_18 = new Label(17, i + 2, bean.getOrder_wh(),fontFormat_Content);
						Label value_19 = new Label(18, i + 2,bean.getOrder_yd() ,fontFormat_Content);
						Label value_20 = new Label(19, i + 2, bean.getOrder_dx(),fontFormat_Content);
						Label value_21 = new Label(20, i + 2, bean.getSend_order_time(),fontFormat_Content);
						Label value_22 = new Label(21, i + 2, bean.getReceipt_order_time(),fontFormat_Content);
						Label value_23 = new Label(22, i + 2, bean.getRecv_order_time(),fontFormat_Content);
						Label value_24 = new Label(23, i + 2, String.valueOf(bean.getRecv_order_use_time()),fontFormat_Content);
						Label value_25 = new Label(24, i + 2, String.valueOf(bean.getRecv_order_cs()),fontFormat_Content);
						Label value_26 = new Label(25, i + 2, String.valueOf(bean.getHandle_order_user_time()),fontFormat_Content);
						Label value_27 = new Label(26, i + 2, String.valueOf(bean.getHandle_order_cs()),fontFormat_Content);
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
						sheet.addCell(value_26);
						sheet.addCell(value_27);
						
					}
		} catch (Exception e) {
			// TODO: handle exception
		}
				

	}
}
