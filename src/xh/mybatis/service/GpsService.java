package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.GpsMapper;
import xh.mybatis.tools.MoreDbTools;

public class GpsService {
	/**
	 * 查询gps信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> gpsInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.gpsInfo(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * gps总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int gpsCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		int count = 0;
		try {
			count = mapper.gpsCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
