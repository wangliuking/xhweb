package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.UserStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class UserStatusService {
	protected final static Log log=LogFactory.getLog(UserStatusService.class);
	
	/**
	 * 终端状态
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> userstatus(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		UserStatusMapper mapper=sqlSession.getMapper(UserStatusMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=mapper.userstatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 终端状态数量
	 * @param map
	 * @return
	 */
	public static int userstatusCount(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		UserStatusMapper mapper=sqlSession.getMapper(UserStatusMapper.class);
		int rlt=0;
		try{
			rlt=mapper.userstatusCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}
	/**
	 * 终端在线状态统计
	 * @return
	 */
	public static List<HashMap> userStatusByChart() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		UserStatusMapper mapper = sqlSession.getMapper(UserStatusMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.userStatusByChart();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int userOnline() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		UserStatusMapper mapper = sqlSession.getMapper(UserStatusMapper.class);
		int count = 0;
		try {
			count = mapper.userOnline();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
