package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.WebUserBean;
import xh.mybatis.mapper.WebUserMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class WebUserServices {
	/**
	 * 软件产业中心用户列表
	 * @return
	 */
	public static List<Map<String,Object>>userlist10002(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.userlist10002();
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
	public static ArrayList<WebUserBean> selectUserByRootAndPass(String root,String userPass){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		WebUserBean bean=new WebUserBean();
		ArrayList<WebUserBean> list=new ArrayList<WebUserBean>();
		bean.setUser(root);
		bean.setUserPass(userPass);
		try {
			bean=mapper.selectUserByUserAndPass(bean);
			//sqlSession.commit();
			if (bean!=null) {
				list.add(bean);
			}
			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
		
		
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
	public static int userAllCount(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebUserMapper mapper=sqlSession.getMapper(WebUserMapper.class);
		int count=0;
		try {
			count=mapper.userAllCount();
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

}
