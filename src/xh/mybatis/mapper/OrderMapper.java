package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
	
	//派单列表
	public List<Map<String,Object>> orderList(Map<String,Object> map) throws Exception;
	//派单列表总数
	public int orderListCount(Map<String,Object> map) throws Exception;

}
