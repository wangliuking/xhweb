package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import xh.mybatis.bean.EmhAlarmBean;
import xh.mybatis.bean.EmhBean;
import xh.mybatis.bean.SensorBean;
import xh.org.socket.AgentDataStruct;
import xh.org.socket.RtEventStruct;

public interface EmhMapper {
	/**
	 * 查询基站环境监控实时状态
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> oneBsEmh(String code)throws Exception;
	
	/**
	 * 基站环境监控实时告警列表
	 * @return
	 * @throws Exception
	 */
	public List<HashedMap<String, Object>> bsEmhNowStatus(Map<String,Object> map)throws Exception;
	/**
	 * 基站环境监控告警列表条目
	 * @return
	 * @throws Exception
	 */
	public int bsEmhNowStatusCount()throws Exception;
	/**
	 * 基站环境监控交流电告警列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>bsEmhJiAlarmList()throws Exception;
	/**
	 * 基站环境监控交流电声音告警数目
	 * @return
	 * @throws Exception
	 */
	public int bsEmhJiAlarmVoiceCount()throws Exception;
	/**
	 * 更新eps交流电声音告警提示
	 * @return
	 * @throws Exception
	 */
	public int update_emh_eps_voice_status()throws Exception;
	/**
	 * 基站环境监控历史告警列表
	 * @return
	 * @throws Exception
	 */
	public List<HashedMap<String, Object>> bsEmhOldStatus(Map<String,Object> map)throws Exception;
	/**
	 * 基站环境监控历史告警列表条目
	 * @return
	 * @throws Exception
	 */
	public int bsEmhOldStatusCount()throws Exception;
	
	/**
	 * 新增测点
	 * @return
	 * @throws Exception
	 */
	public int agent(AgentDataStruct agent)throws Exception;
	/**
	 * 更新测点
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	public int updateAgent(AgentDataStruct agent)throws Exception;
	/**
	 * 测点是否存在
	 * @return
	 * @throws Exception
	 */
	public int isagent(String uuid)throws Exception;
	/**
	 * 新增测点设备
	 * @return
	 * @throws Exception
	 */
	public int agentDevice(SensorBean bean)throws Exception;
	/**
	 * 测点设备是否存在
	 * @return
	 * @throws Exception
	 */
	public int isagentDevice(Map<String,Object> map)throws Exception;
	/**
	 * 更新测点
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAgentDevice(SensorBean bean)throws Exception;
	/**
	 * 更新测点状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAgentDeviceState(SensorBean bean)throws Exception;
	/**
	 * 设备告警
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int deviceAlarm(RtEventStruct bean)throws Exception;
	/**
	 * 更新测点设备告警状态
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateDeviceStateAlarm(SensorBean bean)throws Exception;
	/**
	 * 更新测点设备当前值
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateDeviceValue(SensorBean bean)throws Exception;

}
