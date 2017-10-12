package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.Map;

public interface WorkMapper {
	
	/**
	 * 工作记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Map<String,Object>> worklist(Map<String,Object> map)throws Exception;
	/**
	 * 工作记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> map)throws Exception;

}
