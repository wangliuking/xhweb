package xh.mybatis.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.constant.ConstantMap;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.RadioUserBean;
import xh.mybatis.bean.RadioUserMotoBean;
import xh.mybatis.mapper.RadioUserMapper;
import xh.mybatis.mapper.RadioUserMotoMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.SendData;
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
	public static Map<String,Object> insertRadioUser(RadioUserMotoBean bean,xh.protobuf.RadioUserBean userbean) throws Exception {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int rs = 0;
		
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(MotoTcpClient.getSocket().isConnected()){
			SendData.RadioUserAddReq(userbean);
			long nowtime=System.currentTimeMillis();
			Tag:for(;;){
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(userbean.getCallId())){
					String v=ConstantMap.getMotoResultMap().get(userbean.getCallId()).toString();
					if(v.toLowerCase().equals("ok")){
						try {
							rs=mapper.insertRadioUser(bean);
							rsmap.put("rs", rs);
							sqlSession.commit();
							sqlSession.close();
							break Tag;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break Tag;
						}
					}else{
						rsmap.put("rs", rs);
						rsmap.put("errMsg",ConstantMap.getMotoResultMap().get(userbean.getCallId()+"-info"));
					}
					ConstantMap.getMotoResultMap().remove(userbean.getCallId());
					ConstantMap.getMotoResultMap().remove(userbean.getCallId()+"-info");
				}else{
					if(tt-nowtime>5000){
						rsmap.put("rs", rs);
						rsmap.put("errMsg","返回数据超时");
						break Tag;
					}
				}
			}
		}else{
			rsmap.put("rs", rs);
			rsmap.put("errMsg","连接服务失败");
		}
	
		return rsmap;
	}
	public static int vAdd(RadioUserMotoBean bean) throws Exception {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int rs = 0;
		try {
			rs=mapper.insertRadioUser(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return rs;
	}
	public static Map<String,Object> updateByRadioUserId(RadioUserMotoBean bean,xh.protobuf.RadioUserBean userbean)throws Exception {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int rs = 0;
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(MotoTcpClient.getSocket().isConnected()){
			SendData.RadioUserUpdateReq(userbean);
			long nowtime=System.currentTimeMillis();
			Tag:for(;;){
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(userbean.getCallId())){
					String v=ConstantMap.getMotoResultMap().get(userbean.getCallId()).toString();
					if(v.toLowerCase().equals("ok")){
						try {
							rs=mapper.updateByRadioUserId(bean);
							rsmap.put("rs", rs);
							sqlSession.commit();
							sqlSession.close();
							break Tag;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break Tag;
						}
					}else{
						rsmap.put("rs", rs);
						rsmap.put("errMsg",ConstantMap.getMotoResultMap().get(userbean.getCallId()+"-info"));
					}
					ConstantMap.getMotoResultMap().remove(userbean.getCallId());
					ConstantMap.getMotoResultMap().remove(userbean.getCallId()+"-info");
				}else{
					if(tt-nowtime>5000){
						rsmap.put("rs", rs);
						rsmap.put("errMsg","返回数据超时");
						break Tag;
					}
				}
			}
		}else{
			rsmap.put("rs", rs);
			rsmap.put("errMsg","连接服务失败");
		}
		
		return rsmap;
	}
	public static Map<String,Object> deleteByRadioUserId(xh.protobuf.RadioUserBean bean) throws Exception {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioUserMotoMapper mapper = sqlSession.getMapper(RadioUserMotoMapper.class);
		int rs = 0;
		String[] ids=bean.getRadioUserAlias().split(",");
         List<String> list=new ArrayList<String>();
		
		for (String string : ids) {
			list.add(string);
		}
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(MotoTcpClient.getSocket().isConnected()){
			SendData.RadioUserDelReq(bean);
			long nowtime=System.currentTimeMillis();
			Tag:for(;;){
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					String v=ConstantMap.getMotoResultMap().get(bean.getCallId()).toString();
					if(v.toLowerCase().equals("ok")){
						try {
							rs = mapper.deleteByRadioUserId(list);
							sqlSession.commit();
							sqlSession.close();
							rsmap.put("rs", rs);
							break Tag;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break Tag;
						}
					}else{
						rsmap.put("rs", rs);
						rsmap.put("errMsg",ConstantMap.getMotoResultMap().get(bean.getCallId()+"-info"));
					}
					ConstantMap.getMotoResultMap().remove(bean.getCallId());
					ConstantMap.getMotoResultMap().remove(bean.getCallId()+"-info");
				}else{
					if(tt-nowtime>5000){
						rsmap.put("rs", rs);
						rsmap.put("errMsg","返回数据超时");
						break Tag;
					}
				}
			}
		}else{
			rsmap.put("rs", rs);
			rsmap.put("errMsg","连接服务失败");
		}
		
		return rsmap;
	}

}