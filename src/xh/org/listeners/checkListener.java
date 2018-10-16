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
		
		if(day==1){
			log.info("考核项目检查开始");
			/*机房配套设备检查	*/
			check_room_equ();
			/*考核运维人员是否达到20人*/
			check_phone();
			/*<!-- 考核运办公场所 ,考核仪器仪表 ,考核运维车辆不足3辆 -->*/
			check_officeaddress();
			/*考核备品备件完好率低于80%*/
			check_attachment();
			/*<!-- 考核一级基站 -->*/
		    check_onelevel_bs();
			/*<!-- 考核二级基站 -->*/
			check_twolevel_bs();
			/*<!-- 考核三级级基站 -->*/
			check_threelevel_bs();
		}
		
	
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
				if(map.get("generatorConfig").toString().equals("0")){
					str.append("油机-无;");
				}
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
				if(value>20){
					break;
				}
				
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
	/*	<!-- 考核特别重大故障 -->*/
	public void check_onelevel_fault(){
		List<Map<String, Object>> list=OperationsCheckService.check_onelevel_fault();
		int count=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			count+=Float.parseFloat(map.get("num").toString());
			
		}
		if(count>0){
			m=(int) count*10;
		}
		
		CheckRoomEquBean bean=new CheckRoomEquBean();
		bean.setCheck_type("故障管理");
		bean.setCheck_child("故障控制及故障处理历时");
		bean.setCheck_tag("d1");
		bean.setDetail("特别重大故障");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setScore(m);
		OperationsCheckService.insert_check_month_detail(bean);
		
		
	}
	/*	<!-- 考核重大故障 -->*/
	public void check_twolevel_fault(){
		List<Map<String, Object>> list=OperationsCheckService.check_twolevel_fault();
		int count=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			count+=Float.parseFloat(map.get("num").toString());
			
		}
		if(count>0){
			m=(int) count*2;
		}
		CheckRoomEquBean bean=new CheckRoomEquBean();
		bean.setCheck_type("故障管理");
		bean.setCheck_child("故障控制及故障处理历时");
		bean.setCheck_tag("d1");
		bean.setDetail("重大故障");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setScore(m);
		OperationsCheckService.insert_check_month_detail(bean);
	}
	/*<!-- 考核一级基站 -->*/
	public void check_onelevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_onelevel_bs();
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			String severity=map.get("severity").toString();
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(severity.equals("特别重大故障")){
				if(timeout>2){
					m+=(timeout-2)*1;
				}
				
			}else if(severity.equals("重大故障")){
				if(timeout>2){
					m+=(timeout-2)*1;
				}
			}else{
				if(timeout>2){
					m+=(timeout-2)*0.5;
				}
			}
			
			
		}
		CheckRoomEquBean bean=new CheckRoomEquBean();
		bean.setCheck_type("故障管理");
		bean.setCheck_child("故障控制及故障处理历时");
		bean.setCheck_tag("d1");
		bean.setDetail("一级基站");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setScore(m);
		OperationsCheckService.insert_check_month_detail(bean);
	}
	/*<!-- 考核二级基站 -->*/
	public void check_twolevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_twolevel_bs();
		float a=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			String severity=map.get("severity").toString();
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(severity.equals("特别重大故障")){
				if(timeout>3){
					m+=(timeout-3)*1;
				}
				
			}else if(severity.equals("重大故障")){
				if(timeout>3){
					m+=(timeout-3)*1;
				}
			}else{
				if(timeout>3){
					m+=(timeout-3)*0.3;
				}
			}
			
		}
		CheckRoomEquBean bean=new CheckRoomEquBean();
		bean.setCheck_type("故障管理");
		bean.setCheck_child("故障控制及故障处理历时");
		bean.setCheck_tag("d1");
		bean.setDetail("二级基站");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setScore(m);
		OperationsCheckService.insert_check_month_detail(bean);
	}
	/*<!-- 考核三级级基站 -->*/
	public void check_threelevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_threelevel_bs();
		float a=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			String severity=map.get("severity").toString();
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(severity.equals("特别重大故障")){
				if(timeout>5){
					m+=(timeout-5)*1;
				}
				
			}else if(severity.equals("重大故障")){
				if(timeout>5){
					m+=(timeout-5)*1;
				}
			}else{
				if(timeout>5){
					m+=(timeout-5)*0.1;
				}
			}
			
		}
		CheckRoomEquBean bean=new CheckRoomEquBean();
		bean.setCheck_type("故障管理");
		bean.setCheck_child("故障控制及故障处理历时");
		bean.setCheck_tag("d1");
		bean.setDetail("三级基站");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setScore(m);
		OperationsCheckService.insert_check_month_detail(bean);
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

	@Override
	public void run() {
		Calendar c=Calendar.getInstance();
		int day=c.get(Calendar.DAY_OF_MONTH);
		if(day==1){
			/*<!-- 考核一级基站 -->*/
			check_onelevel_bs();
			/*<!-- 考核二级基站 -->*/
			check_twolevel_bs();
			/*<!-- 考核三级基站 -->*/
			check_threelevel_bs();
			/*	<!-- 考核特别重大故障 -->*/
			check_onelevel_fault();
			/*	<!-- 考核重大故障 -->*/
			check_twolevel_fault();
		}
		
		
		
	}
	/*<!-- 考核一级基站 -->*/
	public void check_onelevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_onelevel_bs();
		float a=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(timeout>1){
				a+=(timeout-1);
			}			
		}
		if(a>0){
			m=(int) (a*1000);
		}
		CheckMoneyBean bean=new CheckMoneyBean();
		bean.setCheck_type("服务可用率");
		bean.setCheck_child("一级基站");
		bean.setCheck_tag("a2");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setMoney(m);
		OperationsCheckService.insert_check_month_money_detail(bean);
	}
	/*<!-- 考核二级基站 -->*/
	public void check_twolevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_twolevel_bs();
		float a=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(timeout>5){
				a+=(timeout-5);
			}
			
		}
		if(a>0){
			m=(int) (a*1000);
		}
		CheckMoneyBean bean=new CheckMoneyBean();
		bean.setCheck_type("服务可用率");
		bean.setCheck_child("二级基站");
		bean.setCheck_tag("a3");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setMoney(m);
		OperationsCheckService.insert_check_month_money_detail(bean);
	}
	/*<!-- 考核三级级基站 -->*/
	public void check_threelevel_bs(){
		List<Map<String, Object>> list=OperationsCheckService.check_threelevel_bs();
		float a=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			float timeout=Float.parseFloat(map.get("timeout").toString());
			if(timeout>9){
				a+=(timeout-9);
			}
			
		}
		if(a>0){
			m=(int) (a*1000);
		}
		CheckMoneyBean bean=new CheckMoneyBean();
		bean.setCheck_type("服务可用率");
		bean.setCheck_child("三级基站");
		bean.setCheck_tag("a4");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setMoney(m);
		OperationsCheckService.insert_check_month_money_detail(bean);
	}
	/*	<!-- 考核特别重大故障 -->*/
	public void check_onelevel_fault(){
		List<Map<String, Object>> list=OperationsCheckService.check_onelevel_fault();
		int count=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			count+=Float.parseFloat(map.get("num").toString());
			
		}
		if(count>0){
			m=(int) count*20000;
		}
		CheckMoneyBean bean=new CheckMoneyBean();
		bean.setCheck_type("故障控制");
		bean.setCheck_child("特别重大故障");
		bean.setCheck_tag("b2");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setMoney(m);
		OperationsCheckService.insert_check_month_money_detail(bean);
	}
	/*	<!-- 考核重大故障 -->*/
	public void check_twolevel_fault(){
		List<Map<String, Object>> list=OperationsCheckService.check_twolevel_fault();
		int count=0;
		int m=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=list.get(i);
			count+=Float.parseFloat(map.get("num").toString());
			
		}
		if(count>0){
			m=(int) count*10000;
		}
		CheckMoneyBean bean=new CheckMoneyBean();
		bean.setCheck_type("故障控制");
		bean.setCheck_child("重大故障");
		bean.setCheck_tag("b1");
		bean.setCheck_date(FunUtil.date_format("yyyy-MM"));
		bean.setMoney(m);
		OperationsCheckService.insert_check_month_money_detail(bean);
	}
	
}
