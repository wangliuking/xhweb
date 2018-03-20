package xh.mybatis.service;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.DgnaMapper;
import xh.mybatis.tools.MoreDbTools;
 
public class DgnaServices {

	/**
	 * 根据组ID查找组名
	 * @param map
	 * @return
	 */
	public static String groupNameById(int id){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DgnaMapper mapper=sqlSession.getMapper(DgnaMapper.class);
		String result="";
		try {
			result=mapper.groupNameById(id);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	
	
}
