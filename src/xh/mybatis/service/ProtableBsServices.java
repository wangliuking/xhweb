package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.ProtableBean;
import xh.mybatis.mapper.ProtableBsMapper;
import xh.mybatis.tools.MoreDbTools;

public class ProtableBsServices {
	
	public static ArrayList<ProtableBean> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		ArrayList<ProtableBean> list=new ArrayList<ProtableBean>();
		try {
			list=mapper.list(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		int rs=0;
		try {
			rs=mapper.count(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}
	public static int exists(String name,String id){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("bsName", name);
		map.put("id", id);
		int rs=0;
		try {
			rs=mapper.exists(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}
	public static int add(ProtableBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		int rs=0;
		try {
			rs=mapper.add(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}
	public static int update(ProtableBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		int rs=0;
		try {
			rs=mapper.update(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}
	public static int del(String bsId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ProtableBsMapper mapper=sqlSession.getMapper(ProtableBsMapper.class);
		int rs=0;
		try {
			rs=mapper.del(bsId);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}


}
