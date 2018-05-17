package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EmhAlarmBean;
import xh.mybatis.bean.EmhBean;
import xh.mybatis.bean.SensorBean;
import xh.mybatis.mapper.EmhMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;
import xh.org.socket.AgentDataStruct;
import xh.org.socket.RtEventStruct;

public class EmhService {
	/**
	 * 查询基站环境监控实时状态
	 * @param code
	 * @return
	 */
	public static List<EmhBean> oneBsEmh(String code) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.emh);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<EmhBean> list = new ArrayList<EmhBean>();
		try {
			list = mapper.oneBsEmh(code);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控实时告警列表
	 * @param map
	 * @return
	 */
	public static List<HashedMap<String, Object>> bsEmhNowStatus(Map<String,Object> map) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<HashedMap<String, Object>> list = new ArrayList<HashedMap<String, Object>>();
		try {
			list = mapper.bsEmhNowStatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控交流电告警列表
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> bsEmhJiAlarmList() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.bsEmhJiAlarmList();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控交流电声音告警数目
	 * @param map
	 * @return
	 */
	public static int bsEmhJiAlarmVoiceCount() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count = mapper.bsEmhJiAlarmVoiceCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 更新eps交流电声音告警提示
	 * @param map
	 * @return
	 */
	public static int update_emh_eps_voice_status() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count = mapper.update_emh_eps_voice_status();
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 基站环境监控实时告警列表条目
	 * @param map
	 * @return
	 */
	public static int bsEmhNowStatusCount() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.bsEmhNowStatusCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 基站环境监控历史告警列表
	 * @param map
	 * @return
	 */
	public static List<HashedMap<String, Object>> bsEmhOldStatus(Map<String,Object> map) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<HashedMap<String, Object>> list = new ArrayList<HashedMap<String, Object>>();
		try {
			list = mapper.bsEmhOldStatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控历史告警列表条目
	 * @param map
	 * @return
	 */
	public static int bsEmhOldStatusCount() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.bsEmhOldStatusCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 测点是否存在
	 * @param uuid
	 * @return
	 */
	public static boolean isagent(String uuid) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.isagent(uuid);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count>0){return true;}else{return false;}
	}
	/**
	 * 新增测点
	 * @return
	 */
	public static int agent(AgentDataStruct agent) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			if(!isagent(agent.getUuid())){
				result= mapper.agent(agent);
				sqlSession.commit();
				sqlSession.close();
			}else{
				result=mapper.updateAgent(agent);
				sqlSession.commit();
				sqlSession.close();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 测点设备是否存在
	 * @param uuid
	 * @return
	 */
	public static boolean isagentDevice(Map<String,Object> map) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.isagentDevice(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count>0){return true;}else{return false;}
	}
	/**
	 * 新增测点设备
	 * @return
	 */
	public static int agentDevice(SensorBean bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("bsId", bean.getBsId());map.put("deviceId", bean.getDeviceId());
		try {
			if(!isagentDevice(map)){
				result= mapper.agentDevice(bean);
				sqlSession.commit();
				sqlSession.close();
			}else{
				result= mapper.updateAgentDevice(bean);
				sqlSession.commit();
				sqlSession.close();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新测点
	 * @param bean
	 * @return
	 *//*
	public static int updateAgentDevice(SensorBean bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			result= mapper.updateAgentDevice(bean);
			sqlSession.commit();
			sqlSession.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}*/
	/**
	 * 更新测点状态
	 * @param bean
	 * @return
	 */
	public static int updateAgentDeviceState(SensorBean bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			result= mapper.updateAgentDeviceState(bean);
			sqlSession.commit();
			sqlSession.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 设备告警
	 * @param bean
	 * @return
	 */
	public static int deviceAlarm(RtEventStruct bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			result= mapper.deviceAlarm(bean);
			sqlSession.commit();
			sqlSession.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新测点设备告警状态
	 * @param bean
	 * @return
	 */
	public static int updateDeviceStateAlarm(SensorBean bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			result= mapper.updateDeviceStateAlarm(bean);
			sqlSession.commit();
			sqlSession.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新测点设备当前值
	 * @param bean
	 * @return
	 */
	public static int updateDeviceValue(SensorBean bean) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int result=0;
		try {
			result= mapper.updateDeviceValue(bean);
			sqlSession.commit();
			sqlSession.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
