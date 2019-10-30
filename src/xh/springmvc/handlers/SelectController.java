package xh.springmvc.handlers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.DataThresholdBean;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.SelectServices;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/select")
public class SelectController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(SelectController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	/**
	 * 获取工作记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/workcontact")
	public void workcontact(HttpServletRequest request, HttpServletResponse response){		

		HashMap result = new HashMap();
		result.put("items", SelectServices.workcontact());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@RequestMapping("/workcontact_add")
	public void workcontact_add(HttpServletRequest request, HttpServletResponse response){
		String name=request.getParameter("name");	
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", name);
		int rlt=SelectServices.workcontact_add(map);
		
		if(rlt>0){
			this.success=true;
			this.message="新增成功";
		}else{
			this.success=false;
			this.message="新增失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
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
	@RequestMapping("/workcontact_del")
	public void workcontact_del(HttpServletRequest request, HttpServletResponse response){
		String name=request.getParameter("name");
		int rlt=SelectServices.workcontact_del(name);
		
		if(rlt>0){
			this.success=true;
			this.message="删除成功";
		}else{
			this.success=false;
			this.message="删除失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
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
	@RequestMapping("/ThresholdMap")
	public void ThresholdMap(HttpServletRequest request, HttpServletResponse response){
		HashMap result = new HashMap();
		result.put("items",SelectServices.ThresholdMap());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/updateThreshold")
	public void updateThreshold(
			@RequestParam("data") String data,
			HttpServletRequest request, HttpServletResponse response){
		DataThresholdBean bean=(DataThresholdBean) GsonUtil.json2Object(data, DataThresholdBean.class);
		int rlt=SelectServices.updateThreshold(bean);
		
		if(rlt>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
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
