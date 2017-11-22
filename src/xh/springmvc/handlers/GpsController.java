package xh.springmvc.handlers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import xh.mybatis.service.GpsService;

@Controller
@RequestMapping(value="/gps")
public class GpsController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(GpsController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询gps信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void gpsInfo(HttpServletRequest request, HttpServletResponse response){
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
		String srcId=request.getParameter("srcId");
		String dstId=request.getParameter("dstId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		if(!"".equals(startTime)){
			currentMonth=startTime.substring(5, 7);
		}
		if(!"".equals(startTime) && !"".equals(endTime)){
			if(!endTime.substring(5, 7).equals(startTime.substring(5, 7))){
				nextMonth=endTime.substring(5, 7);
				nextMonth="xhgmnet_gpsinfo"+nextMonth;
			}		
		}
		currentMonth="xhgmnet_gpsinfo"+currentMonth;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("srcId", srcId);
		map.put("dstId", dstId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("start", start);
		map.put("limit", limit);
		map.put("currentMonth", currentMonth);
		map.put("nextMonth", nextMonth);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",GpsService.gpsCount(map));
		result.put("items", GpsService.gpsInfo(map));
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
}
