package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GpsOperationMapper {
	/**
	 * gps操作记录
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> gpsOperationInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * gps操作记录总数
	 * @return
	 * @throws Exception
	 */
	public int  gpsOperationCount(Map<String,Object> map)throws Exception;
	
	public List<Map<String,String>>now_operation_record(Map<String,Object> map)throws Exception;
	
	public List<Map<String,String>>now_gps_close()throws Exception;

}
