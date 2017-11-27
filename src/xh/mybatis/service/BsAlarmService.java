package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.mapper.BsAlarmMapper;
import xh.mybatis.mapper.UserStatusMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsAlarmService {

	/**
	 * 查询所有告警信息
	 */
	public List<BsAlarmBean> selectAllBsAlarm() throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectAllBsAlarm();
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}
	
	/**
	 * 条件查询告警信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BsAlarmBean> selectBsAlarmList(Map<String,Object> map) throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectBsAlarmList(map);
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}
	
	/**
	 * 告警总数
	 * @return
	 */
	public static int BsAlarmCount(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.BsAlarmCount(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 告警等级统计
	 * @return
	 */
	public static List<HashMap> bsAlarmLevelChart() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmLevelChart();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 告警类型统计
	 * @return
	 */
	public static List<HashMap> bsAlarmTypeChart() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmTypeChart();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 确认告警信息
	 * @param map
	 */
	public static void identifyBsAlarmById(String id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.identifyBsAlarmById(id);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
