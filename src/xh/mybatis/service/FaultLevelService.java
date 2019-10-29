package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.FaultFourBean;
import xh.mybatis.bean.FaultOneBean;
import xh.mybatis.bean.FaultThreeBean;
import xh.mybatis.mapper.FaultLevelMapper;
import xh.mybatis.mapper.MenuMapper;
import xh.mybatis.tools.MoreDbTools;

public class FaultLevelService {
	protected final static Log log=LogFactory.getLog(FaultLevelService.class);
	public static List<FaultOneBean> one_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		List<FaultOneBean> list=new ArrayList<FaultOneBean>();
		try {
			list=mapper.one_list(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<FaultThreeBean> three_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		List<FaultThreeBean> list=new ArrayList<FaultThreeBean>();
		try {
			list=mapper.three_list(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<FaultThreeBean> excel_three_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		List<FaultThreeBean> list=new ArrayList<FaultThreeBean>();
		try {
			list=mapper.excel_three_list(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<FaultFourBean> four_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		List<FaultFourBean> list=new ArrayList<FaultFourBean>();
		try {
			list=mapper.four_list(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static int one_count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.one_count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int three_count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.three_count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int four_count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.four_count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int one_add(FaultOneBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.one_add(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int one_update(FaultOneBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.one_update(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int three_update(FaultThreeBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.three_update(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int three_update_by_order(FaultThreeBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.three_update_by_order(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int four_add(FaultFourBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.four_add(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int four_update(FaultFourBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.four_update(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int one_del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.one_del(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int three_del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.three_del(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int four_del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		FaultLevelMapper mapper=sqlSession.getMapper(FaultLevelMapper.class);
		int count=0;
		try {
			count=mapper.four_del(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	

}
