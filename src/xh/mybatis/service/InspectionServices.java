package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.InspectionBean;
import xh.mybatis.mapper.InspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class InspectionServices {
	
	/**
	 * 运维巡检记录表
	 * @param map
	 * @return
	 */
	public static ArrayList<Map<String,Object>> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.list(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 运维巡检记录表总数
	 * @param map
	 * @return
	 */
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
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
	
	/**
	 * 新增运维巡检记录表
	 * @param bean
	 * @return
	 */
	public static int add(InspectionBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		int result=0;
		try {
			result=mapper.add(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

	/**
	 * 签收运维巡检记录表
	 * @param bean
	 * @return
	 */
	public static int sign(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		int result=0;
		try {
			result=mapper.sign(map);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

}
