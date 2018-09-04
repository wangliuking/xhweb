package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.PublicVariableService;
import xh.mybatis.service.WebLogService;


@Controller
@RequestMapping(value="/bsAlarm")
public class BsAlarmController {
	private boolean success;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BsAlarmController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	private String message;
	
	@RequestMapping(value = "/voiceAlarm", method = RequestMethod.GET)
	public void voiceAlarm(HttpServletRequest request,
			HttpServletResponse response) {

		HashMap result = new HashMap();
		try {
			result.put("totals",PublicVariableService.getVoiceAlarmCount());			
			PublicVariableService.setVoiceAlarmCount(0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/sureAlarm", method = RequestMethod.POST)
	public void sureAlarm(HttpServletRequest request,
			HttpServletResponse response) {
		String ids=request.getParameter("id");
		
		String[] alarmIds=ids.split(",");
		List<String> list=new ArrayList<String>();
		
		for(int i=0,j=alarmIds.length;i<j;i++){
			list.add(alarmIds[i]);
		}
		
		int rs = BsAlarmService.sureAlarm(list);
		
		if(rs>0){
			message="确认告警成功";
			this.success=true;
		}else{
			message="确认失败";
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String[] level=request.getParameter("level").split(",");
			String[] type=request.getParameter("type").split(",");
			String[] category=request.getParameter("category").split(",");
			String[] status=request.getParameter("status").split(",");
			
			int start=funUtil.StringToInt(request.getParameter("start"));
			int limit=funUtil.StringToInt(request.getParameter("limit"));
			List<String> a=new ArrayList<String>();
			List<String> b=new ArrayList<String>();
			List<String> c=new ArrayList<String>();
			List<String> d=new ArrayList<String>();
			for (String str : level) {
				a.add(str);
			}
			for (String str : type) {
				b.add(str);
			}
			for (String str : category) {
				c.add(str);
			}
			for (String str : status) {
				d.add(str);
			}
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("level", a);
			map.put("type", b);
			map.put("category", c);
			map.put("status", d);
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
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String[] level=request.getParameter("level").split(",");
		String[] type=request.getParameter("type").split(",");
		List<String> a=new ArrayList<String>();
		List<String> b=new ArrayList<String>();
		for (String str : level) {
			a.add(str);
		}
		for (String str : type) {
			b.add(str);
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("level", a);
		map.put("type", b);
		
		
		list = BsAlarmService.bsAlarmLevelChart(map);
		
		/*log.info("告警列表->"+Arrays.toString(list.toArray()));*/
	
		for(int i=0;i<list.size();i++){
			HashMap map2 = new HashMap();
			map2=list.get(i);
			if(map2.get("name").toString().equals("1")){
				map2.put("name", "紧急告警");
			}
			if(map2.get("name").toString().equals("2")){
				map2.put("name", "主要告警");
			}
			if(map2.get("name").toString().equals("3")){
				map2.put("name", "次要告警");
			}
			if(map2.get("name").toString().equals("4")){
				map2.put("name", "一般告警");
			}
			list.set(i, map2);
		}
	
		/*HashMap mapResult = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			String status = list.get(i).get("severity").toString();
			
			if (status.equals("1")) {
				mapResult.put("v_1", list.get(i).get("num").toString());

			}
			if (status.equals("2")) {
				mapResult.put("v_2", list.get(i).get("num").toString());
				
			}
			if (status.equals("3")) {
				mapResult.put("v_3", list.get(i).get("num").toString());
				
			}
		}*/
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", "");
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
		List<HashMap> list2 = new ArrayList<HashMap>();
		
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String[] level=request.getParameter("level").split(",");
		String[] type=request.getParameter("type").split(",");
		List<String> a=new ArrayList<String>();
		List<String> b=new ArrayList<String>();
		for (String str : level) {
			a.add(str);
		}
		for (String str : type) {
			b.add(str);
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("level", a);
		map.put("type", b);
		
		list = BsAlarmService.bsAlarmTypeChart(map);
		HashMap<String, Object> map2=new HashMap<String, Object>();
		for (HashMap mapx : list) {
			map2.put("v_"+mapx.get("neType").toString(),mapx.get("num"));
		}
		
		
		for (int i = 1; i <=4; i++) {
			HashMap mapResult = new HashMap();
			if(map2.get("v_"+i)==null){
				mapResult.put("name", alarmText(i));
				mapResult.put("value", 0);
			}else{
				mapResult.put("name", alarmText(i));
				mapResult.put("value", map2.get("v_"+i));
			}
			list2.add(mapResult);
			
			
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",list2.size());
		result.put("items", list2);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);

		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String alarmText(int type){
		String text="未知";
		if(type==1){
			text="基站";
		}else if(type==2){
			text="交换中心";
		}else if(type==3){
			text="调度台";
		}else if(type==4){
			text="网管";
		}else if(type==5){
			text="分组中心";
		}else if(type==6){
			text="综合业务网关";
		}else if(type==7){
			text="INIGW";
		}else if(type==8){
			text="短信中心";
		}else if(type==9){
			text="PDT";
		}else if(type==10){
			text="鉴权中心";
		}else if(type==11){
			text="录音";
		}else if(type==12){
			text="模拟网关";
		}else if(type==13){
			text="MPT基站";
		}else if(type==14){
			text="NTP服务器";
		}else if(type==15){
			text="路由器";
		}else if(type==16){
			text="数字交叉连接设备";
		}else if(type==17){
			text="同播服务器";
		}else if(type==18){
			text="DSGW";
		}else if(type==19){
			text="以太网交换机";
		}else if(type==20){
			text="WEB调度服务器";
		}else if(type==21){
			text="短信业务网关";
		}else if(type==22){
			text="室外基站";
		}else if(type==23){
			text="PDT室外基站";
		}else if(type==24){
			text="桥接基站";
		}else if(type==29){
			text="以太网E1网桥";
		}else if(type==30){
			text="桥接中心";
		}else if(type==32){
			text="媒体服务器";
		}else{
			
		}
		return text;
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
