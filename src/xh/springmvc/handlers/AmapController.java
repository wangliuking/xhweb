package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AmapService;


@Controller
@RequestMapping(value="/amap")
public class AmapController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(AmapController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 根据所选区域查询所有基站信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/bsByArea")
	@ResponseBody
	public void bsByArea(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			AmapService AmapService = new AmapService();
			String temp = request.getParameter("zone");			
			//byte[] b=temp.getBytes("ISO-8859-1");
			//String test=new String(b,"utf-8");
			String[] zonetemp = temp.split(",");
			List<String> zone = Arrays.asList(zonetemp);
			List<HashMap<String, String>> listMap = AmapService.bsByArea(zone);
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据所选级别查询所有基站信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/bsByLevel")
	@ResponseBody
	public void bsByLevel(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			AmapService AmapService = new AmapService();
			String temp = request.getParameter("level");			
			//byte[] b=temp.getBytes("ISO-8859-1");
			//String test=new String(b,"utf-8");
			String[] leveltemp = temp.split(",");
			List<String> zone = Arrays.asList(leveltemp);
			List<HashMap<String, String>> listMap = AmapService.bsByLevel(zone);
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 圈选功能查询
	 */
	@RequestMapping("/rectangle")
	@ResponseBody
	public void rectangle(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String params=request.getParameter("params");
		String [] strArray= params.split(",");
		List<String> groupData = Arrays.asList(strArray);
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupData", groupData);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",AmapService.rectangleCount(map));
		result.put("items", AmapService.rectangle(map));
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
