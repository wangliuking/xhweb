package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioDispatch;

public interface RadioDispatchMapper {
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

    int insert(RadioDispatch record);

    int insertSelective(RadioDispatch record);

    RadioDispatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadioDispatch record);

    int updateByPrimaryKey(RadioDispatch record);
}