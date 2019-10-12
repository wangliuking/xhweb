package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GpsMapper {
	/**
	 * 查询接收短信
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> gpsInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 短信总数
	 * @return
	 * @throws Exception
	 */
	public int  gpsCount(Map<String,Object> map)throws Exception;
	
	/**
	 * <!--根据源ID，查找注册调度台号-->
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String  user_dstId(Map<String,Object> map)throws Exception;
	public int  add(List<Map<String,Object>> list)throws Exception;
	
	public List<HashMap<String,String>>  gps_count(Map<String,Object> map)throws Exception;
	public int  gps_count_total(Map<String,Object> map)throws Exception;
}
