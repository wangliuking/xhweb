package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RecordNetBean;

public interface RecordNetMapper {
	
	List<RecordNetBean> data_all(Map<String,Object> map) throws Exception;
	
	int data_all_count(Map<String, Object> map) throws Exception;
	
	int add(RecordNetBean bean) throws Exception;

	int update(RecordNetBean bean) throws Exception;
	
	int del(List<String> list) throws Exception;
	
	
	
}
