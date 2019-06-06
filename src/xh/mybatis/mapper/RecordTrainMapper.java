package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RecordTrainBean;

public interface RecordTrainMapper {
	
	List<RecordTrainBean> data_all(Map<String,Object> map) throws Exception;
	
	int data_all_count(Map<String, Object> map) throws Exception;
	
	int add(RecordTrainBean bean) throws Exception;

	int update(RecordTrainBean bean) throws Exception;
	
	int del(List<String> list) throws Exception;
	
	
	
}
