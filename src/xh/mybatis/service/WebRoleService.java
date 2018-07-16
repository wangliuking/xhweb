package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.UserPowerBean;
import xh.mybatis.bean.WebRoleBean;
import xh.mybatis.mapper.WebRoleMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class WebRoleService {
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public static List<WebRoleBean> roleByAll(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		List<WebRoleBean> list=new ArrayList<WebRoleBean>();
		try {
			list=mapper.roleByAll(map);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
		
		
	}
	public static WebRoleBean roleOne(String roleId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		WebRoleBean list=new WebRoleBean();
		try {
			list=mapper.roleOne(roleId);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
		
		
	}
	/**
	 * 根据角色类型查找角色列表
	 * @param roleType
	 * @return
	 */
	public static List<Map<String,Object>> roleTypeByList(int roleType){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.roleTypeByList(roleType);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
		
		
	}
	/**
	 * 根据用户查询角色roleId
	 * @param user
	 * @return
	 */
	public static int roleIdByUserId(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int roleId=-1;
		try {
			roleId=mapper.roleIdByUser(user);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  roleId;
	}
	/**
	 * 根据用户查询角色roleId
	 * @param user
	 * @return
	 */
	public static String roleByUserId(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		String role="";
		try {
			role=mapper.roleByUser(user);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  role;
	}
	/**
	 * 添加角色
	 * @param bean
	 * @return
	 */
	public static int addRole(WebRoleBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int count=-1;
		try {
			count=mapper.roleIsExists(bean);
			if(count==0){
				mapper.addRole(bean);
				sqlSession.commit();
				sqlSession.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;	
	}
	/**
	 * 更新角色
	 * @param bean
	 * @return
	 */
	public static int updateByroleId(WebRoleBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int count=-1;
		try {
			count=mapper.updateByroleId(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;	
	}
	/**
	 * 删除角色
	 * @param list
	 * @return
	 */
	public static int deleteByRoleId(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int result=-1;
		try {
			result=mapper.deleteByRoleId(list);
			sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/*获取角色权限*/
	public static UserPowerBean role_power(int roleId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		UserPowerBean bean=new UserPowerBean();
		try {
			bean=mapper.role_power(roleId);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
		
		
	}
	/*判断角色权限是否存在*/
	public static int exists_role_power(int roleId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int count=0;
		try {
			count=mapper.exists_role_power(roleId);			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
		
	}
	/*修改角色权限*/
	public static int update_role_power(UserPowerBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int count=0;
		try {
			count=mapper.update_role_power(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
		
	}
	/*增加角色权限*/
	public static int add_role_power(UserPowerBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebRoleMapper mapper=sqlSession.getMapper(WebRoleMapper.class);
		int count=0;
		try {
			count=mapper.add_role_power(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
		
	}

}
