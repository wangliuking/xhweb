package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.OrderMapper;
import xh.mybatis.tools.MoreDbTools;

public class OrderService {
	
	//派单列表
	public static List<Map<String,Object>> orderList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=mapper.orderList(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//派单列表中暑
	public static int orderListCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
		int count=0;
		try{
			count=mapper.orderListCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
