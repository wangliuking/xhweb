package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.DataThresholdBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.SelectMapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class SelectServices {
	
	public static ArrayList<Map<String,Object>> workcontact(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		SelectMapper mapper=sqlSession.getMapper(SelectMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.workcontact();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	
	public static int workcontact_add(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		SelectMapper mapper=sqlSession.getMapper(SelectMapper.class);
		int count=0;
		try {
			count=mapper.workcontact_add(map);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	public static int workcontact_del(String name){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		SelectMapper mapper=sqlSession.getMapper(SelectMapper.class);
		int count=0;
		try {
			count=mapper.workcontact_del(name);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	public static  DataThresholdBean ThresholdMap(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		SelectMapper mapper=sqlSession.getMapper(SelectMapper.class);
		DataThresholdBean bean=new DataThresholdBean();
		try {
			bean=mapper.ThresholdMap();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  bean;
	}
	public static int updateThreshold(DataThresholdBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		SelectMapper mapper=sqlSession.getMapper(SelectMapper.class);
		int count=0;
		try {
			count=mapper.updateThreshold(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}

}
