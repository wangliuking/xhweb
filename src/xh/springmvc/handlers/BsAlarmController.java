package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.WebLogService;


@Controller
@RequestMapping(value="/bsAlarm")
public class BsAlarmController {
	private boolean success;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WebLogController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询Tetra告警
	 * @param request
	 * 	查询条件		
	 * 		bsId：基站ID(支持模糊查询)
	 * 		name：基站名称(支持模糊查询)
	 * 		dealEn：警告消息状态：
	 * 			0：未处理
	 * 			1：已恢复
	 * 			3：不限制
	 * @param response
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public void selectAllBsAlarm(HttpServletRequest request, HttpServletResponse response){
		HashMap result = new HashMap();
		BsAlarmService bsAlarmService = new BsAlarmService();
		try {
			request.setCharacterEncoding("utf-8");
			/*String bsId=request.getParameter("bsId");
			String name=request.getParameter("name");
			String dealEn=request.getParameter("dealEn");*/
			int start=funUtil.StringToInt(request.getParameter("start"));
			int limit=funUtil.StringToInt(request.getParameter("limit"));
			Map<String, Object> map=new HashMap<String, Object>();
			/*map.put("bsId", bsId);
			map.put("name", name);
			map.put("dealEn", dealEn);*/
			map.put("start", start);
			map.put("limit", limit);
			
			result.put("data", bsAlarmService.selectBsAlarmList(map));
			result.put("totals",bsAlarmService.BsAlarmCount(map));
			
			
			String jsonstr = FlexJSON.Encode(result);
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(jsonstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 故障等级统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/data/bsAlarmLevelChart", method = RequestMethod.GET)
	public void bsAlarmLevelByChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;

		List<HashMap> list = new ArrayList<HashMap>();
		list = BsAlarmService.bsAlarmLevelChart();
		for (int i = 0; i < list.size(); i++) {
			String status = list.get(i).get("name").toString();
			HashMap mapResult = new HashMap();
			if (status.equals("1")) {
				mapResult.put("name", "一级故障");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
			if (status.equals("2")) {
				mapResult.put("name", "二级故障");
				mapResult.put("value", list.get(i).get("value").toString());
				list.set(i, mapResult);
			}
			if (status.equals("3")) {
				mapResult.put("name", "三级故障");
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
	 * 故障类型统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/data/bsAlarmTypeChart", method = RequestMethod.GET)
	public void bsAlarmTypeByChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;

		List<HashMap> list = new ArrayList<HashMap>();
		list = BsAlarmService.bsAlarmTypeChart();
		for (int i = 0; i < list.size(); i++) {
			String status = list.get(i).get("name").toString();
			HashMap mapResult = new HashMap();
			mapResult.put("name", status);
			mapResult.put("value", list.get(i).get("value").toString());
			list.set(i, mapResult);
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
	 * 确认告警信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/identifyBsA", method = RequestMethod.POST)
	public void identifyBsAlarm(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("bsId");
//		List<String> list = new ArrayList<String>();
//		String[] ids=bsId.split(",");
//		for (String str : ids) {
//			list.add(str);
//		}
		
		BsAlarmService.identifyBsAlarmById(id);
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(3);
		webLogBean.setContent("确认基站Tetra告警信息，bsId="+id);
		WebLogService.writeLog(webLogBean);
		HashMap result = new HashMap();
		this.success=true;
		System.out.println("---_-_----");
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
