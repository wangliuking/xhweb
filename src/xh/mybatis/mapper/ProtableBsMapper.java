package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.ProtableBean;

public interface ProtableBsMapper {	

	public ArrayList<ProtableBean> list(Map<String,Object> map)throws Exception;
	public int count(Map<String,Object> map)throws Exception;
	
	public int exists(Map<String,Object> map)throws Exception;
	public int add(ProtableBean bean)throws Exception;
	public int update(ProtableBean bean)throws Exception;	
	public int del(String id)throws Exception;

}
