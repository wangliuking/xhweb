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
import xh.mybatis.service.ChartService;
import xh.mybatis.service.EmhService;

@Controller
@RequestMapping(value = "/emh")
public class EmhController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(EmhController.class);
	private FlexJSON json = new FlexJSON();
	
	/**
	 * 查询基站环境监控实时状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/oneBsEmh", method = RequestMethod.GET)
	public void oneBsEmh(HttpServletRequest request,HttpServletResponse response) {
		String code=request.getParameter("bsId");
		if (code.length()==1){code="00"+code;}
		else if (code.length()==2){
			code="0"+code;
		}else {
			
		}
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", EmhService.oneBsEmh(code));
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
	 * 基站环境监控实时告警列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsEmhNowStatus", method = RequestMethod.GET)
	public void bsEmhNowStatus(HttpServletRequest request,HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", EmhService.bsEmhNowStatus(map));
		result.put("totals", EmhService.bsEmhNowStatusCount());
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
	 * 基站环境监控历史告警列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bsEmhOldStatus", method = RequestMethod.GET)
	public void bsEmhOldStatus(HttpServletRequest request,HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", EmhService.bsEmhOldStatus(map));
		result.put("totals", EmhService.bsEmhOldStatusCount());
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
