package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.ChartReportDispatch;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscCallBean;
import xh.mybatis.bean.EastMscCallDetailBean;
import xh.mybatis.bean.EastVpnCallBean;

public interface ReportDayMapper {
	

	public List<Map<String,Object>> chart_server()throws Exception;
	
	public List<ChartReportDispatch> chart_dispatch()throws Exception;
	
	public List<Map<String,Object>> chart_alarm_now()throws Exception;
	
	public List<Map<String,Object>> chart_alarm_his(Map<String,Object> map)throws Exception;
	
	
	
	
	
/*	public List<EastBsCallDataBean>chart_bs_call(Map<String, Object> map)throws Exception;
	public List<EastVpnCallBean>chart_vpn_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_level_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_area_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_zone_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_zone_top10_call(Map<String, Object> map)throws Exception;
	
	
	
	public List<EastBsCallDataBean>chart_bs_call_top10(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_userreg_top10(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_queue_top10(Map<String, Object> map)throws Exception;
	public List<EastVpnCallBean>chart_vpn_call_top10(Map<String, Object> map)throws Exception;*/
	
	
	
	
	

}
