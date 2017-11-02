package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WorkBean;
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
	
	/**
	 * 新增工作记录
	 * @param bean
	 * @return
	 */
	public static int addwork(WorkBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkMapper mapper=sqlSession.getMapper(WorkMapper.class);
		int result=0;
		try {
			result=mapper.addwork(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

	/**
	 * 签收工作记录
	 * @param bean
	 * @return
	 */
	public static int signWork(int id){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkMapper mapper=sqlSession.getMapper(WorkMapper.class);
		WorkBean bean=new WorkBean();
		FunUtil fun=new FunUtil();
		bean.setId(id);
		bean.setSignTime(fun.nowDate());
		int result=0;
		try {
			result=mapper.signWork(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

}
