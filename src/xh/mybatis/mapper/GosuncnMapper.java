package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AlarmList;
import xh.mybatis.bean.HistoryList;

public interface GosuncnMapper {
	/**
	 * 添加注册信息
	 */
	public int insertLogin(Map<String,String> map)throws Exception;
	
	/**
	 * 根据fsuId更新注册信息
	 */
	public int updateLogin(Map<String,String> map)throws Exception;
	
	/**
	 * 查询注册信息用于维持心跳
	 */
	public List<Map<String,String>> selectForGetLogin();
	
	/**
	 * 根据流水号删除对应告警
	 */
	public int deleteBySerialNo(Map<String,String> map);
	
	/**
	 * 增加告警前查询是否有相同流水号的告警
	 */
	public List<Map<String,Object>> selectBySerialNo(Map<String,String> map);
	
	/**
	 * 添加告警信息
	 */
	public int insertAlarm(List<Map<String,String>> list);
	
	/**
	 * 根据FSUID删除对应配置信息
	 */
	public int deleteConfigByFSUID(String FSUID);
	
	/**
	 * 添加FSU配置信息
	 */
	public int insertConfig(List<Map<String,String>> list)throws Exception;
	
	/**
	 * 根据FSUID查询对应配置信息
	 */
	public List<String> selectConfigByFSUID(String FSUID);
	
	/**
	 * 添加监控点数据
	 */
	public int insertData(List<Map<String, String>> list)throws Exception;
	
	/**
	 * 添加监控点历史数据
	 */
	public int insertHData(List<Map<String, String>> list)throws Exception;
	
	/**
	 * 根据FSUID查询是否存在数据
	 */
	public int selectByFSUID(String FSUID)throws Exception;
	
	/**
	 * 根据FSUID删除对应数据
	 */
	public int deleteByFSUID(String FSUID)throws Exception;
	
	/*
	 * 环控告警页面部分
	 */
	/**
	 * 告警查询
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectEMHAlarm(Map<String,Object> map)throws Exception;
	
	/**
	 * 告警总数
	 * @return
	 * @throws Exception
	 */
	public int countEMHAlarm(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询不同传感器的告警 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectByDevice()throws Exception;
	
	/**
	 * 查询不同级别的告警
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectByAlarmLevel()throws Exception;
	
	/**
	 * 根据基站id查询摄像头IP
	 */
	public List<Map<String,Object>> selectCameraIpByBsId(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据fsuId修改无限摄像头IP
	 */
	public int updataCameraIpByFSUID(Map<String,String> map)throws Exception;
	
	/**
	 * 查询4期所有站的环控通断情况
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectFor4EMH()throws Exception;
	
	/**
	 * 环控历史数据
	 */
	public List<HashMap<String,String>> emhHistory(Map<String,Object> map)throws Exception;
	
	/**
	 * 环控历史数据总数
	 */
	public int emhHistoryCount(Map<String,Object> map)throws Exception;

	/**
	 * 查询单个基站本月电压情况用于显示曲线图
	 */
	public List<HashMap<String,String>> emhHistoryForBsId(Map<String,Object> map)throws Exception;
	
	/**
	 * 删除NVR所有通道信息
	 */
	public int truncateNVRChannels();
	
	/**
	 * 添加最新NVR通道信息
	 */
	public int insertNVRChannels(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询所有NVR通道信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectNVRChannels()throws Exception;
	
	/**
	 * 查询四期所有FSU的bsId和IP
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> selectNVRStatus()throws Exception;
	
	/**
	 * 告警导出到excel
	 * @return
	 * @throws Exception
	 */
	public List<AlarmList> selectEMHAlarmForExcel(Map<String,Object> map)throws Exception;
	
	/**
	 * 历史数据导出到excel
	 * @return
	 * @throws Exception
	 */
	public List<HistoryList> emhHistoryForExcel(Map<String,Object> map)throws Exception;
	
}
