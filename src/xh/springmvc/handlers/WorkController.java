package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping("/work")
public class WorkController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WorkController.class);
	private FlexJSON json=new FlexJSON();
	
	@RequestMapping("/worklist")
	public void worklist(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String recvUser=request.getParameter("user");
		String contact=request.getParameter("contact");
		String tel=request.getParameter("tel");
		String note=request.getParameter("note");
		String fileName=request.getParameter("fileName");
		int roleId=funUtil.StringToInt(request.getParameter("roleId"));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",WebUserServices.userlistByRoleId(roleId).size());
		result.put("items", WebUserServices.userlistByRoleId(roleId));
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
