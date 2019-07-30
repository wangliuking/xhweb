package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.MoveBusBean;

public interface MoveBusMapper {	

	public ArrayList<MoveBusBean> list(Map<String,Object> map)throws Exception;
	public int count(Map<String,Object> map)throws Exception;
	
	public int exists(Map<String,Object> map)throws Exception;
	public int add(MoveBusBean bean)throws Exception;
	public int update(MoveBusBean bean)throws Exception;	
	public int del(String id)throws Exception;

}
