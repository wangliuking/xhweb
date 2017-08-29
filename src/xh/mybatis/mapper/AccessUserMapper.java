package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AccessUserBean;

public interface AccessUserMapper {
	
	/**
	 * 接入用户列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> accessUserList() throws Exception;
	
	/**
	 * 判断接入用户是否存在
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int accessUserExists(int id)throws Exception;
	/**
	 * 添加接入用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insert(AccessUserBean bean)throws Exception;
	
	/**
	 * 修改接入用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAccessUser(AccessUserBean bean)throws Exception;
	/**
	 * 删除接入用户
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteByUserId(List<String> list)throws Exception;

}
