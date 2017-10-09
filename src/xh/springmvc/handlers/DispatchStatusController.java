package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;

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
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.ServerStatusService;
@Controller
@RequestMapping(value="/status")
public class DispatchStatusController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(DispatchStatusController.class);
	private WebLogBean webLogBean=new WebLogBean();
	
	@RequestMapping(value="/dispatch",method = RequestMethod.GET)
	public void dispatch(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",DispatchStatusService.dispatchstatus().size());
		result.put("items", DispatchStatusService.dispatchstatus());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
