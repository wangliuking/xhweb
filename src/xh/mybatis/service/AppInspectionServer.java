package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.InspectionMbsBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.mapper.AppInspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class AppInspectionServer {
	
	/*<!--查询800M移动基站巡检表-->*/
	public static List<InspectionMbsBean> mbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionMbsBean> list=new ArrayList<InspectionMbsBean>();
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
	public static List<InspectionSbsBean> sbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionSbsBean> list=new ArrayList<InspectionSbsBean>();
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
	
	/*<!--交换中心巡检表-->*/
	public static List<InspectionMscBean> mscinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionMscBean> list=new ArrayList<InspectionMscBean>();
		try {
			list = mapper.mscinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--交换中心巡检表总数-->*/
	public static int mscCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mscCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int msc_add(InspectionMscBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.msc_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int msc_edit(InspectionMscBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.msc_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int mbs_add(InspectionMbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbs_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int mbs_edit(InspectionMbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbs_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int sbs_add(InspectionSbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbs_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int sbs_edit(InspectionSbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbs_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
}
