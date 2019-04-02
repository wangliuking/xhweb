package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebRoleService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/center")
public class MemberCenterController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MemberCenterController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 邮件记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/email/list",method = RequestMethod.GET)
	@ResponseBody
	public void emailInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String status=request.getParameter("status");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("status", status);
		map.put("loginUser", funUtil.loginUser(request));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",EmailService.emailCount(map));
		result.put("items", EmailService.emailInfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/email/noReadEmailCount",method = RequestMethod.GET)
	@ResponseBody
	public void noReadEmailCount(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginUser", funUtil.loginUser(request));
		
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",EmailService.noReadEmailCount(map));
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
	 * 标记已读
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/email/update",method = RequestMethod.POST)
	@ResponseBody
	public void updateById(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		int rslt=EmailService.updateById(list);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		result.put("result", rslt);
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
	 * 删除邮件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/email/del",method = RequestMethod.POST)
	public void deleteById(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		int rslt=EmailService.deleteById(list);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		result.put("result", rslt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/user/LoginUserInfo")
	@ResponseBody
	public void LoginUserInfo(HttpServletRequest request, HttpServletResponse response){
		String user="",role="";
		if (SingLoginListener.isOnline(request.getSession())) {
			user=funUtil.loginUser(request);
		}
		WebUserBean bean=new WebUserBean();
		bean=WebUserServices.selectUserByUser(user);
		HashMap result = new HashMap();
		result.put("success", true);
		result.put("items",bean);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/email/updateVoice",method = RequestMethod.POST)
	@ResponseBody
	public void updateVoice(HttpServletRequest request, HttpServletResponse response){
		int rslt=EmailService.updateVoice();
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		result.put("result", rslt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/email/noVoiceEmailCount",method = RequestMethod.GET)
	@ResponseBody
	public void noVoiceEmailCount(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginUser", funUtil.loginUser(request));
		int rslt=EmailService.noVoiceEmailCount(map);
		HashMap result = new HashMap();
		result.put("count", rslt);
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
