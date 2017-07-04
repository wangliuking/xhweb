package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.TalkGroupIa;

public interface TalkGroupIaMapper {
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

    int insert(TalkGroupIa record);

    int insertSelective(TalkGroupIa record);

    TalkGroupIa selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TalkGroupIa record);

    int updateByPrimaryKey(TalkGroupIa record);
}