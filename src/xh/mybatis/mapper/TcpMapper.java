package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TcpMapper {
	
	/**
	 * app根据userId查询负责的基站
	 * 
	 */
	public List<Map<String,Object>> selectByAppUser(String userId)throws Exception;

}
