package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface AppInspectionMapper {
	
	/*<!--查询800M移动基站巡检表-->*/
	public List<Map<String,Object>> mbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--查询800M移动基站巡检表-->*/
	public int mbsinfoCount()throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public List<Map<String,Object>> sbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public int sbsinfoCount()throws Exception;
	

	/*<!--网管巡检表-->*/
	public List<Map<String,Object>> netinfo(Map<String,Object> map)throws Exception;
	
	/*<!--网管巡检表-->*/
	public int netinfoCount()throws Exception;
	
	/*<!--调度台巡检表-->*/
	public List<Map<String,Object>> dispatchinfo(Map<String,Object> map)throws Exception;
	
	/*<!--调度台巡检表-->*/
	public int dispatchinfoCount()throws Exception;

}
