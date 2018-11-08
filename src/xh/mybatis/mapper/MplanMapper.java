package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.MplanBean;

public interface MplanMapper {
	int add(MplanBean bean) throws Exception;
	
	int update(MplanBean bean) throws Exception;
	
	int del(List<String> list)throws Exception;
	
	int count(Map<String,Object> map) throws Exception;
	
	List<MplanBean> mplanList(Map<String,Object> map)throws Exception;
	
	List<MplanBean> mplanList_month_one(String time)throws Exception;
	
	List<MplanBean> mplanList_month_two(String time)throws Exception;

}
