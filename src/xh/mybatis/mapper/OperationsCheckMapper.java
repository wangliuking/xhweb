package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;

public interface OperationsCheckMapper {
	
	List<OperationsCheckBean> dataList(Map<String,Object> map)throws Exception;
	int count(Map<String,Object> map)throws Exception;
	
	int add(OperationsCheckBean bean)throws Exception;
	
	OperationsCheckDetailBean searchDetail(String time)throws Exception;
	
	int detailExists(String time)throws Exception;
	
	int addDetail(OperationsCheckDetailBean bean)throws Exception;
	
	int updateDetail(OperationsCheckDetailBean bean)throws Exception;
	

}
