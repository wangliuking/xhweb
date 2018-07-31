package xh.mybatis.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsFlashBean;
import xh.mybatis.bean.BsRunStatusBean;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.EmhBean;
import xh.mybatis.mapper.BsStatusMapper;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsStatusService {
	
	public static int alarmVoiceCount() throws Exception {
		int bs_offline_count=0;
		int ups_count=0;
		int water_count=0;
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		Map<String,Object> map=mapper.emhVoiceCount();
		if(FunUtil.readXml("alarm", "bs_offine").equals("1")){
			bs_offline_count=BsStatusService.bsOffVoiceCount();
		}
		
		
		if(FunUtil.readXml("alarm", "bs_water").equals("1")){
			water_count=Integer.parseInt(map.get("water").toString());
		}
		if(FunUtil.readXml("alarm", "bs_ups").equals("1")){
			ups_count=Integer.parseInt(map.get("ups").toString());
		}
		session.close();
		return bs_offline_count+ups_count+water_count;
		
	}

	public List<BsStatusBean> selectAllBsStatus() throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsStatusBean> BsStatus = mapper.selectAllBsStatus();
		/*session.commit();*/
		session.close();
		return BsStatus;
	}

	/**
	 * 导出现网基站的运行记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<BsStatusBean> excelToBsStatus() throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsStatusBean> list = mapper.excelToBsStatus();
		session.close();
		return list;
	}
	/**
	 * 导出现网基站的运行状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<BsRunStatusBean> excelToBsRunStatus() throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsRunStatusBean> list = mapper.excelToBsRunStatus();
		session.close();
		return list;
	}
	
	/**
	 * 基站区域列表告警
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> bsZoneAlarm(List<String> period) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.bsZoneAlarm(period);
			for (int i=0;i<list.size();i++) {
				Map<String, Object> map=list.get(i);
				if(map.get("value")==null){
					map.put("value", 0);
					list.set(i, map);
					/*list.remove(i);
					i--;*/
				}
				
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/* tera系统告警基站部分,获取当前的断站列表*/
	public static List<BsAlarmExcelBean> bsFaultInfo() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<BsAlarmExcelBean> list = new ArrayList<BsAlarmExcelBean>();
		
		try {
			SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=f.format(new Date(System.currentTimeMillis()-2*60*1000));
			
			/*Calendar c = Calendar.getInstance();
	        c.setTime(new Date());
	        c.add(Calendar.MONTH, -3);
	        Date m3 = c.getTime();
	        String mon3 = f.format(m3);*/
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("time", time);
			list = mapper.bsFaultInfo(map);
			
			

			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<BsAlarmExcelBean> bsRestoreInfo() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<BsAlarmExcelBean> list = new ArrayList<BsAlarmExcelBean>();
		
		try {
			SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=f.format(new Date(System.currentTimeMillis()-2*60*1000));
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("time", time);
			list = mapper.bsRestoreInfo(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, Object>> bsGroupTop5() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.bsGroupTop5();
			for (int i=0;i<list.size();i++) {
				Map<String, Object> map=list.get(i);
				
				
				map.put("name", map.get("bsId")==null?map.get("name"):map.get("bsId")+"-"+map.get("name"));
				map.remove("bsId");
				list.set(i, map);
				
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String, Object>> bsRadioTop5() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.bsRadioTop5();
			for (int i=0;i<list.size();i++) {
				Map<String, Object> map=list.get(i);
				map.put("name", map.get("bsId")==null?map.get("name"):map.get("bsId")+"-"+map.get("name"));
				/*map.put("value", Integer.parseInt(map.get("value").toString())+500);*/
				map.remove("bsId");
				list.set(i, map);
				
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 基站闪断列表
	 * @return
	 */
	public static List<Map<String, Object>> bsflash(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.bsflash(map);
			for(int i=0;i<list.size();i++){
				Map<String,Object> map2=list.get(i);
				if(map2.get("time_break")!=null && map2.get("time_restore")!=null){
					String time1=map2.get("time_break").toString();
					String time2=map2.get("time_restore").toString();
					
					long _time1=FunUtil.nowTimeMill(time1);
					long _time2=FunUtil.nowTimeMill(time2);
					map2.put("sumtime", timeStamp(_time1,_time2));
					list.set(i, map2);
				}
				
				
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<BsFlashBean> excelToBsflash(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<BsFlashBean> list = new ArrayList<BsFlashBean>();
		try {
			list = mapper.excelToBsflash(map);
			for(int i=0;i<list.size();i++){
				BsFlashBean bean=list.get(i);
				if(bean.getTime_break()!="" && bean.getTime_restore()!=""){
					String time1=bean.getTime_break();
					String time2=bean.getTime_restore();
					
					long _time1=FunUtil.nowTimeMill(time1);
					long _time2=FunUtil.nowTimeMill(time2);
					bean.setSumtime(timeStamp(_time1,_time2));
					list.set(i, bean);
				}
				
				
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static String timeStamp(long time1,long time2){ 
		
		int second_time=(int) ((time2-time1)/1000);
		
		String time = second_time+ "秒";  
		if( second_time> 60){  	  
		    int second = second_time% 60;  
		    int min = second_time / 60;  
		    time = min + "分" + second + "秒";  
		      
		    if( min > 60 ){  
		        min = (second_time / 60) % 60;  
		        int hour = (second_time / 60) /60 ;  
		        time = hour + "小时" + min + "分" + second + "秒";  
		  
		        if( hour > 24 ){  
		            hour =((second_time / 60) /60 ) % 24;  
		            int day = ((second_time / 60) /60 ) / 24 ;  
		            time = day + "天" + hour + "小时" + min + "分" + second + "秒";  
		        }  
		    }        
		  
		}    
		return time;          
	} 
	
	/**
	 * 基站闪断列表总数
	 * @return
	 */
	public static int bsflashCount(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count=0;
		try {
			count=mapper.bsflashCount(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	/**
	 * 4期所有基站环控告警
	 * @return
	 */
	public static List<Map<String, Object>> fourEmhAlarmList(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.fourEmhAlarmList(map);
			
			for(int i=0;i<list.size();i++){
				Map<String, Object> map2=list.get(i);
				Map<String, Object> map3=new HashMap<String, Object>();
				String AlarmText="";
				map3.put("bsId", map2.get("siteId")!=null?map2.get("siteId").toString():"");
				map3.put("DevName", map2.get("deviceName"));
				if(map2.get("singleId").toString().equals("017010") || map2.get("singleId").toString().equals("017011")){
					AlarmText="温度高告警";
				}
				if(map2.get("singleId").toString().equals("017012")){
					AlarmText="温度过低告警";
				}
				if(map2.get("singleId").toString().equals("017013")){
					AlarmText="湿度过高告警";
				}
				if(map2.get("singleId").toString().equals("017014")){
					AlarmText="湿度过低告警";
				}
				if(map2.get("singleId").toString().equals("017001")){
					AlarmText="水浸告警";
				}
				if(map2.get("singleId").toString().equals("017002")){
					AlarmText="烟雾告警";
				}
				if(map2.get("singleId").toString().equals("017004")){
					AlarmText="红外告警";
				}
				if(map2.get("singleId").toString().equals("017020")){
					AlarmText="门打开";
				}
				if(map2.get("singleId").toString().equals("092001")){
					AlarmText="市电停电";
				}
				if(map2.get("singleId").toString().equals("092002")){
					AlarmText="市电缺相";
				}
				if(map2.get("singleId").toString().equals("092004")){
					AlarmText="交流电压过高告警";
				}
				if(map2.get("singleId").toString().equals("092005")){
					AlarmText="交流电压过低告警";
				}
				if(map2.get("singleId").toString().equals("076002")){
					AlarmText="非智能设备采集器通信状态告警";
				}
				if(map2.get("singleId").toString().equals("076501")){
					AlarmText="开关电源通信状态告警";
				}
				if(map2.get("singleId").toString().equals("076507")){
					AlarmText="UPS设备通信状态告警";
				}
				if(map2.get("singleId").toString().equals("076509")){
					AlarmText="智能电表通信状态告警";
				}
				
				map3.put("AlarmText", (map2.get("siteId")!=null?map2.get("siteId").toString():"")+"-"+map2.get("siteName")+". "+map2.get("deviceName")+". "+AlarmText);
				map3.put("time", map2.get("updateTime").toString());
				list.set(i, map3);
				
			}
			
			
			sqlSession.close();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 4期所有基站环控告警数目
	 * @return
	 */
	public static int fourEmhAlarmListCount() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count = mapper.fourEmhAlarmListCount();
			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int bsOfflineCount() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count = mapper.bsOfflineCount();
			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 基站下的环控fsuId
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static String fsuIdBySiteId(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		String fsuId = mapper.fsuIdBySiteId(siteId);
		session.close();
		return fsuId;
	}

	/**
	 * 基站下的环控状态
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> bsEmh(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		/* List<EmhBean> list = mapper.bsEmh(fsuId); */

		String fsuId = null;
		HashMap<String, Object> result = new HashMap<String, Object>();

		fsuId = BsStatusService.fsuIdBySiteId(siteId);

		List<EmhBean> list = mapper.bsEmh(fsuId);
		;
		Map<String, Object> map = new HashMap<String, Object>();
		for (EmhBean emhBean : list) {
			map.put(emhBean.getSingleId(), emhBean.getSingleValue());
			map.put("time", emhBean.getUpdateTime());
		}
		DecimalFormat df = new DecimalFormat("#.0");

		if (list.size() > 0) {

			try {

				result.put("time", map.get("time").toString());
				// 开关量
				if (map.get("017001") != null) {
					result.put("water",
							Float.parseFloat(map.get("017001").toString()));// 水浸告警
				}
				if (map.get("017002") != null) {
					result.put("smoke",
							Float.parseFloat(map.get("017002").toString()));// 烟雾告警
				}
				if (map.get("017004") != null) {
					result.put("red",
							Float.parseFloat(map.get("017004").toString()));// 红外告警
				}
				if (map.get("017020") != null) {
					result.put("door",
							Float.parseFloat(map.get("017020").toString()));// 门碰告警
				}

				// 温湿度

				if (map.get("017301") != null) {
					result.put("temp", df.format(Float.parseFloat(map.get(
							"017301").toString())));// 温度XX ℃
				}
				if (map.get("017302") != null) {
					result.put("damp", df.format(Float.parseFloat(map.get(
							"017302").toString())));// 湿度XX %RH
				}

				// UPS
				if (map.get("008304") != null) {
					result.put("ups1",
							Float.parseFloat(map.get("008304").toString()));// 输入相电压Ua
				}
				if (map.get("008315") != null) {
					result.put("ups2",
							Float.parseFloat(map.get("008315").toString()));// 输出相电压Ua
				}
				if (map.get("008321") != null) {
					result.put("ups3",
							Float.parseFloat(map.get("008321").toString()));// 输出频率
				}
				if (map.get("008334") != null) {
					result.put("ups4",
							Float.parseFloat(map.get("008334").toString()));// 电池组电压
				}
				if (map.get("008408") != null) {
					result.put("ups5",
							Float.parseFloat(map.get("008408").toString()));// 电池方式工作状态
				}
				// FSU

				if (map.get("076002") != null) {
					result.put("fsu1",
							Float.parseFloat(map.get("076002").toString()));// 非智能设备采集器通信状态告警

				}
				if (map.get("076501") != null) {
					result.put("fsu2",
							Float.parseFloat(map.get("076501").toString()));// 开关电源通信状态告警

				}
				if (map.get("076507") != null) {
					result.put("fsu3",
							Float.parseFloat(map.get("076507").toString()));// UPS设备通信状态告警

				}
				if (map.get("076509") != null) {
					result.put("fsu4",
							Float.parseFloat(map.get("076509").toString()));// 智能电表通信状态告警

				}
				/*if (map.get("008408") != null) {
					result.put("fsu5",
							Float.parseFloat(map.get("008408").toString()));// 智能电表通信状态告警

				}*/
				// 智能电表
				if (map.get("092301") != null) {
					result.put("e1",
							Float.parseFloat(map.get("092301").toString()));// 电压--->相电压Ua

				}
				if (map.get("092304") != null) {
					result.put("e2",
							Float.parseFloat(map.get("092304").toString()));// 电流--->相电流Ia

				}
				if (map.get("092314") != null) {
					result.put("e3",
							Float.parseFloat(map.get("092314").toString()));// 功率因数--->总功率因数

				}
				if (map.get("092316") != null) {
					result.put("e4",
							Float.parseFloat(map.get("092316").toString()));// 电度度数-->正向有功电能

				}
				if (map.get("092315") != null) {
					result.put("e5",
							Float.parseFloat(map.get("092315").toString()));// 频率-->频率

				}

			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		session.close();
		return result;
	}

	/**
	 * 基站下的环控告警
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static List<EmhBean> bsEmhAlarm(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		String fsuId = null;

		fsuId = BsStatusService.fsuIdBySiteId(siteId);
		List<EmhBean> list = mapper.bsEmhAlarm(fsuId);
		session.close();
		return list;
	}

	/**
	 * 基站下的bsc状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> bsc(Map<String, Object> map) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.bsc(map);
		session.close();
		return list;
	}
	/**
	 * 基站断站告警
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> bsOffList() throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.bsOffList();
		session.close();
		return list;
	}
	/**
	 * 基站断站声音告警数目 
	 * @return
	 * @throws Exception
	 */
	public static int bsOffVoiceCount() {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count = mapper.bsOffVoiceCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/**
	 * 基站断站告警更新
	 * @return
	 */
	public static void bsOffVoiceChange() {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		Map<String,Object> map1=new HashMap<String, Object>();
		map1.put("tag", 1);
		Map<String,Object> map2=new HashMap<String, Object>();
		map2.put("tag", 2);
		try {
			mapper.bsOffVoiceChange(map1);
			session.commit();
			mapper.bsOffVoiceChange(map2);
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	}
	/**
	 * 更新基站断站告警状态
	 */
	public static void updateAlarmStatus() {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		try {
			mapper.updateAlarmStatus();
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	}

	/**
	 * 基站下的psm状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> psm(Map<String, Object> map) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.psm(map);
		session.close();
		return list;
	}

	/**
	 * 基站下的bsr状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> bsr(Map<String, Object> map) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.bsr(map);
		session.close();
		return list;
	}

	/**
	 * 基站下的dpx状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> dpx(Map<String, Object> map) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.dpx(map);
		session.close();
		return list;
	}

	/**
	 * 基站异常统计
	 * @return
	 */
	public static int  MapBsOfflineCount() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count=mapper.MapBsOfflineCount();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 交换中心异常统计
	 * @return
	 */
	public static int  MapMscAlarmCount() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count=mapper.MapMscAlarmCount();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 调度台异常统计
	 * @return
	 */
	public static int  MapDispatchAlarmCount() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count = 0;
		try {
			count=mapper.MapDispatchAlarmCount();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * tera系统告警基站部分  导出excel
	 * @param map
	 * @return
	 */
	public static List<BsAlarmExcelBean> bsAlarmExcel(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<BsAlarmExcelBean> list = new ArrayList<BsAlarmExcelBean>();
		try {
			list = mapper.bsAlarmExcel(map);
			
			/*System.out.println(Arrays.toString(list.toArray()));*/
			/*Map<String, Object> map2=new HashMap<String, Object>();
			map2.put("bsId", "");
			map2.put("name", "");
			map2.put("start", 0);
			map2.put("limit", 500);
			List<BsstationBean> bs = BsstationService.bsInfo(map2);*/
			
			
			
			sqlSession.close();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> bsFaultList(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = mapper.bsFaultList(map);
			sqlSession.close();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int bsFaultListCount(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count=0;
		try {
			count = mapper.bsFaultListCount(map);
			sqlSession.close();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/*更新基站故障表 */
	public static int editBsFault(BsAlarmExcelBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = sqlSession.getMapper(BsStatusMapper.class);
		int count=0;
		try {
			count = mapper.editBsFault(bean);
			sqlSession.close();

		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
