package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.TalkGroupBean;
import xh.mybatis.mapper.TalkGroupMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.socket.TalkGroupStruct;
import xh.org.socket.TcpKeepAliveClient;

public class TalkGroupService {
	/**
	 * 查询通话组信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radioUserBusinessInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.ById(map);
			sqlSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询通话组信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> vpnList() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.vpnList();
			sqlSession.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取虚拟专网属性
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> vaList() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.vaList();
			sqlSession.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询通话组信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> vpnList2() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.vpnList2();
			sqlSession.close();
			
		} catch (Exception e) {
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
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
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
	 * 根据通话组ID判断组是否存在
	 * @param id
	 * @return
	 */
	public static int isExists(int id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		int count = 0;
		try {
			count = mapper.isExists(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据ID获取组名称
	 * @param id
	 * @return
	 */
	public static String GroupNameById(int id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		String groupName="";
		try {
			groupName = mapper.GroupNameById(id);
			sqlSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupName;
	}
	
	/**
	 * 添加组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int insertTalkGroup(TalkGroupBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		int count = 0;
		try {
			count=mapper.insertTalkGroup(bean);
			/*if(count>0 && TcpKeepAliveClient.getSocket().isConnected()){
				TalkGroupStruct setTalkGroupData = new TalkGroupStruct();
				setTalkGroupData.setOperation(1);
				setTalkGroupData.setId(bean.getId());
				setTalkGroupData.setName(bean.getE_name());
				setTalkGroupData.setAlias(bean.getE_alias());
				setTalkGroupData.setMscId(bean.getE_mscId());
				setTalkGroupData.setVpnId(bean.getE_vpnId());
				setTalkGroupData.setSaId(bean.getE_said());
				setTalkGroupData.setIaId(bean.getE_iaid());
				setTalkGroupData.setVaId(bean.getE_vaid());
				setTalkGroupData.setPreempt(bean.getE_preempt());
				setTalkGroupData.setRadioType(Integer.parseInt(bean.getE_radioType()));
				setTalkGroupData.setRegroupAble(Integer.parseInt(bean.getE_regroupable()));
				setTalkGroupData.setEnabled(bean.getE_enabled());
				setTalkGroupData.setDirectDial(bean.getE_directDial());
				UcmService.sendTalkGroupData(setTalkGroupData);
			}*/
			
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 修改通话组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int update(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
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
	 * 删除通话组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void delete(List<String> list) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
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
