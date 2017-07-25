package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WebUserBean;

public interface WebUserMapper {
	
	/**
	 * 软件产业中心用户列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> userlist10002()throws Exception;
	/**
	 * 根据登录用户名,密码查找登录用户
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public WebUserBean selectUserByUserAndPass(WebUserBean bean)throws Exception;
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
	public int userAllCount()throws Exception;
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

}
