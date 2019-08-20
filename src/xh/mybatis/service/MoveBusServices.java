package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.MoveBusBean;
import xh.mybatis.mapper.MoveBusMapper;
import xh.mybatis.tools.MoreDbTools;

public class MoveBusServices {
	
	public static ArrayList<MoveBusBean> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
		ArrayList<MoveBusBean> list=new ArrayList<MoveBusBean>();
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
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
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
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("carNumber", name);
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
	public static int add(MoveBusBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
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
	public static int update(MoveBusBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
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
		MoveBusMapper mapper=sqlSession.getMapper(MoveBusMapper.class);
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
