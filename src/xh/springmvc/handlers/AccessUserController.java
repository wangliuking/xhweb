package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Severity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tcpServer.ServerDemo;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AccessUserBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AccessUserService;
import xh.mybatis.service.WebLogService;


@Controller
@RequestMapping(value="/access")
public class AccessUserController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(AccessUserController.class);
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 接入用户列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		/*String bsId=request.getParameter("bsId");
		String name=request.getParameter("name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("name", name);
		map.put("start", start);
		map.put("limit", limit);*/
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",AccessUserService.accessUserList().size());
		result.put("items", AccessUserService.accessUserList());
		result.put("app", ServerDemo.mThreadList.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 添加接入用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	public void insert(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String user=request.getParameter("formData").replace("on", "1");
		AccessUserBean bean=GsonUtil.json2Object(user, AccessUserBean.class);
		int rst=AccessUserService.insert(bean);
		if(rst==0){
			message="该用户已经存在";
		}else if(rst==1){
			message="新增接入用户成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增接入用户，userId="+bean.getUser_id());
			WebLogService.writeLog(webLogBean);
		}else{
			message="操作失败";
		}
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("result",rst);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 修改接入用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateAccessUser",method = RequestMethod.POST)
	public void updateAccessUser(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String user=request.getParameter("formData").replace("on", "1");
		AccessUserBean bean=GsonUtil.json2Object(user, AccessUserBean.class);
		int rst=AccessUserService.updateAccessUser(bean);
		if(rst==1){
			message="修改接入用户成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("修改接入用户，userId="+bean.getUser_id());
			WebLogService.writeLog(webLogBean);
		}else{
			message="操作失败";
		}
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("result",rst);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除接入用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteByUserId",method = RequestMethod.POST)
	public void deleteByUserId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String userId=request.getParameter("userId");
		List<String> list = new ArrayList<String>();
		String[] ids=userId.split(",");
		for (String str : ids) {
			list.add(str);
		}
		int rst=AccessUserService.deleteByUserId(list);
		if(rst==1){
			message="删除接入用户成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除接入用户，userId="+userId);
			WebLogService.writeLog(webLogBean);
		}else{
			message="操作失败";
		}
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("result",rst);
		result.put("message", message);
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
