package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.UserPowerBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.mapper.WebUserMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class WebUserServices {
	/**
	 * 软件产业中心用户列表
	 * @return
	 */
//	public static List<Map<String,Object>> userlist10002(){
//		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
//		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
//		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//		try {
//			list=mapper.userlist10002();
//			sqlSession.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return  list;	
//	}
	
	/**
	 * 根据RoleID角色组用户列表
	 * @return
	 */
	public static List<Map<String,Object>> userlistByRoleId(Integer roleId) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.userlistByRoleId(roleId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	
	/**
	 * 根据RoleID角色组用户列表
	 * @return
	 */
	public static List<Map<String,Object>> userlistByRoleIdExceptUser(Map<String,Object> map) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.userlistByRoleIdExceptUser(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	/**
	 * 根据用户权限获取用户
	 * @param powerstr
	 * @return
	 */
	public static List<Map<String,Object>>userlistByPower(String powerstr) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("powerstr", powerstr);
		try {
			list=mapper.userlistByPower(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	/**
	 * 根据权限字段查找用户
	 * @param fieldstr
	 * @return
	 */
	public static List<Map<String,Object>>userPowerListByType(Map<String,Object> map) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

		try {
			list=mapper.userPowerListByType(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	
	/**
	 * 根据用户权限+RoleID获取用户
	 * @param powerstr
	 * @return
	 */
	public static List<Map<String,Object>> userlistByPowerAndRoleId(Map<String, Object> map) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.userlistByPowerAndRoleId(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	public static List<Map<String,Object>> emailRecvUsersByPower(Map<String, Object> map) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.emailRecvUsersByPower(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	/**
	 * 根据Role类型查找用户列表
	 * @param roleId
	 * @return
	 */
	public static List<Map<String,Object>> userlistByRoleType(List<String> roleIdlist) {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.userlistByRoleType(roleIdlist);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	/**
	 * 根据登录用户名，密码查找登录用户
	 * @param root
	 * @return
	 */
	public static Map<String,Object>selectUserByRootAndPass(String root,String userPass){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);

		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> map2=new HashMap<String, Object>();
		/*bean.setUser(root);
		bean.setUserPass(userPass);*/
		map.put("user", root);map.put("userPass", userPass);
		try {
			map2=mapper.selectUserByUserAndPass(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  map2;
		
		
	}
	/**
	 * 根据用户名,查找用户信息
	 * @param user
	 * @return
	 */
	public static Map<String,Object>userInfoByName(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			map=mapper.userInfoByName(user);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  map;	
	}
	/**
	 * 根据用户名,查找用户权限信息
	 * @param user
	 * @return
	 */
	public static Map<String,Object>userPowerInfoByName(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			map=mapper.userPowerInfoByName(user);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  map;	
	}
	/**
	 * 根据登录用户名,登录用户
	 * @param root
	 * @return
	 */
	public static WebUserBean selectUserByUser(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		WebUserBean bean=new WebUserBean();
		try {
			bean=mapper.selectUserByUser(user);
			//sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  bean;
		
		
	}
	/**
	 * 添加用户
	 * @param bean
	 * @return
	 */
	public static int insertUser(WebUserBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int count=-1;
		try {
			count=mapper.userIsExists(bean.getUser());
			if(count==0){
				mapper.insertUser(bean);
				sqlSession.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return  count;	
	}
	/**
	 * 根据用户名查找用户ID
	 * @param user
	 * @return
	 */
	public static int userIdByUser(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int count=-1;
		try {
			count=mapper.userIdByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return  count;	
	}
	/**
	 * 修改用户
	 * @param bean
	 * @return
	 */
	public static int updateUser(WebUserBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int count=-1;
		try {
			count=mapper.updateByUser(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  count;	
	}
	/**
	 * 用户列表
	 * @param map
	 * @return
	 */
	public static List<WebUserBean> userList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<WebUserBean> list=new ArrayList<WebUserBean>();
		try {
			list=mapper.userList(map);
			//sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;	
	}
	/**
	 * 用户总数
	 * @return
	 */
	public static int userAllCount(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int count=0;
		try {
			count=mapper.userAllCount(map);
			//sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;	
	}
	/**
	 * 根据ID删除用户
	 * @param list
	 * @return
	 */
	public static int deleteByUserId(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int result=-1;
		try {
			result=mapper.deleteByUserId(list);
			sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 启用，禁用账号
	 * @param map
	 * @return
	 */
	public static int lockUser(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int result=-1;
		try {
			result=mapper.lockUser(map);
			sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 判断用户权限是否存在
	 * @param  userId
	 * @return
	 */
	public static int existsUserPower(int userId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int result=0;
		try {
			result=mapper.existsUserPower(userId);		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	public static HashMap<String,String> getUserPower(int userId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		HashMap<String,String> map=new HashMap<String, String>();
		try {
			map=mapper.getUserPower(userId);		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 *  添加用户权限
	 * @param bean
	 * @return
	 */
	public static int addUserPower(UserPowerBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int result=-1;
		try {
			result=mapper.addUserPower(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 修改用户权限
	 * @param bean
	 * @return
	 */
	public static int updateUserPower(UserPowerBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int result=-1;
		try {
			result=mapper.updateUserPower(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	

}
