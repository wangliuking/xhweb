package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.QualityCount;

public interface QualityCountMapper {
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

    int insert(QualityCount record);

    int insertSelective(QualityCount record);

    QualityCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QualityCount record);

    int updateByPrimaryKey(QualityCount record);
}