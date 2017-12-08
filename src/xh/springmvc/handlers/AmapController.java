package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
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
import xh.mybatis.service.RadioStatusService;


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
	 * 根据所选条件查询所有基站信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/bsByBoth")
	@ResponseBody
	public void bsByBoth(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			Map<String,List<String>> tempMap = new HashMap<String,List<String>>();
			List<String> level = null;
			List<String> zone = null;
			AmapService AmapService = new AmapService();
			String temp1 = request.getParameter("level");	
			if(!"".equals(temp1)){
				String[] leveltemp = temp1.split(",");
				level = Arrays.asList(leveltemp);
			}
						
			String temp2 = request.getParameter("zone");
			if(!"".equals(temp2)){
				String[] zonetemp = temp2.split(",");
				zone = Arrays.asList(zonetemp);	
			}
				
			tempMap.put("level", level);
			tempMap.put("zone", zone);
			List<HashMap<String, String>> listMap = AmapService.bsByBoth(tempMap);
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
	 * 根据bsId查询单个基站的排队数及其他（注册组和注册用户）
	 */
	@RequestMapping("/map/numtotals")
	@ResponseBody
	public void selectNumTotalsByBsId(HttpServletRequest request, HttpServletResponse response){
		try {
			AmapService AmapService = new AmapService();
			String bsId = request.getParameter("bsId");
			HashMap map = new HashMap();
			List<HashMap<String, String>> listMap = AmapService.selectNumTotalsByBsId(bsId);
			int radioTotals = RadioStatusService.oneBsRadioCount(Integer.parseInt(bsId));
			int groupTotals = RadioStatusService.oneBsGroupCount(Integer.parseInt(bsId));
			map.put("items", listMap);
			map.put("radioTotals", radioTotals);
			map.put("groupTotals", groupTotals);
			response.setContentType("application/json;charset=utf-8");
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
	 * 不规则圈选功能查询
	 */
	@RequestMapping("/polyline")
	@ResponseBody
	public void polyline(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		//判断当前时间查询话务量
		Calendar cal = Calendar.getInstance();
		int temp = cal.get(Calendar.MONTH)+1;
		String currentMonth;
		if(temp<10){
			currentMonth="0"+temp;
		}else{
			currentMonth=Integer.toString(temp);
		}
		currentMonth="xhgmnet_gps_voice.xhgmnet_calllist"+currentMonth;
		
		String params=request.getParameter("params");
		String [] strArray= params.split(",");
		List<String> groupData = Arrays.asList(strArray);
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupData", groupData);
		map.put("currentMonth", currentMonth);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",AmapService.polylineCount(map));
		result.put("items", AmapService.polyline(map));
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
	 * 圈选功能查询
	 */
	@RequestMapping("/rectangle")
	@ResponseBody
	public void rectangle(HttpServletRequest request, HttpServletResponse response){
		this.success = true;
		// 判断当前时间查询话务量
		Calendar cal = Calendar.getInstance();
		int tempTime = cal.get(Calendar.MONTH) + 1;
		String currentMonth;
		if (tempTime < 10) {
			currentMonth = "0" + tempTime;
		} else {
			currentMonth = Integer.toString(tempTime);
		}
		currentMonth = "xhgmnet_gps_voice.xhgmnet_calllist" + currentMonth;
		
		String params=request.getParameter("params");
		String [] temp= params.split(",");
		String smallLng;
		String bigLng;
		String smallLat;
		String bigLat;
		if(Double.parseDouble(temp[0])<Double.parseDouble(temp[2])){
			smallLng = temp[0];
			bigLng = temp[2];
		}else{
			smallLng = temp[2];
			bigLng = temp[0];
		}
		if(Double.parseDouble(temp[1])<Double.parseDouble(temp[3])){
			smallLat = temp[1];
			bigLat = temp[3];
		}else{
			smallLat = temp[3];
			bigLat = temp[1];
		}
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("currentMonth", currentMonth);
		map.put("smallLng", smallLng);
		map.put("bigLng", bigLng);
		map.put("smallLat", smallLat);
		map.put("bigLat", bigLat);
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
