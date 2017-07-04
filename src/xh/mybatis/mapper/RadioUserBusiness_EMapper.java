package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioUserBusiness_E;

public interface RadioUserBusiness_EMapper {
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

    int insert(RadioUserBusiness_E record);

    int insertSelective(RadioUserBusiness_E record);

    RadioUserBusiness_E selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadioUserBusiness_E record);

    int updateByPrimaryKey(RadioUserBusiness_E record);
}