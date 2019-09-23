package xh.springmvc.handlers;

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
	@RequestMapping(value="/bs_offline_record",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> bs_offline_record(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String bsId=request.getParameter("bsId");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start_time", starttime);
		map.put("end_time", endtime);
		map.put("bsId", bsId);
		HashMap result = new HashMap();
		List<Map<String,Object>> list=ElecGenerationService.bs_offline_record(map);
		result.put("totals",list.size());
		result.put("items", list);
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
		String recv_user=request.getParameter("recv_user");
		String copy_user=request.getParameter("copy_user");
		String recv_user_name=request.getParameter("recv_user_name");
		String copy_user_name=request.getParameter("copy_user_name");

		String serialnumber=FunUtil.RandomAlphanumeric(8);
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fault_id", id);
		map.put("bsId", bsid);
		map.put("serialnumber", serialnumber);
		map.put("send_user", FunUtil.loginUser(request));
		map.put("recv_user", "");
		map.put("recever", recv_user_name);
		map.put("copier", copy_user_name);
		map.put("recever_user", recv_user);
		map.put("copier_user", copy_user);
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
			bean.setSerialnumber(serialnumber);
			bean.setBsname(bsname);
			bean.setBsid(String.valueOf(bsid));
			bean.setDispatchtime(map.get("dispatchtime").toString());
			bean.setAddress(BsstationService.select_bs_by_bsid(bsid).get("address").toString());
			bean.setDispatchman(FunUtil.loginUser(request));
			bean.setPowerofftime(time);
			bean.setRemarka(note);
			
			bean.setProstate(String.valueOf(0));
			
			ServerDemo demo=new ServerDemo();
			//发送接单人
			String[] a1=recv_user.split(",");
			String[] a2=recv_user_name.split(";");
			if(recv_user!=null && !recv_user.equals("")){
				for(int i=0;i<a1.length;i++){
					bean.setUserid(a1[i]);
					bean.setWorkman(a2[i]);
					demo.startMessageThread(bean.getUserid(), bean);
				}
			}
			
			//发送抄送人
			String[] b1=copy_user.split(",");
			String[] b2=copy_user_name.split(";");
			if(copy_user!=null && !copy_user.equals("")){
				for(int i=0;i<b1.length;i++){
					bean.setUserid(b1[i]);
					bean.setWorkman(b2[i]);
					demo.startMessageThread(bean.getUserid(), bean);
				}
			}
			
			
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
	@RequestMapping(value="/checkOne",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkOne(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		int status=Integer.parseInt(request.getParameter("status"));
		String userid=request.getParameter("userid");
		String serialnumber=request.getParameter("serialnumber");
		String note1=request.getParameter("note1");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("time", FunUtil.nowDate());
		map.put("check_user", FunUtil.loginUser(request));
		map.put("note", note1);
		System.out.println("mmmm->"+map);
		int rs=ElecGenerationService.checkOne(map);
		if(rs>0){
			this.success=true;
			this.message="成功";			
			GenCheckAck bean=new GenCheckAck();			
			bean.setUserid(userid);
			bean.setSerialnumber(serialnumber);		
			bean.setAuditor(FunUtil.loginUser(request));
			bean.setAck(String.valueOf(status));
			log.info("审核发电："+bean);
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
	@RequestMapping(value="/checkTwo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkTwo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=funUtil.StringToInt(request.getParameter("id"));
		int status=Integer.parseInt(request.getParameter("status"));
		String userid=request.getParameter("userid");
		String serialnumber=request.getParameter("serialnumber");
		String note1=request.getParameter("note1");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("time", FunUtil.nowDate());
		map.put("check_user", FunUtil.loginUser(request));
		map.put("note", note1);
		System.out.println("mmmm->"+map);
		int rs=ElecGenerationService.checkTwo(map);
		if(rs>0){
			this.success=true;
			this.message="成功";			
			GenCheckAck bean=new GenCheckAck();			
			bean.setUserid(userid);
			bean.setSerialnumber(serialnumber);		
			bean.setAuditor(FunUtil.loginUser(request));
			bean.setAck(String.valueOf(status));
			log.info("审核发电完成："+bean);
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
		int status=funUtil.StringToInt(request.getParameter("status"));
		String userid=request.getParameter("userid");
		String serialnumber=request.getParameter("serialnumber");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", 6);
		if(status>=3){
			map.put("gen_off_time", FunUtil.nowDate());
		}
		int rs=ElecGenerationService.check(map);
		if(rs>0){
			this.success=true;
			this.message="成功";
			GenTable bean=new GenTable();
			bean.setUserid(userid);
			bean.setSerialnumber(serialnumber);
			bean.setProstate("5");
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
