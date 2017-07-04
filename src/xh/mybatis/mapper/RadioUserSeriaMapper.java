package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioUserSeria;

public interface RadioUserSeriaMapper {
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

    int insert(RadioUserSeria record);

    int insertSelective(RadioUserSeria record);

    RadioUserSeria selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadioUserSeria record);

    int updateByPrimaryKey(RadioUserSeria record);
}