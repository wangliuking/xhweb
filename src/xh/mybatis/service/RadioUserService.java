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
			if (count > 0 && TcpKeepAliveClient.getSocket().isConnected()) {
				RadioUserStruct setRadioUser = new RadioUserStruct();
				setRadioUser.setOperation(1);
				setRadioUser.setId(bean.getC_ID());
				setRadioUser.setName(bean.getE_name());
				setRadioUser.setAlias(bean.getE_alias());
				setRadioUser.setMscId(bean.getE_mscId());
				setRadioUser.setVpnId(bean.getE_vpnId());
				setRadioUser.setSn(bean.getE_sn());
				setRadioUser.setCompany(bean.getE_company());
				setRadioUser.setType(bean.getE_type());
				setRadioUser.setEnabled(bean.getE_enabled());
				setRadioUser.setShortData(bean.getE_shortData());
				setRadioUser.setFullDuple(bean.getE_fullDuple());
				setRadioUser.setRadioType(bean.getE_radioType());
				setRadioUser.setAnycall(bean.getE_anycall());
				setRadioUser.setSaId(bean.getE_saId());
				setRadioUser.setIaId(bean.getE_iaId());
				setRadioUser.setVaId(bean.getE_vaId());
				setRadioUser.setRugId(bean.getE_rutgId());
				setRadioUser.setPacketData(bean.getE_packetData());
				setRadioUser.setIp(bean.getE_ip());
				setRadioUser.setPrimaryTGId(bean.getE_PrimaryTGId());
				setRadioUser.setAmbienceMonitoring(bean.getE_ambienceMonitoring());
				setRadioUser.setAmbienceInitiation(bean.getE_ambienceInitiation());
				setRadioUser.setDirectDial(bean.getE_directDial());
				setRadioUser.setPstnAccess(bean.getE_PSTNAccess());
				setRadioUser.setPabxAccess(bean.getE_pabxAccess());
				setRadioUser.setClir(bean.getE_clir());
				setRadioUser.setClirOverride(bean.getE_clirOverride());
				setRadioUser.setKilled(bean.getE_killed());
				setRadioUser.setMsType(bean.getE_msType());
				UcmService.sendRadioUser(setRadioUser);
			}
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
	public static void deleteBsByRadioUserId(List<String> list) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
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

}
