package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.Top10Mapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class Top10Services {
	
	public static ArrayList<Map<String,Object>> offline_bs(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		Top10Mapper mapper=sqlSession.getMapper(Top10Mapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.offline_bs(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	public static ArrayList<Map<String,Object>> elec(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		Top10Mapper mapper=sqlSession.getMapper(Top10Mapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.elec(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}

}
