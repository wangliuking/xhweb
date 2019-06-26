package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.RepeaterBsBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.RepeaterBsServices;
import xh.mybatis.service.SelectServices;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/repeaterbs")
public class RepeaterBsController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RepeaterBsController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("items",RepeaterBsServices.list(map));
		result.put("totals", RepeaterBsServices.count(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		RepeaterBsBean bean=GsonUtil.json2Object(formData, RepeaterBsBean.class);
		int rlt=RepeaterBsServices.add(bean);		
		if(rlt>0){
			this.success=true;
			this.message="新增成功";
			FunUtil.WriteLog(request, ConstantLog.ADD, "增加直放站记录："+bean.getBsName());
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
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		RepeaterBsBean bean=GsonUtil.json2Object(formData, RepeaterBsBean.class);
		int rlt=RepeaterBsServices.update(bean);		
		if(rlt>0){
			this.success=true;
			this.message="修改成功";
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修直放站记录："+bean.getBsName());
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
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response){
		String bsId=request.getParameter("bsId");	
		int rlt=RepeaterBsServices.del(bsId);		
		if(rlt>0){
			this.success=true;
			this.message="删除成功";
			FunUtil.WriteLog(request, ConstantLog.ADD, "删除直放站记录："+bsId);
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
}
