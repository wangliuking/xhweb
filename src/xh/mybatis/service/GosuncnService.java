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
	 * 根据fsuId更新注册信息
	 */
	public static int updateLogin(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.updateLogin(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询注册信息用于维持心跳
	 */
	public static List<Map<String,String>> selectForGetLogin(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String,String>> list = mapper.selectForGetLogin();
		sqlSession.close();
		return list;
	}
	
	/**
	 * 根据流水号删除对应告警
	 * @param map
	 * @return
	 */
	public static int deleteBySerialNo(String serialNo){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.deleteBySerialNo(serialNo);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 增加告警前查询是否有相同流水号的告警
	 * @param map
	 * @return
	 */
	public static List<Map<String, String>> selectBySerialNo(String serialNo){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectBySerialNo(serialNo);
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
	public static int insertAlarm(List<Map<String,String>> list){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertAlarm(list);
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
			result=mapper.deleteConfigByFSUID(FSUID);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据FSUID查询对应配置信息
	 */
	public static List<String> selectConfigByFSUID(String FSUID){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<String> list = mapper.selectConfigByFSUID(FSUID);
		sqlSession.close();
		return list;
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
	 * 添加监控点历史数据
	 * @param list
	 * @return
	 */
	public static int insertHData(List<Map<String, String>> list) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.insertHData(list);
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
	
	/*
	 * 环控告警页面部分
	 */
	/**
	 * 告警查询
	 */
	public static List<Map<String,String>> selectEMHAlarm(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String,String>> list=null;
		try {
			list=mapper.selectEMHAlarm(map);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 告警条数
	 */
	public static int countEMHAlarm(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.countEMHAlarm(map);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询不同传感器的告警 
	 */
	public static List<Map<String, String>> selectByDevice(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectByDevice();			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询不同级别的告警 
	 */
	public static List<Map<String, String>> selectByAlarmLevel(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectByAlarmLevel();			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据基站id查询摄像头ip
	 */
	public static List<Map<String,Object>> selectCameraIpByBsId(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String,Object>> list = null;
		try {
			list = mapper.selectCameraIpByBsId(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据fsuId修改简阳无线IP
	 */
	public static void updataCameraIpByFSUID(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		try {
			mapper.updataCameraIpByFSUID(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
