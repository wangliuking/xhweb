package xh.mybatis.service;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.DispatchStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class DispatchStatusService {
	public static List<Map<String, String>> dispatchstatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		try {
			list = mapper.dispatchstatus();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	//ping 调度台
	public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  5000 ;  //超时应该在3钞以上        
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
	public static void changePingStatus() throws Exception{
    	SqlSession session= MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
    	DispatchStatusMapper mapper = session.getMapper(DispatchStatusMapper.class);
    	List<Map<String, String>> list=new ArrayList<Map<String,String>>();
    	try {
			list = mapper.dispatchstatus();
			for(int i=0;i<list.size();i++){
				Map<String, String> map=list.get(i);
				int flag=0;
				if(ping(map.get("IP").toString())){
					flag=1;
				}else{
					flag=0;
				}
				Map<String, String> map2=new HashMap<String, String>();
				map2.put("dstId", map.get("dstId"));
				map2.put("flag", String.valueOf(flag));
				mapper.updateDispatchStatus(map2);
				
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }

}
