package xh.org.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			SimpleDateFormat fTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fTime2 = new SimpleDateFormat("yyyy-MM-dd 01:00:00");
			String time=fTime2.format(new Date());
			try {
				Date d1 = fTime.parse(time);
				timer.scheduleAtFixedRate(new GetData(), d1, 1000*60*60*24);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
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
