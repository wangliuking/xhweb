package xh.mybatis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tcpBean.ErrProTable;

import xh.mybatis.bean.OrderDataBean;
import xh.mybatis.mapper.OrderMapper;
import xh.mybatis.tools.MoreDbTools;

public class OrderService {
	
	//派单列表
	public static List<OrderDataBean> orderList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
		List<OrderDataBean> list=new ArrayList<OrderDataBean>();
		try{
			list=mapper.orderList(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//运维用户列表
	public static List<Map<String,Object>> userList(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=mapper.userList();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//郊县维护组用户列表
	public static List<Map<String,Object>> jxuserList(){
			SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
			OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
			try{
				list=mapper.userList();
				if(list.size()>0){
					for (Map<String, Object> map : list) {
						if(map.get("role").toString().contains("郊县维护组")){
							list2.add(map);
						}
					}
				}
				sqlSession.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list2;
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
	public static int del(List<Integer> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
		int count=0;
		try{
			count=mapper.del(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	//新增派单
	public static int addOrder(ErrProTable bean){
			SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
			OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
			int count=0;
			try{
				count=mapper.addOrder(bean);
				sqlSession.commit();
				sqlSession.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
	}
	//更新派单
		public static int updateOrderData(ErrProTable bean){
				SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
				OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
				int count=0;
				try{
					count=mapper.updateOrderData(bean);
					sqlSession.commit();
					sqlSession.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return count;
		}
	//更新四方伟业告警状态
	public static int updateSfOrder(ErrProTable bean){
			SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
			OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
			int count=0;
			System.out.println("=============：测试更新四方告警"+bean.toString());
			try{
				count=mapper.updateSfOrder(bean);
				sqlSession.commit();
				sqlSession.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
	}
	//修改确认派单
	public static int updateOrder(Map<String,Object> map){
			SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
			OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
			int count=0;
			try{
				count=mapper.updateOrder(map);
				sqlSession.commit();
				sqlSession.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
	}
	//更新基站故障状态记录
	public static int updateBsFault(Map<String,Object> map){
			SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
			OrderMapper mapper=sqlSession.getMapper(OrderMapper.class);
			int count=0;
			try{
				count=mapper.updateBsFault(map);
				sqlSession.commit();
				sqlSession.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
	}
	
	//根据流水号查询派单信息
	public static Map<String, Object> selectBySerialnumber(String serialnumber) {
		SqlSession sqlSession = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			map = mapper.selectBySerialnumber(serialnumber);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
