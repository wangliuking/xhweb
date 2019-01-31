package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.RadioUserBean;
import xh.mybatis.mapper.RadioUserMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.TcpKeepAliveClient;

public class RadioUserService {
	/**
	 * 查询无线用户信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String, String>> radiouserById(
			Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
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
	 * 根据无线用户ID判断用户是否存在
	 * 
	 * @param id
	 * @return
	 */
	public static int radioUserIsExists(int id) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
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

	/**
	 * 无线用户总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int radiouserCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
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
	public static int insertRadioUser(RadioUserBean bean) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
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

	/**
	 * 修改无线用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int updateByRadioUserId(HashMap<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
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
	public static int deleteBsByRadioUserId(List<String> list) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int code=0;
		try {
			code=mapper.deleteBsByRadioUserId(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	public static List<HashMap> allRadioUser(String code) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.emh);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.allRadioUser();
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据vpnId查询用户
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String, String>> allByVpnId(
			Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			list = mapper.allByVpnId(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据vpnId查询总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int CountByVpnId(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int count = 0;
		try {
			count = mapper.CountByVpnId(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 根据vpnId查询所有无线用户id
	 * 
	 * @param root
	 * @return
	 */
	public static List<String> selectCIdByVpnId(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		List<String> list = new ArrayList<String>();
		try {
			list = mapper.selectCIdByVpnId(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 标记moto手台
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int update_moto(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMapper mapper = sqlSession.getMapper(RadioUserMapper.class);
		int count = 0;
		try {
			count = mapper.update_moto(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
