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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.PropertiesUtil;
import xh.func.plugin.RedisUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AmapService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.RadioStatusService;
import xh.org.listeners.SingLoginListener;


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
			Map<String,Object> tempMap = new HashMap<String,Object>();
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
			
			FunUtil funUtil = new FunUtil();
			String userId = funUtil.loginUser(request);
			tempMap.put("userId", userId);
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
			String bsId = request.getParameter("bsId");
			//获取用户的vpnId	
			HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
			String vpnId = tempMap.get("vpnId").toString();	
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("vpnId", vpnId);
			param.put("bsId", bsId);
			
			AmapService AmapService = new AmapService();
			HashMap map = new HashMap();
			List<HashMap<String, String>> listMap = AmapService.selectNumTotalsByBsId(bsId);
			int radioTotals = RadioStatusService.oneBsRadioCount(param);
			int groupTotals = RadioStatusService.oneBsGroupCount(param);
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
		
		String params=request.getParameter("params");
		String [] strArray= params.split(",");
		List<String> groupData = Arrays.asList(strArray);
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		FunUtil funUtil = new FunUtil();
		String userId = funUtil.loginUser(request);
		map.put("userId", userId);
		map.put("groupData", groupData);
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
		/*Calendar cal = Calendar.getInstance();
		int tempTime = cal.get(Calendar.MONTH) + 1;
		String currentMonth;
		if (tempTime < 10) {
			currentMonth = "0" + tempTime;
		} else {
			currentMonth = Integer.toString(tempTime);
		}
		currentMonth = "xhgmnet_gps_voice.xhgmnet_calllist" + currentMonth;*/		
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
		FunUtil funUtil = new FunUtil();
		String userId = funUtil.loginUser(request);
		map.put("userId", userId);
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
	
	/**
	 * 查询所有路测基站
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/road")
	@ResponseBody
	public void selectAllRoad(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			AmapService amapService = new AmapService();
			List<HashMap<String, String>> listMap = amapService.selectAllRoad();
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
	 * 查询路测数据
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/roadById")
	@ResponseBody
	public void selectRoadById(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			HashMap<String,Object> tempmap = new HashMap<String,Object>();
			String temp = request.getParameter("bsId");	
			String[] roadtemp = temp.split(",");
			List<String> bsIds = Arrays.asList(roadtemp);
			tempmap.put("bsIds", bsIds);
			AmapService amapService = new AmapService();
			List<HashMap<String, String>> listMap = amapService.selectRoadById(tempmap);
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
	 * 查询地图dst数据
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/dstData")
	@ResponseBody
	public void dstData(HttpServletRequest request, HttpServletResponse response){	
		try {
			Calendar cal = Calendar.getInstance();
			int temp = cal.get(Calendar.MONTH)+1;
			String currentMonth;
			if(temp<10){
				currentMonth="0"+temp;
			}else{
				currentMonth=Integer.toString(temp);
			}
			Map<String,Object> tempMap = new HashMap<String, Object>();
			currentMonth="xhgmnet_gpsinfo"+currentMonth;
			tempMap.put("currentMonth", currentMonth);			
			
			HashMap map = new HashMap();
			AmapService amapService = new AmapService();
			List<String> srcIdVisableList = amapService.srcVisable();
			tempMap.put("list", srcIdVisableList);
			List<Map<String, Object>> listMap = amapService.dstData(tempMap);
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
	 * 查询所有手台显示信息以及当前用户的初始化信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/selectForAllVisableStatus")
	@ResponseBody
	public void selectForAllVisableStatus(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
			String userId = tempMap.get("user").toString();
			HashMap map = new HashMap();
			AmapService amapService = new AmapService();
			List<Map<String, String>> listMap = amapService.selectForAllVisableStatus();
			String str = amapService.selectForMapInitByUser(userId);
			map.put("items", listMap);
			map.put("mapInitStr", str);
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
	 * gis显示基站查询
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/gisView")
	@ResponseBody
	public void gisView(HttpServletRequest request, HttpServletResponse response, HttpSession session){	
		try {
			FunUtil funUtil = new FunUtil();
			String userId = funUtil.loginUser(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bsId", null);
			map.put("name", null);
			int bsTableNum = BsstationService.bsCount(map);
			AmapService amapService = new AmapService();
			
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("userId", userId);
			int gisViewTableNum = amapService.gisViewCount(temp);
			
			//比较bsTableNum和gisViewTableNum，若不一致则需要更新该用户的gisView
			if(bsTableNum != gisViewTableNum){
				//更新前删除该用户的gisView
				amapService.deleteByUserId(temp);
				//与bsstation同步
				amapService.insertByUserId(temp);
			}
			

			List<HashMap<String, String>> listMap = null;
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
	 * 根据区域查询该用户的基站显示情况
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/gisViewByUserIdAndZoneForShow")
	@ResponseBody
	public void gisViewByUserIdAndZoneForShow(HttpServletRequest request, HttpServletResponse response, HttpSession session){	
		try {
			FunUtil funUtil = new FunUtil();
			String userId = funUtil.loginUser(request);
			String zone = request.getParameter("zone");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("zone", zone);
			AmapService amapService = new AmapService();
			List<HashMap<String, String>> listMap = amapService.gisViewByUserIdAndZoneForShow(map);
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
	 * 根据用户和基站id更新显示的配置
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/gisViewSave",method=RequestMethod.POST)
	@ResponseBody
	public void gisViewSave(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestBody List<Map<String,Object>> listMap){	
		try {		
			FunUtil funUtil = new FunUtil();
			String userId = funUtil.loginUser(request);
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("userId", userId);
			tempMap.put("listMap", listMap);
			AmapService amapService = new AmapService();
			int result = amapService.updateBatch(tempMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", "success");
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
	 * 更新GIS上终端显示情况
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/saveForAllVisable",method=RequestMethod.POST)
	@ResponseBody
	public void saveForAllVisable(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestBody List<Map<String,Object>> listMap){	
		try {		
			HashMap tMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
			String userId = tMap.get("user").toString();
			String mapInit = request.getParameter("mapInit");
			AmapService amapService = new AmapService();
			Map<String, Object> InitMap = new HashMap<String, Object>();
			InitMap.put("mapInit", mapInit);
			InitMap.put("userId", userId);
			amapService.updateForMapInitByUser(InitMap);
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("listMap", listMap);		
			int result = amapService.saveForAllVisable(tempMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", "success");
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
	 * 新增终端号
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/addRadioId",method=RequestMethod.GET)
	public void addRadioId(HttpServletRequest request, HttpServletResponse response){	
		try {		
			String radioIdAdd = request.getParameter("radioIdAdd");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("radioIdAdd", radioIdAdd);
			AmapService amapService = new AmapService();
			amapService.addRadioId(param);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", "success");
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
	 * 根据用户查询需要显示的基站
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/gisViewByUserIdForShow")
	@ResponseBody
	public void gisViewByUserIdForShow(HttpServletRequest request, HttpServletResponse response){	
		try {
			FunUtil funUtil = new FunUtil();
			String userId = funUtil.loginUser(request);
			AmapService amapService = new AmapService();	
			//查询此用户的地图初始化信息
			String mapInit = amapService.selectForMapInitByUser(userId);
			//检测gisViews是否有基站显示数据					
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("userId", userId);
			int gisViewTableNum = amapService.gisViewCount(temp);			
			//gisViewTableNum若为0则没有gis显示数据，需要更新
			if(gisViewTableNum == 0){
				amapService.insertByUserId(temp);
			}			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			List<HashMap<String, String>> listMap = amapService.gisViewByUserIdForShow(map);
			map.put("items", listMap);
			map.put("mapInit", mapInit);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AmapService amapService = new AmapService();
		String s = amapService.selectForMapInitByUser("admin");
		System.out.println("aaa");
	}
	
	
}
