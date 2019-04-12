package xh.springmvc.handlers;

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
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.MeetServices;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/meet")
public class MeetController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MeetController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@RequestMapping("/meetlist")
	@ResponseBody
	public HashMap meetlist(HttpServletRequest request, HttpServletResponse response){
		String time=request.getParameter("time");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("start", start);
		map.put("limit", limit);

		HashMap result = new HashMap();
		result.put("totals",MeetServices.meetcount(map));
		result.put("items",MeetServices.meetlist(map));
		return result;		
	}
	@RequestMapping("/add")
	@ResponseBody
	public HashMap add(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		bean.setPerson(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setPerson(bean.getPerson().replaceAll(" ", "&nbsp;"));
		int rs=MeetServices.add(bean);
		if(rs>0){
			this.message="添加会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.ADD, "添加会议纪要："+bean.getStart_time());
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping("/update")
	@ResponseBody
	public HashMap update(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		bean.setPerson(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setPerson(bean.getPerson().replaceAll(" ", "&nbsp;"));
		int rs=MeetServices.update(bean);
		if(rs>0){
			this.message="修改会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改会议纪要："+bean.getStart_time());
		}else{
			this.message="修改失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping("/del")
	@ResponseBody
	public HashMap del(HttpServletRequest request, HttpServletResponse response){
		String[] ids=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : ids) {
			list.add(string);
		}
		int rs=MeetServices.del(list);
		if(rs>0){
			this.message="删除会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除会议纪要："+ids);
		}else{
			this.message="删除失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
	

}
