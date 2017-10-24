package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.EventReportBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.EventReportServices;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;

@Controller
@RequestMapping("/eventReport")
public class EventReportController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(EventReportController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	/**
	 * 获取事件上报
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	public void worklist(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String fileName=request.getParameter("filename");
		String contact=request.getParameter("contact");
		String fileType = request.getParameter("fileType");
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("fileName", fileName);
		map.put("contact", contact);
		map.put("status", status);
		map.put("fileType", fileType);
		map.put("start", start);
		map.put("limit", limit);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",EventReportServices.count(map));
		result.put("items",EventReportServices.eventReportlist(map));
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
	 * 新增事件上报
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addEventReport")
	public void addwork(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		EventReportBean bean=GsonUtil.json2Object(formData, EventReportBean.class);
		EmailBean emailBean=new EmailBean();
		bean.setRecvUser("10002");
		bean.setUploadUser(funUtil.loginUser(request));
		log.info(bean.toString());		
		int rlt=EventReportServices.addEventReport(bean);
		
		if(rlt==1){
			this.success=true;
			this.message="事件报告提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("上传事件报告");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("事件报告");
			emailBean.setRecvUser("10002");
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("请签收"+bean.getFileType());
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="事件报告提交失败";
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
	 * 签收事件上报
	 * @param request
	 * @param response
	 */
	@RequestMapping("/signEventReport")
	public void signWork(HttpServletRequest request, HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String recvUser=request.getParameter("recvUser");
		int rlt=EventReportServices.signEventReport(id);
		EmailBean emailBean=new EmailBean();
		
		if(rlt==1){
			this.success=true;
			this.message="签收成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("签收事件报告，id=" +id);
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("事件报告");
			emailBean.setRecvUser(recvUser);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("事件报告已经签收");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
		}else{
			this.success=false;
			this.message="签收失败";
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
