package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.WorkContactBean;
import xh.mybatis.mapper.WorkContactMapper;
import xh.mybatis.tools.MoreDbTools;

public class WorkContactService {
	
	public static List<WorkContactBean> list(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		List<WorkContactBean> list = new ArrayList<WorkContactBean>();
		try {
			list = mapper.list(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int list_count() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.list_count();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int add(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.add(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int sign(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.sign(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
