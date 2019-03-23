package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsJiFourBean;
import xh.mybatis.bean.Sf800mAlarmBean;
import xh.mybatis.mapper.BsAlarmMapper;
import xh.mybatis.mapper.UserStatusMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsAlarmService {
	
	/*实时添加基站断站记录 */
	public static int addBsFault(BsAlarmExcelBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int code=-1;
		try {
			if(mapper.bsFaultIsHave(Integer.parseInt(bean.getBsId()))<1){
				code=mapper.addBsFault(bean);
				sqlSession.commit();
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	public static void bsRestore(BsAlarmExcelBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.bsRestore(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有告警信息
	 */
	public List<BsAlarmBean> selectAllBsAlarm() throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectAllBsAlarm();
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}
	
	/**
	 * 条件查询告警信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BsAlarmBean> selectBsAlarmList(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectBsAlarmList(map);
	        session.close();
	        return BsAlarm;   
	}
	
	public static List<Map<String,Object>> selectTop5(){
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.selectTop5();
			session.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 告警总数
	 * @return
	 */
	public static int BsAlarmCount(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.BsAlarmCount(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int dispatch_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.dispatch_alarm();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int link_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.link_alarm();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 告警等级统计
	 * @return
	 */
	public static List<HashMap> bsAlarmLevelChart(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmLevelChart(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 告警类型统计
	 * @return
	 */
	public static List<HashMap> bsAlarmTypeChart(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmTypeChart(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 确认告警信息
	 * @param map
	 */
	public static void identifyBsAlarmById(String id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.identifyBsAlarmById(id);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.write_bs_emh_eps(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void del_bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.del_bs_emh_eps(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.bs_emh_eps(bean);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
/*	if(bean.getSingleId().equals("008304")){ //eps输入相电压
		rs.put("ups1", bean.getSingleValue());
	}else if(bean.getSingleId().equals("008315")){//eps输出相电压
		rs.put("ups2", bean.getSingleValue());
	}else if(bean.getSingleId().equals("008334")){//电池组电压
		rs.put("ups4", bean.getSingleValue());
	}else if(bean.getSingleId().equals("017021")){
		rs.put("ups5", bean.getSingleValue());//市电开关量 0：正常
	}else if(bean.getSingleId().equals("017031")){//油机开关量 1：正在告警 ；0：正在发电
		rs.put("ups6", bean.getSingleValue());
	}else if(bean.getSingleId().equals("076509")){//电表通讯状态0：正常
		rs.put("ups7", bean.getSingleValue());
	}else if(bean.getSingleId().equals("092316")){//电表当前读数
		rs.put("ups8", bean.getSingleValue());
	}else if(bean.getSingleId().equals("092301")){//电表电压
		rs.put("ups9", bean.getSingleValue());
	}else{
		rs.put("tag", bean.getSingleValue());
	}*/			
	public static void bs_ji_four() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<BsJiFourBean> list=new ArrayList<BsJiFourBean>();
		int eps=0;
		boolean e4=false;
		try {
			list=mapper.bs_ji_four();
			
			for(int i=0,a=list.size();i<a;i++){
				BsJiFourBean bean=new BsJiFourBean();
				bean=list.get(i);
				bean.setDeviceId("0802");
				Map<String,Object> compare=bs_ji_four_compare(bean.getFsuId());
				
				eps=bs_emh_eps(bean);
				e4=e4(bean.getFsuId());
				boolean x=string_to_double(compare.get("ups1"))>
		         string_to_double(compare.get("ups2")) && 
		         string_to_double(compare.get("ups2"))!=0;
		        boolean y=string_to_double(compare.get("ups1"))<
				         string_to_double(compare.get("ups2")) && 
				         string_to_double(compare.get("ups1"))!=0;
		        
		        if(compare.get("ups5")==null && compare.get("ups6")==null){//未整改
		        	if(e4 || x || y){
						
						if(eps==0){
							if(e4|| string_to_double(compare.get("ups1"))<20 || bean.getSingleValue()==1){
									     bean.setDescription("市电中断");
									     write_bs_emh_eps(bean);
									     /*System.out.println("没有记录：写入-》"+bean);*/
							}else if(string_to_double(compare.get("ups4"))<46){
									 bean.setDescription("电池电压过低");
									 write_bs_emh_eps(bean);
							}else if(x){
									     bean.setDescription("市电电压过高");
									     write_bs_emh_eps(bean);
							}else if(y){
									     bean.setDescription("市电电压过低");
									     write_bs_emh_eps(bean);
							}else{
									bean.setDescription("市电未知故障");
									 write_bs_emh_eps(bean);
									 
							}
						}
					}else{
						if(eps>0){
							System.out.println("记录已经存在：更新-》"+bean);					
							del_bs_emh_eps(bean);
						}
					}
		        	
		        }else{//已整改
		        	if(string_to_double(compare.get("ups5"))==1 || e4){
						
						if(eps==0){
							 bean.setDescription("市电中断");
							 write_bs_emh_eps(bean);
						}
					}else{
						if(eps>0){
							System.out.println("记录已经存在：更新-》"+bean);					
							del_bs_emh_eps(bean);
						}
					}
		        }
		        
		        
				
				
			}
			
			sqlSession.close();
			list=null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Double string_to_double(Object str){
		if(str==null || str.equals("")){
			return (double) -1;
		}else{
			return Double.parseDouble(str.toString());
		}
	}
	public static Map<String,Object> bs_ji_four_compare(String fsuId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<BsJiFourBean> list=new ArrayList<BsJiFourBean>();
		Map<String,Object>  rs=new HashMap<String, Object>();
		int eps=0;
		BsJiFourBean bean=new BsJiFourBean();
		try {
			list=mapper.bs_ji_four_compare(fsuId);
			
			
			for(int i=0,a=list.size();i<a;i++){
				bean=list.get(i);
				/*singleId = "008304" or singleId = "008334" or singleId = "008408" or singleId = "008315"*/
				 /*singleId = "017021" or singleId = "017031" or singleId = "076509" or singleId = "092316"*/
				if(bean.getSingleId().equals("008304")){ //eps输入相电压
					rs.put("ups1", bean.getSingleValue());
				}else if(bean.getSingleId().equals("008315")){//eps输出相电压
					rs.put("ups2", bean.getSingleValue());
				}else if(bean.getSingleId().equals("008334")){//电池组电压
					rs.put("ups4", bean.getSingleValue());
				}else if(bean.getSingleId().equals("017021")){
					rs.put("ups5", bean.getSingleValue());//市电开关量 0：正常
				}else if(bean.getSingleId().equals("017031")){//油机开关量 1：正在告警 ；0：正在发电
					rs.put("ups6", bean.getSingleValue());
				}else if(bean.getSingleId().equals("076509")){//电表通讯状态0：正常
					rs.put("ups7", bean.getSingleValue());
				}else if(bean.getSingleId().equals("092316")){//电表当前读数
					rs.put("ups8", bean.getSingleValue());
				}else if(bean.getSingleId().equals("092301")){//电表电压
					rs.put("ups9", bean.getSingleValue());
				}else{
					rs.put("tag", bean.getSingleValue());
				}			
			}		
			sqlSession.close();
			list=null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void bs_water_four() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<BsJiFourBean> list=new ArrayList<BsJiFourBean>();
		int eps=0;
		BsJiFourBean bean=new BsJiFourBean();
		try {
			list=mapper.bs_water_four();
			
			for(int i=0,a=list.size();i<a;i++){
				bean=list.get(i);
				eps=bs_emh_eps(bean);
				if(bean.getSingleValue()==1){
					if(eps==0){
						 bean.setDescription("水浸告警");
						 write_bs_emh_eps(bean);
					}
				}else{
					if(eps>0){
						del_bs_emh_eps(bean);
					}
				}
			}			
			sqlSession.close();
			list=null;
			bean=null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int clear_sf_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int rs=-1;
		
		try {
			rs=mapper.clear_sf_alarm();
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static List<Sf800mAlarmBean> sf_800m_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sf800M);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<Sf800mAlarmBean> list=new ArrayList<Sf800mAlarmBean>();
		
		try {
			list=mapper.sf_800m_alarm();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Sf800mAlarmBean> sf_order_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<Sf800mAlarmBean> list=new ArrayList<Sf800mAlarmBean>();
		
		try {
			list=mapper.sf_order_alarm();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static void insert_sf_alarm() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<Sf800mAlarmBean> orderlist=sf_order_alarm();
		List<Sf800mAlarmBean> order800m=sf_800m_alarm();
		for (Sf800mAlarmBean order : orderlist) {
			for (Sf800mAlarmBean order2 : order800m) {
				if(order2.getBsId()==order.getBsId() && order2.getZbdldm().equals(order.getZbdldm())){
					order2.setTime(order.getTime());
					order2.setStatus(order.getStatus());
				}
			}	
		}
		
		System.out.println("sf-alarm number->"+order800m.size());
		
		if(clear_sf_alarm()>=0){
			try {
				mapper.insert_sf_alarm(order800m);
				sqlSession.commit();
				sqlSession.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static int sureAlarm(List<String> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int rs=0;
		
		try {
			rs=mapper.sureAlarm(list);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static int updateCkeckTag(int id,int tag) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("tag", tag);
		map.put("id",id);
		int rs=0;
		
		try {
			rs=mapper.updateCkeckTag(map);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static boolean e4(String fsuId) {
		boolean tag=false;
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("fsuId", fsuId);
		String table="xhgmnet_emh_sensor_history";
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int month = cal.get(Calendar.MONTH)+1;
		table+=month<10?(0+String.valueOf(month)):month;
		map.put("table", table);	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		try {
			list=mapper.bs_ji_four_e4(map);
			if(list.size()==5){
				Double a=list.get(0)==null?0:Double.parseDouble(list.get(0).get("e4").toString());
				Double b=list.get(1)==null?0:Double.parseDouble(list.get(1).get("e4").toString());
				Double c=list.get(2)==null?0:Double.parseDouble(list.get(2).get("e4").toString());
				Double d=list.get(3)==null?0:Double.parseDouble(list.get(3).get("e4").toString());
				Double e=list.get(4)==null?0:Double.parseDouble(list.get(4).get("e4").toString());
				if(a==b && b==c && c==d && d==e){
					tag=true;
				}
				
			}
			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tag;
	}

}
