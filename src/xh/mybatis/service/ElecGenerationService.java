package xh.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.ElecGenerationMapper;
import xh.mybatis.tools.MoreDbTools;

public class ElecGenerationService {
	protected final static Log log=LogFactory.getLog(ElecGenerationService.class);

	public static List<Map<String,Object>> list(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		List<Map<String,Object>> list=mapper.list(map);
		return list;
	}
	public static int count(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		return mapper.count(map);
	}

	

}
