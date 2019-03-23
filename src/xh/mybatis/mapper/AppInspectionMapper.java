package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.InspectionDispatchBean;
import xh.mybatis.bean.InspectionMbsBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionNetBean;
import xh.mybatis.bean.InspectionRoomBean;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.bean.InspectionStarBean;
import xh.mybatis.bean.InspectionVerticalBean;

public interface AppInspectionMapper {
	
	public List<Map<String,Object>> repeater_list()throws Exception;
	
	public List<Map<String,Object>> room_list()throws Exception;
	
	public List<Map<String,Object>> portable_list()throws Exception;
	
	/*<!--查询800M移动基站巡检表-->*/
	public List<InspectionMbsBean> mbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--查询800M移动基站巡检表-->*/
	public int mbsinfoCount()throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public List<InspectionSbsBean> sbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public int sbsinfoCount(Map<String,Object> map)throws Exception;
	

	/*<!--网管巡检表-->*/
	public List<Map<String,Object>> netinfo(Map<String,Object> map)throws Exception;
	
	/*<!--网管巡检表-->*/
	public int netinfoCount(Map<String,Object> map)throws Exception;
	
	/*<!--直放站巡检表-->*/
	public List<InspectionVerticalBean> verticalinfo(Map<String,Object> map)throws Exception;
	
	/*<!--直放站巡检表-->*/
	public int verticalinfoCount(Map<String,Object> map)throws Exception;
	
	/*<!--室内覆盖巡检表-->*/
	public List<InspectionRoomBean> roominfo(Map<String,Object> map)throws Exception;
	
	/*<!--室内覆盖巡检表-->*/
	public int roominfoCount(Map<String,Object> map)throws Exception;
	
	/*<!--卫星通信车载便携站巡检表-->*/
	public List<InspectionStarBean> starinfo(Map<String,Object> map)throws Exception;
	
	/*<!--卫星通信车载便携站巡检表-->*/
	public int starinfoCount(Map<String,Object> map)throws Exception;
	
	/*<!--调度台巡检表-->*/
	public List<Map<String,Object>> dispatchinfo(Map<String,Object> map)throws Exception;
	
	/*<!--调度台巡检表-->*/
	public int dispatchinfoCount(Map<String,Object> map)throws Exception;
	
	/*<!--交传中心巡检表-->*/
	public List<InspectionMscBean> mscinfo(Map<String,Object> map)throws Exception;
	
	/*<!--交互按中心巡检表-->*/
	public int mscCount(Map<String,Object> map)throws Exception;
	
	public int dispatch_add(InspectionDispatchBean bean)throws Exception;
	
	public int dispatch_edit(InspectionDispatchBean bean)throws Exception;
	
	public int net_add(InspectionNetBean bean)throws Exception;
	
	public int net_edit(InspectionNetBean bean)throws Exception;
	
	public int vertical_add(InspectionVerticalBean bean)throws Exception;
	
	public int vertical_edit(InspectionVerticalBean bean)throws Exception;
	
	public int room_add(InspectionRoomBean bean)throws Exception;
	
	public int room_edit(InspectionRoomBean bean)throws Exception;
	
	public int star_add(InspectionStarBean bean)throws Exception;
	
	public int star_edit(InspectionStarBean bean)throws Exception;
	
	public int msc_add(InspectionMscBean bean)throws Exception;
	
	public int msc_edit(InspectionMscBean bean)throws Exception;
	
	public int mbs_add(InspectionMbsBean bean)throws Exception;
	
	public int mbs_edit(InspectionMbsBean bean)throws Exception;
	
	public int sbs_add(InspectionSbsBean bean)throws Exception;
	
	public int sbs_edit(InspectionSbsBean bean)throws Exception;
	
	public int del_sbs(int id)throws Exception;
	
	public int del_net(List<String> list)throws Exception;
	
	public int del_msc(List<String> list)throws Exception;
	
	public int del_dispatch(List<String> list)throws Exception;
	
	public int del_vertical(List<String> list)throws Exception;
	
	public int del_room(List<String> list)throws Exception;
	
	public int del_star(List<String> list)throws Exception;

}
