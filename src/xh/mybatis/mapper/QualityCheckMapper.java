package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import xh.mybatis.bean.QualityCheck;

public interface QualityCheckMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> ById(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询所有记录
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> radioUserBusinessInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int  Count(Map<String,Object> map)throws Exception;
	
    int deleteByPrimaryKey(Integer id);

    int insert(QualityCheck record);

    int insertSelective(QualityCheck record);

    QualityCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QualityCheck record);

    int updateByPrimaryKey(QualityCheck record);
}