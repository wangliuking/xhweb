package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.WebLogService;
@Controller
@RequestMapping(value="/bs")
public class BsstationController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BsstationController.class);
	private FlexJSON json=new FlexJSON();
	private BsstationBean bsstationBean=new BsstationBean();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String bsId=request.getParameter("bsId");
		String name=request.getParameter("name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("name", name);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.bsCount(map));
		result.put("items", BsstationService.bsInfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/alarmList",method = RequestMethod.GET)
	public void bsOfflineList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		List<HashMap<String,Object>> list=BsstationService.bsOfflineList();
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",list.size());
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
	 * 查询所有基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/allBsInfo",method = RequestMethod.GET)
	public void allBsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BsstationService.allBsInfo());
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
	 * 添加基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public void addBs(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		/*int bsId=funUtil.StringToInt(request.getParameter("bsId"));
		String name=request.getParameter("name");
		int type=funUtil.StringToInt(request.getParameter("type"));
		int level=funUtil.StringToInt(request.getParameter("level"));
		String lat=request.getParameter("lat");
		String lng=request.getParameter("lng");
		bsstationBean.setBsId(bsId);
		bsstationBean.setName(name);
		bsstationBean.setType(type);
		bsstationBean.setLevel(level);
		bsstationBean.setLat(lat);
		bsstationBean.setLng(lng);*/
		
		
		String jsonData=request.getParameter("formData");
        BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
        /*if (bean.getOpenen()==0) {
			bean.setStatus(2);
		}*/
        log.info("data==>"+bean.toString());
		int count=BsstationService.insertBs(bean);
		if (count==0) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
		}
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
	 * 修改基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public void updateBs(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
       /* if (bean.getOpenen()==0) {
			bean.setStatus(2);
		}*/
        log.info("data==>"+bean.toString());
		int count=BsstationService.updateBs(bean);
		if (count==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
		}
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
	 * 删除基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public void deleteBs(HttpServletRequest request, HttpServletResponse response){
		String bsId=request.getParameter("bsId");
		List<String> list = new ArrayList<String>();
		String[] ids=bsId.split(",");
		for (String str : ids) {
			list.add(str);
		}
		BsstationService.deleteBsByBsId(list);
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(3);
		webLogBean.setContent("删除基站，bsId="+bsId);
		WebLogService.writeLog(webLogBean);
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
			BsstationService bsStationService = new BsstationService();
			String temp = request.getParameter("zone");			
			byte[] b=temp.getBytes("ISO-8859-1");
			String test=new String(b,"utf-8");
			String[] zonetemp = test.split(",");
			List<String> zone = Arrays.asList(zonetemp);
			List<HashMap<String, String>> listMap = bsStationService.bsByArea(zone);
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
			BsstationService bsStationService = new BsstationService();
			String temp = request.getParameter("level");
			byte[] b=temp.getBytes("ISO-8859-1");
			String level=new String(b,"utf-8");
			List<HashMap<String, String>> listMap = bsStationService.bsByLevel(level);
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
	 * 查询基站区域信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/area")
	@ResponseBody
	public void selectAllArea(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectAllArea();
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
	 * 查询基站级别信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/level")
	@ResponseBody
	public void selectLevel(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectLevel();
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
	 * 查询所有基站信息，用于首页显示
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/data",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllBsStation(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectAllBsStation();
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
	 * 根据ID查询选中基站的信息，用于首页模态框显示
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/dataById",method=RequestMethod.GET)
	@ResponseBody
	public void selectBsStationById(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,List<HashMap<String, String>>> map = new HashMap<String,List<HashMap<String, String>>>();
		BsstationService bsStationService = new BsstationService();
		String str = request.getParameter("bsId");
		int bsId = Integer.parseInt(str);
		try {
			List<HashMap<String, String>> bsStationBean = bsStationService.selectBsStationById(bsId);
			map.put("items", bsStationBean);
			String dataById = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataById);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询单个基站话务量及其他业务
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/top5Calllist")
	@ResponseBody
	public void selectCalllist(HttpServletRequest request, HttpServletResponse response){	
		try {
			Calendar cal = Calendar.getInstance();
			int temp = cal.get(Calendar.MONTH)+1;
			String currentMonth;
			if(temp<10){
				currentMonth="0"+temp;
			}else{
				currentMonth=Integer.toString(temp);
			}
			currentMonth="xhgmnet_calllist"+currentMonth;
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectCalllistById(currentMonth);
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
	 * 查询单个基站排队数及其他业务
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/top5Channel")
	@ResponseBody
	public void selectChannel(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectChannelById();
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
	@RequestMapping("/map/roadtest")
	@ResponseBody
	public void selectRoadTest(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectRoadTest();
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
		map.put("smallLng", smallLng);
		map.put("bigLng", bigLng);
		map.put("smallLat", smallLat);
		map.put("bigLat", bigLat);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.rectangleCount(map));
		result.put("items", BsstationService.rectangle(map));
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
