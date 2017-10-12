package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class WorkServices {
	
	/**
	 * 工作记录
	 * @param map
	 * @return
	 */
	public static ArrayList<Map<String,Object>> worklist(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkMapper mapper=sqlSession.getMapper(WorkMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.worklist(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 工作记录总数
	 * @param map
	 * @return
	 */
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkMapper mapper=sqlSession.getMapper(WorkMapper.class);
		int count=0;
		try {
			count=mapper.count(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}

}
