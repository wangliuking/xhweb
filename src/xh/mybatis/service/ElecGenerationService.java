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
		sqlSession.close();
		return list;
	}
	public static List<Map<String,Object>> bs_offline_record(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		List<Map<String,Object>> list=mapper.bs_offline_record(map);
		sqlSession.close();
		return list;
	}
	public static int count(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int count=mapper.count(map);
		 sqlSession.close();
		return count;
	}
	public static int insert(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int rs=mapper.insert(map);
	    sqlSession.commit();
	    sqlSession.close();
	    
		return rs;
	}
	public static int update_fault(int id) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int rs=mapper.update_fault(id);
	    sqlSession.commit();
	    sqlSession.close();
	    
		return rs;
	}
	public static int check(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int rs=mapper.check(map);
	    sqlSession.commit();
	    sqlSession.close();
	    
		return rs;
	}
	public static int checkOne(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int rs=mapper.checkOne(map);
	    sqlSession.commit();
	    sqlSession.close();
	    
		return rs;
	}
	public static int checkTwo(Map<String,Object> map) throws Exception{
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ElecGenerationMapper mapper=sqlSession.getMapper(ElecGenerationMapper.class);
		int rs=mapper.checkTwo(map);
	    sqlSession.commit();
	    sqlSession.close();
	    
		return rs;
	}

	

}
