package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.ChartReportDispatch;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.mapper.EastComMapper;
import xh.mybatis.mapper.ReportDayMapper;
import xh.mybatis.tools.MoreDbTools;

public class ReportDayService {
	protected  static  Log log=LogFactory.getLog(ReportDayService.class);
	
	
	public static List<Map<String,Object>> chart_server(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ReportDayMapper mapper=session.getMapper(ReportDayMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();				
		try {
			list=mapper.chart_server();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<ChartReportDispatch> chart_dispatch(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ReportDayMapper mapper=session.getMapper(ReportDayMapper.class);
		List<ChartReportDispatch> list=new ArrayList<ChartReportDispatch>();
		try {
			list=mapper.chart_dispatch();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static EastMscDayBean chart_msc_call(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		EastComMapper mapper=session.getMapper(EastComMapper.class);
		EastMscDayBean list=new  EastMscDayBean();
		try {
			list=mapper.chart_msc_call(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static Map<String,Object> chart_alarm_now(){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ReportDayMapper mapper=session.getMapper(ReportDayMapper.class);
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 Map<String,Object> map=new HashMap<String, Object>();
		try {
			list=mapper.chart_alarm_now();
			for(int i=0,j=list.size();i<j;i++){
				 Map<String,Object> map2=list.get(i);
				 if(map2.get("severity").equals(1)){
					 map.put("a_1", map2.get("num").toString());
				 }else if(map2.get("severity").equals(2)){
					 map.put("a_2", map2.get("num").toString());
				 }else if(map2.get("severity").equals(3)){
					 map.put("a_3", map2.get("num").toString());
				 }else if(map2.get("severity").equals(4)){
					 map.put("a_4", map2.get("num").toString());
				 }
			}
			
			if(map.get("a_1")==null){
				map.put("a_1", 0);
			}
			if(map.get("a_2")==null){
				map.put("a_2", 0);
			}
			if(map.get("a_3")==null){
				map.put("a_3", 0);
			}
			if(map.get("a_4")==null){
				map.put("a_4", 0);
			}
			
			
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	public static Map<String,Object> chart_alarm_his(Map<String,Object> prapmap){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ReportDayMapper mapper=session.getMapper(ReportDayMapper.class);
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 Map<String,Object> map=new HashMap<String, Object>();
		try {
			list=mapper.chart_alarm_his(prapmap);
			for(int i=0,j=list.size();i<j;i++){
				 Map<String,Object> map2=list.get(i);
				 if(map2.get("severity").equals(1)){
					 map.put("a_1", map2.get("num").toString());
				 }else if(map2.get("severity").equals(2)){
					 map.put("a_2", map2.get("num").toString());
				 }else if(map2.get("severity").equals(3)){
					 map.put("a_3", map2.get("num").toString());
				 }else if(map2.get("severity").equals(4)){
					 map.put("a_4", map2.get("num").toString());
				 }
			}
			
			if(map.get("a_1")==null){
				map.put("a_1", 0);
			}
			if(map.get("a_2")==null){
				map.put("a_2", 0);
			}
			if(map.get("a_3")==null){
				map.put("a_3", 0);
			}
			if(map.get("a_4")==null){
				map.put("a_4", 0);
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
}
