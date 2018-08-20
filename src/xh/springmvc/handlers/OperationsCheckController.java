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
import xh.mybatis.service.OperationsCheckService;


@Controller
@RequestMapping("/check")
public class OperationsCheckController {
	private boolean success;
	private String message;
	protected final Log log = LogFactory.getLog(LendController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public void data(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", FunUtil.loginUser(request));
	    map.put("roleType",FunUtil.loginUserInfo(request).get("roleType"));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",OperationsCheckService.count(map));
		result.put("items", OperationsCheckService.dataList(map));
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
