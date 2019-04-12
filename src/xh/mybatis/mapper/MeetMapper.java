package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WorkBean;

public interface MeetMapper {
	
	public ArrayList<MeetBean> meetlist(Map<String,Object> map)throws Exception;

	public int meetcount(Map<String,Object> map)throws Exception;

	public int add(MeetBean bean)throws Exception;

	public int update(MeetBean bean)throws Exception;
	
	public int del(List<String> list)throws Exception;

}
