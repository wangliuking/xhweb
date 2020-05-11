package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.func.plugin.MapRemoveNullUtil;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkContactBean;
import xh.mybatis.service.UserNeedService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WorkContactService;


@Controller
@RequestMapping("/WorkContact")
public class WorkContactController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WorkContactController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	@RequestMapping("/data_by_taskId")
	public void data_by_taskId(
			@RequestParam("taskId") String taskId,
			HttpServletRequest request, HttpServletResponse response){
		
		HashMap result = new HashMap();
		result.put("items", WorkContactService.data_by_taskId(taskId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String start_time=request.getParameter("start_time");
		String end_time=request.getParameter("end_time");
		String type=request.getParameter("type");
		String status=request.getParameter("status");
		String key=request.getParameter("key");
		
		String send_user=request.getParameter("send_user");
		String check_user=request.getParameter("check_user");
		String sign_user=request.getParameter("sign_user");
		
		
		
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("limit",limit);
		map.put("start_time",start_time);
		map.put("end_time",end_time);
		map.put("type",type);
		map.put("key",key);
		map.put("status",status);
		map.put("user",FunUtil.loginUser(request));
		map.put("power", FunUtil.loginUserPower(request).get("o_task"));
		map.put("roleType",FunUtil.loginUserInfo(request).get("roleType"));
		map.put("send_user",send_user);
		map.put("check_user",check_user);
		map.put("sign_user",sign_user);
		HashMap result = new HashMap();
		result.put("totals",WorkContactService.list_count(map));
		result.put("items", WorkContactService.list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/list2")
	public void list2(HttpServletRequest request, HttpServletResponse response){
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		String type=request.getParameter("type");
		start=(start-1)*limit;
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("limit",limit);
		map.put("time",time);
		map.put("type",type);
		map.put("user",FunUtil.loginUser(request));
		map.put("power", FunUtil.loginUserPower(request).get("o_task"));
		map.put("roleType",FunUtil.loginUserInfo(request).get("roleType"));
		HashMap result = new HashMap();
		result.put("totals",WorkContactService.list_count(map));
		result.put("data", WorkContactService.list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/codeNum")
	public void codeNum(HttpServletRequest request, HttpServletResponse response){
		String roleType=FunUtil.loginUserInfo(request)!=null?
				FunUtil.loginUserInfo(request).get("roleType").toString():"";
		String year=FunUtil.nowDateNotTime().split("-")[0];
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("user_type",roleType);
		map.put("year",year);
		HashMap result = new HashMap();
		int num=WorkContactService.codeNum(map)+1;
		String code="【"+year+"】 "+num+"号";
		if(roleType.equals("2")){
			code="软中 "+code;
		}else{
			code="亚光 "+code;
		}
		
		result.put("code",code);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException{
		String data=request.getParameter("formData");
		String files=request.getParameter("files");
		Map<String,Object> mm=GsonUtil.json2Object(data, Map.class);
		MapRemoveNullUtil.removeNullValue(mm);
		
		
		WorkContactBean bean=new WorkContactBean();
		BeanUtils.populate(bean, mm);
		WorkContactBean bean2=new WorkContactBean();
		BeanUtils.populate(bean2, mm);
		bean.setAddUser(funUtil.loginUser(request));
		bean.setTaskId(FunUtil.RandomAlphanumeric(20));
		bean.setTime(bean.getTime().split(" ")[0]);
		bean.setUser_type(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString()));
		bean.setContent(bean.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getContent().replaceAll(" ", "&nbsp;"));
		
		
		String savePath="doc/WorkContact/"+FunUtil.nowDateNotTime().split("-")[0];	
		String fileName=FunUtil.MD5(String.valueOf(new Date().getTime()))+".doc";
		String filePath=savePath+"/"+fileName;
		bean.setFile_name(fileName);
		bean.setFile_path(filePath);
		
		bean2.setFile_name(fileName);
		bean2.setFile_path(filePath);
		
		
		bean2.setAddUser(FunUtil.loginUserInfo(request).get("userName").toString());
		Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
		
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		filelist=GsonUtil.json2Object(files, type);
		if(filelist.size()>0){
			for(int i=0;i<filelist.size();i++){
				Map<String,Object> map=filelist.get(i);
				map.put("taskId", bean.getTaskId());
				filelist.set(i, map);
			}
			WorkContactService.addFile(filelist);
		}
		System.out.println(bean);
		
		
		int rst=WorkContactService.add(bean);
		if(rst>0){
			this.message="工作联系单下发成功";
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("工作联系单信息，data=" + bean.getReason());
			WebLogService.writeLog(webLogBean);
			//createFile(bean2,request);			
			if(bean.getUser_type()==3  || bean.getUser_type()==0){
				FunUtil.sendMsgToUserByPower("o_task", 3, "工作联系单", "你有新的工作联系单需要处理", request);
			}else if(bean.getUser_type()==2){
				FunUtil.sendMsgToUserByPower("o_task", 2, "工作联系单", "你有新的工作联系单需要处理", request);
			}
			
			/*if(bean.getUser_type()==3  || bean.getUser_type()==0){
				FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 2, "工作联系单", "有新的工作联系单，请尽快查收", request);	
			}else if(bean.getUser_type()==2){
				FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
			}*/
			
			
			
		}else{
			this.message="工作联系单下发失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		result.put("bean", bean2);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response){
		String data=request.getParameter("formData");
		String files=request.getParameter("files");		
		Map<String,Object> mm=GsonUtil.json2Object(data, Map.class);
		MapRemoveNullUtil.removeNullValue(mm);		
		WorkContactBean bean=new WorkContactBean();
		try {
			BeanUtils.populate(bean, mm);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		bean.setContent(bean.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getContent().replaceAll(" ", "&nbsp;"));
		bean.setTime(bean.getTime().split(" ")[0]);
		bean.setStatus(0);
		int rst=WorkContactService.update(bean);
		if(rst>0){
			this.message="修改成功";
			this.success=true;
		
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改工作联系单");
			FunUtil.sendMsgToOneUser(bean.getCheck_person(), "工作联系单", "工作联系单已经修改，请审核", request);
			Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
			
			List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
			filelist=GsonUtil.json2Object(files, type);
			if(filelist.size()>0){
				for(int i=0;i<filelist.size();i++){
					Map<String,Object> map=filelist.get(i);
					map.put("taskId", bean.getTaskId());										
				}
				Iterator<Map<String,Object>> it = filelist.iterator();
				while (it.hasNext()) {
					if(WorkContactService.isFileExistis(it.next())>0){
						it.remove();
					}						
				}	
			}
			if(filelist.size()>0){
				WorkContactService.addFile(filelist);
			}
			
		}else{
			this.message="修改失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
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
	@RequestMapping("/sign")
	@ResponseBody
	public Map<String,Object> sign(HttpServletRequest request, HttpServletResponse response){
		String taskId=request.getParameter("taskId");
		String addUser=request.getParameter("addUser");
		String check_person=request.getParameter("check_person");
		String reply=request.getParameter("reply");
		WorkContactBean bean=new WorkContactBean();
		bean.setTaskId(taskId);
		bean.setAddUser(addUser);
		bean.setCheckUser(FunUtil.loginUser(request));
		bean.setCheckTime(FunUtil.nowDate());
		bean.setReply(reply);
		int rst=WorkContactService.sign(bean);
		if(rst>0){
			this.message="签收工作联系单成功";
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("签收工作联系单信息，data=" + bean.getTaskId());
			WebLogService.writeLog(webLogBean);
			if(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString())==2){
			  FunUtil.sendMsgToOneUser(addUser, "工作联系单","管理方已经签收了工作联系单", request);
			}else{
		      FunUtil.sendMsgToOneUser(addUser, "工作联系单","服务提供方已经签收了工作联系单", request);
			}
			
		}else{
			this.message="工作联系单签收失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/handle")
	@ResponseBody
	public Map<String,Object> handle(HttpServletRequest request, HttpServletResponse response){
		String taskId=request.getParameter("taskId");
		String addUser=request.getParameter("addUser");
		String checkUser=request.getParameter("checkUser");
		String type=request.getParameter("type");
		String reply=request.getParameter("note");
		
		String files=request.getParameter("files");
		
		
		WorkContactBean bean=new WorkContactBean();
		bean.setTaskId(taskId);
		bean.setAddUser(addUser);
		bean.setCheckUser(checkUser);
		bean.setHandle_time(FunUtil.nowDate());
		bean.setHandle_user(FunUtil.loginUser(request));
		bean.setHandle_note(reply);
		int rst=WorkContactService.handle(bean);
		if(rst>0){
			this.message="填写处理结果成功";
			this.success=true;
			Type filetype = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
			
			List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
			filelist=GsonUtil.json2Object(files, filetype);
			if(filelist.size()>0){
				for(int i=0;i<filelist.size();i++){
					Map<String,Object> map=filelist.get(i);
					map.put("taskId", bean.getTaskId());
					filelist.set(i, map);
				}
				WorkContactService.addHandleFile(filelist);
			}
			
			/*if(type.equals("通信保障")){
				Map<String,Object> mm=new HashMap<String, Object>();
				mm.put("id", taskId);
				mm.put("type", 1);
				
				UserNeedService.update_communication_by_task(mm);
			}*/
			
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("填写处理结果，data=" + bean.getTaskId());
			WebLogService.writeLog(webLogBean);
			if(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString())==2){
			  FunUtil.sendMsgToOneUser(checkUser, "工作联系单","管理方已经填写了处理结果", request);
			}else{
		      FunUtil.sendMsgToOneUser(checkUser, "工作联系单","服务提供方已经填写了处理结果", request);
			}
			
		}else{
			this.message="失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/summary")
	@ResponseBody
	public Map<String,Object> summary(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String taskId=request.getParameter("taskId");
		String addUser=request.getParameter("addUser");
		String checkUser=request.getParameter("checkUser");
		String reply=request.getParameter("summary_note");
		String fileName=request.getParameter("fileName");
		String filePath=request.getParameter("filePath");
		String type=request.getParameter("type");
		String person_num=request.getParameter("person_num");
		String satellite_time=request.getParameter("satellite_time");
		String bus_num=request.getParameter("bus_num");
		String files=request.getParameter("files");
		String time=request.getParameter("ensure_time");
		WorkContactBean bean=new WorkContactBean();
		bean.setTaskId(taskId);
		bean.setAddUser(addUser);
		bean.setCheckUser(checkUser);
		bean.setSummary_time(FunUtil.nowDate());
		bean.setSummary_user(FunUtil.loginUser(request));
		bean.setSummary_note(reply);
		bean.setSummary_fileName(fileName);
		bean.setSummary_filePath(filePath);
		bean.setPerson_num(person_num);
		bean.setEnsure_satellite_time(satellite_time);
		bean.setEnsure_bus_num(bus_num);
	
		int rst=WorkContactService.summary(bean);
		if(rst>0){
			this.message="填写总结成功";
			this.success=true;
			
           Type filetype = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
			
			List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
			filelist=GsonUtil.json2Object(files, filetype);
			if(filelist.size()>0){
				for(int i=0;i<filelist.size();i++){
					Map<String,Object> map=filelist.get(i);
					map.put("taskId", bean.getTaskId());
					filelist.set(i, map);
					String path = request.getSession().getServletContext().getRealPath("");
					if(type.equals("通信保障") && time!=""){
				
						File srcFile = new File(path+"/"+map.get("filePath"));
						String destDir1=request.getSession().getServletContext()
								.getRealPath("/upload/check");
						destDir1=destDir1+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/3/通信保障报告";
						File Path1 = new File(destDir1);
						if (!Path1.exists()) {
							Path1.mkdirs();
						}
						
						String destDir2=request.getSession().getServletContext()
								.getRealPath("/upload/check");
						destDir2=destDir2+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/4/通信保障报告";
						File Path2 = new File(destDir2);
						if (!Path2.exists()) {
							Path2.mkdirs();
						}
						File file1 = new File(destDir1+"/"+map.get("fileName").toString());
						File file2 = new File(destDir2+"/"+map.get("fileName").toString());
						FunUtil.copyFile(srcFile, file1);
						FunUtil.copyFile(srcFile, file2);
					}
					
					
				}
				WorkContactService.addSummaryFile(filelist);
			}
			if(type.equals("通信保障")){
				Map<String,Object> mm=new HashMap<String, Object>();
				mm.put("id", taskId);
				mm.put("type", 1);
				mm.put("satellite_time", satellite_time);
				mm.put("bus_num", bus_num);
				mm.put("person_num", person_num);
				UserNeedService.update_communication_by_task(mm);
			}
			
			
			
			
			
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("填写总结，data=" + bean.getTaskId());
			WebLogService.writeLog(webLogBean);
			if(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString())==2){
			  FunUtil.sendMsgToOneUser(checkUser, "工作联系单","管理方已经填写了本次工单总结", request);
			}else{
		      FunUtil.sendMsgToOneUser(checkUser, "工作联系单","服务提供方已经填写了本次工单总结", request);
			}
			
		}else{
			this.message="失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/check")
	@ResponseBody
	public Map<String,Object> check(HttpServletRequest request, HttpServletResponse response){
		String data=request.getParameter("data");
		String note=request.getParameter("note");
		int state=Integer.parseInt(request.getParameter("state"));
		WorkContactBean bean=GsonUtil.json2Object(data, WorkContactBean.class);
		bean.setCheck_person(FunUtil.loginUser(request));
		bean.setCheck_time(FunUtil.nowDate());
		bean.setStatus(state);
		bean.setNote(note);
		int rst=WorkContactService.check(bean);
		if(rst>0){
			this.message="确认工作联系单成功";
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("确认工作联系单信息，data=" + bean.getTaskId());
			WebLogService.writeLog(webLogBean);
			if(state==-1){
				FunUtil.sendMsgToOneUser(bean.getAddUser(), "工作联系单","你提交的工作联系单被拒绝了:"+bean.getNote(), request);
			}else{
				FunUtil.sendMsgToOneUser(bean.getAddUser(), "工作联系单","你提交的工作联系单审核通过，等待对方签收", request);

				//FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
				
				if(bean.getRecvUnit().indexOf("软件产业发展中心")>-1){
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 2, "工作联系单", "有新的工作联系单，请尽快查收", request);	
				}else{
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);	
				}
				/*
				if(bean.getUser_type()==3  || bean.getUser_type()==0){
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 2, "工作联系单", "有新的工作联系单，请尽快查收", request);	
				}else if(bean.getUser_type()==2){
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
				}*/
			}
			bean.setCheck_person(FunUtil.loginUserInfo(request).get("userName").toString());
			
		}else{
			this.message="工作联系单确认失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		result.put("bean",bean);
		return result;
	}	
	@RequestMapping("/cancel")
	@ResponseBody
	public Map<String,Object> cancel(@RequestParam("id") int id,HttpServletRequest request, HttpServletResponse response){

		int rst=WorkContactService.cancel(id);
		int type=Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString());
		if(rst>0){
			this.message="撤销工作联系单成功";
			this.success=true;
			//FunUtil.sendMsgToUserByGroupPower("recv_work_contact", type, "工作联系单", "工作联系单被撤销", request);
			
		}else{
			this.message="工作联系单撤销失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/delFile")
	@ResponseBody
	public Map<String,Object> delFile(@RequestParam("id") int id,HttpServletRequest request, HttpServletResponse response){
		int rst=WorkContactService.delFile(id);
		if(rst>0){
			
			this.success=true;
			
		}else{
			this.success=false;
		}		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/del_summary_file")
	@ResponseBody
	public Map<String,Object> del_summary_file(
			@RequestParam("name") String name,
			@RequestParam("path") String path,
			HttpServletRequest request, HttpServletResponse response){
		
		File file=new File(path);
		if(file.exists()){
			file.delete();
			System.out.println("文件存在");
		}else{
			System.out.println("文件不存在");
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	@RequestMapping("/del")
	@ResponseBody
	public Map<String,Object> del(HttpServletRequest request, HttpServletResponse response){
		String[] ids=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String str : ids) {
			list.add(str);
		}
		int rst=WorkContactService.del(list);
		if(rst>0){
			this.message="删除成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除工作联系单："+ids);
		}else{
			this.message="删除失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);
		return result;
	}	
	public void createFile(WorkContactBean bean,HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/template/工作联系单.doc";
		String savePath=request.getSession().getServletContext().getRealPath("doc/WorkContact/");
		savePath +=FunUtil.nowDateNotTime().split("-")[0];	;
		
		System.out.println("地址："+savePath);
		String filePath=savePath+"/"+bean.getFile_name();
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File source=new File(path);
		File dst=new File(filePath);
		try {
			FunUtil.copyFile(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/handleFile")
	@ResponseBody
	public void handleFile(@RequestParam("handle_file_upload") CommonsMultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")+ "/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath = "/upload/" + data[0] + "/" + data[1] + "/"+ data[2];
		String now=FunUtil.MD5(String.valueOf(System.currentTimeMillis()));
        String rename=now+name;
		if(uploadFile(request,file,path,rename)){
			this.success = true;
			this.message = "文件上传成功";
			
		}else{
			this.success = false;
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + rename);
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
	public static Boolean uploadFile(
			HttpServletRequest request, 
			MultipartFile file,
			String filePath,
			String rename) {
		String fileName = file.getOriginalFilename();
		File targetFile = new File(filePath, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			 //想命名的原文件的路径  
	        File file2 = new File(filePath+"/"+fileName); 
	        
	        file2.renameTo(new File(filePath+"/"+rename));  
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	@RequestMapping("/summaryFile")
	@ResponseBody
	public void fileUpload(@RequestParam("summary_file_upload") CommonsMultipartFile file,
			@RequestParam("time") String time,
			@RequestParam("type") String type,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("time->"+time);
		System.out.println("type->"+type);
		String path = request.getSession().getServletContext().getRealPath("")+ "/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath = "/upload/" + data[0] + "/" + data[1] + "/"+ data[2];
		String now=FunUtil.MD5(String.valueOf(System.currentTimeMillis()));
        String rename=now+name;
		if(uploadFile(request,file,path,rename)){
			this.success = true;
			this.message = "文件上传成功";
			/*if(type.equals("通信保障")){
				File srcFile = new File(path+"/"+rename);
				String destDir1=request.getSession().getServletContext()
						.getRealPath("/upload/check");
				destDir1=destDir1+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/3/通信保障报告";
				File Path1 = new File(destDir1);
				if (!Path1.exists()) {
					Path1.mkdirs();
				}
				
				String destDir2=request.getSession().getServletContext()
						.getRealPath("/upload/check");
				destDir2=destDir2+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/4/通信保障报告";
				File Path2 = new File(destDir2);
				if (!Path2.exists()) {
					Path2.mkdirs();
				}
				File file1 = new File(destDir1+"/"+name);
				File file2 = new File(destDir2+"/"+name);
				FunUtil.copyFile(srcFile, file1);
				FunUtil.copyFile(srcFile, file2);
			}*/
			
			
			
		}else{
			this.success = false;
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + rename);
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

}
