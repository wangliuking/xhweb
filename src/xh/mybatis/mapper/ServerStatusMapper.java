package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface ServerStatusMapper {
	
	/**
	 * 服务器运行状态
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> serverstatus() throws Exception;

}
