package xh.springmvc.handlers;

import java.io.IOException;
import java.rmi.RemoteException;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinamobile.fsuservice.Test;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.GosuncnService;
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
		Map<String,String> map = new HashMap<String,String>();
		map.put("bsId", bsId);
		List<Map<String,String>> list = GosuncnService.selectCameraIpByBsId(map);
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
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("alarmlevel", alarmlevel);
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
	
}
