package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import xh.mybatis.service.RadioUserSeriaService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.TalkGroupService;

@Controller
@RequestMapping(value="/talkgroup")
public class TalkGroupController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(TalkGroupController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		String talkgroupid=request.getParameter("talkgroupid");
		String eName=request.getParameter("eName");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("talkgroupid", talkgroupid);
		map.put("eName", eName);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.Count(map));
		result.put("items", TalkGroupService.radioUserBusinessInfo(map));
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
	 * 查询无线用户互联属性
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/mscList",method = RequestMethod.GET)
	public void vpnList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.vpnList().size());
		result.put("items", TalkGroupService.vpnList());
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
	 * 查询无线用户互联属性
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/vpnList",method = RequestMethod.GET)
	public void vpnList2(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.vpnList2().size());
		result.put("items", TalkGroupService.vpnList2());
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
	 * 添加组
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public void insertRadioUser(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int count = 0;
		int userId=funUtil.loginUserId(request);
		HashMap<String,Object> map = new HashMap<String,Object>();
		Enumeration rnames=request.getParameterNames();
		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
			String thisName=e.nextElement().toString();
			String thisValue=request.getParameter(thisName);
			map.put(thisName, thisValue);
		}
		map.put("userId",userId);
		map.put("time",funUtil.nowDate());
		if(map.get("TalkgroupID") == null
				&& map.get("TalkgroupIDS")!=null && map.get("TalkgroupIDE")!=null
				&& Integer.parseInt(map.get("TalkgroupIDS").toString())<Integer.parseInt(map.get("TalkgroupIDE").toString())){
			int TalkgroupIDS = Integer.parseInt(map.get("TalkgroupIDS").toString());
			int TalkgroupIDE = Integer.parseInt(map.get("TalkgroupIDE").toString());
			for(int i = TalkgroupIDS;i <= TalkgroupIDE; i++){
				map.put("TalkgroupID",i);
				count+=TalkGroupService.insertTalkGroup(map);
			}
		}
		else {
			count=TalkGroupService.insertTalkGroup(map);
			System.out.println("------" + count);
		}
			HashMap result = new HashMap();
			result.put("success", success);
			result.put("result",count);
			String jsonstr = json.Encode(result);
			try {
				response.getWriter().write(jsonstr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
