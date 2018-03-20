package xh.mybatis.service;
 
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.WebRoleMapper;
import xh.mybatis.tools.MoreDbTools;

public class DataBaseUtilService {
	/**
	 * 根据用户查询角色role
	 * @param root
	 * @return
	 * 
	 */
	public static String roleByUsername(String user){
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
		return role;
	}

}
