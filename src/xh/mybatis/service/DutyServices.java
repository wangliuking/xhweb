package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.DutyMapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class DutyServices {
	
	/**
	 * 运维值班情况表
	 * @param map
	 * @return
	 */
	public static ArrayList<Map<String,Object>> list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DutyMapper mapper=sqlSession.getMapper(DutyMapper.class);
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
	 * 运维值班情况表总数
	 * @param map
	 * @return
	 */
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DutyMapper mapper=sqlSession.getMapper(DutyMapper.class);
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
	 * 新增运维值班情况表
	 * @param bean
	 * @return
	 */
	public static int add(WorkBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DutyMapper mapper=sqlSession.getMapper(DutyMapper.class);
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
	 * 签收运维值班情况表
	 * @param bean
	 * @return
	 */
	public static int sign(int id){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DutyMapper mapper=sqlSession.getMapper(DutyMapper.class);
		WorkBean bean=new WorkBean();
		FunUtil fun=new FunUtil();
		bean.setId(id);
		
		bean.setSignTime(fun.nowDate());
		int result=0;
		try {
			result=mapper.sign(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}

}
