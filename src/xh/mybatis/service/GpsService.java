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
			sqlSession.commit();
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
	/**
	 *  <!--根据源ID，查找注册调度台号-->
	 * @param map
	 * @return
	 */
	public static int user_dstId(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		String count = null;;
		try {
			count = mapper.user_dstId(map);
			sqlSession.close();
			if(count==null){
				return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(count);
	}
	public static int add(List<Map<String, Object>> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		int count = 0;
		try {
			count = mapper.add(list);
		    sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static List<HashMap<String,String>> gps_count(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.gps_count(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int gps_count_total(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		GpsMapper mapper = sqlSession.getMapper(GpsMapper.class);
		int count=0;
		try {
			count = mapper.gps_count_total(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
