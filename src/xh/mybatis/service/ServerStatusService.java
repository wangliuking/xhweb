package xh.mybatis.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.mapper.ServerStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class ServerStatusService {
	public static List<Map<String,Object> > serverstatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.serverstatus();
		
		   for(int i=0;i<list.size();i++){
			   Map<String,Object> map=list.get(i);
				DecimalFormat df = new DecimalFormat("#0.00");	

				double  a=Double.parseDouble(map.get("diskSize").toString());
				double b=Double.parseDouble(map.get("diskUsed").toString());
				
				double c=Double.parseDouble(map.get("memSize").toString());
				double d=Double.parseDouble(map.get("memUsed").toString());
				
				int r1=(new Double((b/a)*100)).intValue();
				int r2=(new Double((d/c)*100)).intValue();
				
				map.put("diskper",r1);
				map.put("memper", r2);
				list.set(i, map);
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站传输状态
	 * @return
	 */
	public static List<Map<String,Object> > icpStatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.icpStatus();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 交换中心异常
	 * @return
	 */
	public static List<Map<String,Object> > unusualStatus(int type){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		List<Map<String,Object>> list=serverstatus();
		List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			   Map<String,Object> map=list.get(i);
			   Map<String,Object> map2=new HashMap<String, Object>();
			  
			   
			   if(Integer.parseInt(map.get("status").toString())==1 || 
				Integer.parseInt(map.get("diskper").toString())>Integer.parseInt(FunUtil.readXml("web", "disk")) || 
				Integer.parseInt(map.get("memper").toString())>Integer.parseInt(FunUtil.readXml("web", "mem"))|| 
				Integer.parseInt(map.get("cpuLoad").toString())>Integer.parseInt(FunUtil.readXml("web", "cpu"))){
				   String text="";	   
				   if(Integer.parseInt(map.get("status").toString())==1){
					   text=map.get("name").toString()+";链路中断，服务器访问失败";
				   }else{
					   if(Integer.parseInt(map.get("diskper").toString())>Integer.parseInt(FunUtil.readXml("web", "disk"))){
						   text+="磁盘空间占用率"+map.get("diskper").toString()+"% ; ";
					   }
					   if(Integer.parseInt(map.get("memper").toString())>Integer.parseInt(FunUtil.readXml("web", "mem"))){
						   text+="内存占用率"+map.get("memper").toString()+"% ; ";
					   }
					   if(Integer.parseInt(map.get("cpuLoad").toString())>Integer.parseInt(FunUtil.readXml("web", "cpu"))){
						   text+="CPU占用率"+map.get("cpuLoad").toString()+"%";
					   }
				   }
				   map2.put("name", map.get("name"));
				   map2.put("info", text);
				   map2.put("time", map.get("time"));
				   if(type==0){
					   list2.add(map2);
				   }else{
					   if(map.get("typeId").toString().equals("1")){
						   list2.add(map2);
					   }
				   }
			   }
		}
		session.close();
		return list2;
	}
	/**
	 * 更新交换中心告警标志
	 * @return
	 */
	public static void updateAlarmStatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		
		try {
			mapper.updateAlarmStatus();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 解除报警 
	 * @return
	 */
	public static void offAlarmStatus(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		
		try {
			mapper.offAlarmStatus();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 声音告警数目
	 */
	public static int alarmNum(){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ServerStatusMapper mapper = session.getMapper(ServerStatusMapper.class);
		int count=0;
		try {
			count=mapper.alarmNum();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
