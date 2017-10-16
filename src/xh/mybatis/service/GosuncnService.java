package xh.mybatis.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.GosuncnMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class GosuncnService {
	
	/**
	 * 添加注册FSU
	 * @param map
	 * @return
	 */
	public static int insertLogin(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertLogin(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加告警信息
	 * @param map
	 * @return
	 */
	public static int insertAlarm(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertAlarm(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据FSUID删除对应配置信息
	 */
	public static int deleteConfigByFSUID(String FSUID){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.deleteByFSUID(FSUID);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加FSU的配置信息
	 * @param map
	 * @return
	 */
	public static int insertConfig(List<Map<String,String>> list){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertConfig(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加监控点数据
	 * @param list
	 * @return
	 */
	public static int insertData(List<Map<String, String>> list) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertData(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据FSUID查询是否存在数据
	 */
	public static int selectByFSUID(String FSUID) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.selectByFSUID(FSUID);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据FSUID删除对应数据
	 * @param list
	 * @return
	 */
	public static int deleteByFSUID(String FSUID) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.deleteByFSUID(FSUID);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
