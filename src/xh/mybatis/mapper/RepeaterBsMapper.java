package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RepeaterBsBean;
import xh.mybatis.bean.WorkBean;

public interface RepeaterBsMapper {	

	public ArrayList<RepeaterBsBean> list(Map<String,Object> map)throws Exception;
	public int count(Map<String,Object> map)throws Exception;
	
	public int add(RepeaterBsBean bean)throws Exception;
	public int update(RepeaterBsBean bean)throws Exception;	
	public int del(String bsId)throws Exception;

}
