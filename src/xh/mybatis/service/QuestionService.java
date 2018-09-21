package xh.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.QuestionMapper;
import xh.mybatis.tools.MoreDbTools;

public class QuestionService {

	public static int count(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		QuestionMapper mapper=session.getMapper(QuestionMapper.class);
		int count=mapper.count(map);
		session.close();
		return count;
	}

	public static int add(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		QuestionMapper mapper=session.getMapper(QuestionMapper.class);
		int rst=mapper.add(map);
		session.commit();
		session.close();
		return rst;
	}

	public static List<Map<String, Object>> list(Map<String, Object> map)
			throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		QuestionMapper mapper=session.getMapper(QuestionMapper.class);
		List<Map<String, Object>> list=mapper.list(map);
		return list;
	}
	
	

}
