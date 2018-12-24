package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.Top10Services;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/top10")
public class Top10Controller {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(Top10Controller.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@RequestMapping("/offline_bs")
	@ResponseBody
	public Map<String,Object> offline_bs(HttpServletRequest request, HttpServletResponse response){
		int type=Integer.parseInt(request.getParameter("type"));
		int jd=Integer.parseInt(request.getParameter("jd"));
		String time=request.getParameter("time");	
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("time", time);
		map.put("jd", time+jd);

		HashMap result = new HashMap();
		result.put("data", Top10Services.offline_bs(map));
		return result;
		
	}
	@RequestMapping("/elec")
	@ResponseBody
	public Map<String,Object> elec(HttpServletRequest request, HttpServletResponse response){
		int type=Integer.parseInt(request.getParameter("type"));
		int jd=Integer.parseInt(request.getParameter("jd"));
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("time", time);
		map.put("jd", time+jd);
		HashMap result = new HashMap();
		result.put("data", Top10Services.elec(map));
		return result;
		
	}
	
}
