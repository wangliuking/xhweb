package xh.mybatis.service;

import java.util.ArrayList;
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

}
