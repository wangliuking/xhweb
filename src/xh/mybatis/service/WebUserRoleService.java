package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.WebUserRoleBean;
import xh.mybatis.mapper.WebUserRoleMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class WebUserRoleService {
	/**
	 * 添加用户ID到角色关系表
	 * @param bean
	 * @return
	 */
	public static int addUserToRole(WebUserRoleBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserRoleMapper mapper=sqlSession.getMapper(WebUserRoleMapper.class);
		int count=-1,exists=-1;
		try {
			exists=mapper.userIdIsExixts(bean.getUserId());
			if (exists>0) {
				count=mapper.updateUserRole(bean);
			}else {
				count=mapper.addUserToRole(bean);
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;	
	}
	/**
	 * 更新角色信息
	 * @param bean
	 * @return
	 */
	public static int updateUserRole(WebUserRoleBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebUserRoleMapper mapper=sqlSession.getMapper(WebUserRoleMapper.class);
		int count=-1,exists=-1;
		try {
			exists=mapper.userIdIsExixts(bean.getUserId());
			if (exists>0) {
				count=mapper.updateUserRole(bean);
			}else {
				count=mapper.addUserToRole(bean);
			}
			
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;	
	}

}
