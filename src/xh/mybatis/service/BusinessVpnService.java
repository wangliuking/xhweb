package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.VpnMapper;
import xh.mybatis.tools.MoreDbTools;

public class BusinessVpnService {
	/**
	 * 查询
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> assetInfo(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		VpnMapper mapper=sqlSession.getMapper(VpnMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.selectAllName();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 总数
	 * @param root
	 * @return
	 */
	public static void assetInfoCount(Map<String,Object> map){
		
	}
	/**
	 * 删除
	 */
	public static int deleteByVpnId(String vpnId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		VpnMapper mapper=sqlSession.getMapper(VpnMapper.class);
		int count = 0;
		try {
			count = mapper.deleteByVpnId(vpnId);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 更新
	 */
	public static int updateByVpnId(String vpnId,String name){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		VpnMapper mapper=sqlSession.getMapper(VpnMapper.class);
		int count = 0;
		try {
			count = mapper.updateByVpnId(vpnId, name);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
}
