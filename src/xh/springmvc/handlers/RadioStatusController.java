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
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.RadioStatusService;


@Controller
@RequestMapping("/radio")
public class RadioStatusController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RadioStatusController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询基站下的注册终端列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/status/oneBsRadio",method = RequestMethod.GET)
	@ResponseBody
	public void oneBsRadio(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		int bsId=funUtil.StringToInt(request.getParameter("bsId"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",RadioStatusService.oneBsRadioCount(bsId));
		result.put("items", RadioStatusService.oneBsRadio(map));
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
