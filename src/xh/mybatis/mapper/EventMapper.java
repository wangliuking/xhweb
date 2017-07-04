package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EventMapper {
	
	/**
	 * 查询事件记录
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectEvent()throws Exception;
	
	/**
	 * 删除事件
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int deleteEvent(String name)throws Exception;
	/**
	 * 添加事件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertEvent(Map<String, Object> map)throws Exception;

}
