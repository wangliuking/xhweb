package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AmapMapper {
	/**
	 * 根据所选条件查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByBoth(Map<String,List<String>> map) throws Exception;
	
	/**
	 * 根据bsId查询单个基站的排队数
	 */
	public List<HashMap<String,String>> selectNumTotalsByBsId(String bsId) throws Exception;
	
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
	
	/**
	 * 查询所有路测基站
	 */
	public List<HashMap<String,String>> selectAllRoad() throws Exception;
	
	/**
	 * 路测数据查询
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectRoadById(Map<String,Object> map) throws Exception;
	
	
}