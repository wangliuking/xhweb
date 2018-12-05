package xh.mybatis.service;
 
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.DispatchBean;
import xh.mybatis.mapper.DispatchStatusMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.org.listeners.SingLoginListener;

public class DispatchStatusService {
	protected final static Log log = LogFactory.getLog(DispatchStatusService.class);
	/**
	 * 调度台列表
	 * @return
	 */
	public static List<Map<String,Object>> dispatchstatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,List<Map<String,Object>>> map=new HashMap<String, List<Map<String,Object>>>();
			
		List<Map<String,Object>> rsList=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.dispatchstatus();
			for (Map<String, Object> status : list) {
				List<Map<String, Object>> staList=map.get(status.get("type"));
				if(staList==null){
					staList=new ArrayList<Map<String,Object>>();
				}
				staList.add(status);
				
				map.put(status.get("type").toString(), staList);
			}
			map=sortMapByKey(map); 
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				
				
				
				Map.Entry entry=(Map.Entry) it.next();
				Object key = entry.getKey();
				Object value=entry.getValue();
				Map<String, Object> map2=new HashMap<String, Object>();
				map2.put("type", key);
				map2.put("items",value);
				
				rsList.add(map2);

			}
			
			
			
			
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsList;
	}
	public static List<Map<String,Object>> dispatchstatus_list(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
			
		List<Map<String,Object>> rsList=new ArrayList<Map<String,Object>>();
		try {
			rsList = mapper.dispatchstatus();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsList;
	}
	public static List<Map<String,Object>> dispatchSetup(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
			
		List<Map<String,Object>> rsList=new ArrayList<Map<String,Object>>();
		try {
			rsList = mapper.dispatchSetup();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsList;
	}
	/**
	 * 调度台断开报警
	 * @return
	 */
	public static List<Map<String, Object>> dispatchOffAlarm(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.dispatchOffAlarm();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/**
	 * 调度台断开报警数目
	 * @return
	 */
	
	public static int dispatchOffAlarmCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		int count=0;
		try {
			count = mapper.dispatchOffAlarmCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/**
	 * 解除调度台掉线告警
	 */
	public static void updateDispatchAlarmStatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		try {
			mapper.updateDispatchAlarmStatus();
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 调度台是否存在
	 * @param dstId
	 * @return
	 */
	public static int dispatchExists(int dstId){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		int rsl=0;
		try {
			rsl=mapper.dispatchExists(dstId);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsl;
	}
	/**
	 * 增加调度台
	 * @param bean
	 * @return
	 */
	public static int addDispatch(DispatchBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		int rsl=0;
		try {
			rsl=mapper.addDispatch(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsl;
	}
	/**
	 * 修改调度台
	 * @param bean    
	 * @return
	 */
	public static int updateDispatch(DispatchBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		int rsl=0;
		try {
			rsl=mapper.updateDispatch(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsl;
	}
	/**
	 * 删除调度台
	 * @param list
	 * @return
	 */
	public static int deleteDispatch(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		int rsl=0;
		try {
			rsl=mapper.deleteDispatch(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return rsl;
	}
	
	//ping 调度台
	public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  2000 ;  //超时应该在2钞以上        
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        int index=0;
        while(status==false && index<3){
        	status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        	index++;
        	//System.out.println("ping失败，重试！");
        }
        
        return status;
    }
	public static void changePingStatus() throws Exception{
    	SqlSession session= MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
    	DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
    	List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    	try {
			list = mapper.dispatchstatus();
			for(int i=0;i<list.size();i++){
				Map<String, Object> map=list.get(i);
				int setupStatus=Integer.parseInt(map.get("setupStatus").toString());			
				if(setupStatus==1){
					boolean tag=ping(map.get("IP").toString());
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("dstId", map.get("dstId").toString());
					map2.put("flag", tag?1:0);
					//System.out.println("IP->"+map.get("IP").toString()+"::"+tag);
					mapper.updateDispatchStatus(map2);
				}
				
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, List<Map<String, Object>>> sortMapByKey(Map<String, List<Map<String, Object>>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, List<Map<String, Object>>> sortMap = new TreeMap<String, List<Map<String, Object>>>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

}
class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {
        
        return str1.compareTo(str2);
    }
}
