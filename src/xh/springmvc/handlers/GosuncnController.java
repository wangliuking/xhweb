package xh.springmvc.handlers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinamobile.fsuservice.Test;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.GosuncnService;
import xh.mybatis.service.GpsService;
import xh.mybatis.service.SqlServerService;
import xh.org.listeners.EMHListener;
/**
 * 动环设备处理类
 * @author 12878
 *
 */
@Controller
@RequestMapping(value = "/gonsuncn")
public class GosuncnController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(GosuncnController.class);
	private FlexJSON json=new FlexJSON();	
	
	/**
	 * 获取动环相关信息
	 */
	@RequestMapping("/oneBsEmh")
	public void oneBsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String url = "http://192.168.5.254:8080/services/FSUService";
		String FSUID = "09201704160085";
		List<Map<String,String>> list = null;
		List<String> list1 = new ArrayList<String>();
		list1.add("170100000000001");
		list1.add("170200000000001");
		list1.add("170300000000001");
		list1.add("170400000000001");
		list1.add("170500000000001");
		list1.add("170700000000001");
		try {
			list = Test.getDataByList(url,FSUID,list1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", list);
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
	 * 获取摄像头ip
	 */
	@RequestMapping("/cameraIp")
	public void selectCameraIpByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String bsId = request.getParameter("bsId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bsId", bsId);
		List<Map<String,Object>> list = GosuncnService.selectCameraIpByBsId(map);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", list);
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
	 * 根据FSUID更新简阳无线ip
	 */
	public static void updataCameraIpByFSUID(Map<String,String> map){
		GosuncnService.updataCameraIpByFSUID(map);
	}
	
	/**
	 * 根据FSUID删除配置信息(保持最新的配置信息)
	 */
	public static String deleteConfigByFSUID(String FSUID){
		int count = GosuncnService.deleteConfigByFSUID(FSUID);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
	}
	
	/**
	 * 根据FSUID查询对应配置信息
	 */
	public static List<String> selectConfigByFSUID(String FSUID){
		List<String> list = GosuncnService.selectConfigByFSUID(FSUID);
		return list;
	}
	
	/**
	 * 添加fsu注册信息
	 */
	public static String insertLogin(Map<String,String> map){
		int count = GosuncnService.insertLogin(map);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
		
	}
	
	/**
	 * 根据fsuId修改注册信息
	 */
	public static boolean updateLogin(Map<String,String> map){
		int count = GosuncnService.updateLogin(map);
		return count>0?true:false;
	}
	
	/**
	 * 查询fsu注册信息用于维持心跳
	 */
	public static List<Map<String,String>> selectForGetLogin(){
		List<Map<String,String>> list = GosuncnService.selectForGetLogin();
		return list;
	}
	
	/**
	 * 添加fsu配置信息
	 */
	public static String insertConfig(List<Map<String,String>> list){
		int count = GosuncnService.insertConfig(list);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
	}
	
	/**
	 * 添加fsu告警
	 */
	public static String insertAlarm(List<Map<String,String>> list){
		int count = GosuncnService.insertAlarm(list);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
		
	}
	
	/**
	 * 添加监控点数据
	 */
	public static String insertData(List<Map<String,String>> list){
		int count = GosuncnService.insertData(list);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
	}
	
	/**
	 * 添加监控点历史数据
	 */
	public static String insertHData(List<Map<String,String>> list){
		int count = GosuncnService.insertHData(list);
		if(count>0){
			return "success";
		}else{
			return "failure";
		}
	}
	
	/**
	 * 查询传感器表中是否有相同FSUID的数据，有则删除
	 */
	public static String updateFSUID(String FSUID){
		int count = GosuncnService.selectByFSUID(FSUID);
		if(count>0){
			int result = GosuncnService.deleteByFSUID(FSUID);
			if(result>0){
				return "success";
			}else{
				return "failure";
			}
		}else{
			return "none data";
		}
		
	}
	
	/*
	 * 环控告警页面部分
	 */
	/**
	 * 查询所有告警信息
	 */
	@RequestMapping(value="/alarmlist",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String temp=request.getParameter("deviceIds");	
		List<String> list=null;
		if(temp!=null && !"".equals(temp)){
			list = Arrays.asList(temp.split(","));		
		}
		
		String alarmlevel=request.getParameter("alarmLevel");
		String alarmFlag=request.getParameter("alarmFlag");
		String bsLevel = request.getParameter("bsLevel");
		String bsArea = request.getParameter("bsArea");
		if(!"".equals(alarmFlag) && "0".equals(alarmFlag)){
			alarmFlag="";
		}else if("1".equals(alarmFlag)){
			alarmFlag="BEGIN";
		}else if("2".equals(alarmFlag)){
			alarmFlag="END";
		}
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		String tempArea = "全部区域";
		map.put("tempArea", tempArea);
		
		map.put("alarmlevel", alarmlevel);
		map.put("alarmFlag", alarmFlag);
		map.put("bsLevel", bsLevel);
		map.put("bsArea", bsArea);
		map.put("start", start);
		map.put("limit", limit);
		map.put("deviceIds", list);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",GosuncnService.countEMHAlarm(map));
		result.put("items", GosuncnService.selectEMHAlarm(map));
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
	 * 查询环控传感器告警统计
	 */
	@RequestMapping(value="/alarmForDev",method = RequestMethod.GET)
	public void selectByDev(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("alarmDevice",GosuncnService.selectByDevice());
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
	 * 查询环控级别告警统计
	 */
	@RequestMapping(value="/alarmForLevel",method = RequestMethod.GET)
	public void selectByLevel(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("alarmLevel", GosuncnService.selectByAlarmLevel());
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
	 * 查询所有站的环控通断情况
	 */
	@RequestMapping(value="/selectFor4EMH",method = RequestMethod.GET)
	public void selectFor4EMH(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> listEMH4 = GosuncnService.selectFor4EMH();				
		result.put("success", success);
		result.put("items", listEMH4);
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
	 * 查询所有站的环控通断情况
	 */
	@RequestMapping(value="/selectForEMH",method = RequestMethod.GET)
	public void selectForEMH(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> listEMH4 = GosuncnService.selectFor4EMH();
		List<Map<String,Object>> listEMH3 = SqlServerService.selectConnectStatusForEMH3();
		/*for(int i=0;i<listEMH4.size();i++){
			Map<String,String> temp4Map = listEMH4.get(i);
			for(int j=0;j<listEMH3.size();j++){
				Map<String, Object> temp3Map = listEMH3.get(j);
				String tempbsId = temp4Map.get("bsId");
				String tempJFNode = (String) temp3Map.get("JFNode");
				int bsId = Integer.parseInt(tempbsId);
				int JFNode = Integer.parseInt(tempJFNode);
				if(bsId==JFNode){
					String State = (String) temp3Map.get("State");
					if("true".equalsIgnoreCase(State)){
						temp4Map.remove("siteId");
						temp4Map.put("siteId", tempbsId);
					}else{
						temp4Map.remove("siteId");
						temp4Map.put("siteId", "");
					}
				}
			}
		}*/
		result.put("success", success);
		result.put("items4", listEMH4);
		result.put("items3", listEMH3);
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
	 * 查询环控历史数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/emhHistory",method = RequestMethod.GET)
	public void emhHistory(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		Calendar cal = Calendar.getInstance();
		int temp = cal.get(Calendar.MONTH)+1;
		String currentMonth;
		String nextMonth = "";
		if(temp<10){
			currentMonth="0"+temp;
		}else{
			currentMonth=Integer.toString(temp);
		}
		String bsId=request.getParameter("bsId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		if(!"".equals(startTime)){
			currentMonth=startTime.substring(5, 7);
		}
		if(!"".equals(startTime) && !"".equals(endTime)){
			if(!endTime.substring(5, 7).equals(startTime.substring(5, 7))){
				nextMonth=endTime.substring(5, 7);
				nextMonth="xhgmnet_emh_sensor_history"+nextMonth;
			}		
		}
		currentMonth="xhgmnet_emh_sensor_history"+currentMonth;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		System.out.println("startTime为："+startTime+"endTime为："+endTime+"currentMonth为："+currentMonth+"nextMonth为："+nextMonth);
		map.put("bsId", bsId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("start", start);
		map.put("limit", limit);
		map.put("currentMonth", currentMonth);
		map.put("nextMonth", nextMonth);
		HashMap result = new HashMap();
		
		int data1 = GosuncnService.emhHistoryCount(map);
		List<HashMap<String, String>> data2;
		if(data1>0){
			data2 = GosuncnService.emhHistory(map);
		}else{
			data2 = null;
		}
		
		result.put("success", success);
		result.put("totals",data1);
		result.put("items", data2);
		response.setContentType("application/json;charset=utf-8");  
		response.setHeader("Refresh", "1");  
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 查询所有站的环控通断情况
	 */
	@RequestMapping(value="/getForNvrChannelInfo",method = RequestMethod.POST)
	public void getForNvrChannelInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody List<Map<String, String>> list){
		this.success=true;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("list", list);
		GosuncnService.insertNVRChannels(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
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
	 * 查询所有NVR通道信息
	 */
	@RequestMapping("/selectNVRChannels")
	public void selectNVRChannels(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		
		List<Map<String,String>> list = GosuncnService.selectNVRChannels();
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", list);
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
