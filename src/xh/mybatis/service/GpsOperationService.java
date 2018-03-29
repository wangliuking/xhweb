package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.GpsOperationMapper;
import xh.mybatis.tools.MoreDbTools;

public class GpsOperationService {
	/**
	 * 查询gps操作记录信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> gpsOperationInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsOperationMapper mapper = sqlSession.getMapper(GpsOperationMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.gpsOperationInfo(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * gps操作记录总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int gpsOperationCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsOperationMapper mapper = sqlSession.getMapper(GpsOperationMapper.class);
		int count = 0;
		try {
			count = mapper.gpsOperationCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
