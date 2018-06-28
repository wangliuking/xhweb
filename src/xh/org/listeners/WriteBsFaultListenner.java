package xh.org.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.BsStatusService;


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
			log4j.info("=========================================");
			log4j.info("大数据分析告警写入开始");
			log4j.info("=========================================");
			timer.scheduleAtFixedRate(new EmhEpsWater(), 25000, 1000*60*3);
			log4j.info("=========================================");
			log4j.info("基站四期环控水浸，交流电实时写入开始");
			log4j.info("=========================================");
		}
		
	}

}
/*class BsFault extends TimerTask{
	protected final Log log4j = LogFactory.getLog(BsFault.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		bs_fault();
		bs_restore();
		log4j.info("=========================================");
		log4j.info("基站断站故障实时写入结束");
		log4j.info("=========================================");
	}
	public void bs_fault(){
		List<BsAlarmExcelBean> list = BsStatusService.bsFaultInfo();
		
		for (BsAlarmExcelBean bean : list) {
			bean.setWeekly(FunUtil.formateWeekly(bean.getTime()));
			if(Integer.parseInt(bean.getBsId())>1000 && Integer.parseInt(bean.getBsId())<2001){
				bean.setBsId(String.valueOf(Integer.parseInt(bean.getBsId())%1000));
			}else{
				bean.setBsId(bean.getBsId());
			}
			bean.setReason("断站");
			
			BsAlarmService.addBsFault(bean);
			
		}
	}
	public void bs_restore(){
		List<BsAlarmExcelBean> list = BsStatusService.bsRestoreInfo();
		
		for (BsAlarmExcelBean bean : list) {
			if(Integer.parseInt(bean.getBsId())>1000 && Integer.parseInt(bean.getBsId())<2001){
				bean.setBsId(String.valueOf(Integer.parseInt(bean.getBsId())%1000));
			}else{
				bean.setBsId(bean.getBsId());
				
			}
			BsAlarmService.bsRestore(bean);
			
		}
	}
	
}*/
//四期交流电告警
class EmhEpsWater extends TimerTask{
	protected final Log log4j = LogFactory.getLog( EmhEpsWater.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start=System.currentTimeMillis();
		
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
		
		BsAlarmService.insert_sf_alarm();
		System.out.println("=========================================");
		System.out.println("大数据分析告警写入：end:"+(System.currentTimeMillis()-start)+"ms");
		System.out.println("=========================================");
		
	}
	
}

