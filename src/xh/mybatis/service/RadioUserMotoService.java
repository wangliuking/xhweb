package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.RadioUserBean;
import xh.mybatis.bean.RadioUserMotoBean;
import xh.mybatis.mapper.RadioUserMapper;
import xh.mybatis.mapper.RadioUserMotoMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.TcpKeepAliveClient;

public class RadioUserMotoService {
	public static List<Map<String,Object>> radiouserById(
			Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = mapper.radiouserById(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> radioList() {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = mapper.radioList();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static RadioBean radio_one(String id) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		RadioBean bean = new RadioBean();
		try {
			bean = mapper.radio_one(id);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	public static List<Map<String,Object>> securityGroupList() {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = mapper.securityGroupList();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int radioUserIsExists(int id) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int count = 0;
		try {
			count = mapper.radioUserIsExists(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int count(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int count = 0;
		try {
			count = mapper.count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int insertRadioUser(RadioUserMotoBean bean) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int count = 0;
		try {
			count = mapper.insertRadioUser(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int updateByRadioUserId(RadioUserMotoBean bean) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int count = 0;
		try {
			count = mapper.updateByRadioUserId(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int deleteByRadioUserId(List<String> list) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int count = 0;
		try {
			count = mapper.deleteByRadioUserId(list);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}