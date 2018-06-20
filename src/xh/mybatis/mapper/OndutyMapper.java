package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.OndutyBean;
import xh.mybatis.bean.WebLogBean;

public interface OndutyMapper {
	/**
	 * 值班记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> duty_list(Map<String, Object> map)throws Exception;
	
	public int count(Map<String, Object> map)throws Exception;
	
	public int write_data(List<OndutyBean> bean)throws Exception;
	
	public Map<String,Object> onduty()throws Exception;


}
