package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
		//基站ID转换为动环server表的code
		String bsIdTemp="";
		if(str.length()==1){
			bsIdTemp = "0"+"0"+str;
		}else if(str.length()==2){
			bsIdTemp = "0"+str;
		}else if(str.length()==3){
			bsIdTemp = str;
		}
		try {
			List<HashMap<String, String>> bsStationBean = bsStationService.selectBsStationById(bsId);
			//查询动环信息
			List<HashMap<String, String>> moveController = bsStationService.selectAllEMHById(bsIdTemp);
			map.put("items", bsStationBean);
			map.put("moveController", moveController);
			String dataById = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataById);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
