package xh.springmvc.handlers;

import java.io.IOException;
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

import com.tcpBean.ErrProTable;
import com.tcpServer.ServerDemo;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.OrderService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;


@Controller
@RequestMapping("/order")
public class OrderController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(OrderController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	
	
	//派单列表
	@RequestMapping(value="/orderlist", method = RequestMethod.GET)
	public void orderlist(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();
	
		
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put("loginuser", funUtil.loginUser(request));*/
		map.put("start", start);
		map.put("limit", limit);
		map.put("user",user);
		map.put("roleId", roleId);
		map.put("type", 10);
		HashMap result = new HashMap();
		result.put("items",OrderService.orderList(map));
		result.put("totals", OrderService.orderListCount(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//用户列表
	@RequestMapping(value="/userlist", method = RequestMethod.GET)
	public void userlist(HttpServletRequest request, HttpServletResponse response) {
		HashMap result = new HashMap();
		result.put("items",OrderService.userList());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//派单处理未完成数量
	@RequestMapping(value="/orderNoComCount", method = RequestMethod.GET)
	public void orderNoComCount(HttpServletRequest request, HttpServletResponse response) {		
		Map<String,Object> map=new HashMap<String, Object>();
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();
		map.put("type", 3);
		map.put("user",user);
		map.put("roleId", roleId);
		HashMap result = new HashMap();
		result.put("totals", OrderService.orderListCount(map));
		
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/updateOrder", method = RequestMethod.POST)
	public void updateOrder(HttpServletRequest request, HttpServletResponse response) {
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String from=request.getParameter("from");
		String bsid=request.getParameter("bsId");
		String zbdldm=request.getParameter("zbdldm");
		this.success=true;
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", 2);
		map.put("id", id);
		
		int code=OrderService.updateOrder(map);
		
		if(code>0){
			if(from.equals("数据分析")){
				ErrProTable bean=new ErrProTable();
				bean.setBsid(bsid);
				bean.setFrom(from);
				bean.setZbdldm(zbdldm);
				bean.setStatus("已处理");
				OrderService.updateSfOrder(bean);
			}
			
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("确认派单已解决");
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
		}else{
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/writeOrder", method = RequestMethod.POST)
	public void writeOrder(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		ErrProTable bean=GsonUtil.json2Object(fromData, ErrProTable.class);
		bean.setSerialnumber(FunUtil.RandomWord(8));
		bean.setOrderAccount(funUtil.loginUser(request));
		log.info("ErrProTab->"+bean.toString());
		
		int id=bean.getId();
		
		
		this.success=true;
		
		int code=OrderService.addOrder(bean);
		
		if(code>0){
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("派单");
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("status", 1);
			map.put("id", id);
			log.info("id->"+id);
			if(id>0){
				OrderService.updateBsFault(map);	
				
			}
			
			
			ServerDemo demo=new ServerDemo();
			demo.startMessageThread(bean.getUserid(), bean);
			
			
		}else{
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/rewriteOrder", method = RequestMethod.POST)
	public void rewriteOrder(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		ErrProTable bean=GsonUtil.json2Object(fromData, ErrProTable.class);
		//bean.setSerialnumber(FunUtil.RandomWord(8));
		
		log.info("ErrProTab->"+bean.toString());
		ServerDemo demo=new ServerDemo();
		if(demo.getmThreadList().size()>0){
			demo.startMessageThread(bean.getUserid(), bean);
			this.message="发送成功";
			this.success=true;
		}else{
			this.message="没有找到用户";
			this.success=false;
		}
		
		
		this.success=true;

		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
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
