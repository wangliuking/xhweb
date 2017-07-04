package xh.mybatis.mapper;

import xh.mybatis.bean.WebUserRoleBean;

public interface WebUserRoleMapper {
	
	/**
	 * 添加用户与角色关系
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int  addUserToRole(WebUserRoleBean bean)throws Exception;
	/**
	 * 更新用户ID到角色关系表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int  updateUserRole(WebUserRoleBean bean)throws Exception;
	/**
	 * 根据用户ID判断是否存在
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int userIdIsExixts(int userId)throws Exception;

}
