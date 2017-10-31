package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WebRoleBean;

public interface WebRoleMapper {
	/**
	 * 根据用户查询角色role
	 * @return
	 * @throws Exception
	 */
	public String roleByUser(String user)throws Exception;
	
	/**
	 * 根据角色类型查找角色列表
	 * @param roleType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> roleTypeByList(int roleType)throws Exception;
	/**
	 * 根据用户查询角色roleId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int roleIdByUser(String user)throws Exception;
	/**
	 * 根据角色判断角色是否存在
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int roleIsExists(WebRoleBean bean)throws Exception;
	/**
	 * 查询所有角色
	 * @return
	 * @throws Exception
	 */
	public List<WebRoleBean> roleByAll()throws Exception;
	/**
	 * 添加角色
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int addRole(WebRoleBean bean)throws Exception;
	/**
	 * 修改角色
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int updateByroleId(WebRoleBean bean)throws Exception;
	/**
	 * 根据角色ID删除角色
	 * @return
	 * @throws Exception
	 */
	public int deleteByRoleId(List<String> list)throws Exception;

}
