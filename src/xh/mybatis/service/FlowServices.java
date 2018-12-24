package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.FlowMapper;
import xh.mybatis.mapper.Top10Mapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class FlowServices {
	
	public static ArrayList<Map<String,Object>> month_user_flow(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		FlowMapper mapper=sqlSession.getMapper(FlowMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.month_user_flow(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}

}
