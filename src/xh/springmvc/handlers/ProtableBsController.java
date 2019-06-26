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
import xh.mybatis.bean.ProtableBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.ProtableBsServices;

@Controller
@RequestMapping("/protablebs")
public class ProtableBsController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ProtableBsController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		int line=funUtil.StringToInt(request.getParameter("line"));
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("line", line);
		HashMap result = new HashMap();
		result.put("items",ProtableBsServices.list(map));
		result.put("totals", ProtableBsServices.count(map));
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
		ProtableBean bean=GsonUtil.json2Object(formData, ProtableBean.class);
		
		if(ProtableBsServices.exists(bean.getBsName(),bean.getId())>0){
			this.success=false;
			this.message="记录已经存在";
		}else{
			int rlt=ProtableBsServices.add(bean);	
			if(rlt>0){
				this.success=true;
				this.message="新增成功";
				FunUtil.WriteLog(request, ConstantLog.ADD, "增加卫星车载便携站记录："+bean.getBsName());
			}else{
				this.success=false;
				this.message="新增失败";
			}
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
		ProtableBean bean=GsonUtil.json2Object(formData, ProtableBean.class);
		int rlt=ProtableBsServices.update(bean);		
		if(rlt>0){
			this.success=true;
			this.message="修改成功";
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改卫星车载便携站记录："+bean.getBsName());
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
		String id=request.getParameter("id");	
		int rlt=ProtableBsServices.del(id);		
		if(rlt>0){
			this.success=true;
			this.message="删除成功";
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除卫星车载便携站记录："+id);
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
