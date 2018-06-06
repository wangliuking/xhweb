package xh.springmvc.handlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.ServerStatusService;
import xh.mybatis.service.SqlServerService;
import xh.mybatis.service.UserStatusService;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping(value="/dataView")
public class DataViewController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(DataViewController.class);
	private FlexJSON json=new FlexJSON();
	
	
	@RequestMapping(value="/show",method=RequestMethod.GET)
	public void show(HttpServletRequest request, HttpServletResponse response){
	
		
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId")==null?"":usermap.get("vpnId").toString();
		Map<String, Object> user=new HashMap<String, Object>();
		user.put("vpnId",vpnId);
		//在线用户
		int useOnlineCount=UserStatusService.userOnline(user);
		//终端数量
		
		int userCount=RadioUserService.radiouserCount(user);
		//基站数量
		int bsCount=BsstationService.bsTotal();
		//基站断站数量
		int bsOffline=BsStatusService.bsOfflineCount();
		//基站注册组TOP5
		List<Map<String, Object>> group=BsStatusService.bsGroupTop5();
		//基站注册终端TOP5
		List<Map<String, Object>> radio=BsStatusService.bsRadioTop5();
		//基站信道排队TOP5
		
		List<Map<String, Object>> channelQueueTop5=EastComService.queueTop5(ss());
		//环控告警		
		/*int emh=200;*/
		//交换中心
		/*List<Map<String,Object>>  msc=ServerStatusService.unusualStatus(0);
		int mscCount=msc.size();*/
		/*系统告警*/
		/*List<Map<String,Object>> tera=BsAlarmService.selectTop5();*/
		//基站断站列表
		List<Map<String,Object>>  bsOfflineList=BsstationService.monitorBsofflineList();
		List<Map<String,Object>> bsName=new ArrayList<Map<String,Object>>();
		
		for (Map<String, Object> map : bsOfflineList) {
			Map<String,Object> name=new HashMap<String, Object>();
			name.put("name", map.get("bsId")+"-"+map.get("name"));
			bsName.add(name);
			
		}
		
		
		Map<String, Object> map=new HashMap<String, Object>();

		map.put("useOnlineCount",useOnlineCount);
		map.put("userCount", userCount);
		map.put("bsCount", bsCount);
		map.put("bsOffline",bsOffline);
		map.put("group", group);
		map.put("radio", radio);
		map.put("channelQueueTop5", channelQueueTop5);
		map.put("geoCoord",bsOfflineList);
		map.put("bsNames",bsName);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String ss(){

		SimpleDateFormat timeF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeF.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));		
		Date d=new Date();
		String dateStr = timeF.format(d.getTime()-25*60*1000);
		String[] time=dateStr.split(" ")[1].split(":");
		String rtime=dateStr.split(" ")[0];
		int a=Integer.parseInt(time[1]);
		int c=a%5;
		int x=a-c;
		if(x>=10){
			rtime+=" "+time[0]+":"+x+":00";
		}else{
			rtime+=" "+time[0]+":0"+x+":00";
		}
		/*System.out.println("time->"+rtime);*/
		return rtime;
	}

}
