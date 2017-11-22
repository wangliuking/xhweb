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

}
