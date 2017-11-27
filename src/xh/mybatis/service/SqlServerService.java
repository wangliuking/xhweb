package xh.mybatis.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.SqlServerMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.springmvc.handlers.BsStatusController;

public class SqlServerService {
	protected final static Log log = LogFactory.getLog(SqlServerService.class);

	/**
	 * 三期环控列表
	 * 
	 * @param roleId
	 * @return
	 */
	public static HashMap<String, Object> bsmonitorList(int bsId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String table = "tb_Dev";
		if (bsId < 10) {
			table = table + "000" + bsId;
		} else if (bsId >= 10 && bsId < 100) {
			table = table + "00" + bsId;
		} else {
			table = table + "0" + bsId;
		}
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("dbname", table);
		try {
			list = mapper.bsmonitorList(paraMap);			
			for (Map<String, Object> map : list) {
				if(map.get("DevNode").toString().trim().equals("0021")&&map.get("NodeID").toString().equals("1001")){
					result.put("temp", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0021")&&map.get("NodeID").toString().equals("1002")){
					result.put("damp", Float.parseFloat(map.get("value").toString()));
				}
				
				if(map.get("DevNode").toString().trim().equals("0011")&&map.get("NodeID").toString().equals("1001")){
					result.put("smoke", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0011")&&map.get("NodeID").toString().equals("1002")){
					result.put("door", Float.parseFloat(map.get("value").toString())==1?0:1);
				}
				if(map.get("DevNode").toString().trim().equals("0011")&&map.get("NodeID").toString().equals("1003")){
					result.put("water", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0011")&&map.get("NodeID").toString().equals("1004")){
					result.put("red", Float.parseFloat(map.get("value").toString()));
				}
				
				if(map.get("DevNode").toString().trim().equals("0051")&&map.get("NodeID").toString().equals("1001")){
					result.put("lv", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0051")&&map.get("NodeID").toString().equals("1002")){
					result.put("li", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0051")&&map.get("NodeID").toString().equals("1003")){
					result.put("jv", Float.parseFloat(map.get("value").toString()));
				}
				if(map.get("DevNode").toString().trim().equals("0051")&&map.get("NodeID").toString().equals("1004")){
					result.put("ji", Float.parseFloat(map.get("value").toString()));
				}
			}
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 3期某个基站告警列表
	 * @param bsId
	 * @return
	 */
	public static List<Map<String, Object>> bsmonitorAlarmList(int bsId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String table = "tb_Dev";
		if (bsId < 10) {
			table = table + "000" + bsId;
		} else if (bsId >= 10 && bsId < 100) {
			table = table + "00" + bsId;
		} else {
			table = table + "0" + bsId;
		}
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("dbname", table);
		try {
			list = mapper.bsmonitorAlarmList(paraMap);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站交流电断开
	 * @return
	 */
	public static List<Map<String,Object>> bsJiAlarm(){
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		
		try {
			list=mapper.bsJiAlarm(str_date1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * 基站交流电断开数目
	 * @return
	 */
	public static int bsJiAlarmCount(){
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		int count = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		
		try {
			count=mapper.bsJiAlarmCount(str_date1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	/**
	 * 环控告警统计 
	 * @return
	 */
	public static int MapEmhAlarmCount(){
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		int count = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		
		try {
			count=mapper.MapEmhAlarmCount(str_date1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	
	/**
	 * 基站交流电断开数目
	 * @return
	 */
	public static void updateAlarmStatus(){
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", str_date1);
		
		try {
			mapper.updateAlarmStatus(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
