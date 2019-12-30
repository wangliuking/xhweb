package xh.mybatis.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AlarmList;
import xh.mybatis.bean.HistoryList;
import xh.mybatis.mapper.GosuncnMapper;
import xh.mybatis.mapper.GpsMapper;
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
	 */
	public static int deleteBySerialNo(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			result=mapper.deleteBySerialNo(map);
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
	 */
	public static List<Map<String, Object>> selectBySerialNo(Map<String,String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, Object>> result=null;
		try {
			result=mapper.selectBySerialNo(map);
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
	public static List<Map<String, String>> selectByDevice(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectByDevice(map);
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
	public static List<Map<String, String>> selectByAlarmLevel(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectByAlarmLevel(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据基站id查询摄像头ip(非简阳基站)
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
	 * 根据基站id查询摄像头ip(简阳基站)
	 */
	public static List<Map<String,Object>> selectCameraIpByVpn(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String,Object>> list = null;
		try {
			list = mapper.selectCameraIpByVpn(map);
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
	
	/**
	 * 查询4期所有站的环控通断情况 
	 */
	public static List<Map<String, String>> selectFor4EMH(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectFor4EMH();			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 三期环控历史数据总数
	 *
	 * @return
	 * @throws Exception
	 */
	public static int emh3HistoryCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int count = 0;
		try {
			count = mapper.emh3HistoryCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 三期环控历史数据查询
	 */
	public static List<HashMap<String,String>> emh3History(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.emh3History(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 环控历史数据总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int emhHistoryCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int count = 0;
		try {
			count = mapper.emhHistoryCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 环控历史数据查询
	 */
	public static List<HashMap<String,String>> emhHistory(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.emhHistory(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询三期单个基站的电压情况用于显示曲线图
	 *
	 */
	public static List<HashMap<String,String>> emh3HistoryForBsId(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.emh3HistoryForBsId(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询单个基站本月电压情况用于显示曲线图
	 *
	 */
	public static List<HashMap<String,String>> emhHistoryForBsId(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.emhHistoryForBsId(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加最新NVR通道信息（先truncate再添加）
	 */
	public static int insertNVRChannels(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		int result=0;
		try {
			mapper.truncateNVRChannels();
			result=mapper.insertNVRChannels(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询所有NVR通道信息
	 */
	public static List<Map<String, String>> selectNVRChannels(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectNVRChannels();			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询四期所有FSU的bsId和IP
	 */
	public static List<Map<String, String>> selectNVRStatus(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String, String>> result=null;
		try {
			result=mapper.selectNVRStatus();			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 4期告警导出到excel
	 */
	public static List<AlarmList> selectEMHAlarmForExcel(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<AlarmList> list=null;
		try {
			list=mapper.selectEMHAlarmForExcel(map);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 4期历史数据导出到excel
	 */
	public static List<HistoryList> emhHistoryForExcel(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<HistoryList> list=null;
		try {
			list=mapper.emhHistoryForExcel(map);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询环控状态
	 */
	public static List<Map<String,Object>> getEMHStatus(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		List<Map<String,Object>> list = null;
		try {
			list = mapper.getEMHStatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询IP
	 */
	public static String searchFSUIP(String bsId){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		GosuncnMapper mapper = sqlSession.getMapper(GosuncnMapper.class);
		String str = "";
		try {
			str = mapper.searchFSUIP(bsId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
}
