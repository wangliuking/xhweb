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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.InspectionBean;
import xh.mybatis.service.InspectionServices;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;

@Controller
@RequestMapping("/inspection")
public class InspectionController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(InspectionController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	/**
	 * 获取运维巡检
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String fileName=request.getParameter("filename");
		String contact=request.getParameter("contact");
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("fileName", fileName);
		map.put("contact", contact);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",InspectionServices.count(map));
		result.put("items", InspectionServices.list(map));
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
	 * 新增运维巡检
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		InspectionBean bean=GsonUtil.json2Object(formData, InspectionBean.class);
		EmailBean emailBean=new EmailBean();
		bean.setRecvUser("10002");
		bean.setUploadUser(funUtil.loginUser(request));
		log.info(bean.toString());		
		int rlt=InspectionServices.add(bean);
		
		if(rlt==1){
			this.success=true;
			this.message="运维巡检提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("上传运维巡检");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("运维巡检");
			emailBean.setRecvUser("10002");
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("运维巡检计划已经上传，如需整改请通知抢修组");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="运维巡检提交失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rlt);
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
	/**
	 * 签收运维巡检
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sign")
	public void sign(HttpServletRequest request, HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String note=request.getParameter("note");
		int status=Integer.parseInt(request.getParameter("check"));
		String[] roleId=request.getParameter("roleType").split(",");
		String recvUser=request.getParameter("recvUser");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("note1", note);
		map.put("status",status);
		map.put("user1",funUtil.loginUser(request));
		map.put("time1", funUtil.nowDate());
		
		
		int rlt=0;
		EmailBean emailBean=new EmailBean();
		
		if(status==1){
			rlt=InspectionServices.sign(map);
			if(rlt==1){
				this.success=true;
				this.message="操作成功";
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(5);
				webLogBean.setContent("签收运维巡检，id=" +id);
				WebLogService.writeLog(webLogBean);
				
				emailBean.setTitle("运维巡检");
				emailBean.setRecvUser(recvUser);
				emailBean.setSendUser(funUtil.loginUser(request));
				emailBean.setContent("运维巡检计划表已查看，不需要整改");
				emailBean.setTime(funUtil.nowDate());
				EmailService.insertEmail(emailBean);
				
			}else{
				this.success=false;
				this.message="签收失败";
			}
		}else{
			rlt=InspectionServices.sign(map);
			if(rlt==1){
				this.success=true;
				this.message="操作成功";
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(4);
				webLogBean.setContent("运维巡检计划表已查看，需要抢修组，等进行配合整改，id=" +id);
				WebLogService.writeLog(webLogBean);
				List<String> roleIdlist=new ArrayList<String>();
				for(int a=0;a<roleId.length;a++){
					roleIdlist.add(roleId[a]);
				}
				List<Map<String,Object>> list=WebUserServices.userlistByRoleType(roleIdlist);
			
				
				for(int i=0;i<list.size();i++){
					Map<String,Object> usermap=list.get(i);
					EmailBean email=new EmailBean();
					email.setTitle("运维巡检");
					email.setRecvUser(usermap.get("user").toString());
					email.setSendUser(funUtil.loginUser(request));
					email.setContent("请相关负责人将抢修情况汇总记录到平台，并通知巡检组");
					email.setTime(funUtil.nowDate());
					EmailService.insertEmail(email);	
				}
			}else{
				this.success=false;
				this.message="签收失败";
			}
		}
		
		
		
		

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rlt);
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
