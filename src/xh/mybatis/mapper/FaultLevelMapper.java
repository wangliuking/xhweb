package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.FaultFourBean;
import xh.mybatis.bean.FaultOneBean;
import xh.mybatis.bean.FaultThreeBean;

public interface FaultLevelMapper {
	
	List<FaultOneBean> one_list(Map<String,Object> map);
	List<FaultThreeBean> three_list(Map<String,Object> map);
	List<FaultThreeBean> excel_three_list(Map<String,Object> map);
	List<FaultFourBean> four_list(Map<String,Object> map);
	
	int one_count(Map<String,Object> map);
	int three_count(Map<String,Object> map);
	int four_count(Map<String,Object> map);
	
	int one_add(FaultOneBean bean);
	int four_add(FaultFourBean bean);
	
	int one_update(FaultOneBean bean);
	int three_update(FaultThreeBean bean);
	int three_update_by_order(FaultThreeBean bean);
	int four_update(FaultFourBean bean);
	
	int one_del(List<String> list);
	int three_del(List<String> list);
	int four_del(List<String> list);
	
	
	
	

}
