package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WorkBean;

public interface FlowMapper {
	
	ArrayList<Map<String,Object>> month_user_flow(Map<String,Object> map)throws Exception;

}
