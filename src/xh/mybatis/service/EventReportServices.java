package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EventReportBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.EventReportMapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class EventReportServices {
	
	/**
	 * 事件上报
	 * @param map
	 * @return
	 */
	public static ArrayList<EventReportBean> eventReportlist(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EventReportMapper mapper=sqlSession.getMapper(EventReportMapper.class);
		ArrayList<EventReportBean> list=new ArrayList<EventReportBean>();
		try {
			list=mapper.eventReportlist(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 事件上报总数
	 * @param map
	 * @return
	 */
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EventReportMapper mapper=sqlSession.getMapper(EventReportMapper.class);
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
	 * 新增事件上报
	 * @param bean
	 * @return
	 */
	public static int addEventReport(EventReportBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		EventReportMapper mapper=sqlSession.getMapper(EventReportMapper.class);
		int result=0;
		try {
			result=mapper.addEventReport(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

	/**
	 * 签收事件上报
	 * @param bean
	 * @return
	 */
	public static int signEventReport(Map<String,Object> modeMap){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		EventReportMapper mapper=sqlSession.getMapper(EventReportMapper.class);
		int result=0;
		try {
			result=mapper.signEventReport(modeMap);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

}
