package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.CallListBean;
import xh.mybatis.mapper.CallListMapper;
import xh.mybatis.mapper.WebUserMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class CallListServices {
	/**
	 * 查询呼叫列表
	 * @param root
	 * @return
	 */
	public static ArrayList<Map<String,Object>> selectCallList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.selectCallList(map);
			sqlSession.close();		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 通话总数
	 * @return
	 * @throws Exception
	 */
	public static int CallListCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		int count=0;
		try {
			count=mapper.CallListCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 话务统计
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> callChartt(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		List<Map<String,Object>> resultMap=new ArrayList<Map<String,Object>>();
		try {
			resultMap=mapper.callChart(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

}
