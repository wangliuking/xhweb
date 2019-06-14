package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.CoverageBsBean;
import xh.mybatis.mapper.CoverageBsMapper;
import xh.mybatis.tools.MoreDbTools;

public class CoverageBsServices {
	
	public static ArrayList<CoverageBsBean> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		CoverageBsMapper mapper=sqlSession.getMapper(CoverageBsMapper.class);
		ArrayList<CoverageBsBean> list=new ArrayList<CoverageBsBean>();
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
		CoverageBsMapper mapper=sqlSession.getMapper(CoverageBsMapper.class);
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
	public static int add(CoverageBsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		CoverageBsMapper mapper=sqlSession.getMapper(CoverageBsMapper.class);
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
	public static int update(CoverageBsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		CoverageBsMapper mapper=sqlSession.getMapper(CoverageBsMapper.class);
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
		CoverageBsMapper mapper=sqlSession.getMapper(CoverageBsMapper.class);
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
