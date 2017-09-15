package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.RoadMapper;
import xh.mybatis.tools.MoreDbTools;

public class RoadService {
	/**
	 * 查询路测基站信息
	 * @param root
	 * @return
	 */
	public static List roadInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RoadMapper mapper = sqlSession.getMapper(RoadMapper.class);
		List list = new ArrayList();
		try {
			list = mapper.roadInfo(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 路测基站总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int roadCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RoadMapper mapper = sqlSession.getMapper(RoadMapper.class);
		int count = 0;
		try {
			count = mapper.roadCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
