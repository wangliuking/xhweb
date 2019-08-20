package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.ContactsBean;
import xh.mybatis.bean.InspectionNetBean;
import xh.mybatis.bean.OndutyBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.ContactsService;
import xh.mybatis.service.OndutyService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ContactsController.class);
	private FlexJSON json=new FlexJSON();
	
	
	@RequestMapping(value="/phone_list",method = RequestMethod.GET)
	public void phone_list(HttpServletRequest request, HttpServletResponse response){
		List<ContactsBean> list=ContactsService.phone_list();
		HashMap result = new HashMap();
		result.put("items",list);
		result.put("tel", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping(value="/phone_write",method = RequestMethod.POST)
	public void phone_write(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		ContactsBean bean=GsonUtil.json2Object(formData, ContactsBean.class);
		if(ContactsService.phone_info_by_namenum(bean)>0){
			success=false;
			message="该用户或则联系方式已经存在";
		}else{
			if(ContactsService.phone_write(bean)>0){
				success=true;
				message="添加联系人成功";
			}else{
				success=false;
				message="添加联系人失败";
			}
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping(value="/phone_update",method = RequestMethod.POST)
	public void phone_update(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		ContactsBean bean=GsonUtil.json2Object(formData, ContactsBean.class);
		bean.setTag(1);
		if(ContactsService.phone_info_by_namenum(bean)>0){
			success=false;
			message="该用户或则联系方式已经存在";
		}else{
			if(ContactsService.phone_update(bean)>0){
				success=true;
				message="修改联系人成功";
			}else{
				success=false;
				message="修改联系人失败";
			}
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping(value="/phone_del",method = RequestMethod.POST)
	public void phone_del(HttpServletRequest request, HttpServletResponse response){
		String ids=request.getParameter("ids");
		String[] id=ids.split(",");
		List<String> list=new ArrayList<String>();
		
		for(int i=0,j=id.length;i<j;i++){
			list.add(id[i]);
		}
		int rs=ContactsService.phone_del(list);
		if(rs>0){
			success=true;
			message="删除成功";
		}else{
			success=false;
			message="删除失败";
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping(value = "/excel_contacts", method = RequestMethod.POST)
	public void excel_contacts(@RequestParam("time") String time,HttpServletRequest request, HttpServletResponse response) {

		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/checksource");
			String pathname = saveDir + "/运维人员通讯录["+time+"].xls";
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
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
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
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.GRAY_80);
			// 应用字体



			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);

			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			WritableSheet sheet = book.createSheet("运维服务团队通讯录", 0);

			// 第1行
			Label title = new Label(0, 0, "成都市应急通信网运维服务团队通讯录", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			
			for(int i=1;i<5;i++){
				sheet.setRowView(i, 400, false); // 设置行高
			}

			sheet.addCell(new Label(0, 1, "序号", fontFormat_h));
			sheet.addCell(new Label(1, 1, "姓名", fontFormat_h));
			sheet.addCell(new Label(2, 1, "职位", fontFormat_h));
			sheet.addCell(new Label(3, 1, "电话号码", fontFormat_h));
			sheet.addCell(new Label(4, 1, "备注", fontFormat_h));
			List<ContactsBean> list=ContactsService.phone_list();
			for(int i=0;i<list.size();i++){
				ContactsBean bean=list.get(i);
				sheet.addCell(new jxl.write.Number(0, i+2, (i+1), fontFormat_Content));
				sheet.addCell(new Label(1, i+2, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, i+2, bean.getJob(), fontFormat_Content));
				if(FunUtil.is_numberic(bean.getPhoneNumber())){
					sheet.addCell(new jxl.write.Number(3, i+2, FunUtil.StringToLong(bean.getPhoneNumber()), fontFormat_Content));
				}else{
					sheet.addCell(new Label(3, i+2, bean.getPhoneNumber(), fontFormat_Content));
				}
				
				sheet.addCell(new Label(4, i+2, "", fontFormat_Content));
			}

			
			book.write();
			book.close();
			
			String destDir1=request.getSession().getServletContext()
					.getRealPath("/upload/checksource");
			destDir1=destDir1+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/3";
			File Path1 = new File(destDir1);
			if (!Path1.exists()) {
				Path1.mkdirs();
			}
			
			String destDir2=request.getSession().getServletContext()
					.getRealPath("/upload/checksource");
			destDir2=destDir2+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/4";
			File Path2 = new File(destDir2);
			if (!Path2.exists()) {
				Path2.mkdirs();
			}
			File file1 = new File(destDir1+"/运维人员通讯录.xls");
			File file2 = new File(destDir2+"/运维人员通讯录.xls");
			FunUtil.copyFile(file, file1);
			FunUtil.copyFile(file, file2);
			
			
			
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

	
	

}
