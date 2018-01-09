package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AmapMapper {
	/**
	 * 根据所选条件查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByBoth(Map<String,Object> map) throws Exception;
	
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
	
	/**
	 * gis显示部分
	 */
	
	/**
	 * 根据不同用户查询gisView显示表的数目，用于同bsstation比对
	 * @return
	 * @throws Exception
	 */
	public int gisViewCount(Map<String,Object> map)throws Exception;
	
	/**
	 * 删除该用户的所有显示基站
	 * @return
	 * @throws Exception
	 */
	public int deleteByUserId(Map<String,Object> map)throws Exception;
	
	/**
	 * 为该用户添加默认显示所有基站
	 * @return
	 * @throws Exception
	 */
	public int insertByUserId(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据用户查询gisView
	 * @author wlk
	 */
	public List<HashMap<String,String>> gisViewByUserId(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据用户查询需要显示的基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> gisViewByUserIdForShow(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据区域查询该用户的基站显示情况
	 * @author wlk
	 */
	public List<HashMap<String,String>> gisViewByUserIdAndZoneForShow(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据级别查询该用户的基站显示情况
	 * @author wlk
	 */
	public List<HashMap<String,String>> gisViewByUserIdAndLevelForShow(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据用户和基站id更新显示的配置
	 * @return
	 * @throws Exception
	 */
	public int updateBatch(Map<String,Object> map)throws Exception;
	
	
}