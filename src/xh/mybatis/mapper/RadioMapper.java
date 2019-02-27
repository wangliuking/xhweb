package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioBean;

public interface RadioMapper {
	public List<RadioBean> list(Map<String,Object> map) throws Exception;
	
	public int count() throws Exception;
	
	public int add(RadioBean bean) throws Exception;
	
	public int update(RadioBean bean) throws Exception;
	
	public int delete(List<String> list) throws Exception;

}
