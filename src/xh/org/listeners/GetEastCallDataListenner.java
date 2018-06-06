package xh.org.listeners;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.mybatis.service.EastComService;

public class GetEastCallDataListenner implements ServletContextListener {
	
	private Timer timer=null;
	protected final Log log4j = LogFactory.getLog(GetEastCallDataListenner.class);

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
			timer.scheduleAtFixedRate(new GetData(), 20000, 1000*2*60);
			
		}
		
	}

}
class GetData extends TimerTask{
	protected final Log log4j = LogFactory.getLog(GetData.class);
	@Override
	public void run() {
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
	}
	
	
}
