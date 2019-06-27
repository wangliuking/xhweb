package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkContactBean;
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
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		String type=request.getParameter("type");
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("limit",limit);
		map.put("time",time);
		map.put("type",type);
		map.put("roleType",FunUtil.loginUserInfo(request).get("roleType"));
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
	public void add(HttpServletRequest request, HttpServletResponse response){
		String data=request.getParameter("formData");
		String files=request.getParameter("files");
		WorkContactBean bean=GsonUtil.json2Object(data, WorkContactBean.class);
		WorkContactBean bean2=GsonUtil.json2Object(data, WorkContactBean.class);
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
		WorkContactBean bean=GsonUtil.json2Object(data, WorkContactBean.class);
		bean.setContent(bean.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getContent().replaceAll(" ", "&nbsp;"));
		bean.setStatus(0);
		int rst=WorkContactService.update(bean);
		if(rst>0){
			this.message="修改成功";
			this.success=true;
		
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改工作联系单");
			FunUtil.sendMsgToOneUser(bean.getCheck_person(), "工作联系单", "工作联系单已经修改，请审核", request);
			
			
			
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
		WorkContactBean bean=new WorkContactBean();
		bean.setTaskId(taskId);
		bean.setAddUser(addUser);
		bean.setCheckUser(FunUtil.loginUser(request));
		bean.setCheckTime(FunUtil.nowDateNoTime());
		int rst=WorkContactService.sign(bean);
		if(rst>0){
			this.message="确认工作联系单成功";
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("确认工作联系单信息，data=" + bean.getTaskId());
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(addUser, "工作联系单","服务提供方已经确认收到了工作联系单", request);
		}else{
			this.message="工作联系单确认失败";
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
		bean.setCheck_time(FunUtil.nowDateNoTime());
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

				FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
				if(bean.getUser_type()==3  || bean.getUser_type()==0){
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 2, "工作联系单", "有新的工作联系单，请尽快查收", request);	
				}else if(bean.getUser_type()==2){
					FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
				}
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

}
