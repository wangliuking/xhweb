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
import org.springframework.web.bind.annotation.ResponseBody;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
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
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response){
		String data=request.getParameter("formData");
		WorkContactBean bean=GsonUtil.json2Object(data, WorkContactBean.class);
		bean.setAddUser(funUtil.loginUser(request));
		bean.setTaskId(FunUtil.RandomAlphanumeric(20));
		bean.setTime(bean.getTime().split(" ")[0]);
		bean.setUser_type(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString()));
		bean.setContent(bean.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getContent().replaceAll(" ", "&nbsp;"));
		System.out.println("type->"+bean.getContent());
		
		int rst=WorkContactService.add(bean);
		if(rst>0){
			this.message="工作联系单下发成功";
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("工作联系单信息，data=" + bean.getReason());
			WebLogService.writeLog(webLogBean);
			if(bean.getUser_type()==3  || bean.getUser_type()==0){
				FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 2, "工作联系单", "有新的工作联系单，请尽快查收", request);	
			}else if(bean.getUser_type()==2){
				FunUtil.sendMsgToUserByGroupPower("recv_work_contact", 3, "工作联系单", "有新的工作联系单，请尽快查收", request);
			}
			
			
			
		}else{
			this.message="工作联系单下发失败";
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

}
