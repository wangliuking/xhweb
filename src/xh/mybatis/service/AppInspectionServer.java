package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.AppInspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class AppInspectionServer {
	
	/*<!--查询800M移动基站巡检表-->*/
	public static List<Map<String, Object>> mbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.mbsinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--查询800M移动基站巡检表总数-->*/
	public static int mbsinfoCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbsinfoCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/*<!--自建基站巡检表-->*/
	public static List<Map<String, Object>> sbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.sbsinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--自建基站巡检表总数-->*/
	public static int sbsinfoCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbsinfoCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	/*<!--网管巡检表-->*/
	public static List<Map<String, Object>> netinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.netinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--网管巡检表总数-->*/
	public static int netinfoCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.netinfoCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/*<!--调度台巡检表-->*/
	public static List<Map<String, Object>> dispatchinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.dispatchinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--调度台巡检表总数-->*/
	public static int dispatchinfoCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.dispatchinfoCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
}
