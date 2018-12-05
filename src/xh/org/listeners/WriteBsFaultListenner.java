package xh.org.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctc.wstx.util.StringUtil;

import xh.constant.Constants;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.ThreeEmhAlarmBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.PublicVariableService;
import xh.mybatis.service.SqlServerService;


public class WriteBsFaultListenner implements ServletContextListener{
	
	private Timer timer=null;
	protected final Log log4j = LogFactory.getLog(WriteBsFaultListenner.class);
	


	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(timer!=null){
			timer.cancel();
		}
		
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		if(timer==null){
			timer=new Timer();
			timer.scheduleAtFixedRate(new SfAlarm(), 10000, 4*60*60*1000+20*60*1000);
			timer.scheduleAtFixedRate(new EmhEpsWater(), 25000, 1000*60*3);			
			timer.scheduleAtFixedRate(new VoiceAlarm(), 15000, 1000*15);			
			timer.scheduleAtFixedRate(new PullEmhThreeAlarm(), 20000, 1000*60*1);
			timer.scheduleAtFixedRate(new VoiceNotCkeck(), 10000, 1*60*60*1000);
			timer.scheduleAtFixedRate(new VoiceNotOrder(), 10000, 5*60*1000);
		}
		
	}
	
/*	public static void main(String[] args) {
		EmhEpsWater a=new EmhEpsWater();
		a.run();
	}*/
	
	

}
class VoiceNotCkeck extends TimerTask{
	protected final Log log=LogFactory.getLog(VoiceNotCkeck.class);

	@Override
	public void run() {
		Constants.setBS_NOT_CHECK_NUM(BsStatusService.not_check_bs());
	}
	
}
class VoiceNotOrder extends TimerTask{
	protected final Log log=LogFactory.getLog(VoiceNotOrder.class);

	@Override
	public void run() {
		Constants.setBS_NOT_ORDER_NUM(BsStatusService.not_order_bs());
	}
}
//四期交流电告警
class EmhEpsWater extends TimerTask{
	protected final Log log4j = LogFactory.getLog( EmhEpsWater.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start=System.currentTimeMillis();
		log4j.info("=========================================");
		log4j.info("基站四期环控水浸，交流电实时写入开始");
		log4j.info("=========================================");
		
		BsAlarmService.bs_ji_four();
		System.out.println("=========================================");
		System.out.println("EmhEpsWater：end:"+(System.currentTimeMillis()-start));
		System.out.println("=========================================");
		Long start2=System.currentTimeMillis();
		BsAlarmService.bs_water_four();
		log4j.info("=========================================");
		log4j.info("EmhEpsWater：end:"+(System.currentTimeMillis()-start2));
		log4j.info("=========================================");
		
	}
	
}
//四方伟业告警
class SfAlarm extends TimerTask{
	protected final Log log4j = LogFactory.getLog( EmhEpsWater.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start=System.currentTimeMillis();
		log4j.info("=========================================");
		log4j.info("大数据分析告警写入开始");
		log4j.info("=========================================");
		
		BsAlarmService.insert_sf_alarm();
		System.out.println("=========================================");
		System.out.println("大数据分析告警写入：end:"+(System.currentTimeMillis()-start)+"ms");
		System.out.println("=========================================");
		
	}
	
}
//获取声音告警数目
class VoiceAlarm extends TimerTask{
	protected final Log log4j = LogFactory.getLog( VoiceAlarm.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*log4j.info("=========================================");
		log4j.info("获取告警数量");
		log4j.info("=========================================");*/
		int count=0;
		try {
			count = BsStatusService.alarmVoiceCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PublicVariableService.setVoiceAlarmCount(count);
		
	}
	
}
class PullEmhThreeAlarm extends TimerTask{
	protected final Log log=LogFactory.getLog(PullEmhThreeAlarm.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("=========================================");
		log.info("基站三期环控告警写入开始");
		log.info("=========================================");
		try {
			List<ThreeEmhAlarmBean> list=SqlServerService.perDayAlarm();
			System.out.println("emh:3->总数="+list.size());
			int i=0,j=0;
			for (ThreeEmhAlarmBean threeEmhAlarmBean : list) {
				if(threeEmhAlarmBean.getState().equals("报警")){
					threeEmhAlarmBean.setState("0");
				}else{
					threeEmhAlarmBean.setState("1");
				}
				threeEmhAlarmBean.setAlarmTime(timeFormat(threeEmhAlarmBean.getAlarmDate(), threeEmhAlarmBean.getAlarmTime()));
				threeEmhAlarmBean.setRelieveTime(timeFormat(threeEmhAlarmBean.getRelieveDate(), threeEmhAlarmBean.getRelieveTime()));
				threeEmhAlarmBean.setDescription(threeEmhAlarmBean.getAlarmText());
				String[] texts=threeEmhAlarmBean.getAlarmText().split("，");
				
				if(texts.length>2){
					String [] b=texts[2].split(",");
					if(b[0].contains("通信中断")){
						threeEmhAlarmBean.setAlarmText(texts[1]+","+b[0]);
					}else{
						threeEmhAlarmBean.setAlarmText(b[0]);
					}
					
					threeEmhAlarmBean.setName(texts[0]);
				}
				
				//log.info(threeEmhAlarmBean);
				if(threeEmhAlarmBean.getState().equals("0")){
					if(SqlServerService.alarmExists(threeEmhAlarmBean)==0){
						SqlServerService.insertEmhAlarm(threeEmhAlarmBean);
						//log.info("write:"+threeEmhAlarmBean);
						
						i++;
					}
				}else{
					if(SqlServerService.alarmExists(threeEmhAlarmBean)>0){
						SqlServerService.updateEmhAlarm(threeEmhAlarmBean);
						//log.info("update"+threeEmhAlarmBean);
						j++;
					}
					
				}
			}
			log.info("emh:3->写入="+i);
			log.info("emh:3->更新="+j);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String timeFormat(String alarmDate,String alarmTime){
		
		if(alarmDate==null || alarmTime==null){
			return null;
		}

		String a=alarmDate.split(" ")[0];
		String[] t=alarmTime.split(":");
		int t1=Integer.parseInt(t[0]);
		int t2=Integer.parseInt(t[1]);
		String h="",m="";
		if(t1<10){
			h="0"+t1;
		}else{
			h=t[0];
		}
		if(t2<10){
			m="0"+t2;
		}else{
			m=t[1];
		}
		return a+" "+h+":"+m+":00";
	}
	
	
	
}


