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
			timer.scheduleAtFixedRate(new BsFault(), 10000, 1000*60*2);
			log4j.info("=========================================");
			log4j.info("基站断站故障实时写入开始");
			log4j.info("=========================================");
		}
		
	}

}
class BsFault extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		bs_fault();
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
			
			BsAlarmService.addBsFault(bean);
		}
	}
	
}
