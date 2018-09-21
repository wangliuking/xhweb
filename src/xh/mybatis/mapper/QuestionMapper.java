package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface QuestionMapper {
	
	int count(Map<String,Object> map)throws Exception;
	int add(Map<String,Object> map)throws Exception;
	List<Map<String,Object>> list(Map<String,Object> map) throws Exception;

}
