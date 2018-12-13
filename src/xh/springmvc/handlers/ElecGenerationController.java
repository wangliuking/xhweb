package xh.springmvc.handlers;

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

import xh.func.plugin.FunUtil;
import xh.mybatis.service.ElecGenerationService;

@Controller
@RequestMapping("/elec")
public class ElecGenerationController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ElecGenerationController.class);

	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String bsId=request.getParameter("bsId");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("bsId", bsId);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",ElecGenerationService.count(map));
		result.put("items", ElecGenerationService.list(map));
		return result;
		
	}
	/*@RequestMapping(value="/gorder",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gorder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		int bsid=funUtil.StringToInt(request.getParameter("bsid"));
		String userid=request.getParameter("userid");
		String note=request.getParameter("note");
		String time=request.getParameter("time");
		String bsname=request.getParameter("bsname");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("bsId", bsId);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",ElecGenerationService.count(map));
		result.put("items", ElecGenerationService.list(map));
		return result;
		
	}*/

}
