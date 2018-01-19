package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface EastComMapper {
	
	/*基站信道排队top5*/
	public List<Map<String,Object>> queueTop5(String time)throws Exception;
	
	public List<Map<String,Object>> queueTopBsName(List<String> list)throws Exception;

}
