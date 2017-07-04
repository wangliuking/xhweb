package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioDispatchBusiness;

public interface RadioDispatchBusinessMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> ById(Map<String,Object> map)throws Exception;
	
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int  Count(Map<String,Object> map)throws Exception;
	
    int deleteByPrimaryKey(Integer id);

    int insert(RadioDispatchBusiness record);

    int insertSelective(RadioDispatchBusiness record);

    RadioDispatchBusiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadioDispatchBusiness record);

    int updateByPrimaryKey(RadioDispatchBusiness record);
}