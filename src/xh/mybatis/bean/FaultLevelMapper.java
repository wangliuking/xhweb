package xh.mybatis.bean;

import java.util.List;
import java.util.Map;

public interface FaultLevelMapper {
	
	List<FaultOneBean> one_list(Map<String,Object> map);
	List<FaultThreeBean> three_list(Map<String,Object> map);
	List<FaultFourBean> four_list(Map<String,Object> map);
	
	int one_count(Map<String,Object> map);
	int three_count(Map<String,Object> map);
	int four_count(Map<String,Object> map);
	
	int one_add(FaultOneBean bean);
	int four_add(FaultFourBean bean);
	
	int one_update(FaultOneBean bean);
	int four_update(FaultFourBean bean);
	
	
	
	

}
