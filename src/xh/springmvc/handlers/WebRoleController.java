package xh.springmvc.handlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebRoleBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.bean.WebUserRoleBean;
import xh.mybatis.service.MenuService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebRoleService;
import xh.mybatis.service.WebUserRoleService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping("/web")
public class WebRoleController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WebRoleController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询所有角色
	 * @param request
	 * @param response
	 */
	@RequestMapping("/role/allRoleList")
	public void allRoleList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("roleId", userbean.getRoleId());
		paraMap.put("parentId", userbean.getParentId());
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals","");
		result.put("items", WebRoleService.roleByAll(paraMap));
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
	 * 根据角色类型查找角色列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/role/roleTypeByList",method=RequestMethod.GET)
	public void roleTypeByList(HttpServletRequest request, HttpServletResponse response){
		int roleType=funUtil.StringToInt(request.getParameter("roleType"));
		HashMap result = new HashMap();
		result.put("totals",WebRoleService.roleTypeByList(roleType).size());
		result.put("items", WebRoleService.roleTypeByList(roleType));
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
	 * 添加角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/role/add",method = RequestMethod.POST)
	public synchronized void addRole(HttpServletRequest request, HttpServletResponse response){
		String role=request.getParameter("role");
		int roleId=funUtil.StringToInt(request.getParameter("roleId"));
		int roleType=funUtil.StringToInt(request.getParameter("roleType"));
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		String createTime=funUtil.nowDate();
		WebRoleBean bean=new WebRoleBean();
		
		bean.setRoleType(roleType);
		bean.setRoleId(roleId);
		bean.setRole(role);
		bean.setParentId(userbean.getRoleId());
		bean.setCreateTime(createTime);
		int flag=WebRoleService.addRole(bean);
		if (flag==0) {						
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增web角色，role="+role);
			WebLogService.writeLog(webLogBean);
			WebRoleBean roleBean=WebRoleService.roleOne(String.valueOf(roleId));
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("parentId", roleBean.getParentId());
			log.info("add group->"+map);
			if(MenuService.menuExistsByParentId(map)==0){	
				log.info("add group  parent menu   is not have->"+map);
				if(MenuService.menuExists(map)==0){
					log.info("add group  parent menu 1->"+map);
					MenuService.addMenu();
					MenuService.updateMenuRoleId(roleId);
				}
			}else{
				log.info("add group  parent menu  is have->"+map);
				if(MenuService.menuExists(map)==0){
					log.info("add group  parent menu 2->"+map);
					MenuService.addParentMenu(String.valueOf(roleBean.getParentId()));
					MenuService.updateMenuRoleId(roleId);
				}else{
					
				}
			}
			
			
			this.success=true;
			this.message="添加角色成功";
		}else if(flag==1){
			this.success=false;
			this.message="角色已经存在";
		}else {
			this.success=false;
			this.message="添加角色失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 修改角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/role/update",method = RequestMethod.POST)
	public synchronized void updateRole(HttpServletRequest request, HttpServletResponse response){
		String roleId=request.getParameter("roleId");
		int roleType=funUtil.StringToInt(request.getParameter("roleType"));
		String role=request.getParameter("role");
		String createTime=funUtil.nowDate();
		WebRoleBean bean=new WebRoleBean();
		bean.setRoleType(roleType);
		bean.setRoleId(funUtil.StringToInt(roleId));
		bean.setRole(role);
		bean.setCreateTime(createTime);
		int flag=WebRoleService.updateByroleId(bean);
		WebRoleBean roleBean=WebRoleService.roleOne(roleId);
		if (flag==1) {
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改web角色，role="+role);
			webLogBean.setCreateTime(createTime);
			WebLogService.writeLog(webLogBean);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("roleId", roleBean.getRoleId());
			map.put("parentId", roleBean.getParentId());
			

			if(MenuService.menuExistsByParentId(map)==0){			
				if(MenuService.menuExists(map)==0){
					MenuService.addMenu();
					MenuService.updateMenuRoleId(Integer.parseInt(roleId));
				}
			}else{
				if(MenuService.menuExists(map)==0){
					MenuService.addParentMenu(String.valueOf(roleBean.getParentId()));
					MenuService.updateMenuRoleId(Integer.parseInt(roleId));
				}else{
					
				}
			}
			this.message="修改角色成功";
		}else {
			this.success=false;
			this.message="修改角色失败";
		}
		this.success=true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 删除用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/role/del",method = RequestMethod.POST)
	public void deleteByUserId(HttpServletRequest request, HttpServletResponse response){
		String roleId=request.getParameter("roleId");
		List<String> list = new ArrayList<String>();
		String[] ids=roleId.split(",");
		for (String str : ids) {
			list.add(str);
		}
		int rslt=WebRoleService.deleteByRoleId(list);
		if(rslt>0){
			MenuService.deleteMenu(list);
		}
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(3);
		webLogBean.setContent("删除web角色，roleId="+roleId);
		webLogBean.setCreateTime(funUtil.nowDate());
		WebLogService.writeLog(webLogBean);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		result.put("result", rslt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
			log.debug("删除角色==>"+jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
