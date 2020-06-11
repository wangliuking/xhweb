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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.MenuService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/web")
public class MenuController {

	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(MenuController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	
/*	vpn菜单*/
	
	@RequestMapping(value="/vpnMenu", method = RequestMethod.GET)
	public void vpnMenu(HttpServletRequest request, HttpServletResponse response) {
		/*int roleId=funUtil.StringToInt(request.getParameter("roleId"));*/
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId").toString();
		String pId=usermap.get("pId")!=null?usermap.get("pId").toString():"";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("vpnId", vpnId);
		map.put("pId", pId);
		/*log.info("vpnmap---->"+map);*/
		HashMap result = new HashMap();
		result.put("items", MenuService.vpnMenu(map));
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
	 * web角色菜单配置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/menu", method = RequestMethod.GET)
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		int roleId=funUtil.StringToInt(request.getParameter("roleId"));

		HashMap result = new HashMap();
		result.put("items", MenuService.menuChild(roleId,0));
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
	 * 系统菜单获取
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/webMenu", method = RequestMethod.GET)
	public void webMenu(HttpServletRequest request, HttpServletResponse response) {
		
		String role=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId()).get("roleId").toString();
		int roleId=Integer.parseInt(role);
		log.info("roleId:--->"+roleId);
		Map<String,Object> paramap=new HashMap<String, Object>();
		paramap.put("roleId", roleId);
		Map<String,Object> menuMap=MenuService.menuList(paramap);
		menuMap.put("roleId", roleId);
		HashMap result = new HashMap();
		result.put("items", menuMap);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/updateMenu")
	public void updateMenu(HttpServletRequest request,
			HttpServletResponse response) {
		/*String[] nochecks = request.getParameter("nochecks").split(",");
		String[] checks = request.getParameter("checks").split(",");*/
		String roleId = request.getParameter("roleId");
		String id=request.getParameter("id");
		String checked=request.getParameter("checked");
		
	/*	List<String> listChecks=new ArrayList<String>();
		for(int i=0;i<checks.length;i++){
			listChecks.add(checks[i]);
		}
		List<String> listNoChecks=new ArrayList<String>();
		for(int i=0;i<nochecks.length;i++){
			listNoChecks.add(nochecks[i]);
		}*/
		
		List<String> listChecks=new ArrayList<String>();
		listChecks.add(id);
		
		
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("roleId", roleId);
		paraMap.put("idlist", listChecks);
		paraMap.put("checked", checked);
		
		MenuService.updateMenu(paraMap);
		
		/*Map<String,Object> paraMap2=new HashMap<String, Object>();
		paraMap2.put("roleId", roleId);
		paraMap2.put("idlist", listNoChecks);
		paraMap2.put("checked", false);
		MenuService.updateMenu(paraMap2);*/
		
		
		
		

		HashMap result = new HashMap();
		/* result.put("items", ); */
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
