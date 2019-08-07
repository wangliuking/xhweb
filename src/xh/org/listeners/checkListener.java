package xh.org.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;









import xh.func.plugin.FunUtil;
import xh.mybatis.bean.CheckMoneyBean;
import xh.mybatis.bean.CheckMonthBsBreakBean;
import xh.mybatis.bean.CheckRoomEquBean;
import xh.mybatis.service.OperationsCheckService;

public class checkListener implements ServletContextListener{
	private Timer timer=null;
	protected final Log log4j = LogFactory.getLog(checkListener.class);
	private final static long PERIOD_DAY=24*60*60*1000;
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String date_time=df.format(new Date());
		String date=date_time.split(" ")[0];
		String runtime=date+" "+"02:00:00";
		Date rundate=null;
		try {  
			rundate=df.parse(runtime);
			if(timer==null){
				timer=new Timer();
				timer.scheduleAtFixedRate(new Score(), rundate, PERIOD_DAY);
				timer.scheduleAtFixedRate(new Money(), rundate, PERIOD_DAY);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
class Score extends TimerTask{
	private OperationsCheckService service=new OperationsCheckService();
	protected final Log log= LogFactory.getLog(Score.class);

	@Override
	public void run() {
		Calendar c=Calendar.getInstance();
		int day=c.get(Calendar.DAY_OF_MONTH);
		log.info("考核项目检查开始--检查扣分项目");
		OperationsCheckService.del_score(FunUtil.date_format("yyyy-MM"));
		log.info("清除考核得分记录");
		/*机房配套设备检查	*/
		check_room_equ();
		/*考核运维人员是否达到20人*/
		check_phone();
		/*<!-- 考核运办公场所 ,考核仪器仪表 ,考核运维车辆不足3辆 -->*/
		check_officeaddress();
		/*考核备品备件完好率低于80%*/
		check_attachment();
		/*<!--考核基站级别-->*/
		check_level(FunUtil.date_format("yyyy-MM"));
		/*<!--考核故障程度 -->*/
		check_fault(FunUtil.date_format("yyyy-MM"));
	
	}
	/**
	 * 机房配套设备检查
	 * */
	public void check_room_equ(){
		
		List<Map<String, Object>> list=OperationsCheckService.check_room_equ();
		float value=0;
		if(list!=null){
			for(int i=0;i<list.size();i++){				
				Map<String, Object> map=list.get(i);
			
				StringBuilder str=new StringBuilder();
				float score=0;
				if(map.get("envMonitor").toString().equals("2")){
					str.append("动环监控-无;");
				}
				if(map.get("air_conditioning").toString().equals("未配置")){
					str.append("空调-未配置;");
				}
				if(map.get("fire").toString().equals("未配置")){
					str.append("消防-未配置;");
				}
				if(map.get("lightning").toString().equals("未安装")){
					str.append("防雷接地-未安装;");
				}
				/*if(map.get("generatorConfig").toString().equals("0")){
					str.append("油机-无;");
				}*/
				if(map.get("has_spare_power").toString().equals("否")){
					str.append("供配电-无;");
				}
				if(str!=null && str.indexOf(";")>0){
					str.deleteCharAt(str.length()-1);
				}
				
				if(map.get("period").toString().equals("3")){
					score=(float) 0.2;
				}else{
					score=(float) 0.1;
				}
				value+=score;
				
				
				CheckRoomEquBean bean=new CheckRoomEquBean();
				bean.setCheck_type("机房及配套设施");
				bean.setCheck_child("机房及配套设施");
				bean.setCheck_tag("a1");
				bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
				bean.setScore(score);
				bean.setDetail(str.toString());
				bean.setPeriod(map.get("period").toString());
				bean.setBsId(map.get("bsId").toString());

				OperationsCheckService.insert_check_month_detail(bean);
				
				
			}
		}
	}
	/*考核运维人员是否达到20人*/
	public void check_phone(){
		int count=OperationsCheckService.check_phone_book();
		if(count<20){
			float score=(float) 0.5;
			score*=(20-count);
			if(score>3){
				score=3;
			}
			CheckRoomEquBean bean=new CheckRoomEquBean();
			bean.setCheck_type("运维团队及设施配置");
			bean.setCheck_child("运维团队");
			bean.setCheck_tag("b1");
			bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
			bean.setScore(score);
			bean.setDetail("运维团队，"+count+"人,少于20人");
			OperationsCheckService.insert_check_month_detail(bean);
		}
	}
	/*<!-- 考核运办公场所 ,考核仪器仪表 ,考核运维车辆不足3辆 -->*/
	public void check_officeaddress(){
		Map<String, Object> map=OperationsCheckService.check_officeaddress();
		StringBuilder str=new StringBuilder();
		float score=0;
		if(FunUtil.StringToInt(map.get("address").toString())<1){
			str.append("未设立专门的办公场所的;");
			score+=1;
		}
		if(FunUtil.StringToInt(map.get("instrument").toString())<1){
			str.append("仪器仪表缺少;");
			score+=0.2;
		}
		if(FunUtil.StringToInt(map.get("bus").toString())<3){
			str.append("运维车辆不足3辆;");
			score+=(3-FunUtil.StringToInt(map.get("bus").toString()))*0.5;
		}

		if(str!=null && str.indexOf(";")>0){
			str.deleteCharAt(str.length()-1);
		}
		if(score>0){
			if(score>2){
				score=2;
			}
			CheckRoomEquBean bean=new CheckRoomEquBean();
			bean.setCheck_type("运维团队及设施配置");
			bean.setCheck_child("办公场所及运维设施");
			bean.setCheck_tag("b4");
			bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
			bean.setScore(score);
			bean.setDetail(str.toString());
			bean.setPeriod(map.get("period").toString());
			bean.setBsId(map.get("bsId").toString());

			OperationsCheckService.insert_check_month_detail(bean);
		}
	}
	/*	<!-- 考核故障程度 -->*/
	public void check_fault(String time){
		List<Map<String, Object>> list=OperationsCheckService.tb_zd_fault(time, 0);
	
	
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			if(map.get("type").toString().equals("1")){
				CheckRoomEquBean bean=new CheckRoomEquBean();
				bean.setCheck_type("故障管理");
				bean.setCheck_child("故障控制及故障处理历时");
				bean.setBsId("0");
				bean.setCheck_tag("d1");
				bean.setDetail("特别重大故障");
				bean.setPeriod(map.get("period").toString());				
				bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
				if(map.get("period").toString().equals("3")){
					int sc=5;
					
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%60==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/60);
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/60)+1);
						}
					}
					bean.setScore(sc);
					
				}else if(bean.getPeriod().toString().equals("4")){
					int sc=10;
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%60==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/60);
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/60)+1);
						}
					}
					bean.setScore(sc);
				}
				OperationsCheckService.insert_check_month_detail(bean);
			}else if(map.get("type").toString().equals("2")){
				CheckRoomEquBean bean=new CheckRoomEquBean();
				bean.setCheck_type("故障管理");
				bean.setCheck_child("故障控制及故障处理历时");
				bean.setBsId("0");
				bean.setCheck_tag("d1");
				bean.setPeriod(map.get("period").toString());
				bean.setDetail("重大故障");
				bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
                if(map.get("period").toString().equals("3")){
					int sc=1;
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%60==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/60);
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/60)+1);
						}
					}
					bean.setScore(sc);
					
				}else if(bean.getPeriod().toString().equals("4")){
					int sc=2;
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%60==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/60);
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/60)+1);
						}
					}
				}
				OperationsCheckService.insert_check_month_detail(bean);
			}
			
		}
		
	}

	/*<!-- 考核基站级别 -->*/
	public void check_level(String time){
		List<Map<String, Object>> list=OperationsCheckService.bs_error(time);
		
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			float sc=0;
			int timeout=0;
			String message="";
			
			//写入每月基站断站时长
			CheckMonthBsBreakBean checkMonthBsBreakBean=new CheckMonthBsBreakBean();
			checkMonthBsBreakBean.setBsId(FunUtil.StringToInt(map.get("bsId")));
			checkMonthBsBreakBean.setLevel(FunUtil.StringToInt(map.get("level")));
			checkMonthBsBreakBean.setPeriod(FunUtil.StringToInt(map.get("period")));
			checkMonthBsBreakBean.setBreak_time(FunUtil.StringToInt(map.get("faultTimeTotal")));
			checkMonthBsBreakBean.setCheck_date(time+"-01");
			checkMonthBsBreakBean.setCheckcut_time(FunUtil.StringToInt(map.get("checkCutTime")));
			OperationsCheckService.insert_check_month_bs_break(checkMonthBsBreakBean);
			///-----	//写入每月基站断站时长
			
			/**一级基站*/
			if(map.get("level").toString().equals("1")){
				timeout=FunUtil.StringToInt(map.get("faultTimeTotal").toString())
						-FunUtil.StringToInt(map.get("checkCutTime").toString());
				if(timeout-120>1){
					if((timeout-120)%60==0){
						sc=(float) ((int)((timeout-120)/60)*0.5);
					}else{
						sc=(float) ((int)((timeout-120)/60+1)*0.5);
					}
				}
				/**二级基站*/
			}else if(map.get("level").toString().equals("2")){
				timeout=FunUtil.StringToInt(map.get("faultTimeTotal").toString())
						-FunUtil.StringToInt(map.get("checkCutTime").toString());
				if(timeout-180>1){
					if((timeout-120)%60==0){
						sc=(float) ((int)((timeout-120)/60)*0.3);
					}else{
						sc=(float) ((int)((timeout-120)/60+1)*0.3);
					}
				}
				/**三级基站*/
			}else if(map.get("level").toString().equals("3")){
				timeout=FunUtil.StringToInt(map.get("faultTimeTotal").toString())
						-FunUtil.StringToInt(map.get("checkCutTime").toString());
				if(timeout-300>1){
					if((timeout-120)%60==0){
						sc=(float) ((int)((timeout-120)/60)*0.1);
					}else{
						sc=(float) ((int)((timeout-120)/60+1)*0.1);
					}
				}
			}
			
				
			if(sc>0){
				CheckRoomEquBean bean=new CheckRoomEquBean();
				bean.setCheck_type("故障管理");
				bean.setCheck_child("故障控制及故障处理历时");
				bean.setCheck_tag("d1");
				bean.setBsId(map.get("bsId").toString());
				bean.setFault_time(timeout);
				bean.setPeriod(map.get("period").toString());
				bean.setDetail(message);
				bean.setCheck_date(time);
				bean.setScore(sc);
				OperationsCheckService.insert_check_month_detail(bean);
			}
			
		}
		
	}

	/*考核备品备件完好率低于80%*/
	public void check_attachment(){
		List<Map<String, Object>> list=OperationsCheckService.check_attachment();
		float value=0;
		if(list!=null){
			for(int i=0;i<list.size();i++){				
				Map<String, Object> map=list.get(i);
				String str="完好率低于80%";
				float score=(float) 0.5;
				if(Float.parseFloat(map.get("perc").toString())<50){
					score=1;
					str="完好率低于50%";
				}
				value+=score;
				if(value>5){
					break;
				}

				CheckRoomEquBean bean=new CheckRoomEquBean();
				bean.setCheck_type("故障管理");
				bean.setCheck_child("备品备件-"+map.get("attachment_name"));
				bean.setCheck_tag("d2");
				bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
				bean.setScore(score);
				bean.setDetail(str.toString());

				OperationsCheckService.insert_check_month_detail(bean);
				
			}
		}
	}
	
}
class Money extends TimerTask{
	protected final Log log= LogFactory.getLog(Money.class);
	@Override
	public void run() {
		Calendar c=Calendar.getInstance();
		int day=c.get(Calendar.DAY_OF_MONTH);
		log.info("考核项目检查开始--检查扣款项目");
		OperationsCheckService.del_money(FunUtil.date_format("yyyy-MM"));
		log.info("清除考核扣款记录");
		/*<!-- 考核一级基站 -->*/
		//check_level(FunUtil.date_format("yyyy-MM"));
		/*	<!-- 考核故障 -->*/
		check_fault(FunUtil.date_format("yyyy-MM"));
		/*	<!-- 考核故障 超时-->*/
		check_fault_timeout(FunUtil.date_format("yyyy-MM"));
		
		
	}
	/*<!-- 考核基站级别 -->*/
	public void check_level(String time){
		List<Map<String, Object>> list=OperationsCheckService.bs_error_money(time);
		
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			int timeout=FunUtil.StringToInt(map.get("timeout").toString());
			int money=0;
			int fault_time=timeout;
			int check_time=0;
			int addTag=0;
			String child="";
			String tag="";
			CheckMoneyBean bean=new CheckMoneyBean();
			
			if(map.get("level")!=null){
				if(map.get("level").toString().equals("1")){
					
					if(map.get("checkTime")!=null){
						check_time=(int) Double.parseDouble(map.get("checkTime").toString());
					}
					/*if(timeout-check_time-60>0){
						addTag=1;
					}*/
					tag="a2";
					child="一级基站";
					bean.setTimeout_standard(60);
					
				}else if(map.get("level").toString().equals("2")){
					if(map.get("checkTime")!=null){
						check_time=(int) Double.parseDouble(map.get("checkTime").toString());
					}
					/*if(timeout-check_time-300>0){
						addTag=1;
					}*/
					tag="a3";
					child="二级基站";
					bean.setTimeout_standard(300);
					
				}else if(map.get("level").toString().equals("3")){
					if(map.get("checkTime")!=null){
						check_time=(int) Double.parseDouble(map.get("checkTime").toString());
					}
					/*if(timeout-check_time-540>0){
						addTag=1;
					}*/
					tag="a4";
					child="三级基站";
					bean.setTimeout_standard(540);
				}else{
				}
				
				bean.setCheck_type("服务可用率");
				bean.setCheck_child(child);
				bean.setBsId(map.get("bsId").toString());
				if(map.get("period")!=null){
					bean.setPeriod(map.get("period").toString());
				}
				bean.setFault_time(fault_time);
				bean.setCheck_time(check_time);
				bean.setLevel(FunUtil.StringToInt(map.get("level").toString()));
				bean.setCheck_tag(tag);
				bean.setCheck_date(time);
				bean.setCheck_datetime(map.get("faultRecoveryTime").toString());
				bean.setMoney(money);
				OperationsCheckService.insert_check_month_money_detail(bean);
				
			}
			
			
			
		}
	}

	/*	<!-- 考核故障程度 -->*/
	public void check_fault(String time){
		List<Map<String, Object>> list=OperationsCheckService.tb_zd_fault(time, 0);
	
		int sc=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			if(map.get("type").toString().equals("1")){
				CheckMoneyBean bean=new CheckMoneyBean();
				bean.setCheck_type("故障控制");
				bean.setCheck_child("特别重大故障");
				bean.setBsId("0");
				bean.setPeriod(map.get("period").toString());				
				bean.setDetail("发生一次特别重大故障");
				bean.setCheck_tag("b2");
				bean.setCheck_date(time);
				bean.setFault_time(FunUtil.StringToInt(map.get("usetime").toString()));
				
				sc=20000;
		
				
				bean.setMoney(sc);
				OperationsCheckService.insert_check_month_money_detail(bean);
			}else if(map.get("type").toString().equals("2")){
				CheckMoneyBean bean=new CheckMoneyBean();
				bean.setCheck_type("故障控制");
				bean.setCheck_child("重大故障");
				bean.setPeriod(map.get("period").toString());
				bean.setDetail("发生一次重大故障");
				bean.setCheck_tag("b1");
				bean.setCheck_date(time);
				bean.setFault_time(FunUtil.StringToInt(map.get("usetime").toString()));
				sc=10000;
		
				
				bean.setMoney(sc);
				OperationsCheckService.insert_check_month_money_detail(bean);
			}
			
		}
		
	}
	/*	<!-- 考核故障超时 -->*/
	public void check_fault_timeout(String time){
		List<Map<String, Object>> list=OperationsCheckService.tb_zd_fault(time, 0);
	
		
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
		
			if(map.get("type").toString().equals("1")){
				int sc=0;
				CheckMoneyBean bean=new CheckMoneyBean();
				bean.setCheck_type("故障控制");
				bean.setCheck_child("特别重大故障");
				bean.setBsId("0");
				bean.setPeriod(map.get("period").toString());				
				bean.setDetail("发生一次特别重大故障");
				bean.setCheck_tag("c1");
				bean.setCheck_date(time);
				bean.setFault_time(FunUtil.StringToInt(map.get("usetime").toString()));
				if(map.get("period").toString().equals("4")){
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%5==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/5)*500;
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/5)+1)*500;
						}
					}
					
					bean.setMoney(sc);
				}
				if(sc>0){
					OperationsCheckService.insert_check_month_money_detail(bean);
				}
				
				
			}else if(map.get("type").toString().equals("2")){
				int sc=0;
				CheckMoneyBean bean=new CheckMoneyBean();
				bean.setCheck_type("故障控制");
				bean.setCheck_child("重大故障");
				bean.setPeriod(map.get("period").toString());
				bean.setDetail("发生一次重大故障");
				bean.setCheck_tag("c2");
				bean.setCheck_date(time);
				bean.setFault_time(FunUtil.StringToInt(map.get("usetime").toString()));
				if(map.get("period").toString().equals("4")){
					if(FunUtil.StringToInt(map.get("usetime").toString())-60>1){
						if(FunUtil.StringToInt(map.get("usetime").toString())%5==0){
							sc+=(FunUtil.StringToInt(map.get("timeout").toString())/5)*200;
						}else{
							sc+=((FunUtil.StringToInt(map.get("timeout").toString())/5)+1)*200;
						}
					}
					bean.setMoney(sc);
				}
				if(sc>0){
					OperationsCheckService.insert_check_month_money_detail(bean);
				}
			}
			
		}
		
	}
	/** 通信保障*/
}

