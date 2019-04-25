package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.MeetMapper;
import xh.mybatis.mapper.MeetMapper;
import xh.mybatis.tools.MoreDbTools;

public class MeetServices {
	public static ArrayList<MeetBean> meetlist(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MeetMapper mapper=sqlSession.getMapper(MeetMapper.class);
		ArrayList<MeetBean> list=new ArrayList<MeetBean>();
		try {
			list=mapper.meetlist(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	public static int meetcount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MeetMapper mapper=sqlSession.getMapper(MeetMapper.class);
		int count=0;
		try {
			count=mapper.meetcount(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	public static int add(MeetBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MeetMapper mapper=sqlSession.getMapper(MeetMapper.class);
		int result=0;
		try {
			result=mapper.add(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int update(MeetBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MeetMapper mapper=sqlSession.getMapper(MeetMapper.class);
		int result=0;
		try {
			result=mapper.update(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int  del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MeetMapper mapper=sqlSession.getMapper(MeetMapper.class);
		int result=0;
		try {
			result=mapper.del(list);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

}
