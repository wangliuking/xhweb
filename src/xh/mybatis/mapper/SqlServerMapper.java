package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface SqlServerMapper {
	
	/**
	 * 三期基站环控列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsmonitorList(Map<String,Object> map) throws Exception;
	
	/**
	 * 三期基站环控列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsmonitorAlarmList(Map<String,Object> map) throws Exception;
	/**
	 * 三期所有基站环控告警列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> EmhAlarmList(String time) throws Exception;
	
	/**
	 * 交流电断开报警
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsJiAlarm(String time) throws Exception;
	
	
	/**
	 * 环控告警统计 
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public int MapEmhAlarmCount(String time) throws Exception;
	
	/**
	 * 交流电断开报警数目
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public int bsJiAlarmCount(String time) throws Exception;
	
	/**
	 * 更新告警状态
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public int updateAlarmStatus(Map<String,Object> map) throws Exception;

}
