package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.AmapMapper;
import xh.mybatis.tools.MoreDbTools;

public class AmapService {
	/**
	 * 根据所选区域查询所有基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> bsByArea(List<String> zone) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> Amap=mapper.bsByArea(zone);
	        session.commit();  
	        session.close();
	        return Amap;   
	}
	
	/**
	 * 根据所选级别查询所有基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> bsByLevel(List<String> level) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> Amap=mapper.bsByLevel(level);
	        session.commit();  
	        session.close();
	        return Amap;   
	}

	
	/**
	 * 不规则圈选基站查询
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> polyline(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper = sqlSession.getMapper(AmapMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.polyline(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 不规则圈选基站总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int polylineCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper = sqlSession.getMapper(AmapMapper.class);
		int count = 0;
		try {
			count = mapper.polylineCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 圈选基站查询
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> rectangle(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper = sqlSession.getMapper(AmapMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.rectangle(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 圈选基站总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int rectangleCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper = sqlSession.getMapper(AmapMapper.class);
		int count = 0;
		try {
			count = mapper.rectangleCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	

}