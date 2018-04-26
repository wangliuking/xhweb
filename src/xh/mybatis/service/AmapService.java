package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tcpBean.GetForGpsDst;
import com.tcpBean.GetForGpsDstAck;

import xh.mybatis.mapper.AmapMapper;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.mapper.TcpMapper;
import xh.mybatis.tools.MoreDbTools;

public class AmapService {
	/**
	 * 根据所选条件查询所有基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> bsByBoth(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> Amap=mapper.bsByBoth(map);
	        session.commit();  
	        session.close();
	        return Amap;   
	}
	
	/**
	 * 根据基站id查询单个基站的排队数
	 */
	public List<HashMap<String, String>> selectNumTotalsByBsId(String bsId) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> Amap=mapper.selectNumTotalsByBsId(bsId);
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
	
	/**
	 * 查询所有路测基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectAllRoad() throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> roadList =mapper.selectAllRoad();
	        session.commit();  
	        session.close();
	        return roadList;   
	}
	
	/**
	 * 路测数据查询
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectRoadById(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> roadList=mapper.selectRoadById(map);
	        session.commit();  
	        session.close();
	        return roadList;   
	}
	
	/**
	 * gis显示部分
	 */
	
	/**
	 * 根据不同用户查询gisView显示表的数目，用于同bsstation比对
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public int gisViewCount(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		int count = mapper.gisViewCount(map);
	        session.commit();  
	        session.close();
	        return count;   
	}
	
	/**
	 * 删除该用户的所有显示基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public int deleteByUserId(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		int count = mapper.deleteByUserId(map);
	        session.commit();  
	        session.close();
	        return count;   
	}
	
	/**
	 * 为该用户添加默认显示所有基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public int insertByUserId(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		int count = mapper.insertByUserId(map);
	        session.commit();  
	        session.close();
	        return count;   
	}
	
	/**
	 * 根据用户查询gisView
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> gisViewByUserId(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> list = mapper.gisViewByUserId(map);
	        session.commit();  
	        session.close();
	        return list;   
	}

	/**
	 * 根据用户查询需要显示的基站
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> gisViewByUserIdForShow(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> list = mapper.gisViewByUserIdForShow(map);
	        session.commit();  
	        session.close();
	        return list;   
	}
	
	/**
	 * 根据区域查询该用户的基站显示情况
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> gisViewByUserIdAndZoneForShow(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> list = mapper.gisViewByUserIdAndZoneForShow(map);
	        session.commit();  
	        session.close();
	        return list;   
	}
	
	/**
	 * 根据级别查询该用户的基站显示情况
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> gisViewByUserIdAndLevelForShow(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		List<HashMap<String, String>> list = mapper.gisViewByUserIdAndLevelForShow(map);
	        session.commit();  
	        session.close();
	        return list;   
	}
	
	/**
	 * 根据用户和基站id更新显示的配置
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public int updateBatch(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=session.getMapper(AmapMapper.class);
		int count = mapper.updateBatch(map);
	        session.commit();  
	        session.close();
	        return count;   
	}
	
	/**
	 * 获取dst数据
	 */
	public static List<Map<String,String>> dstData(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		List<Map<String,String>> list = null;
		try{
			list = mapper.selectForGpsDst();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

}