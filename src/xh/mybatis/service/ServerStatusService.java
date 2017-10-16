package xh.mybatis.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.ServerStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class ServerStatusService {
	public static List<Map<String, String>> serverstatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		try {
			list = mapper.serverstatus();
			/*for(int i=0;i<list.size();i++){
				Map<String, String> map=list.get(i);
				DecimalFormat df = new DecimalFormat("#.00");
				double f=Double.parseDouble(map.get("dpan").toString());			
				map.put("dpan", df.format(f));
				System.out.println("map===>"+map);
				list.set(i, map);
			}*/
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}

}
