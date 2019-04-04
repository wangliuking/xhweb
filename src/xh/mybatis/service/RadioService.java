package xh.mybatis.service;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.constant.ConstantMap;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.mapper.RadioMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.SendData;

public class RadioService {
	protected  static  Log log=LogFactory.getLog(RadioService.class);
	public List<RadioBean> list(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		List<RadioBean> list=new ArrayList<RadioBean>();
		try {
			list=mapper.list(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int count(Map<String,Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int count=0;
		try {
			count=mapper.count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public Map<String,Object> add(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("RadioID", bean.getRadioID());
		map.put("RadioSerialNumber", bean.getRadioSerialNumber());
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(count(map)==0){
			if(MotoTcpClient.getSocket().isConnected()){
				SendData.RadioAddReq(bean);
				long nowtime=System.currentTimeMillis();
				Tag:for(;;){
					long tt=System.currentTimeMillis();
					if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
						String v=ConstantMap.getMotoResultMap().get(bean.getCallId()).toString();
						if(v.toLowerCase().equals("ok")){
							try {
								rs=mapper.add(bean);
								rsmap.put("rs", rs);
								session.commit();
								session.close();
								break Tag;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break Tag;
							}
						}
						else{
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
		}else{
			rsmap.put("rs", -1);
		}
		
		return rsmap;
	}
	public int vAdd(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("RadioID", bean.getRadioID());
		map.put("RadioSerialNumber", bean.getRadioSerialNumber());
		if(count(map)==0){
			try {
				rs=mapper.add(bean);
				session.commit();
				session.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rs;
	}
	public Map<String,Object> update(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(MotoTcpClient.getSocket().isConnected()){
			SendData.RadioUpdateReq(bean);
			long nowtime=System.currentTimeMillis();
			Tag:for(;;){
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					String v=ConstantMap.getMotoResultMap().get(bean.getCallId()).toString();
					if(v.toLowerCase().equals("ok")){
						try {
							rs=mapper.update(bean);
							session.commit();
							rsmap.put("rs", rs);
							session.close();
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
	public Map<String,Object> delete(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		String[] ids=bean.getRadioID().split(",");
		List<String> list=new ArrayList<String>();
		
		for (String string : ids) {
			list.add(string);
		}
		int rs=0;
		Map<String,Object> rsmap=new HashMap<String, Object>();
		if(MotoTcpClient.getSocket().isConnected()){
			SendData.RadioDelReq(bean);
			long nowtime=System.currentTimeMillis();
			Tag:for(;;){
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					String v=ConstantMap.getMotoResultMap().get(bean.getCallId()).toString();
					if(v.toLowerCase().equals("ok")){
						try {
							rs=mapper.delete(list);
							rsmap.put("rs", rs);
							session.commit();
							session.close();
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
