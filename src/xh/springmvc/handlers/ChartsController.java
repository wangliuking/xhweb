package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.ChartService;

@Controller
@RequestMapping(value = "/chart")
public class ChartsController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(BsstationController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	/**
	 * 终端在线状态统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/userStatusByChart", method = RequestMethod.GET)
	public void userStatusByChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;

		List<HashMap> list = new ArrayList<HashMap>();
		list = ChartService.userStatusByChart();
		for (int i = 0; i < list.size(); i++) {
			String status = list.get(i).get("name").toString();
			HashMap mapResult = new HashMap();
			if (status.equals("0")) {
				mapResult.put("name", "离线");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
			if (status.equals("1")) {
				mapResult.put("name", "在线");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", "");
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 基站状态统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsStatusByChart", method = RequestMethod.GET)
	public void statusByChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bsId", "");
		map.put("name", "");

		List<HashMap> list = new ArrayList<HashMap>();
		list = ChartService.statusByChart();
		for (int i = 0; i < list.size(); i++) {
			String status = list.get(i).get("name").toString();
			HashMap mapResult = new HashMap();
			if (status.equals("0")) {
				mapResult.put("name", "基站断开");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
			if (status.equals("1")) {
				mapResult.put("name", "正常基站");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
			if (status.equals("2")) {
				mapResult.put("name", "未启用");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BsstationService.bsCount(map));
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);

		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 资产状态图形统计分析
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/assetStatusByChart", method = RequestMethod.GET)
	public void AssetStatusByChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", ChartService.AssetStatusByChart());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 基站分布图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsPostion", method = RequestMethod.GET)
	public void bsPostion(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		List<Map> list = new ArrayList<Map>();
		List<HashMap> listLatLng = ChartService.bsPostionLatLng();
		String geoCoord = "";
		Map<String, double[]> geocoordMap = new HashMap<String, double[]>();
		for (HashMap hashMap : listLatLng) {			
			try {
				String lat=hashMap.get("lat").toString();
				String lng=hashMap.get("lng").toString();
				double[] value = new double[] {
						Double.parseDouble(lng),
						Double.parseDouble(lat)};
				geocoordMap.put(hashMap.get("name").toString(), value);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			
			
		}
		list.add(geocoordMap);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("bsInfoData", ChartService.bsPostionInfo());
		result.put("bsLatLngData", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		//log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * -当天呼叫时间统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/hourCallTime", method = RequestMethod.GET)
	public void hourCallTime(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		List<int[]> listTime = new ArrayList<int[]>();
		List<int[]> listNum = new ArrayList<int[]>();
		Map<String, Object> map=new HashMap<String, Object>();
		
		String nowTime=funUtil.nowDate();
		String tableName="xhgmnet_calllist"+nowTime.split(" ")[0].split("-")[1];
		
		log.debug("table_name=="+tableName);
		map.put("tableName", tableName);
		map.put("starttime", funUtil.nowDateNoTime());
		map.put("endtime",funUtil.nowDate());
		List<HashMap> hour = ChartService.hourCallTime(map);
		for (HashMap hashMap : hour) {			
			try {
				int[] value1 = new int[]{
						Integer.parseInt(hashMap.get("date").toString()),
						Integer.parseInt(hashMap.get("time").toString())};
				int[] value2 = new int[]{
						Integer.parseInt(hashMap.get("date").toString()),
						Integer.parseInt(hashMap.get("num").toString())};
				listTime.add(value1);
				listNum.add(value2);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}						
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("callTime", listTime);
		result.put("callNum", listNum);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
