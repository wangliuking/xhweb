package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface ElecGenerationMapper {
	
	List<Map<String,Object>> list(Map<String,Object> map) throws Exception;
	int count(Map<String,Object> map) throws Exception;

}
