package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
import xh.mybatis.service.RadioUserBusiness_EService;
import xh.mybatis.service.RadioUserSeriaService;

@Controller
@RequestMapping(value="/radiouserbusiness")
public class RadioUserBusiness_EController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RadioUserBusiness_EController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询无线用户业务属性
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",RadioUserBusiness_EService.Count(map));
		result.put("items", RadioUserBusiness_EService.radioUserBusinessInfo(map));
		response.setContentType("application/json;charset=utf-8");   
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 添加
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public void insert(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String,Object> map = new HashMap<String,Object>();
			Enumeration rnames=request.getParameterNames();
			for (Enumeration e = rnames ; e.hasMoreElements() ;) {
			         String thisName=e.nextElement().toString();
			        String thisValue=request.getParameter(thisName);
			        map.put(thisName, thisValue);
			}
			int count=RadioUserBusiness_EService.insert(map);
			HashMap result = new HashMap();
			result.put("success", success);
			result.put("result",count);
			String jsonstr = json.Encode(result);
			try {
				response.getWriter().write(jsonstr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String,Object> map = new HashMap<String,Object>();
		Enumeration rnames=request.getParameterNames();
		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
		         String thisName=e.nextElement().toString();
		        String thisValue=request.getParameter(thisName);
		        map.put(thisName, thisValue);
		}
		int count=RadioUserBusiness_EService.update(map);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result",count);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		RadioUserBusiness_EService.delete(list);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
