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
	protected static final Log log = LogFactory.getLog(EMHListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		//timer.schedule(new timerTask(), 60*1000, 60*1000);
	}

}


class timerTask extends TimerTask{
	protected static final Log log = LogFactory.getLog(timerTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		final List<String> list = new ArrayList<String>();
		list.add("170100000000001");// 温度
		list.add("170200000000001");// 湿度
		list.add("170300000000001");// 水浸
		list.add("170400000000001");// 烟雾
		list.add("170500000000001");// 红外
		list.add("170700000000001");// 门碰
		list.add("920100000000001");// 电表
		list.add("080200000000001");// UPS
		list.add("760300000000001");// FSU
		try {
			List<Map<String,String>> listData = Test.getDataForDB("http://192.168.5.254:8080/services/FSUService",
					"09201704160085", list);
			//插入前查询实时表里面有无数据,有则删除
			//String temp = GosuncnController.updateFSUID("09201704160085");
			String result = GosuncnController.insertData(listData);
			log.info(result);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
