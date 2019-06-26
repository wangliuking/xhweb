package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.SubwayBean;
import xh.mybatis.mapper.SubwayBsMapper;
import xh.mybatis.tools.MoreDbTools;

public class SubwayBsServices {
	
	public static ArrayList<SubwayBean> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
		ArrayList<SubwayBean> list=new ArrayList<SubwayBean>();
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
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
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
	public static int exists(String name){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
		int rs=0;
		try {
			rs=mapper.exists(name);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  rs;
	}
	public static int add(SubwayBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
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
	public static int update(SubwayBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
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
		SubwayBsMapper mapper=sqlSession.getMapper(SubwayBsMapper.class);
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
