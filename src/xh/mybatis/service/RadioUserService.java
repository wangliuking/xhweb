package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.RadioUserMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioUserService {
	/**
	 * 查询无线用户信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radiouserById(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.radiouserById(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 无线用户总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int radiouserCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int count = 0;
		try {
			count = mapper.radiouserCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 添加无线用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int insertRadioUser(HashMap<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int count = 0;
		try {
			//count = mapper.selectByBsId(bean.getBsId());
			if (count == 0) {
				mapper.insertRadioUser(map);
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 修改无线用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int updateByRadioUserId(HashMap<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int count = 0;
		try {
			count = mapper.updateByRadioUserId(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除无线用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void deleteBsByRadioUserId(List<String> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		try {
			mapper.deleteBsByRadioUserId(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<HashMap> allRadioUser(String code) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.emh);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.allRadioUser();
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


}
