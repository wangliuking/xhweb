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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.springmvc.handlers.GosuncnController;

import com.chinamobile.fsuservice.Test;
import com.chinamobile.lscservice.LSCServiceSkeleton;

/**
 * 启动项目时开启动环定时获取
 * 
 * @author 12878
 * 
 */
public class EMHListener implements ServletContextListener {
	Timer timer = new Timer();
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	protected static final Log log = LogFactory.getLog(EMHListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//timer.schedule(new timerTaskForLogin(), 60 * 1000, 60 * 1000);// 心跳任务
		//timer1.schedule(new timerTaskForData(), 2 * 60 * 1000, 60 * 1000);//定时获取数据任务
		// timer2.schedule(new timerTaskForTimeCheck(),30*1000,20*1000);//
		// 时间同步（一次）

		// timer.schedule(new timerTaskForConfig(), 5*60*1000);//获取一次配置信息
	}

}

/**
 * 维持心跳
 */
class timerTaskForLogin extends TimerTask {
	protected static final Log log = LogFactory.getLog(timerTaskForLogin.class);
	List<Map<String, String>> list = GosuncnController.selectForGetLogin();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ExecutorService es = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < list.size(); i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					String[] strs = Thread.currentThread().getName().split("-");
					String thr = strs[3];
					Map<String, String> map = list.get(Integer.parseInt(thr) - 1);
					String FSUID = map.get("fsuId");
					String url = "http://" + map.get("fsuIp")
							+ ":8090/services/FSUService";
					try {
						Test.getFSUInfo(url, FSUID);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						log.info("发送心跳信息失败！！！" + FSUID
								+ "send for login failure!!!!");
					}
				}
			});

			/*
			 * Map<String, String> map = list.get(i); String FSUID =
			 * map.get("fsuId"); String url = "http://" + map.get("fsuIp") +
			 * ":8090/services/FSUService"; log.info(FSUID+" "+url); try {
			 * Test.getFSUInfo(url, FSUID); } catch (RemoteException e) { //
			 * TODO Auto-generated catch block log.info("发送心跳信息失败！！！" + FSUID +
			 * "send for login failure!!!!"); }
			 */
		}
		es.shutdown();
		try {
			es.awaitTermination(50 * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("awaitTermination interrupted: " + e);  
	        es.shutdownNow();
		}
	}

}

/**
 * 获取数据
 * 
 * @author 12878
 * 
 */
class timerTaskForData extends TimerTask {
	protected static final Log log = LogFactory.getLog(timerTaskForData.class);
	List<Map<String, String>> temp = GosuncnController.selectForGetLogin();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ExecutorService es = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < temp.size(); i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					String[] strs = Thread.currentThread().getName().split("-");
					String thr = strs[3];
					Map<String, String> map = temp.get(Integer.parseInt(thr) - 1);
					String FSUID = map.get("fsuId");
					String url = "http://" + map.get("fsuIp")
							+ ":8090/services/FSUService";
					try {
						List<String> list = GosuncnController
								.selectConfigByFSUID(FSUID);
						List<Map<String, String>> listData = Test.getDataForDB(
								url, FSUID, list);
						// 插入前查询实时表里面有无数据,有则删除
						GosuncnController.updateFSUID(FSUID);
						// 插入实时数据
						String result = GosuncnController.insertData(listData);
						// 插入历史数据
						GosuncnController.insertHData(listData);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						log.info("获取数据失败！！！" + FSUID + "get data failure!!!");
					}
				}
			});
		}
		es.shutdown();
		try {
			es.awaitTermination(50 * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("awaitTermination interrupted: " + e);  
	        es.shutdownNow();
		}
	}

}

/**
 * 获取配置信息
 */
class timerTaskForConfig extends TimerTask {
	protected static final Log log = LogFactory.getLog(timerTaskForConfig.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = GosuncnController.selectForGetLogin();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://" + map.get("fsuIp")
					+ ":8090/services/FSUService";
			try {
				List<Map<String, String>> configList = Test.getDevConf(url,
						FSUID);
				String result = GosuncnController.deleteConfigByFSUID(FSUID);// 删除之前的配置信息，保持最新
				if ("success".equals(result)) {
					GosuncnController.insertConfig(configList);// 将最新的配置信息入库
					log.info("啦啦啦一个FSU已添加配置！");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block

			}
		}

	}
}

/**
 * 时间同步
 */
class timerTaskForTimeCheck extends TimerTask {
	protected static final Log log = LogFactory
			.getLog(timerTaskForTimeCheck.class);
	List<Map<String, String>> list = GosuncnController.selectForGetLogin();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ExecutorService es = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < list.size(); i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String[] strs = Thread.currentThread().getName().split("-");
					String thr = strs[3];
					Map<String, String> map = list.get(Integer.parseInt(thr) - 1);
					String FSUID = map.get("fsuId");
					String url = "http://" + map.get("fsuIp")
							+ ":8090/services/FSUService";
					log.info(strs + " " + thr + " " + FSUID + " " + url + " "
							+ Thread.currentThread().getName());
					try {
						Test.timeCheck(url, FSUID);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
		}
		es.shutdown();
	}

}
