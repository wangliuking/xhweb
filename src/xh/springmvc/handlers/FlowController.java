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
import xh.mybatis.service.FlowServices;
import xh.mybatis.service.Top10Services;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/flow")
public class FlowController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(FlowController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@RequestMapping("/month_user_flow")
	@ResponseBody
	public Map<String,Object> month_user_flow(HttpServletRequest request, HttpServletResponse response){
		String time=request.getParameter("time");	
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);

		HashMap result = new HashMap();
		result.put("data", FlowServices.month_user_flow(map));
		return result;
		
	}
	
}
