package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AmapMapper {
	/**
	 * 根据所选区域查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByArea(List<String> zone) throws Exception;
	/**
	 * 根据所选级别查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByLevel(List<String> level) throws Exception;
	/**
	 * 不规则圈选基站查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> polyline(Map<String,Object> map)throws Exception;
	
	/**
	 * 不规则圈选基站总数
	 * @return
	 * @throws Exception
	 */
	public int polylineCount(Map<String,Object> map)throws Exception;

	/**
	 * 圈选基站查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> rectangle(Map<String,Object> map)throws Exception;
	
	/**
	 * 圈选基站总数
	 * @return
	 * @throws Exception
	 */
	public int rectangleCount(Map<String,Object> map)throws Exception;
}