package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.OndutyBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.mapper.OndutyMapper;
import xh.mybatis.mapper.WebLogMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;
import xh.springmvc.handlers.LoginController;

public class OndutyService {
	protected final static Log log=LogFactory.getLog(OndutyService.class);
	/**
	 * 查询日志记录
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> duty_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OndutyMapper mapper=sqlSession.getMapper(OndutyMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=mapper.duty_list(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Map<String,Object> onduty(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OndutyMapper mapper=sqlSession.getMapper(OndutyMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			map=mapper.onduty();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public static int count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OndutyMapper mapper=sqlSession.getMapper(OndutyMapper.class);
		int count=0;
		try{
			count=mapper.count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int write_data(List<OndutyBean> bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OndutyMapper mapper=sqlSession.getMapper(OndutyMapper.class);
		int count=0;
		try{
			count=mapper.write_data(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
