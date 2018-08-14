package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.UserPowerBean;
import xh.mybatis.bean.WebUserBean;

public interface WebUserMapper {
	
	/**
	 * 软件产业中心用户列表
	 * @return
	 * @throws Exception
		public List<Map<String,Object>> userlist10002()throws Exception;
	 */
	/**
	 * 根据RoleID角色组用户列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlistByRoleId(Integer roleId)throws Exception;
	/**
	 * 根据RoleID角色组用户列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlistByRoleIdExceptUser(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据权限获取用户
	 * @param powerstr
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlistByPower(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据权限字段查找用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userPowerListByType(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据用户权限+RoleID获取用户
	 * @param powerstr
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlistByPowerAndRoleId(Map<String, Object> map)throws Exception;
	
	public List<Map<String,Object>> emailRecvUsersByPower(Map<String, Object> map)throws Exception;
	
	public List<Map<String,Object>> emailRecvUsersByGroupPower(Map<String, Object> map)throws Exception;
	
	/**
	 * 根据Role类型查找用户列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlistByRoleType(List<String> roleIdlist)throws Exception;
	/**
	 * 根据登录用户名,密码查找登录用户
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> selectUserByUserAndPass(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据用户名,查找用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> userInfoByName(String user)throws Exception;
	/**
	 * 根据用户名,查找用户权限信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object>userPowerInfoByName(String user)throws Exception;
	/**
	 * 根据登录用户名查找登录用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public WebUserBean selectUserByUser(String user)throws Exception;
	/**
	 * 根据用户名查找用户是否存在
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int userIsExists(String user)throws Exception;
	/**
	 * 根据用户名查找用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int userIdByUser(String user)throws Exception;
	/**
	 * 添加用户
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int insertUser(WebUserBean bean)throws Exception;
	/**
	 * 用户列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<WebUserBean> userList(Map<String,Object> map)throws Exception;
	/**
	 * 用户总数
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int userAllCount(Map<String, Object> map)throws Exception;
	/**
	 * 根据用户ID删除用户
	 * @return
	 * @throws Exception
	 */
	public int deleteByUserId(List<String> list)throws Exception;
	
	/**
	 * 修改用户
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateByUser(WebUserBean bean)throws Exception;
	
	/**
	 * 启用，禁用账号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int lockUser(Map<String,Object> map)throws Exception;
	/**
	 * 判断用户权限是否存在
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int existsUserPower(int userId)throws Exception;
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,String> getUserPower(int userId)throws Exception;
	/**
	 * 添加用户权限
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int addUserPower(UserPowerBean bean)throws Exception;
	/**
	 * 修改用户权限
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateUserPower(UserPowerBean bean)throws Exception;

}
