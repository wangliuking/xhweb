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
import xh.mybatis.service.RoadService;

@Controller
@RequestMapping(value="/road")
public class RoadController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RoadController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	private RoadService roadService = new RoadService();
	
	/**
	 * 查询路测基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String positionArea=request.getParameter("positionArea");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("positionArea", positionArea);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",roadService.roadCount(map));
		result.put("items", roadService.roadInfo(map));
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
