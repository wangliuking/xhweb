package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
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
import xh.mybatis.service.EventService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/web")
public class EventController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(MemberCenterController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	/**
	 * 查询事件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public void selectEvent(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", EventService.selectEvent().size());
		result.put("items", EventService.selectEvent());
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
	 * 删除事件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delevent", method = RequestMethod.POST)
	public void deleteEvent(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String name = request.getParameter("name");
		int rtl = EventService.deleteEvent(name);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rtl);
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
	 * 添加计划任务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addvent", method = RequestMethod.POST)
	public void insertEvent(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String rule = request.getParameter("rule");
		String dbname = request.getParameter("dbname");
		String time= request.getParameter("time");
		int day = funUtil.StringToInt(request.getParameter("time"))*30;
		String  name = "",comment="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", day);
		map.put("rule", rule);
		map.put("db", "xhgmnet");
		int rtl =0;
		if (dbname.equals("xhgmnet_calllist")) {
			for (int i = 1; i <= 12; i++) {

				dbname = db_name(dbname, i);
				name = "删除" + time + "个月以前的语音数据"+i;
				map.put("dbname", dbname);
				map.put("name", name);
				map.put("timerchar", "Call_Time");
				map.put("db", "xhgmnet_gps_voice");
				
				
				log.info("name-->"+name);
				rtl=EventService.insertEvent(map);
			}

		} else if (dbname.equals("xhgmnet_gpsinfo")) {
			for (int i = 1; i <= 12; i++) {

				dbname = db_name(dbname, i);
				name = "删除" + time + "个月以前的GPS数据"+i;
				map.put("dbname", dbname);
				map.put("name", name);
				map.put("timerchar", "writeTime");
				log.info("name-->"+name);
				map.put("db", "xhgmnet_gps_voice");
				rtl=EventService.insertEvent(map);
			}

		} else if (dbname.equals("xhgmnet_gpsoperation")) {
			name = "删除" + time + "个月以前的gps操作记录数据";
			map.put("dbname", dbname);
			map.put("name", name);
			/*map.put("comment", "中文");*/
			map.put("timerchar", "writeTime");
			map.put("db", "xhgmnet_gps_voice");
			rtl=EventService.insertEvent(map);

		}

		this.success = true;
		this.message = "计划任务添加成功";
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(1);
		webLogBean.setContent("添加计划任务，name=" + name);
		webLogBean.setCreateTime(funUtil.nowDate());
		WebLogService.writeLog(webLogBean);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rtl);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* 数据库表名拼接 */
	public String db_name(String db, int time) {
		String name = db;
		if (time < 10) {
			name += "0" + time;
		} else {
			name += time;
		}
		return name;
	}

}
