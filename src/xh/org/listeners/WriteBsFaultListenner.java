package xh.org.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctc.wstx.util.StringUtil;

import xh.constant.Constants;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.EmhThreeBean;
import xh.mybatis.bean.ThreeEmhAlarmBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.PublicVariableService;
import xh.mybatis.service.SqlServerService;

public class WriteBsFaultListenner implements ServletContextListener {

	private Timer timer = null;
	protected final Log log4j = LogFactory.getLog(WriteBsFaultListenner.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (timer != null) {
			timer.cancel();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new SfAlarm(), 10000, 4*60*60*1000+20*60*1000);
			timer.scheduleAtFixedRate(new EmhEpsWater(), 5000, 1000*30*1);
			timer.scheduleAtFixedRate(new VoiceAlarm(), 5000, 1000*15);
			timer.scheduleAtFixedRate(new PullThreeEmh(), 6000, 1000 * 60 * 2);
			timer.scheduleAtFixedRate(new VoiceNotCkeck(),7000,1*60*60*1000);
			timer.scheduleAtFixedRate(new VoiceNotOrder(), 8000, 5*60*1000);
		}

	}

}

class VoiceNotCkeck extends TimerTask {
	protected final Log log = LogFactory.getLog(VoiceNotCkeck.class);

	@Override
	public void run() {
		Constants.setBS_NOT_CHECK_NUM(BsStatusService.not_check_bs());
	}

}

class VoiceNotOrder extends TimerTask {
	protected final Log log = LogFactory.getLog(VoiceNotOrder.class);

	@Override
	public void run() {
		Constants.setBS_NOT_ORDER_NUM(BsStatusService.not_order_bs());
	}
}

// 四期交流电告警
class EmhEpsWater extends TimerTask {
	protected final Log log4j = LogFactory.getLog(EmhEpsWater.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start = System.currentTimeMillis();
		log4j.info("=========================================");
		log4j.info("基站四期环控水浸，交流电实时写入开始");
		log4j.info("=========================================");

		BsAlarmService.bs_ji_four();
		System.out.println("=========================================");
		System.out.println("EmhEpsWater：end:"
				+ (System.currentTimeMillis() - start));
		System.out.println("=========================================");
		Long start2 = System.currentTimeMillis();
		//BsAlarmService.bs_water_four();
		log4j.info("=========================================");
		log4j.info("EmhEpsWater：end:" + (System.currentTimeMillis() - start2));
		log4j.info("=========================================");

	}

}

// 四方伟业告警
class SfAlarm extends TimerTask {
	protected final Log log4j = LogFactory.getLog(EmhEpsWater.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start = System.currentTimeMillis();
		log4j.info("=========================================");
		log4j.info("大数据分析告警写入开始");
		log4j.info("=========================================");

		BsAlarmService.insert_sf_alarm();
		System.out.println("=========================================");
		System.out.println("大数据分析告警写入：end:"
				+ (System.currentTimeMillis() - start) + "ms");
		System.out.println("=========================================");

	}

}

// 获取声音告警数目
class VoiceAlarm extends TimerTask {
	protected final Log log4j = LogFactory.getLog(VoiceAlarm.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		 /* log4j.info("=========================================");
		  log4j.info("获取告警数量");
		 log4j.info("=========================================");*/
		 
		int count = 0;
		try {
			count = BsStatusService.alarmVoiceCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PublicVariableService.setVoiceAlarmCount(count);

	}

}

class PullThreeEmh extends TimerTask {
	protected final Log log = LogFactory.getLog(PullEmhThreeAlarm.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Long start = System.currentTimeMillis();
		log.info("=========================================");
		log.info("基站三期环控告警写入开始");
		log.info("=========================================");
		try {
			List<ThreeEmhAlarmBean> list = SqlServerService.perDayAlarm();

			List<String> tablelist = SqlServerService.all_table();
			int i = 0, j = 0;
			for (String string : tablelist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("table", string);

				List<EmhThreeBean> list2 = SqlServerService.tb_dev_info(map);
				for (EmhThreeBean emhThreeBean : list2) {
					ThreeEmhAlarmBean bean = new ThreeEmhAlarmBean();
					bean.setJFNode(emhThreeBean.getBsId());
					bean.setDevNode(Integer.parseInt(emhThreeBean
							.getDevNode().trim()));
					bean.setNodeID(Integer.parseInt(emhThreeBean
							.getNodeID().trim()));
					bean.setDevName(emhThreeBean.getDevName());
					bean.setAlarmText(emhThreeBean.getAlarmEX());

					Map<String, Object> bsmap = BsstationService
							.select_bs_by_bsid(emhThreeBean.getBsId());
					bean.setName(bsmap.get("name") == null ? "" : bsmap
							.get("name").toString());
					bean.setDescription(bean.getName() + ","
							+ bean.getDevName() + ","
							+ emhThreeBean.getNodeAttribute() + ",报警");
					if (emhThreeBean.getAlarmState() != null
							&& emhThreeBean.getAlarmState() != "") {
						if (emhThreeBean.getAlarmState().equals("1")) {
							bean.setAlarmTime(FunUtil.nowDate());
							bean.setState("0");
							
							if (SqlServerService.alarmExists(bean) == 0) {
								SqlServerService.insertEmhAlarm(bean);
								i++;
							}
						} else {
							
							bean.setRelieveTime(FunUtil.nowDate());
							bean.setState("1");
							if (SqlServerService.alarmExists(bean) > 0) {
								SqlServerService.updateEmhAlarm(bean);
								j++;
							}
						}
					}else{
					}
				}

			}
			log.info("emh:3->写入=" + i);
			log.info("emh:3->更新=" + j);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized static void handleList(List<String> data, int threadNum) {
		int length = data.size();
		int tl = length % threadNum == 0 ? length / threadNum : (length
				/ threadNum + 1);

		for (int i = 0; i < threadNum; i++) {
			int end = (i + 1) * tl;
			ListThread thread = new ListThread("线程[" + (i + 1) + "] ", data, i
					* tl, end > length ? length : end);
			thread.start();
		}
	}
}

class ListThread extends Thread {
	private String threadName;
	private List<String> data;
	private int start;
	private int end;

	public ListThread(String threadName, List<String> data, int start, int end) {
		this.threadName = threadName;
		this.data = data;
		this.start = start;
		this.end = end;
	}

	public void run() {
		// TODO 这里处理数据
		List<String> subList = data.subList(start, end)/* .add("^&*") */;
		for (int i = 0; i < subList.size(); i++) {
			System.out.println(threadName + "处理了" + subList.get(i));
		}

		System.out.println(threadName + "处理了" + subList.size() + "条！");
	}

}

class PullEmhThreeAlarm extends TimerTask {
	protected final Log log = LogFactory.getLog(PullEmhThreeAlarm.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("=========================================");
		log.info("基站三期环控告警写入开始");
		log.info("=========================================");
		try {
			List<ThreeEmhAlarmBean> list = SqlServerService.perDayAlarm();
			System.out.println("emh:3->总数=" + list.size());
			int i = 0, j = 0;
			for (ThreeEmhAlarmBean threeEmhAlarmBean : list) {
				if (threeEmhAlarmBean.getState().equals("报警")) {
					threeEmhAlarmBean.setState("0");
				} else {
					threeEmhAlarmBean.setState("1");
				}
				threeEmhAlarmBean.setAlarmTime(timeFormat(
						threeEmhAlarmBean.getAlarmDate(),
						threeEmhAlarmBean.getAlarmTime()));
				threeEmhAlarmBean.setRelieveTime(timeFormat(
						threeEmhAlarmBean.getRelieveDate(),
						threeEmhAlarmBean.getRelieveTime()));
				threeEmhAlarmBean.setDescription(threeEmhAlarmBean
						.getAlarmText());
				String[] texts = threeEmhAlarmBean.getAlarmText().split("，");

				if (texts.length > 2) {
					String[] b = texts[2].split(",");
					if (b[0].contains("通信中断")) {
						threeEmhAlarmBean.setAlarmText(texts[1] + "," + b[0]);
					} else {
						threeEmhAlarmBean.setAlarmText(b[0]);
					}

					threeEmhAlarmBean.setName(texts[0]);
				}

				// log.info(threeEmhAlarmBean);
				if (threeEmhAlarmBean.getState().equals("0")) {
					if (SqlServerService.alarmExists(threeEmhAlarmBean) == 0) {
						SqlServerService.insertEmhAlarm(threeEmhAlarmBean);
						// log.info("write:"+threeEmhAlarmBean);

						i++;
					}
				} else {
					if (SqlServerService.alarmExists(threeEmhAlarmBean) > 0) {
						SqlServerService.updateEmhAlarm(threeEmhAlarmBean);
						// log.info("update"+threeEmhAlarmBean);
						j++;
					}

				}
			}
			log.info("emh:3->写入=" + i);
			log.info("emh:3->更新=" + j);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String timeFormat(String alarmDate, String alarmTime) {

		if (alarmDate == null || alarmTime == null) {
			return null;
		}

		String a = alarmDate.split(" ")[0];
		String[] t = alarmTime.split(":");
		int t1 = Integer.parseInt(t[0]);
		int t2 = Integer.parseInt(t[1]);
		String h = "", m = "";
		if (t1 < 10) {
			h = "0" + t1;
		} else {
			h = t[0];
		}
		if (t2 < 10) {
			m = "0" + t2;
		} else {
			m = t[1];
		}
		return a + " " + h + ":" + m + ":00";
	}

}
