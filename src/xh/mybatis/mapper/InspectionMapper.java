package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.Inspection;

public interface InspectionMapper {
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

    int insert(Inspection record);

    int insertSelective(Inspection record);

    Inspection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inspection record);

    int updateByPrimaryKey(Inspection record);
}