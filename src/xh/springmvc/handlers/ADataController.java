package xh.springmvc.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chinamobile.fsuservice.Test;
import xh.func.plugin.FlexJSON;
import xh.mybatis.service.GosuncnService;
import cc.eguid.FFmpegCommandManager.test.TestFFmpegForWeb;
/**
 * 数据获取测试类
 * @author 12878
 *
 */
@Controller
@RequestMapping(value = "/camera")
public class ADataController {
	private boolean success;
	private String message;
	public static String id;
	protected final Log log = LogFactory.getLog(ADataController.class);
	private FlexJSON json=new FlexJSON();	
	private Timer timer = new Timer();
	private Timer timer1 = new Timer();	
	private Timer timer2 = new Timer();	
	
	/**
	 * 获取动环相关信息
	 */
	@RequestMapping("/startById")
	public void oneBsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		String bsId = request.getParameter("bsId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("bsId", bsId);
		List<Map<String,Object>> listMap = GosuncnService.selectCameraIpByBsId(map);
		Map<String, Object> cameraMap = listMap.get(0);
		try {
			TestFFmpegForWeb.test1(cameraMap);
			//timer.schedule(new timerTaskForStop(), 3*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/**
 * 维持心跳
 */
class timerTaskForStop extends TimerTask {
	@Override
	public void run() {
		String id = ADataController.id;
		// TODO Auto-generated method stub
		try {
			TestFFmpegForWeb.stop(id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
							+ ":8080/services/FSUService";
					try {
						List<Map<String, String>> listData = Test.getDataForDB(url, FSUID);
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
		/*try {
			es.awaitTermination(50 * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("awaitTermination interrupted: " + e);
			es.shutdownNow();
		}*/
	}

}

/**
 * 获取配置信息
 */
class timerTaskForConfig extends TimerTask {
	protected static final Log log = LogFactory
			.getLog(timerTaskForConfig.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = GosuncnController.selectForGetLogin();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://" + map.get("fsuIp")
					+ ":8080/services/FSUService";
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
							+ ":8080/services/FSUService";
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

