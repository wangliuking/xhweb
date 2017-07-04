package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.RadioDispatchBusinessMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioDispatchBusinessService {
	/**
	 * 查询调度用户业务信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radioUserBusinessInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioDispatchBusinessMapper mapper = sqlSession.getMapper(RadioDispatchBusinessMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.ById(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int Count(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioDispatchBusinessMapper mapper = sqlSession.getMapper(RadioDispatchBusinessMapper.class);
		int count = 0;
		try {
			count = mapper.Count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
