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
import xh.mybatis.bean.AssetCheckBean;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.AssetCheckServices;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;

@Controller
@RequestMapping("/asset")
public class AssetCheckController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(AssetCheckController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	/**
	 * 资产核查申请列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/assetCheckList")
	public void assetCheckList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		/*String fileName=request.getParameter("filename");
		String contact=request.getParameter("contact");
		int status=funUtil.StringToInt(request.getParameter("status"));*/
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",AssetCheckServices.count());
		result.put("items", AssetCheckServices.assetCheckList(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping("/apply")
	public void apply(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		AssetCheckBean bean=GsonUtil.json2Object(formData, AssetCheckBean.class);
		EmailBean emailBean=new EmailBean();
		bean.setAccount(funUtil.loginUser(request));
		bean.setApplyTime(funUtil.nowDate());
		log.info(bean.toString());		
		int rlt=AssetCheckServices.apply(bean);
		
		if(rlt==1){
			this.success=true;
			this.message="资产核查申请提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("资产核查申请提交");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("资产核查申请");
			emailBean.setRecvUser(String.valueOf(10002));
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("请审核资产核查申请");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="资产核查申请提交失败";
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
	 * 管理部门领导审核
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkOne")
	public void checkOne(HttpServletRequest request, HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		
		int status=Integer.parseInt(request.getParameter("checked"));
		String time1=funUtil.nowDate();
		String note1=request.getParameter("note1");
		String account=request.getParameter("account");
		AssetCheckBean bean=new AssetCheckBean();
		EmailBean emailBean=new EmailBean();
		bean.setId(id);
		bean.setUser1(funUtil.loginUser(request));
		bean.setTime1(time1);
		bean.setNote1(note1);
		bean.setStatus(status);
		
				
		int rlt=AssetCheckServices.checkOne(bean);	
		
		if(rlt==1){
			this.success=true;
			this.message="审核成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("管理部门领导审核资产核查申请");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("资产核查");
			emailBean.setRecvUser(account);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("你提交的资产核查申请已经处理");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="资产核查申请审核失败";
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
	 * 资产核查
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkAsset")
	public void checkAsset(HttpServletRequest request, HttpServletResponse response){
		String ids[]=request.getParameter("id").split(",");
		int status=Integer.parseInt(request.getParameter("status"));
		String checkTime=funUtil.nowDate();
		String checkResult=request.getParameter("checkResult");
		
		log.info("ids="+ids);
		for(int i=0;i<ids.length;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", ids[i]);
			map.put("status", status);
			map.put("checkTime", checkTime);
			map.put("checkResult", checkResult);
			map.put("checkUser", funUtil.loginUser(request));
		    BusinessService.checkAsset(map);
		}
		
		this.success=true;
		this.message="核查记录已保存";
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(4);
		webLogBean.setContent("资产核查");
		WebLogService.writeLog(webLogBean);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", 1);
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
	 * 确认完成核查
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkTwo")
	public void check2(HttpServletRequest request, HttpServletResponse response){
		EmailBean emailBean=new EmailBean();
		int id=Integer.parseInt(request.getParameter("id"));
		String user1=request.getParameter("user1");
		String note2=request.getParameter("note");
		String fileName=request.getParameter("fileName");
		String filePath=request.getParameter("filePath");
		String time2=funUtil.nowDate();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("note2", note2);
		map.put("time2", time2);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		int rsl=AssetCheckServices.checkTwo(map);
		
		if(rsl==1){
			this.success=true;
			this.message="确认完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(4);
			webLogBean.setContent("资产核查结束");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("资产核查");
			emailBean.setRecvUser(user1);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("资产核查反馈文件");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);

		}else{
			this.success=false;
			this.message="确认失败";
		}
		
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
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
	 * 新增工作记录
	 * @param request
	 * @param response
	 *//*
	@RequestMapping("/addwork")
	public void addwork(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");	
		WorkBean bean=GsonUtil.json2Object(formData, WorkBean.class);
		EmailBean emailBean=new EmailBean();
		bean.setUploadUser(funUtil.loginUser(request));
		log.info(bean.toString());		
		int rlt=WorkServices.addwork(bean);
		
		if(rlt==1){
			this.success=true;
			this.message="工作记录提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("上传工作记录");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("签收工作记录");
			emailBean.setRecvUser(bean.getRecvUser());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("请签收工作记录");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="工作记录提交失败";
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
	*//**
	 * 签收工作记录
	 * @param request
	 * @param response
	 *//*
	@RequestMapping("/signwork")
	public void signWork(HttpServletRequest request, HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));
		String recvUser=request.getParameter("recvUser");
		int rlt=WorkServices.signWork(id);
		EmailBean emailBean=new EmailBean();
		
		if(rlt==1){
			this.success=true;
			this.message="签收成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("签收工作记录，id=" +id);
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("工作记录已经签收");
			emailBean.setRecvUser(recvUser);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("工作记录已经签收");
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
		
	}*/

}
