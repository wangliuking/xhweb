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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.MenuService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping("/web")
public class MenuController {
	
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MenuController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	@RequestMapping("/menu")
	public void menu(HttpServletRequest request, HttpServletResponse response){
		/*int roleId=funUtil.StringToInt(request.getParameter("roleId"));*/
		/*Map<String, Object> map=new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("user", user);*/
		
		HashMap result = new HashMap();
		result.put("items", MenuService.menuChild());
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
