package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.AccessUserBean;
import xh.mybatis.mapper.AccessUserMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

/**
 * 
 * @author muwei
 *
 */
public class AccessUserService {
	/**
	 * 接入用户列表
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> accessUserList(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AccessUserMapper mapper = session.getMapper(AccessUserMapper.class);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		try {
			list = mapper.accessUserList();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/**
	 * 添加接入用户
	 * @param map
	 * @return
	 */
	public static int insert(AccessUserBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AccessUserMapper mapper = session.getMapper(AccessUserMapper.class);
		int result=-1;
		try {
			if(mapper.accessUserExists(FunUtil.StringToInt(bean.getUser_id()))==0){
				result=mapper.insert(bean);
				session.commit();
				session.close();
			}else{
				result=0;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return result;
	}
	/**
	 * 修改接入用户
	 * @param map
	 * @return
	 */
	public static int updateAccessUser(AccessUserBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AccessUserMapper mapper = session.getMapper(AccessUserMapper.class);
		int result=-1;
		try {
			result=mapper.updateAccessUser(bean);
			session.commit();
			session.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return result;
	}	
	/**
	 * 删除接入用户
	 * @param list
	 * @return
	 */
	public static int deleteByUserId(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AccessUserMapper mapper = session.getMapper(AccessUserMapper.class);
		int result=-1;
		try {
			result=mapper.deleteByUserId(list);
			session.commit();
			session.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return result;
	}

}
