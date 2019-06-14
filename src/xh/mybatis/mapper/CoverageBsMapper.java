package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.CoverageBsBean;
import xh.mybatis.bean.WorkBean;

public interface CoverageBsMapper {	

	public ArrayList<CoverageBsBean> list(Map<String,Object> map)throws Exception;
	public int count(Map<String,Object> map)throws Exception;
	
	public int add(CoverageBsBean bean)throws Exception;
	public int update(CoverageBsBean bean)throws Exception;	
	public int del(String id)throws Exception;

}
