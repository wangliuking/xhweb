package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.InspectionMbsBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionSbsBean;

public interface AppInspectionMapper {
	
	/*<!--查询800M移动基站巡检表-->*/
	public List<InspectionMbsBean> mbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--查询800M移动基站巡检表-->*/
	public int mbsinfoCount()throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public List<InspectionSbsBean> sbsinfo(Map<String,Object> map)throws Exception;
	
	/*<!--自建基站巡检表-->*/
	public int sbsinfoCount()throws Exception;
	

	/*<!--网管巡检表-->*/
	public List<Map<String,Object>> netinfo(Map<String,Object> map)throws Exception;
	
	/*<!--网管巡检表-->*/
	public int netinfoCount()throws Exception;
	
	/*<!--调度台巡检表-->*/
	public List<Map<String,Object>> dispatchinfo(Map<String,Object> map)throws Exception;
	
	/*<!--调度台巡检表-->*/
	public int dispatchinfoCount()throws Exception;
	
	/*<!--交传中心巡检表-->*/
	public List<InspectionMscBean> mscinfo(Map<String,Object> map)throws Exception;
	
	/*<!--交互按中心巡检表-->*/
	public int mscCount()throws Exception;
	
	public int msc_add(InspectionMscBean bean)throws Exception;
	
	public int msc_edit(InspectionMscBean bean)throws Exception;
	
	public int mbs_add(InspectionMbsBean bean)throws Exception;
	
	public int mbs_edit(InspectionMbsBean bean)throws Exception;
	
	public int sbs_add(InspectionSbsBean bean)throws Exception;
	
	public int sbs_edit(InspectionSbsBean bean)throws Exception;

}
