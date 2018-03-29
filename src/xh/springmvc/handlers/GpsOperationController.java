package xh.springmvc.handlers;

import java.io.IOException;
import java.util.Calendar;
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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.GpsOperationService;
import xh.mybatis.service.RadioUserService;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping(value="/gpsOperation")
public class GpsOperationController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(GpsOperationController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询gps信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void gpsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		//获取用户的vpnId	
		HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId = tempMap.get("vpnId").toString();	
		Map<String, Object> tMap=new HashMap<String, Object>();
		tMap.put("vpnId", vpnId);
		List<String> list = RadioUserService.selectCIdByVpnId(tMap);	
		String srcId=request.getParameter("srcId");
		String dstId=request.getParameter("dstId");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		map.put("srcId", srcId);
		map.put("dstId", dstId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",GpsOperationService.gpsOperationCount(map));
		result.put("items", GpsOperationService.gpsOperationInfo(map));
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
