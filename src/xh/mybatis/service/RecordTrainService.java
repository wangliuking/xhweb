package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.RecordTrainBean;
import xh.mybatis.mapper.OperationsCheckMapper;
import xh.mybatis.mapper.RecordTrainMapper;
import xh.mybatis.tools.MoreDbTools;

public class RecordTrainService {

	public static List<RecordTrainBean> data_all(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RecordTrainMapper mapper=sqlSession.getMapper(RecordTrainMapper.class);
		List<RecordTrainBean> list=new ArrayList<RecordTrainBean>();
		try{
			list=mapper.data_all(map);
			
			int i=0;
			for (RecordTrainBean recordTrainBean : list) {
				List<Map<String, Object>> m=mapper.searchFile(recordTrainBean.getId());
				recordTrainBean.setFiles(m);
				list.set(i, recordTrainBean);
				i++;
				
			}
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int data_all_count(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RecordTrainMapper mapper=sqlSession.getMapper(RecordTrainMapper.class);
		int count=0;
		try {
			count = mapper.data_all_count(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int add(RecordTrainBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordTrainMapper mapper=sqlSession.getMapper(RecordTrainMapper.class);
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
	public static int update(RecordTrainBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordTrainMapper mapper=sqlSession.getMapper(RecordTrainMapper.class);
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
		RecordTrainMapper mapper=sqlSession.getMapper(RecordTrainMapper.class);
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
	public static int addFile(List<Map<String,Object>> list) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RecordTrainMapper mapper = session
				.getMapper(RecordTrainMapper.class);
		int count=0;
		try {
			count = mapper.addFile(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
