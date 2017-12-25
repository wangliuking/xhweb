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
import xh.mybatis.bean.bsLinkConfigBean;
import xh.mybatis.bean.bsrConfigBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.ServerStatusService;
import xh.mybatis.service.SqlServerService;
import xh.mybatis.service.WebLogService;
@Controller
@RequestMapping(value="/monitor")
public class MonitorController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MonitorController.class);
	private FlexJSON json=new FlexJSON();
	private BsstationBean bsstationBean=new BsstationBean();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bsoffline",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		int emhstart=funUtil.StringToInt(request.getParameter("emhstart"));
		int emhlimit=funUtil.StringToInt(request.getParameter("emhlimit"));
		
		Map<String,Object> emhParamMap=new HashMap<String, Object>();
		emhParamMap.put("start", emhstart);
		emhParamMap.put("limit", emhlimit);
		
		
		List<Map<String,Object>>  bs=BsstationService.monitorBsofflineList();
		List<Map<String,Object>>  msc=ServerStatusService.unusualStatus(0);
		List<Map<String,Object>>  threeEmh=SqlServerService.EmhAlarmList();
		List<Map<String,Object>>  fourEmh=BsStatusService.fourEmhAlarmList(emhParamMap);
		List<Map<String,Object>> emh=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : threeEmh) {
			emh.add(map);
		}
		for (Map<String, Object> map : fourEmh) {
			emh.add(map);
		}
		
		HashMap result = new HashMap();
		result.put("bsList", bs);
		result.put("bsListCount", bs.size());
		result.put("mscList", msc);
		result.put("mscCount", msc.size());
		result.put("emhList", emh);
		result.put("emhListCount", emh.size());
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
