package xh.mybatis.service;

import java.net.NoRouteToHostException;
import java.sql.SQLTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
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
			System.out.print("huankong->"+list.size());
			for (Map<String, Object> map : list) {
				System.out.print("huankong->"+map.toString());
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
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		} catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		}catch(NumberFormatException e){
			log.info("三期环控数据NumberFormatException");
			sqlSession.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return result;
	}
	/**
	 * 3期某个基站告警列表  tb_Dev
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("dbname", table);
		paraMap.put("time", str_date1);
		try {
			list = mapper.bsmonitorAlarmList(paraMap);
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		} catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return list;
	}
	/**
	 * 三期所有基站环控告警列表
	 * @param bsId
	 * @return
	 */
	public static List<Map<String, Object>> EmhAlarmList(Map<String,Object> mappara) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date currentTime = new Date();//得到当前系统时间
		String str_date1 = format.format(currentTime); //将日期时间格式化 
		mappara.put("time", str_date1);
		try {
			list = mapper.EmhAlarmList(mappara);
			for(int i=0;i<list.size();i++){
				Map<String, Object> map=list.get(i);
				Map<String, Object> map2=new HashMap<String, Object>();
				String DevName=map.get("DevName").toString();
				if(map.get("DevNode").toString().equals("0011")&&map.get("NodeID").toString().equals("1001")){
					DevName="烟感";
				}
				if(map.get("DevNode").toString().equals("0011")&&map.get("NodeID").toString().equals("1002")){
					DevName="门磁";
				}
				if(map.get("DevNode").toString().equals("0011")&&map.get("NodeID").toString().equals("1003")){
					DevName="水浸";
				}
				if(map.get("DevNode").toString().equals("0011")&&map.get("NodeID").toString().equals("1004")){
					DevName="消防";
				}
				
				
				map2.put("bsId", Integer.parseInt(map.get("JFNode").toString()));
				map2.put("DevName", DevName);
				map2.put("AlarmText", Integer.parseInt(map.get("JFNode").toString())+"-"+map.get("AlarmText"));
				map2.put("time", map.get("AlarmDate").toString().split(" ")[0]+" "+map.get("AlarmTime"));
				list.set(i, map2);
				
			}
		
			
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		}catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
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
			for(int i=0;i<list.size();i++){
				Map<String, Object> map =list.get(i);
				
				String time=map.get("AlarmDate").toString().split(" ")[0]+" "+map.get("AlarmTime").toString();
				map.put("time", time);
				//map.put("name", map.get("name").toString().split(",")[0]);
				map.put("bsId", Integer.parseInt(map.get("bsId").toString()));
				map.remove("AlarmDate");
				map.remove("AlarmTime");
				list.set(i, map);
			}
		
			
			
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		}catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
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
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		}catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
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
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		} catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return count;
		
	}
	
	/**
	 * 更新基站交流电断开数目
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
			sqlSession.commit();
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		}catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		
	}
	
	/**
	 * 查询三期基站环控通断状态 wlk
	 * @param bsId
	 * @return
	 */
	public static List<Map<String, Object>> selectConnectStatusForEMH3() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.sqlServer);
		SqlServerMapper mapper = sqlSession.getMapper(SqlServerMapper.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mapper.selectConnectStatusForEMH3();
			
		}catch(SQLTimeoutException e){
			log.info("获取三期环控数据超时");
			sqlSession.close();
		}catch(NoRouteToHostException e){
			log.info("到主机的TCP/IP连接失败");
			sqlSession.close();
		} catch(PersistenceException e){
			log.info("三期环控数据库连接失败");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlSession.close();
		return list;
	}
	

}
