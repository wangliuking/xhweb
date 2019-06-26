package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.RecordNetBean;
import xh.mybatis.mapper.RecordNetMapper;
import xh.mybatis.tools.MoreDbTools;

public class RecordNetService {

	public static List<RecordNetBean> data_all(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RecordNetMapper mapper=sqlSession.getMapper(RecordNetMapper.class);
		List<RecordNetBean> list=new ArrayList<RecordNetBean>();
		try{
			list=mapper.data_all(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int data_all_count(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RecordNetMapper mapper=sqlSession.getMapper(RecordNetMapper.class);
		int count=0;
		try {
			count = mapper.data_all_count(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int add(RecordNetBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordNetMapper mapper=sqlSession.getMapper(RecordNetMapper.class);
		int count=0;
		try {
			count = mapper.add(bean);
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int update(RecordNetBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordNetMapper mapper=sqlSession.getMapper(RecordNetMapper.class);
		int count=0;
		try {
			count = mapper.update(bean);
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordNetMapper mapper=sqlSession.getMapper(RecordNetMapper.class);
		int count=0;
		try {
			count = mapper.del(list);
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
