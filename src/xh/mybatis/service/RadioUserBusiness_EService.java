package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.RadioUserBusiness_EMapper;
import xh.mybatis.mapper.RadioUserSeriaMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioUserBusiness_EService {
	/**
	 * 查询无线用户业务信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radioUserBusinessInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserBusiness_EMapper mapper = sqlSession.getMapper(RadioUserBusiness_EMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.ById(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int Count(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserBusiness_EMapper mapper = sqlSession.getMapper(RadioUserBusiness_EMapper.class);
		int count = 0;
		try {
			count = mapper.Count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int insert(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserBusiness_EMapper mapper = sqlSession
				.getMapper(RadioUserBusiness_EMapper.class);
		int count = 0;
		try {
			count = mapper.insert(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int update(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserBusiness_EMapper mapper = sqlSession
				.getMapper(RadioUserBusiness_EMapper.class);
		int count = 0;
		try {
			count = mapper.update(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void delete(List<String> list) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserBusiness_EMapper mapper = sqlSession
				.getMapper(RadioUserBusiness_EMapper.class);
		try {
			mapper.delete(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
