package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface DispatchStatusMapper {
	
	/**
	 *调度台运行状态
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> dispatchstatus() throws Exception;

}
