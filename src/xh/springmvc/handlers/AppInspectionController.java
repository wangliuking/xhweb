package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AppInspectionServer;

@Controller
@RequestMapping("/app")
public class AppInspectionController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(AppInspectionController.class);
	private WebLogBean webLogBean=new WebLogBean();
	
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
}
