package xh.springmvc.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcpBean.GenCheckAck;
import com.tcpBean.GenTable;
import com.tcpServer.ServerDemo;

import xh.func.plugin.FunUtil;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.ElecGenerationService;

@Controller
@RequestMapping("/elec")
public class ElecGenerationController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(ElecGenerationController.class);

	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String bsId=request.getParameter("bsId");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("bsId", bsId);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",ElecGenerationService.count(map));
		result.put("items", ElecGenerationService.list(map));
		return result;
		
	}
	@RequestMapping(value="/gorder",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gorder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		int bsid=funUtil.StringToInt(request.getParameter("bsid"));
		String userid=request.getParameter("userid");
		String note=request.getParameter("note");
		String time=request.getParameter("time");
		String bsname=request.getParameter("bsname");
		String recv_user_name=request.getParameter("recv_user_name");
		String send_user_name=request.getParameter("send_user_name");

		String serialnumber=FunUtil.RandomAlphanumeric(8);
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fault_id", id);
		map.put("bsId", bsid);
		map.put("serialnumber", serialnumber);
		map.put("send_user", FunUtil.loginUser(request));
		map.put("recv_user", userid);
		map.put("poweroff_time", time);
		map.put("comment", note);
		map.put("dispatchtime", FunUtil.nowDate());
		System.out.println(map);
		
		Map<String,Object> bsMap=BsstationService.select_bs_by_bsid(bsid);
		
		
		
		
		
		int rs=ElecGenerationService.insert(map);
		if(rs>0){
			this.success=true;
			this.message="发电通知已经发送";
			ElecGenerationService.update_fault(id);
			GenTable bean=new GenTable();
			bean.setUserid(userid);
			bean.setSerialnumber(serialnumber);
			bean.setBsname(bsname);
			bean.setBsid(String.valueOf(bsid));
			bean.setDispatchtime(map.get("dispatchtime").toString());
			bean.setDispatchman(send_user_name);
			bean.setPowerofftime(time);
			bean.setRemarka(note);
			bean.setWorkman(recv_user_name);
			bean.setProstate(String.valueOf(0));
			ServerDemo demo=new ServerDemo();
			demo.startMessageThread(bean.getUserid(), bean);
		}else{
			this.success=false;
			this.message="失败";
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;
		
	}
	@RequestMapping(value="/resend_order",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resend_order(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int bsid=funUtil.StringToInt(request.getParameter("bsid"));
		String userid=request.getParameter("userid");
		String note=request.getParameter("note");
		String time=request.getParameter("time");
		String bsname=request.getParameter("bsname");
		String recv_user_name=request.getParameter("recv_user_name");
		String send_user_name=request.getParameter("send_user_name");
		String serialnumber=request.getParameter("serialnumber");
		String dispatchtime=request.getParameter("dispatchtime");	
		
		Map<String,Object> bsMap=BsstationService.select_bs_by_bsid(bsid);
		this.success=true;
		this.message="发电通知已经发送";
		GenTable bean=new GenTable();
		bean.setUserid(userid);
		bean.setSerialnumber(serialnumber);
		bean.setBsname(bsname);
		bean.setBsid(String.valueOf(bsid));
		bean.setDispatchtime(dispatchtime);
		bean.setDispatchman(send_user_name);
		bean.setPowerofftime(time);
		bean.setRemarka(note);
		bean.setWorkman(recv_user_name);
		bean.setProstate(String.valueOf(0));
		ServerDemo demo=new ServerDemo();
		demo.startMessageThread(bean.getUserid(), bean);
		HashMap result = new HashMap();
		result.put("success", true);
		result.put("message",message);
		return result;
		
	}
	@RequestMapping(value="/check",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		int status=funUtil.StringToInt(request.getParameter("status"));
		String userid=request.getParameter("userid");
		String serialnumber=request.getParameter("serialnumber");
		
		if(status==1){
			status=3;
		}else{
			status=1;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		int rs=ElecGenerationService.check(map);
		if(rs>0){
			this.success=true;
			this.message="成功";			
			GenCheckAck bean=new GenCheckAck();			
			bean.setUserid(userid);
			bean.setSerialnumber(serialnumber);		
			bean.setAuditor(FunUtil.loginUser(request));
			bean.setAck(status==1?"1":"0");
			
			ServerDemo demo=new ServerDemo();			
			demo.startMessageThread(bean.getUserid(), bean);
		}else{
			this.success=false;
			this.message="失败";
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;
		
	}
	@RequestMapping(value="/cancelOrder",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		String userid=request.getParameter("userid");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", 5);
		int rs=ElecGenerationService.check(map);
		if(rs>0){
			this.success=true;
			this.message="成功";
			GenTable bean=new GenTable();
			bean.setUserid(userid);
			ServerDemo demo=new ServerDemo();
			demo.startMessageThread(bean.getUserid(), bean);
		}else{
			this.success=false;
			this.message="失败";
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;
		
	}

}
