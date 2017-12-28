package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.TalkGroup;
import xh.mybatis.bean.TalkGroupBean;

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
	 * 根据通话组ID判断组是否存在
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int  isExists(int id)throws Exception;
	
	/**
	 * 增加组
	 * @return
	 * @throws Exception
	 */
	public int  insertTalkGroup(TalkGroupBean bean)throws Exception;
	
	/**
	 * 根据ID修改通话组
	 * @return
	 * @throws Exception
	 */
	public int  update(Map<String,Object> map)throws Exception;
	
	/**
	 * 删除用户组
	 * @return
	 * @throws Exception
	 */
	public void delete(List<String> list)throws Exception;
	
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
	
    
}