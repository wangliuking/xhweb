package xh.mybatis.service;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscCallBean;
import xh.mybatis.bean.EastMscCallDetailBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.mapper.EastComMapper;
import xh.mybatis.tools.MoreDbTools;

public class EastComService {
	protected  static  Log log=LogFactory.getLog(EastComService.class);
	public static List<Map<String,Object>> queueTop5(String time){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		SqlSession session2=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		EastComMapper mapper2=session2.getMapper(EastComMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> bsNames=new ArrayList<Map<String,Object>>();
		List<String> bsIds=new ArrayList<String>();
		
		try {
			list=mapper.queueTop5(time);
			if(list.size()<1){
				return list;
			}		
			for (Map<String,Object> map : list){
				int id=0;
				if(Integer.parseInt(map.get("bsId").toString())>=1200){
					id=Integer.parseInt(map.get("bsId").toString())%1000;
				}else if(Integer.parseInt(map.get("bsId").toString())>1000 && Integer.parseInt(map.get("bsId").toString())<1100){
					id=Integer.parseInt(map.get("bsId").toString());
				}else{
					id=Integer.parseInt(map.get("bsId").toString());
				}
				if(id>0){
					bsIds.add(String.valueOf(id));
				}
				
			}			
			bsNames=mapper2.queueTopBsName(bsIds);
			
			for (Map<String,Object> map2 : bsNames) {
				int bsId=Integer.parseInt(map2.get("bsId").toString());
				String name=map2.get("name").toString();
                for(int j=0;j<list.size();j++){
                	Map<String,Object> map3=new HashMap<String, Object>();
                	map3=list.get(j);
                	if(map3.get("bsId")!=null){
                	if(Integer.parseInt(map3.get("bsId").toString())==bsId){
                		map3.put("name", bsId+"-"+name);
                		map3.remove("bsId");
                		list.set(j, map3);
                	}
                	}
				}
			}
			
			 for(int j=0;j<list.size();j++){
             	Map<String,Object> map3=new HashMap<String, Object>();
             	map3=list.get(j);
             	if(map3.get("bsId")!=null){
             		map3.put("name", map3.get("bsId")+"-应急基站");
             		map3.remove("bsId");
             		list.set(j, map3);
             	}
				}
			
			session.close();
			session2.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void get_bs_call_data(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		
		String now_date_zero_time=FunUtil.nowDateNoTime();
		
		String a[]=now_date_zero_time.split(" ");
		String time="";
		Map<String, Object> map=new HashMap<String, Object>();
		String h="00",m="00",s="00";
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			/*51*/
			for(int dayNum=1;dayNum<=1;dayNum++){
				int day=dayNum;	
				for(int i=0;i<=23;i++){	
					
					if(i<10){
						h="0"+i;
					}else{
						h=String.valueOf(i);
					}
						
					for(int j=0;j<=55;j+=5){
						
						if(j<10){
							m="0"+j;
						}else{
							m=String.valueOf(j);
						}					
						time=a[0]+" "+h+":"+m+":"+s;
						
						map.put("time", time);
						map.put("day", day);
						list=mapper.get_bs_call_data(map);
						if(list.size()>0){
							write_bs_call_data(list);
						}
						
						map.clear();
						list.clear();
						
					}			
					
				}
				
				System.out.println("call---"+dayNum);
				
			}
			
			
			
		
			
				
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void get_bs_now_call_data(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:00");	   
		   
		   String now=df.format(new Date());
		   int m=Integer.parseInt(now.split(" ")[1].split(":")[1])%5;	   
		   Calendar c=Calendar.getInstance();	   
		   int x=m+20;	   
		   //获取20分钟以前的时间
		   c.add(Calendar.MINUTE, -x);
		   Date d=c.getTime();
		   String date_time=df.format(d);
		try {
			List<EastBsCallDataBean> list=mapper.get_bs_now_call_data(date_time);
			if(list!=null){
				write_bs_now_call_data(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void write_bs_call_data(List<EastBsCallDataBean> list){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		try {
			mapper.write_bs_call_data(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_bs_now_call_data(List<EastBsCallDataBean> list){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		try {
			mapper.write_bs_now_call_data(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void get_msc_call_data(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		
		String now_date_zero_time=FunUtil.nowDateNoTime();
		
		String a[]=now_date_zero_time.split(" ");
		String time=a[0];
		Map<String, Object> map=new HashMap<String, Object>();
		
		try {
			
			for(int dayNum=1;dayNum<=1;dayNum++){
				int day=dayNum;
				
				map.put("time", time);
				map.put("day", day);
				List<EastMscCallBean> list=mapper.get_msc_call_data(map);
				if(list.size()>0){
					write_msc_call_data(list);	
				}
				
			}
					
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_msc_call_data(List<EastMscCallBean> list){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		try {
			mapper.write_msc_call_data(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void get_vpn_call_data(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		
		String now_date_zero_time=FunUtil.nowDateNoTime();
		
		String a[]=now_date_zero_time.split(" ");
		String time=a[0];
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			for(int dayNum=1;dayNum<=1;dayNum++){
				int day=dayNum;
				
				map.put("time", time);
				map.put("day", day);
				List<EastVpnCallBean> list=mapper.get_vpn_call_data(map);
				if(list.size()>0){
					write_vpn_call_data(list);	
				}
				
			}
					
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_vpn_call_data(List<EastVpnCallBean> list){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		try {
			mapper.write_vpn_call_data(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void get_msc_call_detail_data(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.eastcom);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		
		String now_date_zero_time=FunUtil.nowDateNoTime();
		
		String a[]=now_date_zero_time.split(" ");
		String time=a[0];
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			for(int dayNum=1;dayNum<=1;dayNum++){
				int day=dayNum;
				
				map.put("time", time);
				map.put("day", day);
				List<EastMscCallDetailBean> list=mapper.get_msc_call_detail_data(map);
				if(list.size()>0){
					write_msc_call_detail_data(list);	
				}
				
			}
				
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_msc_call_detail_data(List<EastMscCallDetailBean> list){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		try {
			mapper.write_msc_call_detail_data(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<EastBsCallDataBean> chart_bs_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastVpnCallBean> chart_vpn_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastVpnCallBean> list=new ArrayList<EastVpnCallBean>();
		try {

			list=mapper.chart_vpn_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> chart_bs_level_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_level_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> chart_bs_area_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_area_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> chart_bs_zone_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_zone_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> chart_bs_zone_top10_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_zone_top10_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean>chart_bs_call_top10(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_call_top10(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean>chart_bs_userreg_top10(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_userreg_top10(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean>chart_bs_queue_top10(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {

			list=mapper.chart_bs_queue_top10(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastVpnCallBean>chart_vpn_call_top10(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		List<EastVpnCallBean> list=new ArrayList<EastVpnCallBean>();
		try {

			list=mapper.chart_vpn_call_top10(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static EastMscDayBean chart_month_msc(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		EastMscDayBean list=new EastMscDayBean();
		try {

			list=mapper.chart_month_msc(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
}
