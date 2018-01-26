package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface AppInspectionMapper {
	
	/*<!--查询800M移动基站巡检表-->*/
	public List<Map<String,Object>> mbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--查询800M移动基站巡检表-->*/
	public int mbsinfoCount()throws Exception;

}
