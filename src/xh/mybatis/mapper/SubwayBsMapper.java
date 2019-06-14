package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.SubwayBean;

public interface SubwayBsMapper {	

	public ArrayList<SubwayBean> list(Map<String,Object> map)throws Exception;
	public int count(Map<String,Object> map)throws Exception;
	
	public int exists(String name)throws Exception;
	public int add(SubwayBean bean)throws Exception;
	public int update(SubwayBean bean)throws Exception;	
	public int del(String bsId)throws Exception;

}
