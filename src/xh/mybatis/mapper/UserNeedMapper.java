package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.UserNeedBean;

public interface UserNeedMapper {
	
	List<UserNeedBean> data_all(Map<String,Object> map) throws Exception;
	
	int data_all_count(Map<String, Object> map) throws Exception;
	
	int add(UserNeedBean bean) throws Exception;

	int update(UserNeedBean bean) throws Exception;
	
	int del(List<String> list) throws Exception;

}
