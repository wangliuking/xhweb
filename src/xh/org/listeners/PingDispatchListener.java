package xh.org.listeners;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.mybatis.service.DispatchStatusService;


public class PingDispatchListener implements ServletContextListener{
	private Timer timer = new Timer();
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
		/*if(timer==null){
			timer.schedule(new pingTask(), 10*1000, 60*1000);
		}*/
		timer.schedule(new pingTask(), 5*1000, 3*60*1000);
	}

}
class pingTask extends TimerTask{
	protected static final Log log = LogFactory.getLog(pingTask.class);
	@Override
	public void run() {
		try {
			Long start=System.currentTimeMillis();
			log.info("=========================================");
			log.info("ping调度台状态线程开始运行");
			log.info("=========================================");
			DispatchStatusService.changePingStatus();
			log.info("=========================================");
			log.info("ping调度台状态线程结束:"+(System.currentTimeMillis()-start));
			log.info("=========================================");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
