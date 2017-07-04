package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.UserStatusH;

public interface UserStatusHMapper {
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

    int insert(UserStatusH record);

    int insertSelective(UserStatusH record);

    UserStatusH selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStatusH record);

    int updateByPrimaryKey(UserStatusH record);
}