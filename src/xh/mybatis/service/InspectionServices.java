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
	 * 填写巡检记录相关信息，汇总上报项目负责人
	 * @param bean
	 * @return
	 */
	public static int check2(InspectionBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		int result=0;
		try {
			result=mapper.check2(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 抢修组将抢修情况汇总记录到平台，并发送消息通知巡检组
	 * @param bean
	 * @return
	 */
	public static int check3(InspectionBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		int result=0;
		try {
			result=mapper.check3(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 巡检组整理填写巡检记录相关信息，汇总上报项目负责人，流程结束
	 * @param bean
	 * @return
	 */
	public static int check4(InspectionBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper=sqlSession.getMapper(InspectionMapper.class);
		int result=0;
		try {
			result=mapper.check4(bean);
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
