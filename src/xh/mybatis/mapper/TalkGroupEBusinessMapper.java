package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.TalkGroupEBusiness;

public interface TalkGroupEBusinessMapper {
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

    int insert(TalkGroupEBusiness record);

    int insertSelective(TalkGroupEBusiness record);

    TalkGroupEBusiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TalkGroupEBusiness record);

    int updateByPrimaryKey(TalkGroupEBusiness record);
}