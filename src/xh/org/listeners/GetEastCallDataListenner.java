package xh.org.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.constant.ConstantMap;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EmhThreeBean;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.RadioUserMotoBean;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.RadioService;
import xh.mybatis.service.RadioUserMotoService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.SqlServerService;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.SendData;
import xh.protobuf.RadioUserBean;

public class GetEastCallDataListenner implements ServletContextListener {

	protected final Log log4j = LogFactory.getLog(GetEastCallDataListenner.class);
	private static List<String> tableListMap=new ArrayList<String>();
	//时间间隔
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); 
        if (date.before(new Date())) {
        	date = this.addDay(date, 1);
        	}	
        Timer timer=new Timer();		
		//timer.schedule(new GetData(), date, PERIOD_DAY);
		timer.schedule(new GetMotoData(), date, PERIOD_DAY);
		timer.scheduleAtFixedRate(new GetChData(),5000,5*60*1000);
		timer.scheduleAtFixedRate(new CollectionData(),5000,1*60*1000);	
		
	}
	// 增加或减少天数
		public  Date addDay(Date date, int num) {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, num);
			return startDT.getTime();
		}
	


}
class GetData extends TimerTask{
	protected final Log log4j = LogFactory.getLog(GetData.class);
	@Override
	public void run() {
		SimpleDateFormat fTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d1 = fTime.format(new Date());
		log4j.info("=========================================");
		log4j.info("获取东信数据库数据执行时间"+d1);
		log4j.info("=========================================");
		log4j.info("=========================================");
		log4j.info("获取东信VPN话务统计数据线程启动");
		log4j.info("=========================================");
		long starttime1=System.currentTimeMillis();
		EastComService.get_vpn_call_data();
		long endtime1=System.currentTimeMillis();
		float seconds1 = (endtime1 - starttime1);
		log4j.info("=========================================");
		log4j.info("获取东信VPN话务统计数据结束,历时="+seconds1+" ms");
		log4j.info("=========================================");
		
		
		log4j.info("=========================================");
		log4j.info("获取东信MSC话务统计数据线程启动");
		log4j.info("=========================================");
		long starttime2=System.currentTimeMillis();
		EastComService.get_msc_call_data();
		long endtime2=System.currentTimeMillis();
		float seconds2 = (endtime2 - starttime2);
		log4j.info("=========================================");
		log4j.info("获取东信MSC话务统计数据结束,历时="+seconds2+" ms");
		log4j.info("=========================================");
		
		log4j.info("=========================================");
		log4j.info("获取东信MSC-DETAIL话务统计数据线程启动");
		log4j.info("=========================================");
		long starttime3=System.currentTimeMillis();
		EastComService.get_msc_call_detail_data();
		long endtime3=System.currentTimeMillis();
		float seconds3 = (endtime3 - starttime3);
		log4j.info("=========================================");
		log4j.info("获取东信MSC-DETAIL话务统计数据结束,历时="+seconds3+" ms");
		log4j.info("=========================================");
		
		log4j.info("=========================================");
		log4j.info("获取东信基站话务统计数据线程启动");
		log4j.info("=========================================");
		long starttime=System.currentTimeMillis();
		EastComService.get_bs_call_data();
		long endtime=System.currentTimeMillis();
		float seconds = (endtime - starttime)/1000;
		log4j.info("=========================================");
		log4j.info("获取东信基站话务统计数据结束,历时="+seconds+" s");
		log4j.info("=========================================");
		
		
	}
	
	
}

class GetChData extends TimerTask{
	protected final Log log4j = LogFactory.getLog(GetData.class);
	@Override
	public void run() {
		EastComService.get_bs_now_call_data();
		
		/*log4j.info("=========================================");
		log4j.info("获取基站信道数据线程启动");
		log4j.info("=========================================");
		long starttime1=System.currentTimeMillis();
		EastComService.get_bs_now_call_data();
		long endtime1=System.currentTimeMillis();
		float seconds1 = (endtime1 - starttime1);
		log4j.info("=========================================");
		log4j.info("获取东信基站信道数据结束,历时="+seconds1+" ms");
		log4j.info("=========================================");*/
		
	}
	
	
}
class CollectionData extends TimerTask{
	protected final Log log4j = LogFactory.getLog(CollectionData.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			log4j.info("=========================================");
			log4j.info("获取三期基站环控数据数据线程启动");
			log4j.info("=========================================");
			long starttime1=System.currentTimeMillis();

			List<String> list=SqlServerService.all_table();
			for (String string : list) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("table", string);

				List<EmhThreeBean> list2=SqlServerService.tb_dev_info(map);
				//SqlServerService.insertEmh(list2);
				for (EmhThreeBean bean : list2) {
					EmhThreeBean emh_three_one=SqlServerService.emh_three_one(bean);
					bean.setUpdateTime(FunUtil.nowDate());
					if(emh_three_one!=null){
						if(!bean.getValue().equals(emh_three_one.getValue()) || 
								!bean.getAlarmState().equals(emh_three_one.getAlarmState())){
							SqlServerService.insertEmh(bean);
						}
					}else{
						SqlServerService.insertEmh(bean);
					}
					
				}
				
				
			}
			long endtime1=System.currentTimeMillis();
			float seconds1 = (endtime1 - starttime1);
			log4j.info("=========================================");
			log4j.info("获取三期基站环控数据结束,历时="+(seconds1/1000)+" ms");
			log4j.info("=========================================");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
class GetMotoData extends TimerTask{
	protected final Log log4j = LogFactory.getLog(GetData.class);
	private RadioService radio_s=new RadioService();
	@Override
	public void run() {
		log4j.info("=========================================");
		log4j.info("获取MoTo数据线程启动");
		log4j.info("=========================================");
		long starttime1=System.currentTimeMillis();
		GetAllRadio();
		GetAllRadioUser();
		long endtime1=System.currentTimeMillis();
		float seconds1 = (endtime1 - starttime1);
		log4j.info("=========================================");
		log4j.info("获取MoTo数据结束,历时="+seconds1+" ms");
		log4j.info("=========================================");
		
	}
	public void GetAllRadio(){
		List<HashMap> list=new ArrayList<HashMap>();
		list=RadioUserService.allRadioUser("0");
		for (HashMap map : list) {
			try {
				radioGet(map.get("C_ID").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void GetAllRadioUser(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		list=RadioUserMotoService.radioList();
		for (Map<String,Object> map : list) {
			try {
				radioUserMotoGet(map.get("RadioID").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String radioGet(String radioId) throws Exception{
		RadioBean bean=new RadioBean();
		if(MotoTcpClient.getSocket().isConnected()){			
			bean.setRadioID(radioId);
			bean.setCallId(FunUtil.RandomAlphanumeric(8));
			bean.setRadioReferenceID("0");
			bean.setRadioSerialNumber("0");
			bean.setSecurityGroup("0");
			SendData.RadioGetReq(bean);
			long nowtime=System.currentTimeMillis();
			tag:for(;;){			
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					if(ConstantMap.getMotoResultMap().get(bean.getCallId()).equals("0")){
						
					}else{
						bean=(RadioBean) ConstantMap.getMotoResultMap().get(bean.getCallId()+"map");
						radio_s.vAdd(bean);
						
					}	
					ConstantMap.getMotoResultMap().remove(bean.getCallId());
					ConstantMap.getMotoResultMap().remove(bean.getCallId()+"map");
					break tag;
				}else{
					if(tt-nowtime>10000){
						break tag;
					}
				}
			}			
		}
		return "SUCCESS";
	}
	public String radioUserMotoGet(String radioUserAlias) throws Exception{
		RadioUserMotoBean bean2=new RadioUserMotoBean();
		if(MotoTcpClient.getSocket().isConnected()){
			RadioUserBean bean=new RadioUserBean();
			bean.setRadioUserAlias(radioUserAlias);
			bean.setCallId(FunUtil.RandomAlphanumeric(8));
			SendData.RadioUserGetReq(bean);
			long nowtime=System.currentTimeMillis();
			tag:for(;;){			
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					if(ConstantMap.getMotoResultMap().get(bean.getCallId()).equals("0")){
					}else{
						bean2=(RadioUserMotoBean) ConstantMap.getMotoResultMap().get(bean.getCallId()+"map");
						RadioUserMotoService.vAdd(bean2);
					}	
					ConstantMap.getMotoResultMap().remove(bean.getCallId());
					ConstantMap.getMotoResultMap().remove(bean.getCallId()+"map");
					break tag;
				}else{
					if(tt-nowtime>10000){
						break tag;
					}
				}
			}			
		}else{
		}
		
		return "SUCCESS";
	}

}
