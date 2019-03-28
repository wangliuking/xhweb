package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetCheckBean;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.AssetCheckServices;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

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
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();
		Map<String,Object> power = SingLoginListener.getLoginUserPowerMap().get(request.getSession().getId());
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user);
		map.put("power", power.get("o_check_change"));
		map.put("roleId", roleId);
		map.put("roleType", userbean.getRoleType());

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
			
			FunUtil.sendMsgToUserByPower("o_check_change", 2, "资产核查", "资产管理员提交了核查资产申请，请审核相关信息", request);
			
			
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
			if(status==1){
				FunUtil.sendMsgToOneUser(account,"资产核查","你提交的资产核查申请领导已经处理，请尽快完成核查相关工作", request);
			}else{
				FunUtil.sendMsgToOneUser(account,"资产核查","你提交的资产核查申请被拒绝了", request);
			}
			
			
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
			FunUtil.sendMsgToOneUser(user1,"资产核查","资产管理员提交了核查结果，请审核结果", request);

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
	@RequestMapping("/check3")
	public void check3(HttpServletRequest request, HttpServletResponse response){
		int id=Integer.parseInt(request.getParameter("id"));		
		int status=Integer.parseInt(request.getParameter("checked"));
		String time=funUtil.nowDate();
		String note3=request.getParameter("note3");
		String account=request.getParameter("account");
		AssetCheckBean bean=new AssetCheckBean();
		bean.setId(id);
		/*bean.setUser1(funUtil.loginUser(request));*/
		bean.setTime3(time);
		bean.setNote3(note3);
		bean.setStatus(status);
		
				
		int rlt=AssetCheckServices.check3(bean);	
		
		if(rlt==1){
			this.success=true;
			this.message="审核成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("管理部门领导审核资产核查申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(account,"资产核查","你提交的核查结果已反馈，请注意查收", request);
			
			
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
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("filePath") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));

		
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/assetCheck/";
		String fileName = file.getOriginalFilename();
		
		/*File de=new File(fileName);
		file.renameTo(new   File(name+".jpg"));   //改名  
*/		
		
		// String fileName = new Date().getTime()+".jpg";
		log.info("path==>" + path);
		log.info("fileName==>" + fileName);
		System.out.println("fileName==>" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";
			fileName=data+"-"+fileName;
			File rename = new File(path,fileName);
			targetFile.renameTo(rename);
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path + "/" + fileName);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 发送邮件(指定收件人)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoSingle(String recvUser, String content,
			HttpServletRequest request) {
		// ----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("资产变更");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		// ----END
	}

	/**
	 * 发送邮件(指定权限)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoGroup(String powerstr, int roleId, String content,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("powerstr", powerstr);
		map.put("roleId", roleId);
		List<Map<String, Object>> items = WebUserServices.userlistByPowerAndRoleId(map);
		log.info("邮件发送：" + items);
		for (Map<String, Object> item : items) {
			// ----发送通知邮件
			EmailBean emailBean = new EmailBean();
			emailBean.setTitle("资产变更");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}


}
