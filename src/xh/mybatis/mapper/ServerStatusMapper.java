package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface ServerStatusMapper {
	
	/**
	 * 服务器运行状态
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> serverstatus() throws Exception;
	
	/* <!-- 更新交换中心告警标志 -->*/
	/**
	 * 更新交换中心告警标志
	 * @throws Exception
	 */
	public void updateAlarmStatus() throws Exception;
	
	/**
	 * 解除报警 
	 * @throws Exception
	 */
	public void offAlarmStatus() throws Exception;
	
	/**
	 * 声音告警数目 
	 * @throws Exception
	 */
	public int alarmNum() throws Exception;

}
