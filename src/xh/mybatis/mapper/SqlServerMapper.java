package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.EmhThreeBean;
import xh.mybatis.bean.ThreeEmhAlarmBean;

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
	public List<Map<String,Object>> EmhAlarmList(Map<String,Object> map) throws Exception;
	
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
	
	/**
	 * 查询三期基站环控通断状态 wlk
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectConnectStatusForEMH3() throws Exception;
	
	/** 获取当天的告警*/
	public List<ThreeEmhAlarmBean> perDayAlarm() throws Exception;
	
	public int alarmExists(ThreeEmhAlarmBean bean) throws Exception;
	public int updateEmhAlarm(ThreeEmhAlarmBean bean) throws Exception;
	public int insertEmhAlarm(ThreeEmhAlarmBean bean) throws Exception;
	
	public List<String> all_table() throws Exception;
	public List<EmhThreeBean> tb_dev_info(Map<String,Object> map) throws Exception;
	public int insertEmh(EmhThreeBean bean) throws Exception;
	
	public EmhThreeBean emh_three_one(EmhThreeBean bean) throws Exception;
}
