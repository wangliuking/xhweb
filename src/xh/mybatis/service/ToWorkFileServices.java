package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.mapper.ToWordFileMapper;
import xh.mybatis.tools.MoreDbTools;

public class ToWorkFileServices {
	public static Map<String,Object> system_call(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		Map<String,Object> rsmap=new HashMap<String, Object>();
		try {
			rsmap=mapper.system_call(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsmap;
	}
	
	public static List<Map<String,Object>> system_call_year(String year){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.system_call_year(year);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String,Object>> system_gps_year(String year){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.system_gps_year(year);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<EastBsCallDataBean> system_call_level(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_level(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> system_call_year_level(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_year_level(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> system_call_area(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_area(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<EastBsCallDataBean> system_call_year_area(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_year_area(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<EastBsCallDataBean> system_call_zone_top10(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_zone_top10(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastBsCallDataBean> system_call_bs_top10(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_bs_top10(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<EastBsCallDataBean> system_call_queue_top10(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.system_call_queue_top10(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<EastVpnCallBean> system_call_vpn_top10(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastVpnCallBean> list=new ArrayList<EastVpnCallBean>();
		try {
			list=mapper.system_call_vpn_top10(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static  Map<String,Object> system_bs_call(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		 Map<String,Object> rs=new HashMap<String, Object>();
		try {
			rs=mapper.system_bs_call(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static List<EastBsCallDataBean> chart_bs_userreg_top10(String time){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		ToWordFileMapper mapper=sqlSession.getMapper(ToWordFileMapper.class);
		List<EastBsCallDataBean> list=new ArrayList<EastBsCallDataBean>();
		try {
			list=mapper.chart_bs_userreg_top10(time);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


}
