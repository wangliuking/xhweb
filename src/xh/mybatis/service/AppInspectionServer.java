package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.InspectionDispatchBean;
import xh.mybatis.bean.InspectionMbsBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionNetBean;
import xh.mybatis.bean.InspectionRoomBean;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.bean.InspectionStarBean;
import xh.mybatis.bean.InspectionVerticalBean;
import xh.mybatis.mapper.AppInspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class AppInspectionServer {
	
	/*<!--查询800M移动基站巡检表-->*/
	public static List<InspectionMbsBean> mbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionMbsBean> list=new ArrayList<InspectionMbsBean>();
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
	/*<!--自建基站巡检表-->*/
	public static List<InspectionSbsBean> sbsinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionSbsBean> list=new ArrayList<InspectionSbsBean>();
		try {
			list = mapper.sbsinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--自建基站巡检表总数-->*/
	public static int sbsinfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbsinfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	/*<!--网管巡检表-->*/
	public static List<Map<String, Object>> netinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.netinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--网管巡检表总数-->*/
	public static int netinfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.netinfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/*<!--直放站巡检表-->*/
	public static List<InspectionVerticalBean> verticalinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionVerticalBean> list=new ArrayList<InspectionVerticalBean>();
		try {
			list = mapper.verticalinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--直放站巡检表总数-->*/
	public static int verticalinfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.verticalinfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/*<!--室内覆盖巡检表-->*/
	public static List<InspectionRoomBean> roominfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionRoomBean> list=new ArrayList<InspectionRoomBean>();
		try {
			list = mapper.roominfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--室内覆盖巡检表总数-->*/
	public static int roominfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.roominfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static List<InspectionStarBean> starinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionStarBean> list=new ArrayList<InspectionStarBean>();
		try {
			list = mapper.starinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--卫星通信车载便携站巡检表总数-->*/
	public static int starinfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.starinfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	/*<!--调度台巡检表-->*/
	public static List<Map<String, Object>> dispatchinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list = mapper.dispatchinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--调度台巡检表总数-->*/
	public static int dispatchinfoCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.dispatchinfoCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	/*<!--交换中心巡检表-->*/
	public static List<InspectionMscBean> mscinfo(Map<String, Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		List<InspectionMscBean> list=new ArrayList<InspectionMscBean>();
		try {
			list = mapper.mscinfo(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return list;
	}
	/*<!--交换中心巡检表总数-->*/
	public static int mscCount(Map<String,Object> map){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mscCount(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int dispatch_add(InspectionDispatchBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.dispatch_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int dispatch_edit(InspectionDispatchBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.dispatch_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int net_add(InspectionNetBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.net_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int net_edit(InspectionNetBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.net_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int msc_add(InspectionMscBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.msc_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int msc_edit(InspectionMscBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.msc_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int mbs_add(InspectionMbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbs_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int mbs_edit(InspectionMbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.mbs_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int sbs_add(InspectionSbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbs_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int sbs_edit(InspectionSbsBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.sbs_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int vertical_add(InspectionVerticalBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.vertical_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int vertical_edit(InspectionVerticalBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.vertical_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int room_add(InspectionRoomBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.room_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int room_edit(InspectionRoomBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.room_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int star_add(InspectionStarBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.star_add(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int star_edit(InspectionStarBean bean){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.star_edit(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	
	public static int del_sbs(int id){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_sbs(id);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int del_net(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_net(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int del_msc(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_msc(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int del_dispatch(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_dispatch(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int del_vertical(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_vertical(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	public static int del_room(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_room(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
	
	public static int del_star(List<String> list){
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AppInspectionMapper mapper = session.getMapper(AppInspectionMapper.class);
		int count=0;
		try {
			count = mapper.del_star(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		return count;
	}
}
