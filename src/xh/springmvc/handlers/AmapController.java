package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

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

import xh.func.plugin.*;
import xh.mybatis.bean.AlarmList;
import xh.mybatis.bean.PowerOffRes;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AmapService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.GosuncnService;
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
	 * 批量删除
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/deleteRadioId",method=RequestMethod.POST)
	@ResponseBody
	public void deleteRadioId(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestBody List<Map<String,Object>> listMap){
		try {
			AmapService amapService = new AmapService();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("listMap", listMap);
			amapService.deleteRadioId(tempMap);
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
			Map<String, Object> map = new HashMap<String, Object>();
			String radioIdAdd = request.getParameter("radioIdAdd");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("radioIdAdd", radioIdAdd);
			//根据id查找是否添加
			int res = AmapService.selectRadioId(radioIdAdd);
			if(res == 0){
				AmapService amapService = new AmapService();
				amapService.addRadioId(param);
				map.put("success", true);
			}else{
				map.put("success",false);
			}

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

	/**
	 *
	 */
	@RequestMapping("/analysisPowerOff")
	public void analysisPowerOff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String start = request.getParameter("startTime");
		String end = request.getParameter("endTime");
		List<PowerOffRes> finalList = analysisModel(start,end);
		ExportExcel<PowerOffRes> ee= new ExportExcel<PowerOffRes>();
		String[] headers = {"基站ID","名称","断站时间","恢复时间","停电时间","来电或最后一次时间","停电时电池电压","来电或最后一次电池电压","持续时间","综合分析"};
		String fileName = "停电分析表";
		ee.exportExcel(headers,finalList,fileName,response);
	}

	public static void main(String[] args) throws Exception {
		//analysisModel("2019-07-01 00:00:00","2019-07-31 23:59:00");
		List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
		for(int i=1;i<=13;i++){
			String d = "";
			if(i<10){
				d = "0"+i;
			}else {
				d = ""+i;
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("startTime","2019-11-"+d+" 00:00:00");
			map.put("endTime","2019-11-"+d+" 23:59:59");
			map.put("id","1011");
			int count1 = AmapService.searchCount(map);
			map.put("id","57703001");
			int count2 = AmapService.searchCount(map);
			map.put("id","57703002");
			int count3 = AmapService.searchCount(map);
			Map<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("id_1",count1);
			resMap.put("id_2",count2);
			resMap.put("id_3",count3);
			resMap.put("time","2019-11-"+d);
			list.add(resMap);
		}
		System.out.println(list);
		Map<String,Object> tMap = new HashMap<String,Object>();
		tMap.put("list",list);
		AmapService.insertTemp(tMap);
	}

	public static List<PowerOffRes> analysisModel(String start,String end) throws Exception{
		List<PowerOffRes> finalList = new LinkedList<PowerOffRes>();
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("startTime",start);
		parameter.put("endTime",end);
		List<Map<String,Object>> list = AmapService.selectBsOffByTime(parameter);
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String bsId = map.get("bsId")+"";
			String name = map.get("name")+"";
			String bsOffTime = map.get("time")+"";
			String bsOnTime = map.get("faultRecoveryTime")+"";
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("bsId",bsId);
			param.put("bsOffTime",bsOffTime);
			//获取断站前后一小时的时间
			long l = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bsOffTime).getTime();
			long l1 = l-1000*60*60*1;
			long l2 = l+1000*60*60*1;
			String startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(l1));
			String endTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(l2));
			param.put("startTime",startTime1);
			param.put("endTime",endTime1);
			param.put("preMonth","xhgmnet_emh_sensor_history"+startTime1.substring(5, 7));
			param.put("currentMonth","xhgmnet_emh_sensor_history"+endTime1.substring(5, 7));

			List<Map<String,Object>> res = AmapService.selectVolWhenPowerOff(param);
			//断站最近一刻蓄电池电压低于45V，为停电造成
			if(res.size()>0 && !"".equals(res.get(0).get("ups4")==null?"":res.get(0).get("ups4")+"") && Double.parseDouble(res.get(0).get("ups4")+"")<46.00){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("bsId",bsId);
				params.put("bsOffTime",bsOffTime);
				//获取断站前13小时的时间
				long ll1 = l-1000*60*60*13;
				String startTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ll1));
				String endTime2 = res.get(0).get("createTime")+"";
				params.put("startTime",startTime2);
				params.put("endTime",endTime2);
				params.put("preMonth","xhgmnet_emh_sensor_history"+startTime2.substring(5, 7));
				params.put("currentMonth","xhgmnet_emh_sensor_history"+endTime2.substring(5, 7));
				//处理为空或无效数据
				List<Map<String,Object>> dataList = checkData(AmapService.selectHistoryByTime(params));
				List<Map<String,Object>> analysisList = new LinkedList<Map<String,Object>>();
				int powerOffIndex = 0;
				int powerOnIndex = 0;
				for(int j=5;j<dataList.size();j++){
					Map<String,Object> temp1 = dataList.get(j-5);
					Map<String,Object> temp2 = dataList.get(j-4);
					Map<String,Object> temp3 = dataList.get(j-3);
					Map<String,Object> temp4 = dataList.get(j-2);
					Map<String,Object> temp5 = dataList.get(j-1);
					Map<String,Object> temp6 = dataList.get(j);

					//处理为空或无效数据
					String ups1Pre = temp1.get("ups1")==null?"":temp1.get("ups1")+"";
					String ups1Next = temp2.get("ups1")==null?"":temp2.get("ups1")+"";

					String e4Pre = temp1.get("e4")==null?"":temp1.get("e4")+"";
					String e4Next = temp2.get("e4")==null?"":temp2.get("e4")+"";
					String e4Third = temp3.get("e4")==null?"":temp3.get("e4")+"";
					String e4Four = temp4.get("e4")==null?"":temp4.get("e4")+"";
					String e4Five = temp5.get("e4")==null?"":temp5.get("e4")+"";
					String e4Six = temp6.get("e4")==null?"":temp6.get("e4")+"";

					//来电特殊情况，电表一直无数据，后出现数据
					if(powerOffIndex != powerOnIndex && "".equals(e4Pre) && "".equals(e4Next) && "".equals(e4Third) && "".equals(e4Four) && "".equals(e4Five) && !"".equals(e4Six)){
						//来电
						powerOnIndex++;
						temp6.put("powerOnIndex",powerOnIndex);
						analysisList.add(temp6);
					}
					//判断停电时间
					if(!"".equals(e4Pre) && !"".equals(e4Next) && !"".equals(e4Third) && !"".equals(e4Four) && !"".equals(e4Five) && !"".equals(e4Six)){
						//电表有读数，直接判断电表
						if(powerOffIndex == powerOnIndex && !e4Pre.equals(e4Next) && e4Next.equals(e4Third) && e4Third.equals(e4Four) && e4Four.equals(e4Five) && e4Five.equals(e4Six)){
							//停电
							powerOffIndex++;
							temp1.put("powerOffIndex",powerOffIndex);
							analysisList.add(temp1);
						}
						if(powerOffIndex != powerOnIndex && e4Pre.equals(e4Next) && e4Next.equals(e4Third) && e4Third.equals(e4Four) && e4Four.equals(e4Five) && !e4Five.equals(e4Six)){
							//来电
							powerOnIndex++;
							temp6.put("powerOnIndex",powerOnIndex);
							analysisList.add(temp6);
						}
					}else{
						//电表无读数，判断eps
						if(powerOffIndex == powerOnIndex && Double.parseDouble(ups1Pre) > 100 && Double.parseDouble(ups1Next) < 50){
							//停电
							powerOffIndex++;
							temp1.put("powerOffIndex",powerOffIndex);
							analysisList.add(temp1);
						}
						if(powerOffIndex != powerOnIndex && Double.parseDouble(ups1Pre) < 50 && Double.parseDouble(ups1Next) > 100){
							powerOnIndex++;
							temp1.put("powerOnIndex",powerOnIndex);
							analysisList.add(temp1);
						}
					}
				}
				analysisList.add(res.get(0));
				System.out.println(analysisList);
				List<PowerOffRes> calcList = new LinkedList<PowerOffRes>();
				if(analysisList.size()%2 == 0){
					for(int x=0;x<analysisList.size();x+=2){
						PowerOffRes powerOffRes = new PowerOffRes();
						Map<String,Object> tempMap1 = analysisList.get(x);
						Map<String,Object> tempMap2 = analysisList.get(x+1);
						powerOffRes.setBsId(bsId);
						powerOffRes.setName(name);
						powerOffRes.setBsOffTime(bsOffTime);
						powerOffRes.setBsOnTime(bsOnTime);
						powerOffRes.setPowerOffTime(tempMap1.get("createTime")+"");
						powerOffRes.setPowerOffVol(tempMap1.get("ups4")+"");
						powerOffRes.setPowerOnTime(tempMap2.get("createTime")+"");
						powerOffRes.setPowerOnVol(tempMap2.get("ups4")+"");
						String tempStr = calcTime(tempMap1.get("createTime")+"",tempMap2.get("createTime")+"");
						powerOffRes.setCalcTime(tempStr);
						if(!"".equals(tempStr)){
							calcList.add(powerOffRes);
						}
					}
				}
				//综合分析
				double centerVol = 0.00;
				List<String> timeList = new LinkedList<String>();
				//System.out.println(calcList);
				if(calcList.size()>0){
					for(int y=0;y<calcList.size();y++){
						PowerOffRes powerOffRes = calcList.get(y);
						//String powerOffVol = powerOffRes.getPowerOffVol()==null?"":powerOffRes.getPowerOffVol();
						String powerOnVol = powerOffRes.getPowerOnVol()==null?"":powerOffRes.getPowerOnVol();
						String powerOffTime = powerOffRes.getPowerOffTime()==null?"":powerOffRes.getPowerOffTime();
						String powerOnTime = powerOffRes.getPowerOnTime()==null?"":powerOffRes.getPowerOnTime();
						if(centerVol == 0){
							String tempTime;
							if(y == calcList.size()-1){
								//最后一条计算停电时间到断站时间的间隔
								tempTime = calcTimeForBsOff(powerOffTime,bsOffTime);
								powerOffRes.setCalcTime(tempTime);
							}else{
								tempTime = calcTime(powerOffTime,powerOnTime);
							}
							timeList.add(tempTime);
							centerVol = Double.parseDouble(powerOnVol);
						}else if(Double.parseDouble(powerOnVol) < centerVol){
							Map<String,Object> searchParam = new HashMap<String,Object>();
							searchParam.put("bsId",bsId);
							searchParam.put("startTime",powerOffTime);
							searchParam.put("endTime",powerOnTime);
							searchParam.put("preMonth","xhgmnet_emh_sensor_history"+powerOffTime.substring(5, 7));
							searchParam.put("currentMonth","xhgmnet_emh_sensor_history"+powerOnTime.substring(5, 7));
							List<Map<String,Object>> volList = AmapService.selectHistoryByTime(searchParam);
							for(int z=0;z<volList.size();z++){
								String searchVolVal = volList.get(z).get("ups4")==null?"":volList.get(z).get("ups4")+"";
								if(!"".equals(searchVolVal) && Double.parseDouble(searchVolVal) <= centerVol){
									//下降到该电压时计算时间
									String searchTime = volList.get(z).get("createTime")==null?"":volList.get(z).get("createTime")+"";
									String tempTime;
									if(y == calcList.size()-1){
										//最后一条计算停电时间到断站时间的间隔
										tempTime = calcTimeForBsOff(searchTime,bsOffTime);
										powerOffRes.setCalcTime(tempTime);
									}else{
										tempTime = calcTime(searchTime,powerOnTime);
									}
									timeList.add(tempTime);
									break;
								}
							}
							centerVol = Double.parseDouble(powerOnVol);
						}
					}

					System.out.println(timeList);
					PowerOffRes sumTimeMap = calcList.get(calcList.size()-1);
					sumTimeMap.setFinalTime(sumTime(timeList));
					System.out.println(calcList);
				}/*else{
					PowerOffRes sumTimeMap = new PowerOffRes();
					sumTimeMap.setBsId(bsId);
					sumTimeMap.setName(name);
					sumTimeMap.setFinalTime("该基站需手动分析");
					calcList.add(sumTimeMap);
					System.out.println(calcList);
				}*/
				for(PowerOffRes po : calcList){
					finalList.add(po);
				}
			}
		}
		return finalList;
	}

	public static boolean compareTime(String time1,String time2) {
		if(time1 == null || "".equals(time1)){
			return false;
		}
		try {
			long l1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1).getTime();
			long l2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2).getTime();
			if(l1>l2){
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			System.out.println(time1+"============="+time2);
		}
		return false;
	}

	public static String calcTime(String time1,String time2) {
		long nd = 1000 *24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		if(time1 == null || "".equals(time1)){
			return "";
		}
		try {
			long l1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1).getTime();
			long l2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2).getTime();
			long diff = l2 - l1;
			long temp = diff / nm;
			if(temp < 20){
				return "";
			}
			long day = diff / nd;
			long hour = diff % nd / nh;
			long min = diff % nd % nh / nm;
			return hour + "小时" + min + "分钟";
		}catch (Exception e){
			System.out.println(time1+"============="+time2);
		}
		return "";
	}

	public static String calcTimeForBsOff(String time1,String time2) {
		long nd = 1000 *24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		if(time1 == null || "".equals(time1)){
			return "";
		}
		try {
			long l1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1).getTime();
			long l2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2).getTime();
			if(l2 < l1){
				return "0小时0分钟";
			}
			long diff = l2 - l1;
			long hour = diff % nd / nh;
			long min = diff % nd % nh / nm;
			return hour + "小时" + min + "分钟";
		}catch (Exception e){
			System.out.println(time1+"============="+time2);
		}
		return "";
	}

	public static String sumTime(List<String> timeList) {
		if(timeList.size()>0){
			int hour = 0;
			int min = 0;
			for(int i=0;i<timeList.size();i++){
				String tempTime = timeList.get(i);
				String hourStr = tempTime.split("小时")[0];
				String minStr = tempTime.split("小时")[1].split("分钟")[0];
				hour+=Integer.parseInt(hourStr);
				min+=Integer.parseInt(minStr);
			}
			hour+=min/60;
			min=min%60;
			return hour+"小时"+min+"分钟";
		}
		return "";
	}

	public static List<Map<String,Object>> checkData(List<Map<String,Object>> dataList) {
		List<Map<String,Object>> finalList = new LinkedList<Map<String,Object>>();
		if(dataList.size()>0){
			for(int i=0;i<dataList.size();i++){
				Map<String,Object> temp = dataList.get(i);
				String ups1 = temp.get("ups1")==null?"":temp.get("ups1")+"";
				String ups2 = temp.get("ups2")==null?"":temp.get("ups2")+"";
				String e4 = temp.get("e4")==null?"":temp.get("e4")+"";
				if(!"".equals(ups2) && Double.parseDouble(ups2) != 0){
					if(!"".equals(ups1) || !"".equals(e4)){
						finalList.add(temp);
					}
				}
			}
		}
		return finalList;
	}
	
	
}
