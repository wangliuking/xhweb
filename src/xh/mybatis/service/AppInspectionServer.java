package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.mapper.AppInspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class AppInspectionServer {
	
	/*<!--查询800M移动基站巡检表-->*/
	public static List<Map<String, Object>> mbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.mbsinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--查询800M移动基站巡检表总数-->*/
	public static int mbsinfoCount(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbsinfoCount();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}

}
