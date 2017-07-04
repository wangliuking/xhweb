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
import xh.mybatis.service.UserStatusHService;

@Controller
@RequestMapping(value="/userstatush")
public class UserStatusHController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(UserStatusHController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		String userid=request.getParameter("userid");
		String regstatus=request.getParameter("regstatus");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("regstatus", regstatus);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",UserStatusHService.Count(map));
		result.put("items", UserStatusHService.radioUserBusinessInfo(map));
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
