package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WorkBean;

public interface SelectMapper {	

	public ArrayList<Map<String,Object>> workcontact()throws Exception;
	
	public int workcontact_add(Map<String,Object> map)throws Exception;
	
	public int workcontact_del(String name)throws Exception;

}
