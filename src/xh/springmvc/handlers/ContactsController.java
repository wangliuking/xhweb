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
import jxl.Sheet;
import jxl.Workbook;

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
	
	

}
