package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EmhBean;
import xh.mybatis.mapper.EmhMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class EmhService {
	/**
	 * 查询基站环境监控实时状态
	 * @param code
	 * @return
	 */
	public static List<EmhBean> oneBsEmh(String code) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.emh);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<EmhBean> list = new ArrayList<EmhBean>();
		try {
			list = mapper.oneBsEmh(code);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控实时告警列表
	 * @param map
	 * @return
	 */
	public static List<HashedMap<String, Object>> bsEmhNowStatus(Map<String,Object> map) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<HashedMap<String, Object>> list = new ArrayList<HashedMap<String, Object>>();
		try {
			list = mapper.bsEmhNowStatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控实时告警列表条目
	 * @param map
	 * @return
	 */
	public static int bsEmhNowStatusCount() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.bsEmhNowStatusCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 基站环境监控历史告警列表
	 * @param map
	 * @return
	 */
	public static List<HashedMap<String, Object>> bsEmhOldStatus(Map<String,Object> map) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<HashedMap<String, Object>> list = new ArrayList<HashedMap<String, Object>>();
		try {
			list = mapper.bsEmhOldStatus(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站环境监控历史告警列表条目
	 * @param map
	 * @return
	 */
	public static int bsEmhOldStatusCount() {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		int count=0;
		try {
			count= mapper.bsEmhOldStatusCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
