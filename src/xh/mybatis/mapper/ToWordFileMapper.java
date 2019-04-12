package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastVpnCallBean;

public interface ToWordFileMapper {
	public Map<String,Object> system_call(Map<String,Object> map) throws Exception;
	
	public  Map<String,Object> system_bs_call(String time) throws Exception;
	
	public List<Map<String,Object>> system_call_year(String year) throws Exception;
	public List<Map<String,Object>> system_gps_year(String year) throws Exception;
	
	public List<EastBsCallDataBean> system_call_level(Map<String,Object> map) throws Exception;
	
	public List<EastBsCallDataBean> system_call_year_level(Map<String,Object> map) throws Exception;
	
	public List<EastBsCallDataBean> system_call_area(Map<String,Object> map) throws Exception;
	
	public List<EastBsCallDataBean> system_call_year_area(Map<String,Object> map) throws Exception;
	
	public List<EastBsCallDataBean> system_call_zone_top10(String time) throws Exception;
	
	public List<EastBsCallDataBean> system_call_bs_top10(String time) throws Exception;
	
	public List<EastBsCallDataBean> system_call_queue_top10(String time) throws Exception;
	
	public List<EastVpnCallBean> system_call_vpn_top10(String time) throws Exception;
	
	public List<EastBsCallDataBean> chart_bs_userreg_top10(String time) throws Exception;
	
	
	public Map<String,Object> xj_bs_all_type_num(int period) throws Exception;
	
	public Map<String,Object> xj_bs_num(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> fault_num(Map<String,Object> map) throws Exception;
	
	

}
