package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.ChartReportDispatch;
import xh.mybatis.bean.ChartReportImpBsBean;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscCallBean;
import xh.mybatis.bean.EastMscCallDetailBean;
import xh.mybatis.bean.EastVpnCallBean;

public interface ReportDayMapper {
	

	public List<Map<String,Object>> chart_server()throws Exception;
	
	public List<ChartReportDispatch> chart_dispatch()throws Exception;
	
	public List<Map<String,Object>> chart_alarm_now()throws Exception;
	
	public List<Map<String,Object>> chart_alarm_his(Map<String,Object> map)throws Exception;
	
	public List<ChartReportImpBsBean> chart_bs_imp_call(String time)throws Exception;
	
	public List<Map<String,Object>> now_week_gpsnumber(String day)throws Exception;
	
	public List<Map<String,Object>> other_device_status()throws Exception;
	
	public Map<String,Object> operations_question(String time)throws Exception;
	
	public Map<String,Object> now_gpsunit_status()throws Exception;
	
	
	

	
	
	
	
	

}
