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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import xh.org.listeners.SingLoginListener;

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
		
		String power=SingLoginListener.
				getLoginUserPowerMap().
				get(request.getSession().getId()).get("o_check_inspection").toString();
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loginuser", funUtil.loginUser(request));
		map.put("power", power);
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
		bean.setUploadUser(funUtil.loginUser(request));
		bean.setCreatetime(funUtil.nowDate());
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
			
			 List<Map<String,Object>> list=WebUserServices.userlistByPower("o_check_inspection");
				
				for (Map<String, Object> map : list) {
					EmailBean emailBean=new EmailBean();
					emailBean.setTitle("运维巡检");
					emailBean.setRecvUser(map.get("user").toString());
					emailBean.setSendUser(funUtil.loginUser(request));
					emailBean.setContent("运维巡检计划已经上传，如需整改请通知抢修组");
					emailBean.setTime(funUtil.nowDate());
					EmailService.insertEmail(emailBean);
				}
			
			
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
	@RequestMapping("/uploadOne")
	@ResponseBody
	public void uploadOne(@RequestParam("pathName2") MultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath="/Resources/upload/"+data[0] + "/" + data[1] + "/" + data[2];
		// 判断是否有相同日期下的文件夹

		File targetFile = new File(path, name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";

		} catch (Exception e) {
			e.printStackTrace();
			this.success=false;
			this.message = "文件上传失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + name);
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
	@RequestMapping("/uploadTwo")
	@ResponseBody
	public void uploadTwo(@RequestParam("pathName3") MultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath="/Resources/upload/"+data[0] + "/" + data[1] + "/" + data[2];
		// 判断是否有相同日期下的文件夹

		File targetFile = new File(path, name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";

		} catch (Exception e) {
			e.printStackTrace();
			this.success=false;
			this.message = "文件上传失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + name);
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
	@RequestMapping("/uploadThree")
	@ResponseBody
	public void uploadThree(@RequestParam("pathName4") MultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath="/Resources/upload/"+data[0] + "/" + data[1] + "/" + data[2];
		// 判断是否有相同日期下的文件夹

		File targetFile = new File(path, name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";

		} catch (Exception e) {
			e.printStackTrace();
			this.success=false;
			this.message = "文件上传失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + name);
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
	 * 填写巡检记录相关信息，汇总上报项目负责人
	 * @param request
	 * @param response
	 */
	@RequestMapping("/check2")
	public void check2(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		String user1=request.getParameter("user1");
		InspectionBean bean=GsonUtil.json2Object(formData, InspectionBean.class);
		EmailBean emailBean=new EmailBean();
		
		bean.setUser2(funUtil.loginUser(request));
		bean.setTime2(funUtil.nowDate());
		bean.setStatus(2);
		log.info(bean.toString());
		
		int rslt=InspectionServices.check2(bean);
		
		if(rslt==1){
			this.success=true;
			this.message="操作成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("填写巡检记录相关信息，汇总上报项目负责人");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("运维巡检");
			emailBean.setRecvUser(user1);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("巡检组已经提交巡检记录，请查收！");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="操作失败";
			
		}

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
	 * 抢修组将抢修情况汇总记录到平台，并发送消息通知巡检组
	 * @param request
	 * @param response
	 */
	@RequestMapping("/check3")
	public void check3(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		/*String user1=request.getParameter("user1");*/
		InspectionBean bean=GsonUtil.json2Object(formData, InspectionBean.class);
		EmailBean emailBean=new EmailBean();
		
		bean.setUser3(funUtil.loginUser(request));
		bean.setTime3(funUtil.nowDate());
		bean.setStatus(3);
		log.info(bean.toString());
		
		int rslt=InspectionServices.check3(bean);
		
		if(rslt==1){
			this.success=true;
			this.message="操作成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传抢修情况汇总记录");
			WebLogService.writeLog(webLogBean);
			
			
			
			List<String> roleIdlist=new ArrayList<String>();
			for(int a=0;a<bean.getRoleId().split(",").length;a++){
				roleIdlist.add(bean.getRoleId().split(",")[a]);
			}
			List<Map<String,Object>> list=WebUserServices.userlistByRoleType(roleIdlist);
		
			
			for(int i=0;i<list.size();i++){
				Map<String,Object> usermap=list.get(i);
				EmailBean email=new EmailBean();
				email.setTitle("运维巡检");
				email.setRecvUser(usermap.get("user").toString());
				email.setSendUser(funUtil.loginUser(request));
				email.setContent("抢修情况汇总记录");
				email.setTime(funUtil.nowDate());
				EmailService.insertEmail(email);	
			}
			
			
			
		}else{
			this.success=false;
			this.message="操作失败";
			
		}

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
	 * 巡检组整理填写巡检记录相关信息，汇总上报项目负责人，流程结束
	 * @param request
	 * @param response
	 */
	@RequestMapping("/check4")
	public void check4(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		String user1=request.getParameter("user1");
		InspectionBean bean=GsonUtil.json2Object(formData, InspectionBean.class);
		EmailBean emailBean=new EmailBean();
		
		bean.setUser4(funUtil.loginUser(request));
		bean.setTime4(funUtil.nowDate());
		bean.setStatus(4);
		log.info(bean.toString());
		
		int rslt=InspectionServices.check4(bean);
		
		if(rslt==1){
			this.success=true;
			this.message="操作成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("填写巡检记录相关信息，汇总上报项目负责人");
			WebLogService.writeLog(webLogBean);
			
			emailBean.setTitle("运维巡检");
			emailBean.setRecvUser(user1);
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent("巡检组已经提交巡检记录，请查收！");
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			
			
		}else{
			this.success=false;
			this.message="操作失败";
			
		}

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
	

}
