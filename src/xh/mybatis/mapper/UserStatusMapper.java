package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserStatusMapper {
	/**
	 * 终端状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> userstatus(Map<String, Object> map)throws Exception;
	/**
	 * 终端状态数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int  userstatusCount(Map<String, Object> map)throws Exception;
	/**
	 * 终端在线状态统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> userStatusByChart()throws Exception;
	
	public int userOnline()throws Exception;

}
