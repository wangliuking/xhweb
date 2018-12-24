package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WorkBean;

public interface Top10Mapper {
	
	ArrayList<Map<String,Object>> offline_bs(Map<String,Object> map)throws Exception;
	ArrayList<Map<String,Object>> elec(Map<String,Object> map)throws Exception;

}
