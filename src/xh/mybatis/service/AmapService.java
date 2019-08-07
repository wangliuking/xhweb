package xh.mybatis.service;
 
import java.util.*;

import org.apache.ibatis.session.SqlSession;

import com.tcpBean.GetForGpsDst;
import com.tcpBean.GetForGpsDstAck;

import xh.mybatis.mapper.AmapMapper;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.mapper.TcpMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.springmvc.handlers.AmapController;

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
	 * 更新终端显示情况
	 */
	public int saveForAllVisable(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=session.getMapper(TcpMapper.class);
		int count = mapper.saveForAllVisable(map);
	        session.commit();  
	        session.close();
	        return count;   
	}

	/**
	 * 批量删除终端
	 */
	public int deleteRadioId(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=session.getMapper(TcpMapper.class);
		int count = mapper.deleteRadioId(map);
		session.commit();
		session.close();
		return count;
	}
	
	/**
	 * 更新用户初始化信息
	 */
	public int updateForMapInitByUser(Map<String,Object> map) throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=session.getMapper(TcpMapper.class);
		int count = mapper.updateForMapInitByUser(map);
	        session.commit();  
	        session.close();
	        return count;   
	}
	
	/**
	 * 获取dst数据
	 */
	public static List<Map<String,Object>> dstData(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		List<Map<String,Object>> list = null;
		try{
			list = mapper.selectForGpsDst(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 新增终端号
	 */
	public int addRadioId(Map<String,Object> param){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		int res = 0;
		try{
			res = mapper.addRadioId(param);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 查询终端号
	 */
	public static int selectRadioId(String radioId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		int res = 0;
		try{
			res = mapper.selectRadioId(radioId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 查询需要显示的终端手台
	 */
	public static List<String> srcVisable(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		List<String> list = null;
		try{
			list = mapper.selectForAllVisable();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询所有手台信息
	 */
	public static List<Map<String,String>> selectForAllVisableStatus(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		List<Map<String,String>> list = null;
		try{
			list = mapper.selectForAllVisableStatus();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据用户查询GIS初始化信息
	 */
	public static String selectForMapInitByUser(String userId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		String str = "";
		try{
			str = mapper.selectForMapInitByUser(userId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 分析后备时长
	 */
	public static List<Map<String,Object>> selectBsOffByTime(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AmapMapper mapper=sqlSession.getMapper(AmapMapper.class);
		List<Map<String,Object>> list = null;
		try{
			list = mapper.selectBsOffByTime(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String,Object>> selectVolWhenPowerOff(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		AmapMapper mapper=sqlSession.getMapper(AmapMapper.class);
		List<Map<String,Object>> list = null;
		try{
			list = mapper.selectVolWhenPowerOff(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String,Object>> selectDataByTime(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		AmapMapper mapper=sqlSession.getMapper(AmapMapper.class);
		List<Map<String,Object>> list = null;
		try{
			list = mapper.selectDataByTime(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String,Object>> selectVolUpdate(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		AmapMapper mapper=sqlSession.getMapper(AmapMapper.class);
		List<Map<String,Object>> res = null;
		try{
			res = mapper.selectVolUpdate(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) {
		List<String> timeList = new LinkedList<String>();
		timeList.add("0小时5分钟");
		timeList.add("1小时25分钟");
		timeList.add("2小时35分钟");
		timeList.add("3小时46分钟");
		String str = AmapController.sumTime(timeList);
		System.out.println(str);
	}

}