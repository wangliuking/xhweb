package xh.org.listeners;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.tools.WebSocketPushHandler;
import xh.redis.server.UserRedis;
import xh.springmvc.handlers.GosuncnController;

import com.chinamobile.fsuservice.Test;

/**
 * 启动项目时开启动环定时获取
 * 
 * @author 12878
 * 
 */
public class RedisDataListener implements ServletContextListener {
	private Timer timer = null;

	protected static final Log log = LogFactory.getLog(RedisDataListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		SimpleDateFormat dd=new SimpleDateFormat("yyyy-MM-dd 01:00:00");
		//Date d=dd.parse(FunUtil.nowDate());
		UserRedis.getAllUserSession();
		timer.schedule(new timerTaskForRedisData(), 100000, 24*60*60*1000);// 心跳任务
		
	}

}

/**
 * 同步redis中的session
 * 
 * @author 12878
 * 
 */
class timerTaskForRedisData extends TimerTask {
	protected static final Log log = LogFactory.getLog(timerTaskForRedisData.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*System.out.println("SingLoginListener.logUserMap : "+SingLoginListener.logUserMap);
		System.out.println("SingLoginListener.logUserInfoMap : "+SingLoginListener.logUserInfoMap);
		System.out.println("SingLoginListener.loginUserPowerMap : "+SingLoginListener.loginUserPowerMap);*/
		UserRedis.getAllUserSession();
		/*System.out.println("=================================================================");
		System.out.println("=================================================================");
		System.out.println("=================================================================");
		System.out.println("SingLoginListener.logUserMap : "+SingLoginListener.logUserMap);
		System.out.println("SingLoginListener.logUserInfoMap : "+SingLoginListener.logUserInfoMap);
		System.out.println("SingLoginListener.loginUserPowerMap : "+SingLoginListener.loginUserPowerMap);*/
	}

}