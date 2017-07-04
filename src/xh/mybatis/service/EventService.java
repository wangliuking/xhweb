package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.EventMapper;
import xh.mybatis.tools.MoreDbTools;

public class EventService {
	/**
	 * 查询事件
	 * @param map
	 * @return
	 */
	public static List<HashMap> selectEvent(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.mysqlDb);
		EventMapper mapper=sqlSession.getMapper(EventMapper.class);
		List<HashMap> list=new ArrayList<HashMap>();
		try{
			list=mapper.selectEvent();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 删除事件
	 * @param name
	 * @return
	 */
	public static int deleteEvent(String name){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.mysqlDb);
		EventMapper mapper=sqlSession.getMapper(EventMapper.class);
		int rsl=0;
		try{
			rsl=mapper.deleteEvent(name);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsl;
	}
	/**
	 * 添加事件
	 * @param map
	 * @return
	 */
	public static int insertEvent(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.mysqlDb);
		EventMapper mapper=sqlSession.getMapper(EventMapper.class);
		int rsl=0;
		try{
			mapper.insertEvent(map);
			sqlSession.close();
			rsl=1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsl;
	}

}
