package xh.org.listeners;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.springmvc.handlers.GosuncnController;

import com.chinamobile.fsuservice.Test;
import com.chinamobile.lscservice.LSCServiceSkeleton;
/**
 * 启动项目时开启动环定时获取
 * @author 12878
 *
 */
public class EMHListener implements ServletContextListener{
	Timer timer = new Timer();
	protected static final Log log = LogFactory.getLog(EMHListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub	
		timer.schedule(new timerTaskForLogin(), 3*60*1000, 5*60*1000);//心跳任务
		timer.schedule(new timerTaskForConfig(), 60*1000, 10*60*1000);//获取配置任务
		timer.schedule(new timerTaskForData(), 2*60*1000, 60*1000);//定时获取数据任务
	}

}

/**
 * 维持心跳
 */
class timerTaskForLogin extends TimerTask{
	protected static final Log log = LogFactory.getLog(timerTaskForData.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Map<String,String>> list = GosuncnController.selectForGetLogin();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://"+map.get("fsuIp")+":8080/services/FSUService";
			try {
				Test.getLoginInfo(url, FSUID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				log.info("发送心跳信息失败！！！send for login failure!!!!");
			}
		}
			
	}
	
}

/**
 * 获取配置信息
 */
class timerTaskForConfig extends TimerTask{
	protected static final Log log = LogFactory.getLog(timerTaskForData.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Map<String,String>> list = GosuncnController.selectForGetLogin();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://"+map.get("fsuIp")+":8080/services/FSUService";
			try {
				List<Map<String,String>> configList = Test.getDevConf(url, FSUID);
				String result = GosuncnController.deleteByFSUID(FSUID);//删除之前的配置信息，保持最新
				if("success".equals(result)){
					GosuncnController.insertConfig(configList);//将最新的配置信息入库
				}				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				
			}
		}		
		
	}
}

/**
 * 获取数据
 * @author 12878
 *
 */
class timerTaskForData extends TimerTask{
	protected static final Log log = LogFactory.getLog(timerTaskForData.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Map<String,String>> temp = GosuncnController.selectForGetLogin();
		for(int i=0;i<temp.size();i++){
			Map<String,String> map = temp.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://"+map.get("fsuIp")+":8080/services/FSUService";
			try {
				List<String> list = GosuncnController.selectConfigByFSUID(FSUID);
				List<Map<String,String>> listData = Test.getDataForDB(url,FSUID, list);
				//插入前查询实时表里面有无数据,有则删除
				//String temp = GosuncnController.updateFSUID(FSUID);
				String result = GosuncnController.insertData(listData);
				log.info(result);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				log.info("获取数据失败！！！get data failure!!!");
			}
		}
		
		
	}
	
}
