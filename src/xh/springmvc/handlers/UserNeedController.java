package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;
import com.tcpBean.ErrCheckAck;
import com.tcpBean.ErrProTable;
import com.tcpServer.ServerDemo;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.RecordCommunicationBean;
import xh.mybatis.bean.RecordEmergencyBean;
import xh.mybatis.bean.UserNeedBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.OrderService;
import xh.mybatis.service.RecordTrainService;
import xh.mybatis.service.UserNeedService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;


@Controller
@RequestMapping("/userneed")
public class UserNeedController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(UserNeedController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping(value="/data_all", method = RequestMethod.GET)
	@ResponseBody
	public HashMap data_all(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		HashMap result = new HashMap();
		result.put("items",UserNeedService.data_all(map));
		result.put("totals", UserNeedService.data_all_count(map));		
		return result;
	}
	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap add(@RequestBody UserNeedBean bean) {
		
		System.out.println("bean-->"+bean.toString());
		int rs=UserNeedService.add(bean);
		if(rs>0){
			this.message="添加成功";
			this.success=true;
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap update(@RequestBody UserNeedBean bean) {
		int rs=UserNeedService.update(bean);
		if(rs>0){
			this.message="修改成功";
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
	@RequestMapping(value="/del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=UserNeedService.del(list);
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
	
	@RequestMapping(value="/communication_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap communication_list(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		HashMap result = new HashMap();
		result.put("items",UserNeedService.communication_list(map));
		result.put("totals", UserNeedService.communication_list_count(map));		
		return result;
	}
	@RequestMapping(value="/add_communication", method = RequestMethod.POST)
	@ResponseBody
	public HashMap add_communication(@RequestBody RecordCommunicationBean bean) {
		
		System.out.println("bean-->"+bean.toString());
		int rs=UserNeedService.add_communication(bean);
		if(rs>0){
			this.message="添加成功";
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
	@RequestMapping(value="/update_communication", method = RequestMethod.POST)
	@ResponseBody
	public HashMap update_communication(@RequestBody RecordCommunicationBean bean) {
		int rs=UserNeedService.update_communication(bean);
		if(rs>0){
			this.message="修改成功";
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
	@RequestMapping(value="/upload_communication", method = RequestMethod.POST)
	@ResponseBody
	public HashMap upload_communication(@RequestBody RecordCommunicationBean bean) {
		int rs=UserNeedService.upload_communication(bean);
		if(rs>0){
			this.message="上传成功";
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
	@RequestMapping(value="/del_communication", method = RequestMethod.POST)
	@ResponseBody
	public HashMap del_communication(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=UserNeedService.del_communication(list);
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
	
	
	@RequestMapping(value="/emergency_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap emergency_list(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		HashMap result = new HashMap();
		result.put("items",UserNeedService.emergency_list(map));
		result.put("totals", UserNeedService.emergency_list_count(map));		
		return result;
	}
	@RequestMapping(value="/add_emergency", method = RequestMethod.POST)
	@ResponseBody
	public HashMap add_emergency(@RequestBody RecordEmergencyBean bean) {
		
		System.out.println("bean-->"+bean.toString());
		int rs=UserNeedService.add_emergency(bean);
		if(rs>0){
			this.message="添加成功";
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
	@RequestMapping(value="/update_emergency", method = RequestMethod.POST)
	@ResponseBody
	public HashMap update_emergency(@RequestBody RecordEmergencyBean bean) {
		int rs=UserNeedService.update_emergency(bean);
		if(rs>0){
			this.message="修改成功";
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
	@RequestMapping(value="/upload_emergency", method = RequestMethod.POST)
	@ResponseBody
	public HashMap upload_emergency(@RequestBody RecordEmergencyBean bean) {
		int rs=UserNeedService.upload_emergency(bean);
		if(rs>0){
			this.message="上传成功";
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
	@RequestMapping(value="/del_emergency", method = RequestMethod.POST)
	@ResponseBody
	public HashMap del_emergency(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=UserNeedService.del_emergency(list);
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
	@RequestMapping(value="/addfile", method = RequestMethod.POST)
	@ResponseBody
	public HashMap addfile(HttpServletRequest request, HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter("id"));
		String files=request.getParameter("files");
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
		filelist=GsonUtil.json2Object(files, type);
		int rs=0;
		if(filelist.size()>0){
			for(int i=0;i<filelist.size();i++){
				Map<String,Object> map=filelist.get(i);
				map.put("id", id);
				filelist.set(i, map);
			}
			rs=UserNeedService.addFile(filelist);
		}
		if(rs>0){
			this.message="成功";
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
	@RequestMapping(value="/excel", method = RequestMethod.POST)
	public void excel(@RequestBody UserNeedBean user ,HttpServletRequest request, HttpServletResponse response) {
	   
		System.out.println(user.toString());
		String now_date=FunUtil.nowDateNotTime();
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/download");
			String pathname = saveDir + "/"+FunUtil.RandomAlphanumeric(8)+".xls";
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


			WritableSheet sheet = book.createSheet("用户需求处理详表", 0);
		
			sheet.addCell(new Label(0, 0, "时间/联系人", fontFormat_Content));
			sheet.addCell(new Label(1, 0, user.getTime(), fontFormat_Content));
			sheet.addCell(new Label(2, 0, user.getContact_person(), fontFormat_Content));
			
			sheet.addCell(new Label(0, 1, "地点", fontFormat_Content));
			sheet.addCell(new Label(1, 1, user.getAddress(), fontFormat_Content));
			sheet.mergeCells(1, 1, 2, 1);
			
			sheet.addCell(new Label(0, 2, "现象描述", fontFormat_Content));
			sheet.addCell(new Label(1, 2, user.getDescription(), fontFormat_Content));
			sheet.mergeCells(1, 2, 2, 2);
			
			sheet.addCell(new Label(0, 3, "接单响应", fontFormat_Content));
			sheet.addCell(new Label(1, 3, user.getResponse(), fontFormat_Content));
			sheet.mergeCells(1, 3, 2, 3);
			
			sheet.addCell(new Label(0, 4, "处理跟踪", fontFormat_Content));
			sheet.addCell(new Label(1, 4, user.getHandle(), fontFormat_Content));
			sheet.mergeCells(1, 4, 2, 4);
			
			sheet.addCell(new Label(0, 5, "处理结果", fontFormat_Content));
			sheet.addCell(new Label(1, 5, user.getResult(), fontFormat_Content));
			sheet.mergeCells(1, 5, 2, 5);
			
			sheet.addCell(new Label(0, 6, "备注", fontFormat_Content));
			sheet.addCell(new Label(1, 6, user.getNote(), fontFormat_Content));
			sheet.mergeCells(1, 6, 2, 6);
			sheet.setColumnView(0, 20);
			sheet.setColumnView(1, 40);
			sheet.setColumnView(2, 40);
			
			sheet.setRowView(2, 600);
			sheet.setRowView(3, 1200);
			sheet.setRowView(4, 1200);
			sheet.setRowView(5, 1200);
			sheet.setRowView(6, 1200);

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

	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	public void batch_upload(@RequestParam("path_name") MultipartFile[] file,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String date=sdf.format(d);
		String savePath="/upload/emergency/"+date.split("-")[0];
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/emergency/"+date.split("-")[0];
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		if(!(file.length==0)){
			
			for(int i=0;i<file.length;i++){
				String fileName = file[i].getOriginalFilename();
				System.out.println("文件名："+fileName);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();

				}
				// 保存
				try {
					file[i].transferTo(targetFile);
					long time=new Date().getTime();
					String newName=time+fileName.substring(fileName.lastIndexOf("."));
					FunUtil.renameFile(path, fileName, newName);
					this.success = true;
					this.message = "文件上传成功";
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("fileName", fileName);
					map.put("filePath", savePath + "/" + newName);
					map.put("index", i+1);
					map.put("size", file[i].getSize());
					rs.add(map);

				} catch (Exception e) {
					e.printStackTrace();
					this.message = "文件上传失败";
				}
			}
			
			
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("rs", rs);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
