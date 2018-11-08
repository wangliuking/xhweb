package xh.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.MplanBean;
import xh.mybatis.mapper.CallListMapper;
import xh.mybatis.mapper.MplanMapper;
import xh.mybatis.tools.MoreDbTools;

public class MplanService {

	public static int add(MplanBean bean) throws Exception {	
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		int rs=mapper.add(bean);
		session.commit();
		session.close();
		return rs;
	}

	public static int update(MplanBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		int rs=mapper.update(bean);
		session.commit();
		session.close();
		
		return rs;
	}

	public static int del(List<String> list) throws Exception {
		
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		int rs=mapper.del(list);
		session.commit();
		session.close();
		return rs;
	}

	public static int count(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_master);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		int count=mapper.count(map);
		session.close();
		
		return count;
	}

	public static List<MplanBean> mplanList(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		List<MplanBean> list=mapper.mplanList(map);
		session.close();
		return list;
	}
	public static List<MplanBean> mplanList_month_one(String time) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		List<MplanBean> list=mapper.mplanList_month_one(time);
		session.close();
		return list;
	}
	public static List<MplanBean> mplanList_month_two(String time) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		MplanMapper mapper=session.getMapper(MplanMapper.class);
		List<MplanBean> list=mapper.mplanList_month_two(time);
		session.close();
		return list;
	}

}
