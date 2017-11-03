package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.TalkGroup;

public interface TalkGroupMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> ById(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据ID查找组名
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String GroupNameById(int id)throws Exception;
	
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int  Count(Map<String,Object> map)throws Exception;
	
	/**
	 * 增加组
	 * @return
	 * @throws Exception
	 */
	public int  insertTalkGroup(Map<String,Object> map)throws Exception;
	
	/**
	 * 获取交换中心标识列表 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> vpnList()throws Exception;
	
	/**
	 * 获取交换中心标识列表 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> vpnList2()throws Exception;
	
    int deleteByPrimaryKey(Integer id);

    int insert(TalkGroup record);

    int insertSelective(TalkGroup record);

    TalkGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TalkGroup record);

    int updateByPrimaryKey(TalkGroup record);
}