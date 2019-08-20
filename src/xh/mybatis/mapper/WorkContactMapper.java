package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WorkContactBean;

public interface WorkContactMapper {
	
	List<WorkContactBean> list(Map<String,Object> map) throws Exception;
	int list_count(Map<String,Object> map) throws Exception;
	int add(WorkContactBean bean) throws Exception;
	
	int update(WorkContactBean bean) throws Exception;
	int addFile(List<Map<String,Object>> list) throws Exception;
	List<Map<String,Object>> searchFile(String taskId) throws Exception;
	int sign(WorkContactBean bean) throws Exception;
	
	int check(WorkContactBean bean) throws Exception;
	
	int cancel(int id) throws Exception;
	
	int delFile(int id) throws Exception;
	
	int del(List<String> list) throws Exception;
	
	int codeNum(Map<String,Object> map) throws Exception;
	
	int isFileExistis(Map<String,Object> map) throws Exception;

}
