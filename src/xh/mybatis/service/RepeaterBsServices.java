package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RepeaterBsBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.RepeaterBsMapper;
import xh.mybatis.mapper.SelectMapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class RepeaterBsServices {
	
	public static ArrayList<RepeaterBsBean> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RepeaterBsMapper mapper=sqlSession.getMapper(RepeaterBsMapper.class);
		ArrayList<RepeaterBsBean> list=new ArrayList<RepeaterBsBean>();
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
		RepeaterBsMapper mapper=sqlSession.getMapper(RepeaterBsMapper.class);
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
	public static int add(RepeaterBsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepeaterBsMapper mapper=sqlSession.getMapper(RepeaterBsMapper.class);
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
	public static int update(RepeaterBsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepeaterBsMapper mapper=sqlSession.getMapper(RepeaterBsMapper.class);
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
		RepeaterBsMapper mapper=sqlSession.getMapper(RepeaterBsMapper.class);
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
