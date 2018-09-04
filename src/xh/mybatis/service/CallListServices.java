package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.CallListBean;
import xh.mybatis.mapper.CallListMapper;
import xh.mybatis.mapper.WebUserMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class CallListServices {
	/**
	 * 查询呼叫列表
	 * @param root
	 * @return
	 */
	public static ArrayList<Map<String,Object>> selectCallList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		ArrayList<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		Map<String,Object> map2=new HashMap<String, Object>();
		map2.put("type", -1);
		map2.put("level", -1);
		map2.put("start", 0);
		map2.put("limit", 1000);
		List<BsstationBean> bslist=BsstationService.bsInfo(map2);
		
		
	
		Map<String,Object> map3=new HashMap<String, Object>();
		for (BsstationBean bsstationBean : bslist) {
			
			if(FunUtil.StringToInt(bsstationBean.getBsId())>200 &&
					FunUtil.StringToInt(bsstationBean.getBsId())<2000){
				map3.put("id-"+(FunUtil.StringToInt(bsstationBean.getBsId())+1000), bsstationBean.getName());
			}else{
				map3.put("id-"+FunUtil.StringToInt(bsstationBean.getBsId()), bsstationBean.getName());
			}
		}
		
		
		try {
			list=mapper.selectCallList(map);
			for (int i=0;i<list.size();i++) {
				Map<String,Object> map4=list.get(i);
				map4.put("name", map3.get("id-"+map4.get("Call_TS_Id")));
				list2.add(map4);
			}
			sqlSession.close();		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list2;
	}
	/**
	 * 通话总数
	 * @return
	 * @throws Exception
	 */
	public static int CallListCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		int count=0;
		try {
			count=mapper.CallListCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 话务统计
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> callChartt(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		List<Map<String,Object>> resultMap=new ArrayList<Map<String,Object>>();
		try {
			resultMap=mapper.callChart(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}
	/**
	 * 话务统计
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> chart_call_hour_now(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		CallListMapper mapper=sqlSession.getMapper(CallListMapper.class);
		List<Map<String,Object>> resultMap=new ArrayList<Map<String,Object>>();
		try {
			resultMap=mapper.chart_call_hour_now();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

}
